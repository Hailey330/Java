package address.service;

import java.util.List;

import address.dao.MemberDao;
import address.model.GroupType;
import address.model.Member;

public class MemberService { 

	// 트랜잭션을 관리할 수 있다! 
	
	private MemberService() {} // 싱글톤 생성 private
	private static MemberService instance = new MemberService();
	public static MemberService getinstance() {
		return instance;
	}

	private MemberDao memberDao = MemberDao.getInstance();
	
	public int 주소록추가(Member member) {
		// 3. DAO에 접근해서 추가함수 호출 -> Member 전달
		return memberDao.추가(member);
	}
	
	public List<Member> 전체목록() {
		return memberDao.전체목록();
	}
	
	public Member 상세보기(int memberId) {
		return memberDao.상세보기(memberId);
	}
	
	public int 삭제하기(int memberId) {
		return memberDao.삭제(memberId);
	}
	
	public int 수정하기(Member member) {
		return memberDao.수정(member);
	}
	
	public List<Member> 그룹목록(GroupType groupType){
		return memberDao.그룹목록(groupType);
	}
	
}
