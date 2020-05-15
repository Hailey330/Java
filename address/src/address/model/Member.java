package address.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Member {
	private int id; // PK - 넘버링 
	private String name; // 이름 
	private String phone; // 전화번호 (String 으로 만드는 것이 좋음)
	private String address; // 주소
	// 그룹 : 친구, 회사, 학교, 가족
	private GroupType groupType; // 도메인 설정이 안됨. 만들 때 그룹 4개만 들어올 수 있도록 설정해야함.
	
	@Builder
	public Member(int id, String name, String phone, String address, GroupType groupType) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = groupType;
	}

	@Override
	public String toString() {
		return id + "." + name;
	}
	
}
