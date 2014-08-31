package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.util.DBUtil;

/**
 * 推送消息接口实现类
 * 
 * @author xp
 * 
 * @created 2014-8-22
 */
public class MessagePushDaoImpl implements MessagePushDao {

	@Override
	public boolean createMessage(MessagePush messagePush) {
		String sql = "insert into MessagePush values(NULL," + messagePush.getToPushUserId() + ",'"
				+ messagePush.getPushUserName() + "','" + messagePush.getPushContent() + "',now()," + messagePush.getIsPushed()
				+ "," + messagePush.getPushCategoryId() + ",'" + messagePush.getRemark() + "')";
		System.out.println(sql);
		DBUtil util = new DBUtil();
		int sqlResult = 0;
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean createMessage(List<MessagePush> messagePushs) {
		boolean result = false;
		for (MessagePush messagePush : messagePushs) {
			result = createMessage(messagePush);
		}
		return result;
	}

	private MessagePush getData(ResultSet rs) throws SQLException {
		MessagePush messagePush = new MessagePush();
		messagePush.setCreateTime(rs.getString("createTime"));
		messagePush.setIsPushed(rs.getInt("isPushed"));
		messagePush.setMessageId(rs.getInt("messageId"));
		messagePush.setPushCategory(rs.getInt("pushCategoryId"));
		messagePush.setPushContent(rs.getString("pushContent"));
		messagePush.setPushUserName(rs.getString("pushName"));
		messagePush.setRemark(rs.getString("remark"));
		messagePush.setToPushUserId(rs.getInt("toPushUserId"));
		return messagePush;
	}

	@Override
	public List<MessagePush> getMessages(int uid) {
		List<MessagePush> messagePushs = new ArrayList<MessagePush>();
		System.out.println("request uid:" + uid);
		String sql = "select * from MessagePush where toPushUserId=? and isPushed=0";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, uid);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				messagePushs.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}

		// 获取推送后，置推送状态为已推送
		updateToPushed(uid);

		return messagePushs;
	}

	@Override
	public List<MessagePush> getMessages(int uid, int messageCategoryId) {
		List<MessagePush> messagePushs = new ArrayList<MessagePush>();
		System.out.println("request uid:" + uid + ",request categoryId:" + messageCategoryId);
		String sql = "select * from MessagePush where toPushUserId=? and pushCategoryId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, uid);
			statement.setInt(2, messageCategoryId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				messagePushs.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}

		// 获取推送后，置推送状态为已推送
		updateToPushed(uid, messageCategoryId);

		return messagePushs;
	}

	@Override
	public boolean updateToPushed(int uid) {
		String sql = "update MessagePush set isPushed=1 where toPushUserId=" + uid;
		System.out.println(sql);
		DBUtil util = new DBUtil();
		int sqlResult = 0;
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("Message have pushed！Uid:" + uid);
		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean updateToPushed(int uid, int messageCategoryId) {
		String sql = "update MessagePush set isPushed=1 where pushCategoryId=" + messageCategoryId + " and toPushUserId=" + uid;
		System.out.println(sql);
		DBUtil util = new DBUtil();
		int sqlResult = 0;
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("Message have pushed！Uid:" + uid);
		return sqlResult == 0 ? false : true;
	}

}
