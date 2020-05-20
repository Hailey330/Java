package catchcatch.client.game;

import catchcatch.server.MainServer;

public class GameController {
	static public String answer;
	static public boolean gamestate = true; // 게임시작여부
	static public boolean turnflag = true; // 게임 턴 
	static public boolean gameflag = false; // 게임
	static public boolean answerflag = false; // 정답
	static public String userName;
	
	static public void firstStart() {
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.("CHAT:" + "게임을 시작하겠습니다.");
			MainServer.List.get(i).sendMessage("SET:FALSE");
			MainServer.List.get(i).sendMessage("MODE:CLEAR");
		}
	}
	
	static public void allUserFalse() {
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.get(i).sendMessage("SET:FALSE");
			MainServer.List.get(i).sendMessage("MODE:CLEAR");
		}
	}
	
	static public void rightAnswer(String userName) {
		answerflag = true;
		answer = "a";
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.get(i).sendMessage("CHAT:[알림]" + userName + "님이 정답을 맞추셨습니다!");
		}
	}
	
	static public void allUserMsg(String msg) {
		for (int i = 0; i < MainServer.List.size(); i++) {
			MainServer.List.get(i).sendMessage(msg);
		}
	}
}