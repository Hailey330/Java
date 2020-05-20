package catchcatch.client;

import java.awt.Color;
import java.awt.image.BufferedImage;

// BufferedReader Thread - 받는 메세지

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import catchcatch.client.game.GameController;
import catchcatch.gui.drawComponents.Brush;

public class ReceiveThread extends Thread {

	private Socket socket;
	private BufferedReader br;
	private BufferedImage imgReader;
	private String msg;
	private Brush brush;
	private int x, y;
	private JTextArea chatScreen;
	private JTextField answerField;
	
	@Override
	public void run() {
		super.run();
		readMsg();
		getMsg();
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	private void readMsg() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getMsg() { // 받는 채팅
		while (true) {
			try {
				msg = br.readLine();
				if (msg.contains(":")) {
					String[] pars = msg.split(":");
					if (pars[0].equals("Position")) { // 그림
						pars = pars[1].split(",");
						x = Integer.parseInt(pars[0]);
						y = Integer.parseInt(pars[1]);
						brush.setX(x);
						brush.setY(y);
						brush.repaint();
						brush.printAll(imgReader.getGraphics());
					} else if (pars[0].equals("Color")) {
						if (pars[1].equals("BLACK"))
							brush.setColor(Color.BLACK);
						else if (pars[1].equals("BLUE"))
							brush.setColor(Color.BLUE);
						else if (pars[1].equals("RED"))
							brush.setColor(Color.RED);
						else if (pars[1].equals("GREEN"))
							brush.setColor(Color.GREEN);
						else if (pars[1].equals("YELLOW"))
							brush.setColor(Color.YELLOW);
						else if (pars[1].equals("WHITE"))
							brush.setColor(Color.WHITE); // 지우개
					
					} else if (pars[0].equals("CHAT")) { // 채팅
						chatScreen.append(pars[1]+ "\n");
						chatScreen.setCaretPosition(chatScreen.getDocument().getLength());
					} else if (pars[0].equals("JOIN")) { // 입장
						chatScreen.append(pars[1] + "입장했습니다.\n");
						chatScreen.setCaretPosition(chatScreen.getDocument().getLength());
					} else if (pars[0].equals("TOOL")) { // 모두 지우기
						if (pars[1].equals("CLEAR")) {
							ClearScreen();
						}
					} else if (pars[0].equals("TURN")) { // 게임 턴
						if (pars[1].equals("FALSE")) {
							GameController.turnflag = false;
						} else if (pars[1].equals("TRUE")) {
							GameController.turnflag = true;
						}
					} else if (pars[0].equals("ANSWER")) { // 제시어
						answerField.setText(pars[1]);
					}
				} // if
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void ClearScreen()	{
		brush.setFlag(false);
		brush.repaint();
		brush.printAll(imgReader.getGraphics());
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
	}
	
	public void setImgReader(BufferedImage imgReader) {
		this.imgReader = imgReader;
	}
	
	public void setChatScreen(JTextArea chatScreen) {
		this.chatScreen = chatScreen;
	}
	
	public void setAnswerField(JTextField answerField) {
		this.answerField = answerField;
	}
}