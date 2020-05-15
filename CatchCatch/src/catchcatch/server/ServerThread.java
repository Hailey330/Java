package catchcatch.server;

// Server Thread - 소켓의 메세지를 입출력함

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextArea;

import catchcatch.gui.GameRoomFrame;
import catchcatch.models.Users;

class ServerThread extends Thread {

	private Socket socket;
	private ServerThread st;
	private BufferedReader br;
	private PrintWriter pw;
	private Vector<ServerThread> vc;

	private String msg;
	private String uId;
	private JTextArea chatField;

	private Users users;
	private GameRoomFrame gameFrame;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		게임입장();
		메시지대기();
		st.set게임채팅창(gameFrame.get게임채팅창()); // 게임채팅창 동기화

	}

	private void 게임입장() { // 소켓에서 보내는 채팅 전달
		try {
			uId = br.readLine();
			users.setUserName(uId);
			게임유저리스트();
			chatField.append("* " + uId + " * 님이 입장했습니다.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// for문 전체 유저에 뿌리기

	}

	private void 게임유저리스트() { // 게임 참여 유저 리스트
		String str = " ";
		for (int i = 0; i < vc.size(); i++) {
//			str += 
		}
	}

	private void 모든유저메시지() {
		for (int i = 0; i < vc.size(); i++) {
			vc.get(i).pw.println(msg);
			pw.flush();
		}
		if (msg.contains("CHAT: ")) {
			String[] line = msg.split(":");
			chatField.append(line[1] + "\n");
			chatField.setCaretPosition(chatField.getDocument().getLength()); // 항상 스크롤 자동
		}
	}

	private void 메시지대기() {
		while (true) {
			try {
				msg = br.readLine();
				if (msg.contains("CHAT: ")) {
					msg += " ";
					String[] line = msg.split(":");
//					if (line[1].equals(/* 정답 */)) {} 
					if (line[0].equals("CHAT: ")) {
						line[1] += " ";
						msg = "CHAT: " + " [" + uId + "] " + line[1];
					}
					모든유저메시지();
				}
			} catch (IOException e) {
				msg = "CHAT: " + "*" + uId + "* 님이 나가셨습니다.";
				모든유저메시지();
				vc.remove(st);
				게임유저리스트();
				break;

			}
		}
	}

	public void set게임채팅창(JTextArea chatField) {
		this.chatField = chatField;
	}

}