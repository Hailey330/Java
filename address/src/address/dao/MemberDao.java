package address.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import address.db.DBConnection;
import address.db.DBUtils;
import address.model.GroupType;
import address.model.Member;

// Dao : 자바와 디비의 거점(Dao를 통해서 DBConnection 붙임)
// 싱글톤(private)
// static 일 시 Dao 클라이언트 수 만큼 new 됨(동기화 문제 생김) - DB와 커넥트할 때 말고 DML 할 때.
public class MemberDao {

	private MemberDao() {
	}

	private static MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}

	// DML은 return 값이 int이다. 리턴되는 값은 변경된 행의 개수이다.
	public int 추가(Member m) {
		
		final String SQL = "INSERT INTO member(id, name, phone, address, group) VALUES(member_seq.nextval, ?, ?, ?, ?)"; // 대소문자 구분 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setString(1,  m.getName());
			pstmt.setString(2,  m.getPhone());
			pstmt.setString(3,  m.getAddress());
			pstmt.setString(4,  m.getGroup().toString());
			// 4. 쿼리 전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("추가 오류 : " + e.getMessage());
			// e.getMessage 보다 더 자세한 오류 발견 : e.printStackTrace
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt);
		} 
		return -1;
	}

	public int 삭제(int id) {
		return -1;
	}

	public int 수정(Member m) {
		return -1;
	}

	// DQL 은 return 값이 ResultSet == Cursor
	public Member 상세보기(int id) {
		return null;
	}

	public List<Member> 전체목록() {
		return null;
	}

	public List<Member> 그룹목록(GroupType group) {
		return null;
	}

}