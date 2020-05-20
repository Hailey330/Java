package catchcatch.gui.drawComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catchcatch.client.SendThread;
import catchcatch.client.game.GameController;

public class BlueButton extends JButton{

	private Brush brush;
	public BlueButton() {
		setBounds(150, 415, 60, 40);
		setBackground(Color.BLUE);
	}
	
	private void ButtonEvent() {
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameController.turnflag == true) {
					SendThread.pw.println("Color:BLUE");
					SendThread.pw.flush();
					brush.setColor(Color.BLUE);
				}
			}
		});
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
		ButtonEvent();
	}
}