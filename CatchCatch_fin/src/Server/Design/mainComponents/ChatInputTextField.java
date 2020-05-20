package Server.Design.mainComponents;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import Client.Core.socket.SendMessage;
import Server.Core.socket.ServerController;

public class ChatInputTextField extends JTextField {
	public ChatInputTextField() {
		setBounds(10,360,480,30);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
}