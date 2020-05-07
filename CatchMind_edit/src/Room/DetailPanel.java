package Room;
// 게임방 LIST FRAME
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Server.Data;

public class DetailPanel extends JPanel implements ActionListener {
	// 게임방 리스트
	public static String labelName[] = { "방 번호 : ", "      ", "방 제목 : ", "      ", "인원 수 : ", "      ", "      "};
	public JLabel labelArray[]; // 0, 1(방번호), 2, 3(방제목), 4, 5(인원수), 6(방장)
	private JButton enterButton;

	private BufferedReader br;
	private PrintWriter pw;

	public DetailPanel(BufferedReader br, PrintWriter pw) {
		this.br = br;
		this.pw = pw;
	}

	public void init() {

		this.setLayout(new GridLayout(5, 2, 1, 1));

		labelArray = new JLabel[labelName.length];

		for (int i = 0; i < labelName.length; i++) {
			labelArray[i] = new JLabel(labelName[i]);
			this.add(labelArray[i]);
		}

		enterButton = new JButton("입 장");
		this.add(enterButton);
		enterButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("입장 버튼");
		if (e.getSource() == enterButton) {

			String count[] = labelArray[7].getText().split("/");

			if (count[0].compareTo(count[1]) == 0) {
				JOptionPane.showMessageDialog(this, "인원 수 초과로 들어갈 수 없습니다");
			} else {
				// 서버로 입장요청 -> 룸 ID
				String line = "";
				line += (Data.ENTERROOM + "|" + labelArray[1].getText()); // Pro + 방번호
				pw.println(line);
				pw.flush();
			}
		}
	}

}