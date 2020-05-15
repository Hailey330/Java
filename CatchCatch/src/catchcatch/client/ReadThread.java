package catchcatch.client;

// BufferedReader Thread - 받는 메세지

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import catchcatch.gui.GameRoomFrame;

public class ReadThread extends Thread {

	private Socket socket;
	private BufferedReader br;
	private String msg;

	private GameRoomFrame gameFrame;

	public ReadThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			gameFrame = new GameRoomFrame();
		} catch (Exception e) {
			e.printStackTrace();

			getMsg(); // 받는 메시지 프로토콜
		}

	}

	private void getMsg() { // 받는 채팅
		while (true) {
			try {
				msg = br.readLine();
				if (msg.contains(":")) {
					String[] line = msg.split(":");
					if (line[0].equals("CHAT")) {

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}