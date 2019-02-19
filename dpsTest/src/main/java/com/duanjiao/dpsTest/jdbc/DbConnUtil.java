package com.duanjiao.dpsTest.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnUtil {

	private String dbUrl = "jdbc:oracle:thin:@192.168.8.22:1521:orcl"; // 数据库连接地址
	private String dbUserName = "tnmsdb"; // 用户名
	private String dbPassword = "tnmsdb"; // 密码
	private String jdbcName = "oracle.jdbc.OracleDriver"; // 驱动名称

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() {
		try {
			Class.forName(jdbcName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}
}
