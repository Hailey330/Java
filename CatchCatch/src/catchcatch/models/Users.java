package catchcatch.models;

public class Users {
	private int id; // PK - 넘버링 
	private String userId; //  유저 아이디, 닉네임 
	private String password; // 유저 비밀번호 
	
	// 사용할 생성자
	public Users(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public Users(int id, String userId, String password) {
		this.id = id;
		this.userId = userId;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
