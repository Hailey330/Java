package catchcatch.server;

// Server Thread - 소켓의 메세지를 입출력함

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import catchcatch.gui.GameRoomFrame;
import catchcatch.models.Users;

class ServerThread extends Thread {

	private Socket socket;
	private ServerThread st = this;
	private BufferedReader br;
	private PrintWriter pw;
	private String msg;
	private static ArrayList<Users> List;
	private String id;
	
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
			// 채팅

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void joinChat() { // 소켓에서 보내는 채팅 전달 
		try {
			id = br.readLine(); 
			users.setUserId(id);
			// 참여 리스트 업뎃 joinFieldList
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void joinFieldList() { // 게임 참여 유저 리스트
		for (int i = 0; i < List.size(); i++) {

		}
	}

}

