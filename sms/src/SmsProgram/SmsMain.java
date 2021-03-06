package SmsProgram;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SmsMain extends JFrame {

	private JLabel lb1, lb2;
	private JTextField tf1, tf2;
	private JButton bt1, bt2;

	public SmsMain() {
		setTitle("SMS ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(4, 2);
		grid.setVgap(5);

		Container c = getContentPane();
		c.setLayout(grid);

		lb1 = new JLabel(" �޴� ��ȣ ");
		lb2 = new JLabel(" ��    �� ");

		tf1 = new JTextField();
		tf2 = new JTextField();

		bt1 = new JButton(" ��  �� ");
		bt2 = new JButton(" �� �� �� ");

		// ������
		c.setBackground(new Color(224, 255, 139));
//		c.setBackground(new Color(245, 214, 255));
		
		
		lb1.setFont(new Font("���� ����", Font.BOLD, 15));
		lb2.setFont(new Font("���� ����", Font.BOLD, 15));
		
		tf1.setBackground(Color.WHITE);
		
		bt1.setFont(new Font("���� ����", Font.BOLD, 15));
		bt2.setFont(new Font("���� ����", Font.BOLD, 15));
		bt1.setBackground(new Color(246	, 246, 246));
		bt2.setBackground(new Color(246	, 246, 246));
		
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

		setSize(400, 200);
		setVisible(true);
	}

	public static void main(String[] args) {
		new SmsMain();
	}

}