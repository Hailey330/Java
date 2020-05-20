package catchcatch.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CatchMind.client.MainClient;
import catchcatch.client.SendThread;
import catchcatch.client.game.GameController;
import catchcatch.client.game.GameThread;
import catchcatch.gui.drawComponents.Brush;
import catchcatch.server.MainServer;

public class GameRoomFrame extends JFrame{

	private final static String TAG = "GameRoomFrame : ";
	private GameRoomFrame gameFrame = this;
	private Brush brush;
	private ColorButtons buttons;
	
	private final int panelWidth = 500; // 그림판 너비
	private final int panelHeight = 550; // 그림판 높이

	private BufferedImage imgReader;
	private JTextField answerField, userlistField, chatText;
	private JTextArea chatField;
	private JButton startButton, answerButton, enterButton;
	private JLabel userlistLabel, imgPanel;

	
	public void start() {
		initFrame();
		drawScreen();
		drawBrush();
		drawListener();
		drawButtons();
		answerField();
		chatFrame();
		userListFrame();
		startButton();
	}
	

	private void initFrame() {
		setTitle("CATCHMIND GAME!");
		setBounds(100, 100, 962, 738);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void drawScreen() {
		imgReader = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
		imgPanel = new JLabel(new ImageIcon(imgReader));
		imgPanel.setBounds(40, 100, panelWidth, panelHeight);
		imgPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		gameFrame.add(imgPanel);
	}

	private void drawBrush() {
		brush = new Brush();
		brush.setBounds(40, 100, panelWidth, panelHeight);
		brush.repaint();
		brush.printAll(imgReader.getGraphics());
		gameFrame.add(brush);
	}
	
	private void drawButtons() {
		buttons = new ColorButtons();
		buttons.setBrush(brush);
		buttons.setButtons();
		gameFrame.add(buttons.blackbtn());
		gameFrame.add(buttons.bluebtn());
		gameFrame.add(buttons.redbtn());
		gameFrame.add(buttons.greenbtn());
		gameFrame.add(buttons.yellowbtn());
		gameFrame.add(buttons.eraserbtn());
		gameFrame.add(buttons.clearbtn());
	}
	
	private void answerField() {
		answerButton = new JButton("제시어");
		answerField = new JTextField();
		answerField.setColumns(10);
		answerField.setDisabledTextColor(Color.BLACK);
		answerField.setHorizontalAlignment(JTextField.CENTER);
		answerButton.setBounds(40, 46, 112, 34);
		answerField.setBounds(160, 46, 262, 34);
		answerField.setEnabled(false);
		answerField.setDisabledTextColor(Color.BLACK);
		gameFrame.add(answerButton);
		gameFrame.add(answerField);
	}
	
	private void chatFrame() {
		chatScreen();
		chatText();
	}
	
	
	private void chatScreen() {
		chatField = new JTextArea(); // 채팅창
		JScrollPane scroll = new JScrollPane(chatField);
		scroll.setBounds(580, 292, 323, 305);
		chatField.setEnabled(false);
		chatField.setDisabledTextColor(Color.BLACK);
		chatField.setCaretPosition(chatField.getDocument().getLength());
		gameFrame.add(scroll);
	}
	
	
	private void chatText() {
		// 채팅 입력 창
		chatText = new JTextField(); 
		chatText.setBounds(580, 609, 229, 38);
		chatText.setColumns(10);
		// 엔터 키
		enterButton = new JButton("Enter");
		enterButton.setBounds(823, 609, 80, 38);
		
		chatText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					SendThread.pw.println("CHAT:" + chatText.getText());
					SendThread.pw.flush();
					chatText.setText("");
				}
			}
		});
		
		gameFrame.add(chatText);
		gameFrame.add(enterButton);
	}
	
	
	private void userListFrame() {
		userlistLabel = new JLabel("User List");
		userlistLabel.setBounds(579, 121, 308, 27);
		userlistField = new JTextField();
		userlistField.setBounds(580, 157, 323, 120);
		userlistField.setEnabled(false);
		gameFrame.add(userlistLabel);
		gameFrame.add(userlistField);
	}
	
	
	private void startButton() {
		startButton = new JButton("게임시작");
		startButton.setBounds(580, 46, 323, 63);
		gameFrame.add(startButton);
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkStart()) {
					chatField.append("[SERVER] 인원이 2명 이상이어야 시작할 수 있습니다.");
					chatField.setCaretPosition(chatField.getDocument().getLength());
				} else {
					gameStart();
				}
			}
		});
	}
	
	private boolean checkStart() {
		if(MainServer.List.size() > 1) {
			return false;
		} else {
			return true;
		}
	}
	
	private void gameStart() {
		chatField.append("[SERVER] 게임을 시작합니다.");
		chatField.setCaretPosition(chatField.getDocument().getLength());
		GameThread gameThread = new GameThread();
		gameThread.setChatScreen(chatField);
		gameThread.start();
		
	}
	

	private void drawListener() {
		imgPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(GameController.turnflag == true) {
					System.out.println(TAG + "게임 룸 프레임 오류");
					SendThread.pw.println("Position:" + e.getX() + "," + e.getY());
					SendThread.pw.flush();
				}				
			}
		}); 
	}
	

	
	public Brush getBrush() {
		return this.brush;
	}
	
	public BufferedImage getImgReader() {
		return this.imgReader;
	}
	
	public JTextArea getChatScreen() {
		return this.chatField;
	}
	
	public JTextField getAnswerField () {
		return this.answerField;
	}
	
	public void setUserlistField(JTextField userlistField) {
		this.userlistField = userlistField;
	}
	
	
}