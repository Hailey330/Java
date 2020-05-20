package catchcatch.client.game;

import java.util.ArrayList;

public class Game {

	private FileRead fileRead;
	private ArrayList<String> answerList;
	private String answer;
	
	public void start() {
		readFile();
		saveAnswer();
	}
	
	private void readFile()	{
		fileRead = new FileRead();
		fileRead.read();
	}
	
	private void saveAnswer() {
		answerList = fileRead.getAnswer();
	}
	
	public void print() {
		for (int i = 0; i < answerList.size(); i++) {
			System.out.println(answerList.get(i));
		}
	}
	
	public boolean moreAnswer()	{
		if(answerList.size() != 0) {
			int index = (int) (Math.random() * answerList.size());
			answer = answerList.get(index);
			answerList.remove(index);
			return true;
		} else return false;
	}
	
	public String getAnswer() {
		return this.answer;
	}
}