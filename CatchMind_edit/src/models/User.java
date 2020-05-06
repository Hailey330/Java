package models;

public class User {
	private int idNumber;
	private String idName;
	private String password;
	private String name;

	public User() {
		this(0, "", "", "");
	}

	public User(int idNumber, String idName, String password, String name) {
		super();
		this.idNumber = idNumber;
		this.idName = idName;
		this.password = password;
		this.name = name;
	}

	public int getidNumber() {
		return idNumber;
	}

	public void setidNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [idNumber=" + idNumber + ", idName=" + idName + ", password=" + password + ", name=" + name + "]";
	}

}