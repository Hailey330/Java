package catchcatch.server;

// MainServer Thread - 서버 소켓, 소켓 생성해서 스레드에 담음 

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServer {

	private int port = 8888;
	private ServerSocket serverSocket;
	private Socket socket;
	private ServerThread st;
	private Vector<ServerThread> vc;

	public MainServer() {

		try {
			serverSocket = new ServerSocket(port); // 서버 소켓 생성
			socket = new Socket(); // 소켓 생성
			vc = new Vector<>(); // 소켓 스레드 담기(소켓1, 소켓2, 소켓3 ... )

			while (true) {
				socket = serverSocket.accept(); // 서버 소켓 - 소켓 연결
				System.out.println("서버 준비 완료!");
				st = new ServerThread(socket);
				st.start();
				vc.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainServer();
	}
}