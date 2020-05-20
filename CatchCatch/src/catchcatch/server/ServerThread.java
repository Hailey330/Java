package catchcatch.server;

// Server Thread - 소켓의 메세지를 입출력함

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import catchcatch.client.game.GameController;
import catchcatch.models.Users;

class ServerThread extends Thread {

	private Users users;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private String msg;
	private String userId;

	@Override
	public void run() {
		setSocket(socket);
		makeUserConnect();
		waitMsg();
		joinChat();
		
	}

	private void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	private void makeUserConnect() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			pw = new PrintWriter(socket.getOutputStream());
			System.out.println(socket + "접속 완료!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) {
		pw.println(msg);
		
	}

	private void waitMsg() {
		while (true) {
			try {
				msg = br.readLine();
				if (msg.contains("CHAT:")) {
					msg += " ";
					String[] line = msg.split(":");
					System.out.println(line[1]);
					if (line[1].equals(GameController.answer + " ") && GameController.answerflag == false
							&& (!(this.userId).equals(GameController.userName))) {
						GameController.answerflag = true;
						GameController.rightAnswer(userId);
					}
					if (line[0].equals("CHAT")) {
						line[1] += " ";
						msg = "CHAT:" + "[" + userId + "]" + line[1];
					}
				}
				 allUserSendMsg();
			} catch (IOException e) {
				msg = "CHAT:" + userId + "님이 나가셨습니다.";
				 allUserSendMsg();
				MainServer.List.remove(users);
				 joinFieldUpdate();
				break;

			}

		}
	}
	
	private void joinChat() {
		try {
			userId = br.readLine();
			users.setUserId(userId);
			joinFieldUpdate();
			chatScreen.append(userId + "입장했습니다.\n"); 
		} catch (IOException e) {
			
		}
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.get(i).sendMessage("JOIN" + userId);
			
		}
		
	}
	
	private void allUserSendMsg() {
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.get(i).sendMessage(msg);
		}
		if(msg.contains("CHAT:")) {
			String line[] = msg.split(":");
			chatScreen.append(line[1]+ "\n");
			chatScreen.setCaretPosition(chatScreen.getDocument().getLength());
		}
	}
	
	private void joinFieldUpdate() {
		String str = new String();
		str = "";
		for (int i = 0; i < MainServer.List.size(); i++) {
			str += MainServer.List.get(i).getUserId() + " ";
		}
		userlistField.setText(str);
	}
	

	public void setChatScreen(JTextArea chatScreen) {
		this.chatScreen = chatScreen;
	}
	
	public void setUserlistField(JTextField userlistField) {
		this.userlistField = userlistField;
	}
}