package catchcatch.gui.drawComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catchcatch.client.SendThread;
import catchcatch.client.game.GameController;

public class BlackButton extends JButton{

	private Brush brush;
	public BlackButton() {
		setBounds(10, 415, 60, 40);
		setBackground(Color.BLACK);
	}
	
	private void ButtonEvent() {
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameController.turnflag == true) {
					SendThread.pw.println("Color:BLACK");
					SendThread.pw.flush();
					brush.setColor(Color.BLACK);
				}
			}
		});
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
		ButtonEvent();
	}
}