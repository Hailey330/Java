package catchcatch.gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GameRoomFrame {

	private JFrame frame;
	
	private JTextField tfCard;
	private JTextField tfChat;
	private JTextField taUserList;
	private JTextArea taChat;
	private JButton btGstart;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameRoomFrame window = new GameRoomFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public GameRoomFrame() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 962, 738);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btCard = new JButton("제시어");
		btCard.setBounds(40, 46, 112, 34);
		frame.getContentPane().add(btCard);
		
		tfCard = new JTextField();
		tfCard.setBounds(160, 46, 262, 34);
		frame.getContentPane().add(tfCard);
		tfCard.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(40, 106, 502, 541);
		frame.getContentPane().add(panel);
		
		btGstart = new JButton("게임시작");
		btGstart.setBounds(580, 46, 323, 63);
		frame.getContentPane().add(btGstart);
		
		JLabel LuserList = new JLabel("User List"); 
		LuserList.setBounds(579, 121, 308, 27);
		frame.getContentPane().add(LuserList);
		
		taUserList = new JTextField(); // 유저 리스트 칸
		taUserList.setBounds(580, 157, 323, 120);
		frame.getContentPane().add(taUserList);
		
		taChat = new JTextArea();
		taChat.setBounds(580, 292, 323, 305);
		frame.getContentPane().add(taChat);
		
		tfChat = new JTextField();
		tfChat.setBounds(580, 609, 229, 38);
		frame.getContentPane().add(tfChat);
		tfChat.setColumns(10);
		
		JButton btEnter = new JButton("Enter");
		btEnter.setBounds(823, 609, 80, 38);
		frame.getContentPane().add(btEnter);
	}
	

	
	public JTextArea get게임채팅창() {
		return this.taChat;
	}
	
	public JTextField get게임유저리스트창() {
		return this.taUserList;
	}
	
	public JButton get게임시작버튼(){
		return this.btGstart;
	}
	
}