package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.RecordDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.Record;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.util.DBUtil;

/**
 * 参与记录实现类
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public class RecordDaoImpl implements RecordDao {

	@Override
	public List<Record> getRecordsByActivityId(int activityId) {
		List<Record> recordList = new ArrayList<Record>();
		List<Duty> duties = new ArrayList<Duty>();
		duties = new DutyDaoImpl().getDutyList(activityId);
		System.out.println("duty size:" + duties.size());
		for (Duty duty : duties) {
			recordList.addAll(getRecordsByDutyId(duty.getDutyId()));
		}
		return recordList;
	}

	@Override
	public List<Record> getRecordsByDutyId(int dutyId) {
		List<Record> recordList = new ArrayList<Record>();
		String sql = "select * from Record where dutyId=? and stateId!=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, dutyId);
			statement.setInt(2, Record.RECORD_CANCLED);// 被取消参与的记录不会被显示
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				recordList.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return recordList;
	}

	@Override
	public Record getRecordByRecordId(int recordId) {
		Record record = new Record();
		String sql = "select * from Record where recordId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, recordId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				record = getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return record;
	}

	@Override
	public Record getRecord(int uid, int activityId) {
		System.out.println("uid:" + uid + ",activityId:" + activityId);
		Record record = null;
		List<Duty> duties = new ArrayList<Duty>();
		duties = new DutyDaoImpl().getDutyList(activityId);
		System.out.println("dutySize==" + duties.size());
		for (Duty duty : duties) {
			String sql = "select * from Record where userId=? and dutyId=? ";
			DBUtil util = new DBUtil();
			Connection connection = util.openConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, uid);
				statement.setInt(2, duty.getDutyId());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					record = getData(rs);
					if (record != null) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				util.closeConnection(connection);
			}
		}
		return record;
	}

	private Record getData(ResultSet rs) throws SQLException {
		Record record = new Record();
		record.setRecordId(rs.getInt("recordId"));
		record.setDutyId(rs.getInt("dutyId"));
		record.setUid(rs.getInt("userId"));
		record.setStateId(rs.getInt("stateId"));
		record.setInTime(rs.getString("inTime"));
		record.setRemark(rs.getString("remark"));
		return record;
	}

	@Override
	public boolean userApplyDuty(int uid, int dutyId) {
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		// INSERT INTO `record` VALUES ('100000', '10000', '100000', '2014-07-26
		// 09:39:22', '1', null);
		DutyDao dutyDao = new DutyDaoImpl();
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();
		MyActivity myActivity = new MyActivity();
		UserDao userDao = new UserDaoImpl();
		String sql = "insert into Record values(NULL, " + uid + ", " + dutyId + ", now(), '1', null)";
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

		myActivity = infoDao.getActivityInfo(dutyDao.getDuty(dutyId).getActivityId());
		if (sqlResult > 0) {// 更新职责和活动目前人数
			dutyDao.updateDutyNowNum(dutyId, 1);
			infoDao.updateActivityNowNum(myActivity.getActivityId(), 1);
			System.out.println("update nowNum ok!");
		}

		// 产生职责加入推送消息
		messagePush = new MessagePush(myActivity.getPostUserId(), "用户“" + userDao.getUser(uid).getName() + "”加入了你创建的活动："
				+ myActivity.getPostName() + "，赶快去看看吧！", MessagePush.CATEGORY_USER);
		messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
		pushDao.createMessage(messagePush);

		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean cancelUserDuty(int[] cancelList) {
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		DutyDao dutyDao = new DutyDaoImpl();
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();
		MyActivity myActivity = new MyActivity();
		UserDao userDao = new UserDaoImpl();
		Record record = new Record();
		// System.out.println("length="+cancelList.length);
		DBUtil util = new DBUtil();
		int sqlResult = 0;
		String sql = null;
		PreparedStatement statement;
		Connection connection = util.openConnection();
		try {
			for (int i = 0; i < cancelList.length; i++) {
				record = getRecordByRecordId(cancelList[i]);
				sql = "update Record set stateId=2 where recordId=" + record.getRecordId();
				statement = connection.prepareStatement(sql);
				System.out.println("Now cancel recordID:" + cancelList[i]);
				sqlResult += statement.executeUpdate(sql);

				// 产生职责被取消加入推送消息
				myActivity = infoDao.getActivityInfo(dutyDao.getDuty(record.getDutyId()).getActivityId());
				messagePush = new MessagePush(record.getUid(), "活动“" + myActivity.getPostName() + "”的创建者“"
						+ userDao.getUser(myActivity.getPostUserId()).getName() + "”拒绝了你的加入申请。", MessagePush.CATEGORY_USER);
				messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
				pushDao.createMessage(messagePush);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}

		if (sqlResult > 0) {// 更新职责和活动目前人数
			dutyDao.updateDutyNowNum(getRecordByRecordId(cancelList[0]).getDutyId(), -sqlResult);
			new ActivityInfoDaoImpl().updateActivityNowNum(dutyDao.getDuty(getRecordByRecordId(cancelList[0]).getDutyId())
					.getActivityId(), -sqlResult);
			System.out.println("update nowNum ok!");
		}

		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean updateStateId(int activityId, int stateId) {
		int sqlResult = 0;
		List<Record> recordList = getRecordsByActivityId(activityId);
		DBUtil util = new DBUtil();
		String sql = null;
		PreparedStatement statement;
		Connection connection = util.openConnection();
		try {
			for (Record record : recordList) {
				sql = "update Record  set stateId=" + stateId + " where recordId=" + record.getRecordId();
				statement = connection.prepareStatement(sql);
				sqlResult += statement.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return sqlResult > 0 ? true : false;
	}

	@Override
	public List<Record> getRecordByUid(int uid) {
		List<Record> records = new ArrayList<Record>();
		System.out.println("request uid:" + uid);
		String sql = "select * from Record where userId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, uid);
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

	@Override
	public boolean evaluateActivity(Evaluate evaluate) {
		UserDao userDao = new UserDaoImpl();
		User user = new User();
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		MyActivity myActivity = new MyActivity();
		ActivityInfoDao infoDao = new ActivityInfoDaoImpl();

		int sqlResult = 0;
		Record record = getRecord(evaluate.getUid(), evaluate.getActivityId());
		if (record == null) {
			System.err.println("interal error:not found record to evaluate!");
			return false;
		}
		// 评论内容
		String sql = "update Record set stateId=5,remark='" + evaluate.getContent() + "' where recordId=" + record.getRecordId();
		System.out.println("sql:" + sql);
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("update evaluate content ok!");
		// 评论分数
		userDao.updateUserCredibility(evaluate);

		// 产生评价推送消息：用户评价创建者
		myActivity = infoDao.getActivityInfo(evaluate.getActivityId());
		user = userDao.getUser(evaluate.getUid());
		messagePush = new MessagePush(myActivity.getPostUserId(), "用户“" + user.getName() + "”评价了你发起的活动“"
				+ myActivity.getPostName() + "”，他给这个活动的评分为：" + evaluate.getCredibility(), MessagePush.CATEGORY_CREDIBILITY);
		messagePush.setRemark(evaluate.getContent());
		messagePush.setPushUserName(user.getName());
		pushDao.createMessage(messagePush);

		return sqlResult > 0 ? true : false;
	}
}
