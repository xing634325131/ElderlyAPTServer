package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.ActivityInfoDao;
import com.kingtime.elderlyapt.dao.MessagePushDao;
import com.kingtime.elderlyapt.dao.UserDao;
import com.kingtime.elderlyapt.entity.Evaluate;
import com.kingtime.elderlyapt.entity.MessagePush;
import com.kingtime.elderlyapt.entity.User;
import com.kingtime.elderlyapt.util.DBUtil;

/**
 * 用户接口实现类
 * 
 * @author xp
 * 
 * @created 2014-4-25
 */
public class UserDaoImpl implements UserDao {

	@Override
	public User login(String username, String password) {
		String sql = "select * from User where userName=? and password=? ";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return null;
	}

	private User getData(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUid(rs.getInt("userId"));
		user.setPwd(rs.getString("password"));
		user.setPhotoName(rs.getString("photo"));
		user.setName(rs.getString("userName"));
		user.setInterest(rs.getString("interest"));
		user.setGender(rs.getString("gender"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("phone"));
		user.setRoleId(rs.getInt("roleId"));
		user.setLocationId(rs.getInt("locationId"));
		user.setIntegral(rs.getInt("integral"));
		user.setEvaluateTimes(rs.getInt("evaluateTimes"));
		user.setResPhone(rs.getString("resPhone"));
		user.setAddress(rs.getString("address"));
		user.setCredibility(rs.getFloat("credibility"));
		user.setCreateTime(rs.getString("createTime"));
		return user;
	}

	@Override
	public User getUser(int uid) {
		String sql = "select * from User where userId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, uid);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return null;
	}

	/*
	 * 对获得的用户注册信息进行处理 (non-Javadoc)
	 * 
	 * @see
	 * com.kingtime.buytogether.dao.UserDao#register(com.kingtime.buytogether
	 * .entity.User)
	 */
	@Override
	public User register(User user) {
		System.out.println("1----getname->----");

		// 默认数据
		user.setRoleId(1);
		user.setLocationId(32);
		user.setPhone("1234567890");
		user.setIntegral(50);

		// "insert into User() values(NULL," + user.getRoleId() + ","
		// + user.getLocationId() + ",'" + user.getName() + "','"
		// + user.getPwd() + "','" + user.getGender() + "','"
		// + user.getEmail() + "','" + user.getPhotoName() + "','"
		// + user.getPhone() + "','" + user.getResPhone() + "','"
		// + user.getInterest() + "'," + user.getIntegral() + ","
		// + user.getCredibility() + "," + user.getEvaluateTimes() + ",'"
		// + user.getAddress() + "')";

		String sql = "insert into User(roleId,locationId,userName,password,phone,integral,credibility,evaluateTimes,createTime,disable) values("
				+ user.getRoleId()
				+ ","
				+ user.getLocationId()
				+ ",'"
				+ user.getName()
				+ "','"
				+ user.getPwd()
				+ "','"
				+ user.getPhone() + "'," + user.getIntegral() + ",10,0" + ",now(),0)";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		User newUser = isExistUser(user.getName());
		if (newUser != null) {
			return null;
		} else {
			try {
				System.out.println("2----getname->----\n" + sql);
				PreparedStatement statement = connection.prepareStatement(sql);
				/*
				 * statement.setString(1, user.getName());
				 * statement.setString(2, user.getPwd()); statement.setString(3,
				 * user.getGender()); statement.setString(4, user.getEmail());
				 * statement.setString(5, user.getPhone());
				 * statement.setString(6, user.getInterest());
				 * System.out.println("3----getname->----");
				 */
				statement.execute(sql);
				newUser = isExistUser(user.getName());
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				util.closeConnection(connection);
			}

		}
		return newUser;

	}

	@Override
	public User getUserByActivityId(int activityId) {
		ActivityInfoDao activityInfoDao = new ActivityInfoDaoImpl();
		return getUser(activityInfoDao.getActivityInfo(activityId).getPostUserId());
	}

	/**
	 * 判断数据库中是否已存在该用户
	 * 
	 * @param username
	 * @return 存在则返回该用户实例，没有则返回为一个空用户
	 */
	private User isExistUser(String username) {
		String sql = "select * from User where userName=?";
		User user = null;
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				user = getData(rs);
				System.out.println("01----getname->----name" + user.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("01----getname->----");
		return user;
	}

	@Override
	public User updateUserInfo(User updateUser) {
		User tempUser = isExistUser(updateUser.getName());
		if (tempUser != null && tempUser.getUid() != updateUser.getUid()) {// 已存在该用户名
			return null;
		}

		String sql = "update User set userName='" + updateUser.getName() + "',gender='" + updateUser.getGender() + "',phone='"
				+ updateUser.getPhone() + "',resPhone='" + updateUser.getResPhone() + "',email='" + updateUser.getEmail()
				+ "',interest='" + updateUser.getInterest() + "',address='" + updateUser.getAddress() + "' where userId="
				+ updateUser.getUid();
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return isExistUser(updateUser.getName());
	}

	@Override
	public boolean updateUserCredibility(Evaluate evaluate) {
		int userid = new ActivityInfoDaoImpl().getActivityInfo(evaluate.getActivityId()).getPostUserId();
		evaluate.setUid(userid);
		return updateUserCredibilityByAppraise(evaluate);
	}

	@Override
	public boolean updateUserCredibilityByAppraise(Evaluate evaluate) {
		User user = getUser(evaluate.getUid());
		int nowEvaluateTimes = user.getEvaluateTimes() + 1;
		float nowCredibility = (user.getCredibility() * user.getEvaluateTimes() + evaluate.getCredibility()) / nowEvaluateTimes;

		DBUtil util = new DBUtil();
		int sqlResult = 0;
		String sql = null;
		PreparedStatement statement;
		Connection connection = util.openConnection();
		try {
			sql = "update User set credibility=" + nowCredibility + ",evaluateTimes=" + nowEvaluateTimes + " where userId="
					+ evaluate.getUid();
			System.out.println("sql:" + sql);
			statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("update user credibility ok!");

		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean updateUserIntegral(int uid, int integral) {
		MessagePush messagePush = null;
		MessagePushDao pushDao = new MessagePushDaoImpl();
		User user = getUser(uid);
		int newIntegral = user.getIntegral() + integral;
		DBUtil util = new DBUtil();
		int sqlResult = 0;
		String sql = null;
		PreparedStatement statement;
		Connection connection = util.openConnection();
		try {
			sql = "update User set integral=" + newIntegral + " where userId=" + uid;
			System.out.println("sql:" + sql);
			statement = connection.prepareStatement(sql);
			sqlResult = statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		System.out.println("update user integral ok!");

		// 产生积分变化推送消息
		messagePush = new MessagePush(uid, "你" + (integral > 0 ? "获得" : "支出") + "了" + Math.abs(integral) + "个时间币，快去看看吧！",
				MessagePush.CATEGORY_INTEGRAL);
		messagePush.setRemark(String.valueOf(uid));
		pushDao.createMessage(messagePush);

		return sqlResult == 0 ? false : true;
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		String sql = "select * from User where roleId=1";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				users.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return users;
	}

}
