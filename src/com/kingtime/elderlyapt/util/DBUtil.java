package com.kingtime.elderlyapt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 数据库工具类
 * 
 * @author xp
 * 
 * @created 2014-4-25
 */
public class DBUtil {

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection openConnection() {
		Properties properties = new Properties();
		String driver = null;
		String url = null;
		String username = null;
		String password = null;

		try {
			properties.load(this.getClass().getClassLoader()
					.getResourceAsStream("DBConfig.properties"));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
