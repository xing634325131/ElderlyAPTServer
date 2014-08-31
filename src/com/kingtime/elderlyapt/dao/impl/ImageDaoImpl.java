package com.kingtime.elderlyapt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kingtime.elderlyapt.dao.ImageDao;
import com.kingtime.elderlyapt.entity.PictureUpload;
import com.kingtime.elderlyapt.util.DBUtil;

public class ImageDaoImpl implements ImageDao {

	@Override
	public String getUserPhoto(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getActivityMainPic(int activityId) {
		String mainPic = null;
		String sql = "select * from ActivityPicture where activityId=? ";
		DBUtil util = new DBUtil();
		Connection connection = util.openConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, activityId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				mainPic = rs.getString("pictureName");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.closeConnection(connection);
		}
		return mainPic;
	}

	@Override
	public boolean saveUploadActivityImage(PictureUpload pictureUpload) {
		int sqlResult = 0;
		// INSERT INTO `activitypicture` VALUES ('100000', '10002', '100000',
		// 'dasaoweisheng', '2014-07-26 09:37:44', null);
		String sql = "insert into ActivityPicture values(NULL,"
				+ pictureUpload.getUid() + ",1,'" + pictureUpload.getPicName()
				+ "',now(),null)";
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
		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean updateUploadImage(int activityId, String imageName) {
		int sqlResult = 0;
		if(imageName == null){//没有图片则不用更新
			return true;
		}
		String sql = "update ActivityPicture set activityId=" + activityId
				+ " where pictureName='" + imageName + "'";
		//System.out.println("update image:"+sql);
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
		System.out.println("update activity image:ok!");
		return sqlResult == 0 ? false : true;
	}

	@Override
	public boolean saveUploadUserPhoto(PictureUpload pictureUpload) {
		int sqlResult = 0;
		String sql = "update User set photo='" + pictureUpload.getPicName() + "' where userId=" + pictureUpload.getUid();
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
		return sqlResult == 0 ? false : true;
	}

}
