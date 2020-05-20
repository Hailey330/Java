package catchcatch.models;

public class Users {
	
//	private Socket socket;
//	private PrintWriter pw;
	
	private String userId; // 유저 아이디, 닉네임

//	public void setSocket(Socket socket) {
//		this.socket = socket;
//	}
//	
//	public Socket getSocket() {
//		return this.socket;
//	}
	
//	public void makeSend() {
//		try {
//			pw = new PrintWriter(socket.getOutputStream());
//			System.out.println(socket + "접속 완료!");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	public void sendMessage(String msg) {
//		pw.println(msg);
//		pw.flush();
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}