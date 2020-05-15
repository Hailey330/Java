package catchcatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import catchcatch.db.DBConnection;
import catchcatch.db.DBUtils;
import catchcatch.models.Users;

// 자바와 디비의 거점
public class UsersDao {

	private final static String TAG = "UsersDao : ";

	public UsersDao() {
	}

	private static UsersDao instance = new UsersDao();

	public static UsersDao getInstance() {
		return instance;
	}

	public int 가입(Users users) {

		final String SQL = "INSERT INTO users(id, userName, password) VALUES(users_seq.nextval, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼달기
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setString(1, users.getUserName());
			pstmt.setString(2, users.getPassword());
			// 4. 쿼리 전송(flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "추가오류 : " + e.getMessage());
		} finally {
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
}