package catchcatch.gui.drawComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catchcatch.client.SendThread;
import catchcatch.client.game.GameController;

public class ClearButton extends JButton{

	public ClearButton() {
		super("모두 지우기");
		setBounds(430, 415, 60, 40);
		setBackground(Color.WHITE);
		ButtonEvent();
	} 
	
	private void ButtonEvent() {
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameController.turnflag == true) {
					SendThread.pw.println("MODE:CLEAR");
					SendThread.pw.flush();
				}
			}
		});
	}
}