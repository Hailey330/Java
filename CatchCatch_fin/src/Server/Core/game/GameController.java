package Server.Core.game;

import Server.Core.socket.ServerController;

public class GameController {
	static public String answer;
	static public boolean answerflag = false;
	static public boolean gameflag = false;
	static public String ID;
	
	
	static public void firstStart() { // 게임 시작 
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("CHAT:[SERVER] " + "게임을 시작합니다.");
			ServerController.List.get(i).sendMessage("SET:FALSE");
			ServerController.List.get(i).sendMessage("MODE:CLEAR");
			
		}
	}
	
	static public void allUserPermissionFalse() { // 유저들이 입장만 했을 때
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("SET:FALSE");
			ServerController.List.get(i).sendMessage("MODE:CLEAR");
		}
	}
	
	static public void rightAnswer(String id) { // 정답 맞췄을 때
		answerflag = true;
		answer = "aaaaaaaaaaa";
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("CHAT:[알림] " + id +" 님이 맞추셨습니다.");
		}
	}
	
	static public void allUserMsg(String msg) {
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage(msg);
		}
	}
}