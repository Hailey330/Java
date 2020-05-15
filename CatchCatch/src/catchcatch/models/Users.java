package catchcatch.models;

import lombok.Builder;
import lombok.Data;

@Data
public class Users {
	private int id; // PK - 넘버링
	private String userName; // 유저 아이디, 닉네임
	private String password; // 유저 비밀번호

	@Builder
	public Users(int id, String userName, String password) {
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

}
