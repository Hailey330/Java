package ch16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectEx01 {

	public static void main(String[] args) {
		// OracleDriver o = new OracleDriver();
		// 다른 스택에서 필요하면 또 new 해야한다.
		try {
			final String SQL = "select id, name, email, password from users where id = ?";
			// OJDBC 문서에 해당 드라이버를 로드하라는 메뉴얼이 있음
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 스트림 연결
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ssar", "bitc5600");
			// 버퍼 달기 (? 를 사용하게 해준다)
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, 3); // 첫번째 물음표에 숫자 1 데이터를 insert
			// 버퍼에 쓰기 (ResultSet을 리턴받음)
			// execute 는 select문에서 씀.
			// commit 이 필요없을 뿐더러 커서가 필요하기 때문에 execute 사용
			ResultSet rs = pstmt.executeQuery();
			Users users = null;
			if (rs.next()) { // 커서 내림
				users = new Users( rs.getInt("id"), 
											   rs.getString("name"), 
											   rs.getString("email"),
											   rs.getString("password"));
			}
			System.out.println(users.getId());
			System.out.println(users.getName());
			System.out.println(users.getEmail());
			System.out.println(users.getPassword());

			System.out.println("Select 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
