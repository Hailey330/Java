package Server.Design.mainComponents;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class JoinUserField extends JTextField {
	public JoinUserField() { // 접속 유저 칸
		super("접속 유저 : ");
		setBounds(10,70,480,30);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setDisabledTextColor(Color.BLACK);
		setEnabled(false);
	}
}