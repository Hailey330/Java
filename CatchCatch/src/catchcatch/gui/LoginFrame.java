package catchcatch.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import catchcatch.client.MainClient;

public class LoginFrame extends JFrame {

	private LoginFrame loginFrame = this;
	private JPanel panel;
	private JButton btID, btPW, btSign, btLogin;
	private JTextField tfID, textField;

	public LoginFrame() {
		initObject();
		initDesign();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		panel = new JPanel();
		tfID = new JTextField();
		textField = new JTextField();

		btID = new JButton("아이디");
		btPW = new JButton("비밀번호");
		btSign = new JButton("회원가입");
		btLogin = new JButton("로그인");
	}

	private void initDesign() {
		// 1. 기본 세팅
		setTitle("캐치마인드 로그인");
		setResizable(false);
		setLocationRelativeTo(null);
		loginFrame.setBounds(100, 100, 393, 269);
		btID.setBounds(67, 45, 105, 27);
		tfID.setBounds(197, 46, 128, 24);
		btPW.setBounds(67, 99, 105, 27);
		textField.setBounds(197, 99, 128, 26);
		btSign.setBounds(94, 160, 89, 27);
		btLogin.setBounds(197, 160, 89, 27);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 2. 패널 세팅
		loginFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		panel.setLayout(null);

		// 3. 디자인
		tfID.setColumns(10);
		textField.setColumns(10);

		// 4. 패널에 컴포넌트 추가
		loginFrame.getContentPane().add(panel);
		panel.add(btID);
		panel.add(tfID);
		panel.add(btPW);
		panel.add(textField);
		panel.add(btSign);
		panel.add(btLogin);
	}

	// 리스너 등록
	private void initListener() {

		// 1. 회원가입 버튼
		btSign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignFrame();
			}
		});
		
		// 2. 로그인 버튼
		btLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new GameRoomFrame();
			}
		});
	}
	
	
	

}
