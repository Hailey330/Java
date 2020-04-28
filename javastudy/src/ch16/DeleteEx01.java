package ch16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteEx01 {

	public static void main(String[] args) {
		// OracleDriver o = new OracleDriver();
		// �ٸ� ���ÿ��� �ʿ��ϸ� �� new �ؾ��Ѵ�. 
		try {
			final String SQL = "delete from users where id = ?";
			// OJDBC ������ �ش� ����̹��� �ε��϶�� �޴����� ����
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// ��Ʈ�� ����
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ssar", "bitc5600");
			// ���� �ޱ� (? �� ����ϰ� ���ش�)
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, 1); // ù��° ����ǥ�� ���� 1 �����͸� insert
			// ���ۿ� ���� (commit)
			// execute �� select������ ��.
			// insert, delete �� ������ ����Ǵ� ����. ��, commit �Ǿ�� �ϴ� ������ executeUpdate ���
			pstmt.executeUpdate();
			System.out.println("Delete �Ϸ�");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
