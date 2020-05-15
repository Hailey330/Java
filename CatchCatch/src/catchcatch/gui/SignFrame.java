package catchcatch.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import catchcatch.models.Users;

public class SignFrame extends JFrame {

	private SignFrame signFrame = this;
	private LoginFrame loginFrame;
	private JPanel Login;
	private JTextField tfSid, tfSpw;
	private JButton btSID, btIdCheck, btSPW, btSign, btCancel;

	public SignFrame() {
		initObject();
		initDesign();
		initListener();
		setVisible(true);

	}

	private void initObject() {
		Login = new JPanel();
		tfSid = new JTextField();
		tfSpw = new JTextField();
		btSID = new JButton("아이디");
		btIdCheck = new JButton("중복확인");
		btSPW = new JButton("비밀번호");
		btSign = new JButton("가입하기");
		btCancel = new JButton("취소");
	}

	private void initDesign() {
		setTitle("회원 가입");
		setResizable(false);
		setLocationRelativeTo(null);
	
		signFrame.setBounds(100, 100, 510, 314);
		btSID.setBounds(54, 66, 124, 29);
		tfSid.setBounds(198, 66, 139, 29);
		btIdCheck.setBounds(351, 66, 89, 29);
		btSPW.setBounds(54, 118, 124, 29);
		tfSpw.setBounds(198, 120, 139, 27);
		btSign.setBounds(118, 191, 124, 29);
		btCancel.setBounds(264, 192, 124, 27);
		signFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		signFrame.getContentPane().add(Login, BorderLayout.CENTER);
		Login.setLayout(null);

		tfSid.setColumns(10);
		tfSpw.setColumns(10);

		Login.add(btSID);
		Login.add(tfSid);
		Login.add(btIdCheck);
		Login.add(btSPW);
		Login.add(tfSpw);
		Login.add(btSign);
		Login.add(btCancel);
	}
	
	private void initListener() {
		// 회원가입 버튼
		btSign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 텍스트필드에 있는 값을 가져옴 2. 값을 Users에 담음
				Users users = Users.builder()
						.userName(tfSid.getText())
						.password(tfSpw.getText())
						.build();
//				int result = 
				// 4. 리턴 값 확인 후 성공, 실패 로직 짜기
				// 5. 성공
				signFrame.dispose();
				loginFrame.setVisible(true);
			}
		});
		
		// X 버튼 눌렀을 때 원래 창으로 돌아감
		signFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				loginFrame.setVisible(true);
			}
		});
		
	}

}