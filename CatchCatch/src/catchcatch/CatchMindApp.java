package catchcatch;

import CatchMind.client.MainClient;
import catchcatch.gui.GameRoomFrame;
import catchcatch.gui.LoginFrame;

public class CatchMindApp {

	public static void main(String[] args) {
		
		String userId = null;
		MainClient client = new MainClient();
		GameRoomFrame game = new GameRoomFrame();
		LoginFrame login = new LoginFrame();
		login.initFrame();
		do {
			userId = login.getUserID();
			System.out.println("");
		} while (userId == null);
		client.setUserId(userId);
		game.start();
		client.start();
//		client.setAnswerField(game.getAnswerField());
//		client.setChatScreen(game.getChatScreen());
//		client.setBrush(game.getBrush());
//		client.setImgReader(game.getImgReader());
		
	}
}