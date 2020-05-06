package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import models.Room;
import models.User;

public class MainHandler extends Thread {

	private BufferedReader br;
	private PrintWriter pw;
	private Socket socket;
	private Connection conn;
	private PreparedStatement pstmt;
	private User user;

	private ArrayList<MainHandler> allUserList; // 전체 사용자
	private ArrayList<MainHandler> waitUserList; // 대기실 사용자
	private ArrayList<Room> roomTotalList; // 전체 방 리스트

	private Room priRoom; // 사용자가 있는 방

	// 소켓, 전체 사용자, 대기방, 방 리스트
	public MainHandler(Socket socket, ArrayList<MainHandler> allUserList, ArrayList<MainHandler> waitUserList,
			ArrayList<Room> roomTotalList, Connection conn) throws Exception {
		this.user = new User();
		this.priRoom = new Room();
		this.socket = socket;
		this.allUserList = allUserList;
		this.waitUserList = waitUserList;
		this.roomTotalList = roomTotalList;
		this.conn = conn;

		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

	}

	public static void main(String[] args) {

	}
}
