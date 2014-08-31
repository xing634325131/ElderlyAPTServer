package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.ChatRecordDao;
import com.kingtime.elderlyapt.entity.ChatRecord;
import com.kingtime.elderlyapt.util.DBUtil;

public class ChatRecordDaoImpl implements ChatRecordDao {

	@Override
	public List<ChatRecord> getChatRecords(int activityId) {
		List<ChatRecord> records = new ArrayList<ChatRecord>();
		String sql = "select * from ChatRecord where activityId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, activityId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				records.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return records;
	}

	private ChatRecord getData(ResultSet rs) throws SQLException {
		ChatRecord record = new ChatRecord();
		record.setUid(rs.getInt("userId"));
		record.setActivityId(rs.getInt("activityId"));
		record.setChatId(rs.getInt("chatId"));
		record.setChatTime(rs.getString("chatTime"));
		record.setContent(rs.getString("content"));
		record.setRemark(rs.getString("remark"));
		return record;
	}

	@Override
	public boolean createChatRecord(ChatRecord record) {
		int result = 0;
		String sql = "insert into ChatRecord values(NULL," + record.getActivityId() + "," + record.getUid() + ",'"
				+ record.getContent() + "',now(),null)";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			result = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("New chatrecord!");
		return result > 0 ? true : false;
	}

}
