package smsprogram;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SmsMain extends JFrame {

	private JLabel lb1, lb2;
	private JTextField tf1, tf2;
	private JButton bt1, bt2;

	public SmsMain() {
		setTitle("SMS 전송 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(4, 2);
		grid.setVgap(5);

		Container c = getContentPane();
		c.setLayout(grid);

		lb1 = new JLabel(" 받는 번호 ");
		lb2 = new JLabel(" 내    용 ");

		tf1 = new JTextField();
		tf2 = new JTextField();

		bt1 = new JButton(" 전  송 ");
		bt2 = new JButton(" 지 우 기 ");

		c.add(lb1);
		c.add(tf1);
		c.add(lb2);
		c.add(tf2);
		c.add(bt1);
		c.add(bt2);

		bt1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String number = tf1.getText();
				String text = tf2.getText();
				SmsSend.main(number, text);

			}
		});

		bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				tf2.setText("");
			}
		});

		setSize(300, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new SmsMain();
	}

}