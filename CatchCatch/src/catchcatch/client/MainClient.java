package catchcatch.client;

// PrintWriter Thread - 쓰는 메세지

import java.io.PrintWriter;
import java.net.Socket;

public class MainClient {

	private int port = 9999;
	private Socket socket;
	private PrintWriter pw;
	private String msg;

	public MainClient() {
		try {
			socket = new Socket("localhost", port);
			ReadThread ct = new ReadThread(socket); // br 스레드
			ct.start();

			pw = new PrintWriter(socket.getOutputStream(), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMsg() {
		pw.println(msg);
		pw.flush();
	}

}