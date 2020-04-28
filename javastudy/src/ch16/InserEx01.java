package ch16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InserEx01 {

	public static void main(String[] args) {
		// OracleDriver o = new OracleDriver();
		// �ٸ� ���ÿ��� �ʿ��ϸ� �� new �ؾ��Ѵ�. 
		try {
			final String SQL = "insert into users(id, name, email, password) values(?,?,?,?)";
			// OJDBC ������ �ش� ����̹��� �ε��϶�� �޴����� ����
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��Ʈ�� ����
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ssar", "bitc5600");
			// ���� �ޱ� (? �� ����ϰ� ���ش�)
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, 4); // ù��° ����ǥ�� ���� 1 �����͸� insert
			pstmt.setString(2, "����"); // �ι��� ����ǥ�� ȫ�浿 �����͸� insert
			pstmt.setString(3, "ee@nate.com"); // ����° ����ǥ�� �̸��� �����͸� insert
			pstmt.setString(4, "1234"); // �׹�° ����ǥ�� password ������ insert
			// ���ۿ� ���� (commit)
			// execute �� select������ ��.
			// insert, delete �� ������ ����Ǵ� ����. ��, commit �Ǿ�� �ϴ� ������ executeUpdate ���
			pstmt.executeUpdate();
			System.out.println("Insert �Ϸ�");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}