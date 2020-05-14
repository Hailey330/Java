package db.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryBean {
	Connection conn; // 연결 객체
	Statement stmt; // 질의 객체
	ResultSet rs; // 결과 객체

	public QueryBean() {
		conn = null;
		stmt = null;
		rs = null;
	}

	public void getConnection() { // 연결하기
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() { // 연결 끊기
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public ArrayList getUserInfo() throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT ");
		sb.append("    U_ID, U_NAME, U_PHONE, U_GRADE, WRITE_TIME ");
		sb.append("  FROM "); 
		sb.append("    USER_INFO_SAMPLE");
		sb.append("  ORDER BY  ");
		sb.append("  WRITE_TIME  ");
		
		rs = stmt.executeQuery(sb.toString());
		
		ArrayList res = new ArrayList();
		while (rs.next()) {
			res.add(rs.getString(1));
			res.add(rs.getString(2));
			res.add(rs.getString(3));
			res.add(rs.getString(4));
			res.add(rs.getString(5));
		}
		System.out.println(sb.toString());
		return res;
	}
	
}
