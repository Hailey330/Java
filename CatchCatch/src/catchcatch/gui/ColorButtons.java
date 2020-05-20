package catchcatch.gui;

import catchcatch.gui.drawComponents.BlackButton;
import catchcatch.gui.drawComponents.BlueButton;
import catchcatch.gui.drawComponents.Brush;
import catchcatch.gui.drawComponents.ClearButton;
import catchcatch.gui.drawComponents.EraserButton;
import catchcatch.gui.drawComponents.GreenButton;
import catchcatch.gui.drawComponents.RedButton;
import catchcatch.gui.drawComponents.YellowButton;

public class ColorButtons {

	private Brush brush;
	private BlackButton blackbtn;
	private BlueButton bluebtn;
	private GreenButton greenbtn;
	private RedButton redbtn;
	private YellowButton yellowbtn;
	private EraserButton eraserbtn;
	private ClearButton clearbtn;
	
	
	public void setButtons() {
		makeButtons();
	}

	private void makeButtons() {
		blackbtn = new BlackButton();
		bluebtn = new BlueButton();
		greenbtn = new GreenButton();
		redbtn = new RedButton();
		yellowbtn = new YellowButton();
		eraserbtn = new EraserButton();
		clearbtn = new ClearButton();

		blackbtn.setBrush(brush);
		bluebtn.setBrush(brush);
		greenbtn.setBrush(brush);
		redbtn.setBrush(brush);
		yellowbtn.setBrush(brush);
		eraserbtn.setBrush(brush);
	}
		
	public void setBrush(Brush brush) {
		this.brush = brush;
	}
	
	public BlackButton blackbtn() {
		return blackbtn;
	}
	
	public BlueButton bluebtn() {
		return bluebtn;
	}
	
	public RedButton redbtn() {
		return redbtn;
	}
	
	public GreenButton greenbtn() {
		return greenbtn;
	}
	
	public YellowButton yellowbtn() {
		return yellowbtn;
	}
	
	public EraserButton eraserbtn() {
		return eraserbtn;
	}
	
	public ClearButton clearbtn() {
		return clearbtn;
	}
	
}