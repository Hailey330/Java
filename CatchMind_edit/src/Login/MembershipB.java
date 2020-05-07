package Login;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.GapContent;

public class MembershipB extends JFrame {
	JPasswordField pwT;
	JTextField nameT, idT, tel2T, tel3T, emailT, emailadductionT;
	private JLabel nameL, idL, pwL;
	JButton joinB, calneB, emeilokB, idoverlapB, emailB;;
	JComboBox<String> telC, emailC, ageYearC, ageMonthC, ageDayC;

	public MembershipB() {
		setTitle("회원가입");

		nameL = new JLabel("이름");
		nameT = new JTextField(15);
		JPanel p1 = new JPanel();
		p1.add(nameL);
		p1.add(nameT);

		idL = new JLabel("아이디");
		idT = new JTextField(15);
		idoverlapB = new JButton("중복확인");
		JPanel p2 = new JPanel();
		p2.add(idL);
		p2.add(idT);
		p2.add(idoverlapB);

		pwL = new JLabel("비밀번호");
		pwT = new JPasswordField(15);
		pwT.setEchoChar('*');
		JPanel p3 = new JPanel();
		p3.add(pwL);
		p3.add(pwT);

		joinB = new JButton("가입");
		calneB = new JButton("취소");
		JPanel p8 = new JPanel();
		p8.add(joinB);
		p8.add(calneB);

		JPanel p = new JPanel(new GridLayout(4, 1));
		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p8);

		JPanel joinp = new JPanel();
		joinp.add(joinB);
		joinp.add(calneB);

		Container contentPane = this.getContentPane();
		contentPane.add("Center", p);
		contentPane.add("South", joinp);

//		setVisible(true);
		setResizable(false);
		setBounds(400, 200, 1000, 800);
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

}