package catchcatch.client;

import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread{
	
	private Socket socket;
	static public PrintWriter pw;
	private String userId;
	
	@Override
	public void run() {
		makeSend();
		sendUserId();
	}

	private void makeSend() {
		try {
			pw = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendUserId() {
		pw.println(userId);
		pw.flush();
	}
	
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}