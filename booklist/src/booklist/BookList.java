package booklist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookList {
	Connection con; // 멤버변수
	Statement stmt;
	ResultSet rs;

	// 멤버변수
	// 1. 변수
//	int bookid;
//	String bookname;
//	String publisher;
//	int price;

	// 2. 배열
//	int bookid_arr[] = new int[10];
//	String bookname_arr[] = new String[10];
//	String publisher_arr[] = new String[10];
//	int price_arr[] = new int[10];

	// 3. 객체 (내부 inner 클래스)
//	class Book {
//		int bookid;
//		String bookname;
//		String publisher;
//		int price;
//	}

	// 3. 객체
	Book b1;
	// 4. 객체 배열
	Book[] b_array;

	public BookList() {
		// 3. 객체 방식
//		b1 = new Book();
		// 4. 객체 배열 초기화
		b_array = new Book[12]; // 배열 칸이 몇 개 

		for (int i = 0; i < b_array.length; ++i)
			b_array[i] = new Book();
	}

//		b_array[0] = new Book();
//		b_array[1] = new Book();
//		b_array[2] = new Book();
//		b_array[3] = new Book();
//		b_array[4] = new Book();

	void getConnection() {
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
			System.out.println("데이터베이스 연결 준비");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void getBookDB() {

		String query = "SELECT bookid, bookname, publisher, price FROM book"; // SQL문

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			System.out.println("BOOK ID \tBOOK NAME \t\tPUBLISHER \t\tPRICE");

			int index = 0;
			while (rs.next()) {
				// 1. 변수
//				bookid = rs.getInt(1);
//				bookname = rs.getString(2);
//				publisher = rs.getString(3);
//				price = rs.getInt(4);
//
//				printBook(bookid, bookname, publisher, price);
//				printBook2();

				// 2. 배열
//				bookid_arr[index] = rs.getInt(1));
//				bookname_arr[index] = rs.getString(2));
//				publisher_arr[index] = rs.getString(3));
//				price_arr[index] = rs.getInt(4));
//				
//				index = index + 1;
//				index++;
//				++index;
//				printBook(bookid_arr[0], bookname_arr[0], price_arr[0]);

				// 3. 객체
//				b1.bookid = rs.getInt(1);
//				b1.bookname = rs.getString(2);
//				b1.publisher = rs.getString(3);
//				b1.price = rs.getInt(4);

				// 4. 객체 배열 - index 번지 수!!!
				b_array[index].bookid = rs.getInt(1);
				b_array[index].bookname = rs.getString(2);
				b_array[index].publisher = rs.getString(3);
				b_array[index].price = rs.getInt(4);

				index++;
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 1. 변수
//	void printBook(int bookid, String bookname, String publisher, int price) {
//		System.out.println(bookid + "," + bookname + "," + publisher + "," + price);
//	}

	// 1-1. 변수 2방식
//	void printBook2() {
//		System.out.println(bookid + "," + bookname + "," + publisher + "," + price);
//	}

	// 2. 배열
//	void printBookArr() {
//		for (int i = 0; i < 10; i++) {
//			System.out.print(bookid_arr[i] + "\t");
//			System.out.print(bookname_arr[i] + "\t\t");
//			System.out.print(publisher_arr[i] + "\t\t");
//			System.out.println(price_arr[i]);
//		}
//	}

	// 4. 객체 배열
	void printBook_Obj_array() {
		for (int i = 0; i < 12; ++i) {
			System.out.print(b_array[i].bookid + "\t");
			System.out.print(b_array[i].bookname + "\t");
			System.out.print(b_array[i].publisher + "\t");
			System.out.println(b_array[i].price);
		}
	}

	// 3. 객체
	void printBook_Object() {
		System.out.print(b1.bookid + "\t");
		System.out.print(b1.bookname + "\t");
		System.out.print(b1.publisher + "\t");
		System.out.println(b1.price);
	}

}
