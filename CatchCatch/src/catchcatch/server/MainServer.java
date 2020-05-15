package catchcatch.server;

import java.io.IOException;

// MainServer Thread - 서버 소켓, 소켓 생성해서 스레드에 담음 

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import catchcatch.models.Users;

public class MainServer {

	private int port = 9999;
	private ServerSocket serverSocket;
	private Socket socket;
	private ServerThread st;
//	private Vector<ServerThread> vc;
	public static ArrayList<Users> userList;

	private Users users;

	public MainServer() {
		
		userList = new ArrayList<Users>();

		try {
			serverSocket = new ServerSocket(port); // 서버 소켓 생성
			System.out.println("서버 준비 완료!");
			socket = new Socket(); // 소켓 생성
//			vc = new Vector<>(); // 소켓 스레드 담기(소켓1, 소켓2, 소켓3 ... )

		} catch (Exception e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = serverSocket.accept(); // 서버 소켓 - 소켓 연결
				System.out.println("연결중...");
				st = new ServerThread(socket); // 입출력 받는 스레드
				st.start();
//				vc.add(st);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new MainServer();

	}
}