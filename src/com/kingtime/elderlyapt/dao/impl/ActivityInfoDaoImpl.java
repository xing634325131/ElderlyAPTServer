package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.dao.ImageDao;
import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.RecordDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.entity.MyActivity;
import com.kingtime.elderlyapt.entity.Record;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.service.ActivityRecommendService;
import com.kingtime.elderlyapt.service.impl.ActivityRecommendServiceImpl;
import com.kingtime.elderlyapt.util.DBUtil;

/**
 * 活动信息实现类
 * 
 * @author xp
 * 
 * @created 2014-8-6
 */
public class ActivityInfoDaoImpl implements ActivityInfoDao {

	@Override
	public List<MyActivity> getActivityInfoList(String requestCategory) {
		List<MyActivity> activities = new ArrayList<MyActivity>();
		String sql = "select * from PostActivity where categoryId=? and stateId > 1";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, MyActivity.getCategoryId(requestCategory));
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activities.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return activities;
	}

	@Override
	public MyActivity getActivityInfo(int activityId) {
		MyActivity activity = new MyActivity();
		String sql = "select * from PostActivity where activityId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, activityId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activity = getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return activity;
	}

	private MyActivity getData(ResultSet rs) throws SQLException {
		MyActivity activity = new MyActivity();
		activity.setActivityId(rs.getInt("activityId"));
		activity.setAddress(rs.getString("address"));
		activity.setBeginTime(rs.getString("beginTime"));
		activity.setCategoryId(rs.getInt("categoryId"));
		activity.setCloseTime(rs.getString("closeTime"));
		activity.setContent(rs.getString("content"));
		activity.setEndTime(rs.getString("endTime"));
		activity.setLocationId(rs.getInt("locationId"));
		activity.setNeedNum(rs.getInt("needNum"));
		activity.setNowNum(rs.getInt("nowNum"));
		activity.setPostName(rs.getString("postName"));
		activity.setPostTime(rs.getString("postTime"));
		activity.setPostUserId(rs.getInt("userId"));
		activity.setRemark(rs.getString("remark"));
		activity.setStateId(rs.getInt("stateId"));
		activity.setSumIntegral(rs.getInt("sumIntegral"));
		return activity;
	}

	@Override
	public MyActivity createActivity(MyActivity myActivity, List<Duty> duties) {
		MyActivity newActivity = new MyActivity();
		UserDao userDao = new UserDaoImpl();
		MessagePushDao pushDao = new MessagePushDaoImpl();
		// INSERT INTO `postactivity` VALUES ('100000', '10002', '6', '32',
		// '志愿打扫卫生', '南华大学青志队将到南华社区打扫卫生，请参与。', '2014-07-26 09:35:20',
		// '2014-08-01 09:00:00', '2014-08-01 18:00:00', '2014-07-29 09:00:00',
		// '10', '1', '1', '南华社区', '100', null);
		String sql = "insert into PostActivity values(NULL," + myActivity.getPostUserId() + "," + myActivity.getCategoryId()
				+ "," + myActivity.getLocationId() + ",'" + myActivity.getPostName() + "','" + myActivity.getContent()
				+ "',now(),'" + myActivity.getBeginTime() + "','" + myActivity.getEndTime() + "','" + myActivity.getCloseTime()
				+ "'," + myActivity.getNeedNum() + ",0,1,'" + myActivity.getAddress() + "'," + myActivity.getSumIntegral()
				+ ",null)";
		int sqlResult = 0;
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
		if (sqlResult == 0) {
			return null;
		}
		newActivity = getActivityByContent(myActivity.getContent());
		System.out.println("New Activity ID:" + newActivity.getActivityId());
		ImageDao imageDao = new ImageDaoImpl();
		DutyDao dutyDao = new DutyDaoImpl();
		imageDao.updateUploadImage(newActivity.getActivityId(), myActivity.getMainPic());// 更新图片对应关系
		for (int i = 0; i < duties.size(); i++) {// 更新duties的活动编号
			duties.get(i).setActivityId(newActivity.getActivityId());
			dutyDao.createDuty(duties.get(i));// 创建职责
		}

		// 请求审核
		MessagePush messagePush = new MessagePush(10006, "用户“" + userDao.getUser(newActivity.getPostUserId()).getName()
				+ "”发布了一个新活动：" + newActivity.getPostName() + "，快去审核吧！", MessagePush.CATEGORY_VERIFY);
		messagePush.setRemark(String.valueOf(newActivity.getActivityId()));
		pushDao.createMessage(messagePush);
		// 新活动推荐
		ActivityRecommendService recommendService = new ActivityRecommendServiceImpl();
		recommendService.formRecommend(userDao.getAllUser(), newActivity);

		return newActivity;
	}

	private MyActivity getActivityByContent(String content) {
		MyActivity activity = new MyActivity();
		String sql = "select * from PostActivity where content=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, content);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activity = getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return activity;
	}

	@Override
	public boolean updateActivityState(int activityId, int stateId) {
		UserDao userDao = new UserDaoImpl();
		RecordDao recordDao = new RecordDaoImpl();
		DutyDao dutyDao = new DutyDaoImpl();
		MessagePushDao pushDao = new MessagePushDaoImpl();
		List<Record> records = recordDao.getRecordsByActivityId(activityId);
		MyActivity myActivity = getActivityInfo(activityId);

		System.out.println("updateActivityState-->");
		if (stateId == MyActivity.ACTIVITY_END) {
			// 当活动结束后
			// 1.修改用户参与状态：未评价状态
			recordDao.updateStateId(activityId, Record.RECORD_NOT_EVALUATED);
			System.out.println("update record stateId to RECORD_NOT_EVALUATED ok!");

			// 2.积分更新：创建者和参与者
			for (Record record : records) {// 更新参与者
				Duty duty = dutyDao.getDuty(record.getDutyId());
				userDao.updateUserIntegral(record.getUid(), duty.getDutyIntegral());
			}
			// 更新创建者，积分与参与者正负相反
			userDao.updateUserIntegral(myActivity.getPostUserId(), -myActivity.getSumIntegral());
			System.out.println("State changed,update all integral ok!");
		}

		int result = 0;
		String sql = "update PostActivity set stateId=" + stateId + " where activityId=" + activityId;
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

		// 活动状态更新消息推送
		MessagePush messagePush = null;
		switch (stateId) {
		case MyActivity.ACTIVITY_APPLY:// 通过审核
			messagePush = new MessagePush(myActivity.getPostUserId(), "你发起的活动“" + myActivity.getPostName() + "”通过了社区管理员的审核。",
					MessagePush.CATEGORY_STATE);
			messagePush.setPushUserName("社区管理员");
			messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
			pushDao.createMessage(messagePush);
			break;
		case MyActivity.ACTIVITY_CLOSED:
		case MyActivity.ACTIVITY_RUNNING:
		case MyActivity.ACTIVITY_WILL_START:
			for (Record record : records) {
				messagePush = new MessagePush(record.getUid(), "你参加的活动“" + myActivity.getPostName() + "”活动状态发生了变化，当前活动状态："
						+ MyActivity.ACTIVITY_STATE[stateId - 1], MessagePush.CATEGORY_STATE);
				messagePush.setPushUserName(userDao.getUser(myActivity.getPostUserId()).getName());
				messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
				pushDao.createMessage(messagePush);
			}
			break;
		case MyActivity.ACTIVITY_END:
			messagePush = new MessagePush(myActivity.getPostUserId(), "你发起的活动“" + myActivity.getPostName() + "”已经圆满的结束了，赶快去评论一下吧！",
					MessagePush.CATEGORY_STATE);
			messagePush.setPushUserName("社区管理员");
			messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
			pushDao.createMessage(messagePush);
			for (Record record : records) {
				messagePush = new MessagePush(record.getUid(), "你参加的活动“" + myActivity.getPostName() + "”已经圆满的结束了，赶快去评论一下吧！",
						MessagePush.CATEGORY_STATE);
				messagePush.setPushUserName(userDao.getUser(myActivity.getPostUserId()).getName());
				messagePush.setRemark(String.valueOf(myActivity.getActivityId()));
				pushDao.createMessage(messagePush);
			}
			break;
		default:
			break;
		}

		return result > 0 ? true : false;
	}

	@Override
	public boolean updateActivityNowNum(int activityId, int num) {
		int result = 0;
		int newNum = getActivityInfo(activityId).getNowNum() + num;
		System.out.println("update activity now num-->oldnum:" + getActivityInfo(activityId).getNowNum() + ",addnum:" + num);
		String sql = "update PostActivity set nowNum=" + newNum + " where activityId=" + activityId;
		System.out.println(sql);
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
		return result > 0 ? true : false;
	}

	@Override
	public List<MyActivity> getActivitiesByVerify() {
		List<MyActivity> activities = new ArrayList<MyActivity>();
		String sql = "select * from PostActivity where stateId=? and categoryId<11";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, MyActivity.ACTIVITY_REVIEW);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activities.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return activities;
	}

	@Override
	public List<MyActivity> getPostActivity(int uid) {
		// TODO problem
		List<MyActivity> activities = new ArrayList<MyActivity>();
		String sql = "select * from PostActivity where userId=? and categoryId<10";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, uid);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activities.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return activities;
	}

	@Override
	public List<MyActivity> getNowJoinedActivity(int uid) {
		List<MyActivity> activities = getJoinedActivity(uid);
		// TODO problem
		for (int i = 0; i < activities.size(); i++) {
			System.out.println(i + "---state Id-------->" + activities.get(i).getStateId());
			if (activities.get(i).getStateId() == MyActivity.ACTIVITY_END) {
				activities.remove(i--);// 剔除已经结束的活动
			}
		}
		return activities;
	}

	@Override
	public List<MyActivity> getEndedActivity(int uid) {
		List<MyActivity> activities = getJoinedActivity(uid);
		for (int i = 0; i < activities.size(); i++) {

			System.out.println("activity size:" + activities.size());
			// TODO remove recommend category
			System.out.println(i + "---state Id-------->" + activities.get(i).getStateId());
			if (activities.get(i).getStateId() < MyActivity.ACTIVITY_END) {
				activities.remove(i--);// 只要已经结束的活动
			}
		}
		return activities;
	}

	private List<MyActivity> getJoinedActivity(int uid) {
		List<MyActivity> activities = new ArrayList<MyActivity>();

		// 自己发布的活动优先显示
		activities.addAll(getPostActivity(uid));
		System.out.println("post--activitySize:" + activities.size());

		List<Record> records = new RecordDaoImpl().getRecordByUid(uid);
		System.out.println("join--recordsNum:" + records.size());
		for (Record record : records) {
			MyActivity myActivity = new MyActivity();
			int dutyId = record.getDutyId();
			int activityId = new DutyDaoImpl().getDuty(dutyId).getActivityId();
			myActivity = getActivityInfo(activityId);
			activities.add(myActivity);
		}
		System.out.println("activity size:" + activities.size());
		return activities;
	}

	@Override
	public boolean getAppraiseState(int activityId) {
		String remark = getActivityInfo(activityId).getRemark();
		if (remark == null || remark.equals("0")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean appraiseJoinedUser(List<Evaluate> evaluates, int activityId) {
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		MyActivity myActivity = getActivityInfo(activityId);
		UserDao userDao = new UserDaoImpl();
		User user = userDao.getUser(myActivity.getPostUserId());

		// 更新用户信誉度
		for (Evaluate evaluate : evaluates) {
			new UserDaoImpl().updateUserCredibilityByAppraise(evaluate);

			// 产生评价推送消息：创建者评论参与用户
			messagePush = new MessagePush(evaluate.getUid(), "你参加的活动“" + myActivity.getPostName() + "”得到了活动创建者“" + user.getName()
					+ "”的评价，他给你的评分为：" + evaluate.getCredibility(), MessagePush.CATEGORY_CREDIBILITY);
			messagePush.setPushUserName(user.getName());
			pushDao.createMessage(messagePush);
		}

		// 更新评价状态
		int sqlResult = 0;
		String sql = "update PostActivity set remark='1' where activityId=" + activityId;
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
			System.out.println("update activity appraise ok! activityId" + activityId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}

		// TODO

		return sqlResult > 0 ? true : false;
	}

	@Override
	public List<MyActivity> searchActivity(String searchString) {
		List<MyActivity> activities = new ArrayList<MyActivity>();
		String sql = "select * from PostActivity where ";
		String[] searchs = searchString.split(" ");// 简单空格分词
		for (int i = 0; i < searchs.length; i++) {// 模糊匹配查询字符串
			if (i == 0) {
				sql += "postName like '%" + searchs[i] + "%' or content like '%" + searchs[i] + "%' ";
			} else {
				sql += "or postName like '%" + searchs[i] + "%' or content like '%" + searchs[i] + "%' ";
			}
		}
		System.out.println(sql);

		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activities.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("search activity size:" + activities.size());
		return activities;
	}

	@Override
	public List<MyActivity> getAllNeedTaskActivity(int stateId) {
		List<MyActivity> activities = new ArrayList<MyActivity>();
		String sql = "select * from PostActivity where stateId =?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, stateId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				activities.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("To be task check activity satteId:" + stateId + ", size：" + activities.size());
		return activities;
	}
}
