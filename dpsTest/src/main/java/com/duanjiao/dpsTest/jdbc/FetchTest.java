package com.duanjiao.dpsTest.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import net.sf.json.JSONObject;

public class FetchTest {
	
	@Test
	public void defaultDriver() {
		DbConnUtil connUtil = new DbConnUtil();
		Connection conn =connUtil.getCon();
		
		String sql = "SELECT * FROM SYS_USER";
		
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setFetchSize(1000);
			ResultSet resultSet =statement.getResultSet();
			while(resultSet.next()) {
				System.out.println(JSONObject.fromObject(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
