package catchcatch.gui.drawComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import catchcatch.client.SendThread;
import catchcatch.client.game.GameController;

public class EraserButton extends JButton{

	private Brush brush;
	public EraserButton() {
		super("지우개");
		setBounds(360, 415, 60, 40);
		setFont(getFont().deriveFont(7f));
		setBackground(Color.WHITE);
	}
	
	private void ButtonEvent() {
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(GameController.turnflag == true) {
					SendThread.pw.println("Color:WHITE");
					SendThread.pw.flush();
					brush.setColor(Color.WHITE);
				}
			}
		});
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
		ButtonEvent();
	}
}