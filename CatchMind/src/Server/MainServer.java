package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import models.Room;

public class MainServer {
	private ServerSocket serverSocket; // ���� ����
	private ArrayList<MainHandler> allUserList; // ��ü �����
	private ArrayList<MainHandler> waitUserList; // ���� �����
	private ArrayList<Room> roomTotalList; // ��ü �� ����Ʈ
	
	private Connection conn;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "user";
	private String password = "password";
	
	public MainServer() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password); // DB ȸ�� ����
			
			serverSocket = new ServerSocket(20000);
			System.out.println("���� �غ� �Ϸ�");
			
			allUserList = new ArrayList<MainHandler>(); // ��ü ����� 
			waitUserList = new ArrayList<MainHandler>(); // ���� �����
			roomTotalList = new ArrayList<Room>(); // ��ü �� ����Ʈ 
			while(true) {
				Socket socket = serverSocket.accept();
				MainHandler handler = new MainHandler(socket, allUserList, waitUserList, roomTotalList, conn); // ������ ����
				handler.start(); // ������ ����
				allUserList.add(handler);
				}
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
	
	public static void main(String[] args) {
		new MainServer();
	}
}

