package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kingtime.elderlyapt.dao.DutyDao;
import com.kingtime.elderlyapt.entity.Duty;
import com.kingtime.elderlyapt.util.DBUtil;

/**
 * 职责接口实现类
 * 
 * @author xp
 * 
 * @created 2014-8-9
 */
public class DutyDaoImpl implements DutyDao {

	@Override
	public List<Duty> getDutyList(int activityId) {
		List<Duty> duties = new ArrayList<Duty>();
		String sql = "select * from DutyDistribution where activityId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, activityId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				duties.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return duties;
	}

	@Override
	public Duty getDuty(int dutyId) {
		Duty duty = new Duty();
		String sql = "select * from DutyDistribution where dutyId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, dutyId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				duty = getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return duty;
	}

	private Duty getData(ResultSet rs) throws SQLException {
		Duty duty = new Duty();
		duty.setDutyId(rs.getInt("dutyId"));
		duty.setActivityId(rs.getInt("activityId"));
		duty.setDutyContent(rs.getString("dutyContent"));
		duty.setDutyIntegral(rs.getInt("dutyIntegral"));
		duty.setNeedNum(rs.getInt("needNum"));
		duty.setNowNum(rs.getInt("nowNum"));
		duty.setDutyName(rs.getString("dutyName"));
		duty.setRemark(rs.getString("remark"));
		return duty;
	}

	@Override
	public boolean updateDutyNowNum(int dutyId, int updateNum) {
		int nowNum = getNowNum(dutyId) + updateNum;
		String sql = "update DutyDistribution set nowNum=" + nowNum + " where dutyId=" + dutyId;
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
	public int getNowNum(int dutyId) {
		Duty duty = new Duty();
		String sql = "select * from DutyDistribution where dutyId=?";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, dutyId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				duty = getData(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return duty.getNowNum();
	}

	@Override
	public boolean createDuty(Duty duty) {
		// INSERT INTO `dutydistribution` VALUES ('100000', '100000', '10', '1',
		// '看护青志队打扫卫生', '-10', null);
		String sql = "insert into DutyDistribution values(NULL," + duty.getActivityId() + ",'" + duty.getDutyName() + "',"
				+ duty.getNeedNum() + ",0,'" + duty.getDutyContent() + "'," + duty.getDutyIntegral() + ",null)";
		// System.out.println("create duty:" + sql);
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
		System.out.println("create duty:ok!");
		return sqlResult == 0 ? false : true;
	}

	@Override
	public int[] searchDuty(String searchString) {
		List<Duty> duties = new ArrayList<Duty>();
		String sql = "select * from DutyDistribution where ";
		String[] searchs = searchString.split(" ");
		for (int i = 0; i < searchs.length; i++) {
			if (i == 0) {
				sql += "dutyName like '%" + searchs[i] + "%' or dutyContent like '%" + searchs[i] + "%' ";
			} else {
				sql += "or dutyName like '%" + searchs[i] + "%' or dutyContent like '%" + searchs[i] + "%' ";
			}
		}
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				duties.add(getData(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}

		// 去重
		for (int i = 0; i < duties.size(); i++) {
			for (int j = i + 1; j < duties.size(); j++) {
				if (duties.get(i).getActivityId() == duties.get(j).getActivityId()) {
					duties.remove(j--);
				}
			}
			// System.out.println(duties.get(i).getActivityId());
		}
		System.out.println("search duty size:" + duties.size());

		int[] result = new int[duties.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = duties.get(i).getActivityId();
			//System.out.println(result[i]);
		}
		return result;
	}

}
