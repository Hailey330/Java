package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
	private int idNumber;
	private String idName;
	private String password;
	private String name;


	@Override
	public String toString() {
		return "User [idNumber=" + idNumber + ", idName=" + idName + ", password=" + password + ", name=" + name + "]";
	}

}