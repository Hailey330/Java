package catchcatch.client.game;

import javax.swing.JTextArea;

import catchcatch.server.MainServer;

public class GameThread extends Thread {
	
	private JTextArea chatScreen;

	@Override
	public void run() {
		super.run();
		Game game = new Game();
		String answer;
		int index = 0;
		game.start();
		GameController.firstStart();
		while (game.moreAnswer()) {
			answer = game.getAnswer();
			GameController.answer = answer;
			System.out.println(answer);
			GameController.gameflag = true;
			GameController.answerflag = false;
			GameController.userName = MainServer.List.get(index).getUserId();
			GameController.allUserMsg("CHAT:" + GameController.userName + "유저 차례입니다.");
			MainServer.List.get(index).sendMessage("TURN:TRUE");
			MainServer.List.get(index).sendMessage("CHAT:[알림] " + "당신 차례입니다.");
			MainServer.List.get(index).sendMessage("CHAT:[알림] " + "정답은 " + answer + " 입니다.");
			MainServer.List.get(index).sendMessage("CHAT:[알림] " + "정답 한번 잘 설명해보슈");
			MainServer.List.get(index).sendMessage("ANSWER: " + answer);
			while (true) {
				if (GameController.answerflag == true)
					break;
				else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						
					}
				}
			}
			MainServer.List.get(index).sendMessage("ANSWER: " + " ");
			GameController.allUserFalse();
			++index;
			if(index == MainServer.List.size()) {
				index = 0;
			}

		}
	}
	
	public void setChatScreen(JTextArea chatScreen) {
		this.chatScreen = chatScreen;
	}
	
}