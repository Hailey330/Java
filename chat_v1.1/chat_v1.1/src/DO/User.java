package DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int pryNumber;
	private String idName;
	private String password;
	private String name;
	
	@Override
	public String toString() {
		return "User [pryNumber=" + pryNumber + ", idName=" + idName + ", password=" + password + ", name=" + name
				+ "]";
	}

}