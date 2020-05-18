package address.dao;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import address.db.DBConnection;
import address.db.DBUtils;
import address.model.GroupType;
import address.model.Member;

// Dao : 자바와 디비의 거점(Dao를 통해서 DBConnection 붙임)
// 싱글톤(private)
// static 일 시 Dao 클라이언트 수 만큼 new 됨(동기화 문제 생김) - DB와 커넥트할 때 말고 DML 할 때.
public class MemberDao {

	private final static String TAG = "MemberDao : ";

	private MemberDao() {
	}

	private static MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}

	// DML은 return 값이 int이다. 리턴되는 값은 변경된 행의 개수이다.
	public int 추가(Member member) {

		final String SQL = "INSERT INTO member(id, name, phone, address, groupType) VALUES(member_seq.nextval, ?, ?, ?, ?)"; 
		// 대소문자 구분
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			// 4. 쿼리 전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "추가 오류 : " + e.getMessage());
			// e.getMessage 보다 더 자세한 오류 발견 : e.printStackTrace
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}

	public int 삭제(int id) { // 
		final String SQL = "DELETE FROM member WHERE id = ? "; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setInt(1,  id);
			// 4. 쿼리 전송 (flush + rs)
			int result = pstmt.executeUpdate(); // 변경한 행의 개수를 return 받음
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "삭제 오류 : " + e.getMessage());
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt);
		}
		 return -1;
	}

	public int 수정(Member member) {
		
		final String SQL = "UPDATE member set name = ?, phone = ?, address =?,  groupType = ? WHERE id =?" ; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			pstmt.setInt(5, member.getId());
			// 4. 쿼리 전송 (flush + commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println(TAG + "수정 오류 : " + e.getMessage());
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}

	// DQL 은 return 값이 ResultSet == Cursor
	public Member 상세보기(int id) {
		final String SQL = "SELECT * FROM member WHERE id = ? "; // 목록에서 Order By는 항상!
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setInt(1, id);
			// 4. 쿼리 전송 (flush + rs 받기)
			rs = pstmt.executeQuery();
			if (rs.next()) { // return 값이 true, false
				member = Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build();
			}
			return member;
		} catch (Exception e) {
			System.out.println(TAG + "상세보기 오류 : " + e.getMessage());
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	public List<Member> 전체목록() {
		final String SQL = "SELECT * FROM member ORDER BY id"; // 목록에서 Order By는 항상!
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			// 4. 쿼리 전송 (flush + rs 받기)
			rs = pstmt.executeQuery();
			while (rs.next()) { // return 값이 true, false
				members.add(Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println(TAG + "전체목록 오류 : " + e.getMessage());
			// e.getMessage 보다 더 자세한 오류 발견 : e.printStackTrace
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt, rs);
		}
		return null;

	}

	public List<Member> 그룹목록(GroupType groupType) {
		final String SQL = "SELECT * FROM member WHERE groupType = ?"; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			// 1. 스트림 연결
			conn = DBConnection.getConnection();
			// 2. 버퍼 달기 (? 를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			// 3. 물음표 완성
			pstmt.setString(1, groupType.toString());
			// 4. 쿼리 전송 (flush + rs 받기)
			rs = pstmt.executeQuery();
			while (rs.next()) { // return 값이 true, false
				members.add(Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println(TAG + "그룹목록 오류 : " + e.getMessage());
		} finally { // 무조건 실행됨
			DBUtils.close(conn, pstmt, rs);
		}
		return null;
	}

}