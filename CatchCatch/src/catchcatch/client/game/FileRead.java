package catchcatch.client.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {

	private File file;
	private final String dir = "src" + File.separator + "catchcatch" + File.separator + "client" + File.separator + "game" + File.separator + "answer.txt";
	private ArrayList<String> list;
	
	public void read() {
		makeList();
		readStart();
	}
	
	private void makeList() {
		list = new ArrayList<String>();
	}
	
	private void readStart() {
		try {
			file = new File(dir);
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			br.close();
			} catch (FileNotFoundException e) {
				System.out.println("Working Directory = " + System.getProperty("user.dir"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	public ArrayList<String> getAnswer(){
		return this.list;
	}
}