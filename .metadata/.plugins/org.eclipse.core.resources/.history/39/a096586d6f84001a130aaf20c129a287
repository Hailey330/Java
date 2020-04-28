package booklist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerList {
	Connection con; // 객체 변수, 인스턴스 변수
	Statement stmt;
	ResultSet rs;

	// 1. 변수
//	int custid;
//	String name;
//	String address;
//	String phone;
//
	// 2. 배열
//	int custid_arr[] = new int[10];
//	String name_arr[] = new String[10];
//	String address_arr[] = new String[10];
//	String phone_arr[] = new String[10];

	public CustomerList() {
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		// 11g express edition 은 orcl 대신 XE 를 입력한다.
		String userid = "c##madang"; // c## 추가
		String pwd = "c##madang"; // c## 추가

		// 드라이버를 찾는 과정
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 데이터베이스를 연결하는 과정
		try {
			System.out.println("데이터베이스 연결 준비-");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void printCustomer() {

		String query = "SELECT custid, name, address, phone FROM customer"; // SQL문

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			System.out.println("CUSTID \tNAME \t\tADDRESS \t\tPHONE");

			while (rs.next()) {
				System.out.print("\t" + rs.getInt(1));
				System.out.print("\t" + rs.getString(2));
				System.out.print("\t\t\t" + rs.getString(3));
				System.out.println("\t\t\t\t" + rs.getString(4));

			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
