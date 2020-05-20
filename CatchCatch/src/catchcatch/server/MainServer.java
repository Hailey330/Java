package catchcatch.server;

import java.io.IOException;

// MainServer Thread - 서버 소켓, 소켓 연결 이어주는 역할

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import catchcatch.models.Users;

public class MainServer {

	private int port = 9999;
	private ServerSocket serverSocket; // 서버 소켓
	private Socket socket; // 클라이언트 소켓
	private Users users;
	public static ArrayList<ServerThread> List;
//	public static Vector<ServerThread> vc;

	public void start() {
		List = new ArrayList<ServerThread>();
		ServerSocketConnect(); // 서버 소켓과 클라이언트 소켓 생성
		acceptClient(); // 서버 소켓과 클라이언트 소켓 연결
	}

	public void ServerSocketConnect() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("접속 대기중입니다...");
			socket = new Socket(); // 소켓 생성

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void acceptClient() {
		while (true) {
			try {
				socket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(); // 입출력 받는 스레드
//				users = new Users();
//				users.setSocket(socket);
//				users.makeSend();
//				serverThread.setUsers(users);
				serverThread.start();
				List.add(serverThread);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void sendAllClient(String msg) {
		for (int i = 0; i < List.size(); i++) {
			ServerThread serverThread = List.get(i);
			serverThread.sendMessage(msg);
				
			}
		}

	

	public static void main(String[] args) {
		MainServer server = new MainServer();
		server.start();
	}

}