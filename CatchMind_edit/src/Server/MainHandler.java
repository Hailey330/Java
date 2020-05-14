package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Room;
import models.User;

public class MainHandler extends Thread { // 메인 스레드 
	private BufferedReader br;
	private PrintWriter pw;
	private Socket socket;
	private Connection conn;
	private PreparedStatement pstmt;
	private User user;

	private ArrayList<MainHandler> allUserList; // 전체 사용자
	private ArrayList<MainHandler> waitUserList; // 대기실 사용자
	private ArrayList<Room> roomtotalList; // 전체 방 리스트

	private Room priRoom; // 사용자가 있는 방

	// 사용자 연결 소켓, 전체 사용자, 대기방, 방 리스트, JDBC
	public MainHandler(Socket socket, ArrayList<MainHandler> allUserList, ArrayList<MainHandler> waitUserList,
			ArrayList<Room> roomtotalList, Connection conn) throws IOException {
		this.user = new User();
		this.priRoom = new Room();
		this.socket = socket;
		this.allUserList = allUserList;
		this.waitUserList = waitUserList;
		this.roomtotalList = roomtotalList;
		this.conn = conn;

		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

	}

	@Override
	public void run() {
		// 데이터 입력 받음 데이터파싱 -> 결과 실행
		try {
			String[] line = null; // 입력받은 데이터를 배열에 담음 
			while (true) {
				line = br.readLine().split("\\|");

				if (line == null) { // while 문 끝냄
					break;
				}

				if (line[0].compareTo(Data.REGISTER) == 0) { // [회원가입]

					String userContent[] = line[1].split("%"); // 회원 정보를 userContent 배열에 담음 

					String sql = "Insert into UserContent values(num.nextval,?,?,?)"; // DB 연결 - 정보 insert
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, userContent[0]); // ID NAME
					pstmt.setString(2, userContent[1]); // PASSWORD
					pstmt.setString(3, userContent[2]); // NAME

					int su = pstmt.executeUpdate(); // auto flush 자동 커밋
					System.out.println(su + "회원가입 [DB]");

				} else if (line[0].compareTo(Data.IDSEARCHCHECK) == 0) { // [회원가입 ID 중복 체크]

					System.out.println(line[0] + "/" + line[1]); // line[0] 프로토콜 + / + 유저ID
					
					String sql = "select * from UserContent where IDNAME = '" + line[1] + "'"; // 유저ID select 
					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery(sql);
					String name = null; 
					int count = 0;
					while (rs.next()) {
						name = rs.getString("IDNAME");
						if (name.compareTo(line[1]) == 0) { // 중복 확인 ID 비교  
							count++; 
						}
					}
					System.out.println(count);
					
					if (count == 0) {// 중복 X -> 가입 가능
						pw.println(Data.IDSEARCHCHECK_OK + "|" + "MESSAGE");
						pw.flush();
					} else {
						pw.println(Data.IDSEARCHCHECK_NO + "|" + "MESSAGE");
						pw.flush();
					}

				} else if (line[0].compareTo(Data.ENTERLOGIN) == 0) { // [로그인]

					boolean con = true; // 기존에 로그인이 됐는지 안됐는지 확인
					System.out.println("Login success");
					String userContent[] = line[1].split("%"); // 유저 정보 userContent 담김 

					System.out.println(userContent[0] + "/" + userContent[1]); // User ID / 비밀번호 

					for (int i = 0; i < waitUserList.size(); i++) {
						if ((waitUserList.get(i).user.getIdName()).compareTo(userContent[0]) == 0) { // 대기방 user 리스트와 비교해서 중복 로그인 확인
							con = false; // 중복 로그인 방지 
						}
					}
					
					if (con) { // con 이 true 일 때(로그인 시) 무조건 실행 
						String sql = "select * from UserContent where (IDNAME = '" + userContent[0]
								+ "' and password = '" + userContent[1] + "')"; // DB 회원정보 select 

						pstmt = conn.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery(sql);
						int count = 0;

						while (rs.next()) { // while 문 돌면서 select 한 값과 비교!! DB에 들어가있는 유저의 ID와 비밀번호가 입력한 값과 일치하는지 확인 
							user.setIdNumber(rs.getInt("IDNUMBER"));
							user.setIdName(rs.getString("IDNAME"));
							user.setPassword(rs.getString("password"));
							user.setName(rs.getString("NAME"));

							count++;
						}

						System.out.println(count);

//						if (count == 0) { // 아이디, 비밀번호 틀렸을 때
//							pw.println(Data.ENTERLOGIN_NO + "|" + "로그인에 실패하였습니다");
//							pw.flush();
//
//							user.setName("");
//							user.setIdName("");
//							user.setPassword("");
//							user.setIdNumber(0);
//
//						} else 
						
							if (count == 1) { // 로그인 되었을때
							waitUserList.add(this); // 대기방 인원수 추가
							String userline = "";
							for (int i = 0; i < waitUserList.size(); i++) {
								userline += (waitUserList.get(i).user.getIdName() + ":"); // 새로운 유저들 추가 
							}

							for (int i = 0; i < waitUserList.size(); i++) {
								waitUserList.get(i).pw.println( // EnterFrame 로그인 연동
										Data.ENTERLOGIN_OK + "|" + user.getIdName() + "| 님이 입장하였습니다. |" + userline);
								waitUserList.get(i).pw.flush();
							}
							System.out.println("[대기방 인원수] :" + waitUserList.size());

							System.out.println("[Room 정보]");
							for (Room room : roomtotalList) {
								System.out.println(room.toString() + "현재방에 인원수 : " + room.roomInUserList.size());
							}
							System.out.println("[전체 Room 갯수 ]" + roomtotalList.size());

							// RoomtotalList 전체 정보를 Message로 만들어서 보냄
							String roomListMessage = "";

							for (int i = 0; i < roomtotalList.size(); i++) {
								roomListMessage += (roomtotalList.get(i).getrID() + "%"
										+ roomtotalList.get(i).getTitle() + "%" + roomtotalList.get(i).getUserCount()
										+ "%" + roomtotalList.get(i).getMasterName() + "%"
										+ roomtotalList.get(i).roomInUserList.size() + "-"); // 방 만들면 콘솔 창에 정보 출력 
							}

							System.out.println(roomListMessage);

							if (roomListMessage.length() != 0) { // 방이 만들어지면 
								for (int i = 0; i < waitUserList.size(); i++) {
									waitUserList.get(i).pw.println(Data.ROOMMAKE_OK + "|" + roomListMessage); // 대기 채팅창에 문구 출력(~님이 ~번 방을 만들었습니다.)
									waitUserList.get(i).pw.flush();
								}
							}

						}

						System.out.println(user.toString()); // 새로운 유저가 입장하면 유저의 정보가 콘솔 창에 뜸 
					} else { // con 이 false 일 때 (중복 로그인 일 때) 
						pw.println(Data.ENTERLOGIN_NO + "|" + "이미 로그인 중입니다.");
						pw.flush();
					}

				} else if (line[0].compareTo(Data.EXITWAITROOM) == 0) { // 대기실방에서 로그인 페이지(로그 아웃할 때);

					String thisName = waitUserList.get(waitUserList.indexOf(this)).user.getIdName(); // - 메인화면 창으로 간 유저의 이름

					waitUserList.remove(this); // 대기실 리스트 유저를 삭제
					System.out.println("[대기방 인원] :" + waitUserList.size());

					String userline = "";
					for (int i = 0; i < waitUserList.size(); i++) {
						userline += (waitUserList.get(i).user.getIdName() + ":"); 
					}

					System.out.println("대기자 인원 :" + userline);
					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw.println(Data.EXITWAITROOM + "|" + thisName + "|님이 퇴장하였습니다.|" + userline);
						waitUserList.get(i).pw.flush();
					}
					// 유저 초기화 
					user.setName("");
					user.setIdName("");
					user.setPassword("");
					user.setIdNumber(0);

				} else if (line[0].compareTo(Data.SENDMESSAGE) == 0) { // 대기실에서 메세지 보내기

					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw.println(Data.SENDMESSAGE_ACK + "|" + user.getIdName() + " |" + line[1]);
						// 대기방에 message 전송
						waitUserList.get(i).pw.flush();
					}

				} else if (line[0].compareTo(Data.ROOMMAKE) == 0) { // 방 만들기
					String userContent[] = line[1].split("%"); // 배열을 한 라인씩 % 로 파싱 
					Room tempRoom = new Room(); // Room 띄워줌 

					String sql = "Insert into Room values(num.nextval,?,?,?)"; // DB에 방 정보를 insert 
					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, userContent[0]); // title
					pstmt.setString(2, userContent[1]); // count
					pstmt.setString(3, user.getIdName()); // 방장이름

					tempRoom.setTitle(userContent[0]);
					tempRoom.setUserCount(userContent[1]);
					tempRoom.setMasterName(user.getIdName());

					sql = "select * from Room where title = '" + userContent[0] + "' and  userCount = '" + userContent[1]
							+ "' and masterName = '" + user.getIdName() + "'";

					int su = pstmt.executeUpdate(); // 항상 몇개를 실행(CRUD)한지 갯수를 return
					System.out.println(su + "Room 생성 [DB]");

					pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery(sql);

					int count = 0;
					int priNumber = 0;

					while (rs.next()) {
						count++;
						priNumber = rs.getInt("rID"); // ??? 텍스트인지 DB 인지 모르겠음 원래 값은 ROOMID
					}

					if (count != 0) { // 방이 만들어짐 
						tempRoom.setrID(priNumber); // 방 ID 넣고 
						tempRoom.roomInUserList.add(this); // 유저 넣고 
						roomtotalList.add(tempRoom);
						priRoom = tempRoom; // 현재 룸을 지정함
					}

					System.out.println("[Room 정보]");
					for (Room room : roomtotalList) {
						System.out.println(room.toString() + "현재 방의 인원 : " + room.roomInUserList.size()); // 콘솔 창에 room 정보 띄움
					}
					System.out.println("[전체 Room 갯수 ]" + roomtotalList.size());

					// RoomtotalList 전체 정보를 Message로 만들어서 보냄 
					String roomListMessage = "";

					for (int i = 0; i < roomtotalList.size(); i++) {
						roomListMessage += (roomtotalList.get(i).getrID() + "%" + roomtotalList.get(i).getTitle() + "%"
								+ roomtotalList.get(i).getUserCount() + "%" + roomtotalList.get(i).getMasterName() + "%"
								+ roomtotalList.get(i).roomInUserList.size() + "-"); 
					}

					System.out.println(roomListMessage); // 콘솔 창 메세지 

					for (int i = 0; i < waitUserList.size(); i++) {
						if (waitUserList.get(i).user.getIdName().compareTo(tempRoom.getMasterName()) == 0) { // ????? 
							waitUserList.get(i).pw.println(Data.ROOMMAKE_OK1 + "|" + tempRoom.getMasterName());
							waitUserList.get(i).pw.flush();
						} else { // 다른 대기방사람들에게는 대기방만 새로고침
							waitUserList.get(i).pw.println(Data.ROOMMAKE_OK + "|" + roomListMessage);
							waitUserList.get(i).pw.flush();
						}

					}

					waitUserList.remove(this); // 대기방에서 나가고
					System.out.println("대기방 인원수 누군가 방만들었을때" + waitUserList.size());

					String userline = "";
					for (int i = 0; i < waitUserList.size(); i++) {
						userline += (waitUserList.get(i).user.getIdName() + ":");
					}
					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw.println(Data.ENTERLOGIN_OK + "|" + tempRoom.getMasterName() + "| 님이"
								+ tempRoom.getrID() + "번 방을 만들었습니다. |" + userline);
						waitUserList.get(i).pw.flush();
					}


				} else if (line[0].compareTo(Data.ENTERROOM) == 0) { // [방 입장버튼]

					String thisName = waitUserList.get(waitUserList.indexOf(this)).user.getIdName(); //유저 이름

					int roomid = Integer.parseInt(line[1]); // 룸ID

					int index = 0;
					for (int i = 0; i < roomtotalList.size(); i++) {
						if (roomtotalList.get(i).getrID() == roomid) {
							roomtotalList.get(i).roomInUserList.add(this); // 방에 유저 넣고
							priRoom = roomtotalList.get(i);
							index = i;
						}
					}

					String roomListMessage = "";
					for (int i = 0; i < roomtotalList.size(); i++) {
						roomListMessage += (roomtotalList.get(i).getrID() + "%" + roomtotalList.get(i).getTitle() + "%"
								+ roomtotalList.get(i).getUserCount() + "%" + roomtotalList.get(i).getMasterName() + "%"
								+ roomtotalList.get(i).roomInUserList.size() + "-");
					}

					System.out.println(roomListMessage);
					System.out.println(thisName);

					String roomMember = ""; // --->여기 방마다 방에 유저를 넣어주는 것 추가 방에 입장한 유저를 가지고와야해 ㅁ몇번방인지 찾아야함

					for (int i = 0; i < roomtotalList.get(index).roomInUserList.size(); i++) { // 룸안에 유저의 수만큼
						roomMember += (roomtotalList.get(index).roomInUserList.get(i).user.getIdName() + "%");
					}

					for (int i = 0; i < waitUserList.size(); i++) {
						if (waitUserList.get(i).user.getIdName().compareTo(thisName) == 0) { // 방들어가는사람에게는
																								// 바로
																								// 채팅화면으로\
							waitUserList.get(i).pw.println(Data.ENTERROOM_OK1 + "|" + "message");
							waitUserList.get(i).pw.flush();
						} else { // 다른 대기방사람들에게는 대기방만 새로고침
							waitUserList.get(i).pw.println(Data.ROOMMAKE_OK + "|" + roomListMessage); // 룸리스트 새로고침
							waitUserList.get(i).pw.flush();
						}

					}

//					String folder = "C:\\eclipse\\WorkSpace\\CooProject\\roomFolder\\" + priRoom.getrID() + "\\";
//					System.out.println(folder);
//					// 폴더명으로 파일객체 생성
//					File file = new File(folder);
//
//					// 폴더라면 폴더가 가진 파일객체를 리스트로 받는다.
//					File[] list = file.listFiles();
//
//					String fileList = "";
//					// 리스트에서 파일을 하나씩 꺼낸다
//					for (File f : list) {
//						// 파일일 경우만 출력
//						if (f.isFile()) {
//							fileList += (f.getName() + "%");
//						}
//						System.out.println();
//					}
//					System.out.println("FileList : " + fileList);
//
//					for (int i = 0; i < roomtotalList.get(index).roomInUserList.size(); i++) {
//						roomtotalList.get(index).roomInUserList.get(i).pw.println(Data.ENTERROOM_USERLISTSEND + "|"
//								+ roomMember + "|" + user.getIdName() + "님이 입장하셨습니다.|" + fileList);
//						roomtotalList.get(index).roomInUserList.get(i).pw.flush();
//					}

					waitUserList.remove(this); // 대기방에서 나가고
					System.out.println("방입장동작 부분  -->>[대기실 인원수 ]" + waitUserList.size());

					String userline = ""; // 채팅창에

					for (int i = 0; i < waitUserList.size(); i++) {
						userline += (waitUserList.get(i).user.getIdName() + ":" + "");
					}
					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw.println(
								Data.EXITWAITROOM + "|" + thisName + "|님이 " + roomid + "방에 입장하였습니다.|" + userline);// 대기방에
																													// 바꿔주고
						// Message
						// 전송;
						waitUserList.get(i).pw.flush();
					}

				} else if (line[0].compareTo(Data.EXITCHATTINGROOM) == 0) // 방 나가기 버튼.
				{

					int roomIndex = 0;
					boolean con = true;

					for (int i = 0; i < roomtotalList.size(); i++) {
						if (roomtotalList.get(i).getrID() == priRoom.getrID()) {

							if (roomtotalList.get(i).roomInUserList.size() == 1) // 나올 자기가 마지막일 때.
							{
								System.out.println("나올때 내가 마지막일때");
								roomtotalList.remove(priRoom);
								priRoom = new Room();
								con = false;

							} else { // 최소 2명일 때
								System.out.println("나올때 내가 마지막아닐때!! XXX");
								roomtotalList.get(i).roomInUserList.remove(this); // 방에 유저 빼고
								priRoom = new Room();// 현재룸을 비워주고
								roomIndex = i;

							}

						}
					}

					if (con) // 남아있는방에 최소 2명이상일때
					{
						String roomMember = ""; // --->여기 방마다 방에 유저를 넣어주는 것 추가 방에 입장한 유저를 가지고와야해 ㅁ몇번방인지 찾아야함

						for (int i = 0; i < roomtotalList.get(roomIndex).roomInUserList.size(); i++) { // 룸안에 유저의 수만큼
							roomMember += (roomtotalList.get(roomIndex).roomInUserList.get(i).user.getIdName() + "%");
						}

						System.out.println("특정 방의 인원 수 : " + roomtotalList.get(roomIndex).roomInUserList.size());
						System.out.println(roomMember);
						for (int i = 0; i < roomtotalList.get(roomIndex).roomInUserList.size(); i++) {
							roomtotalList.get(roomIndex).roomInUserList.get(i).pw.println(Data.ENTERROOM_USERLISTSEND
									+ "|" + roomMember + "|" + user.getIdName() + "님이 퇴장하셨습니다.");
							roomtotalList.get(roomIndex).roomInUserList.get(i).pw.flush();
						}
					}

					String roomListMessage = "";

					System.out.println(roomListMessage);

					waitUserList.add(this); // 대기방에서 추가
					if (roomtotalList.size() > 0) {
						roomListMessage = "";
						for (int i = 0; i < roomtotalList.size(); i++) {
							roomListMessage += (roomtotalList.get(i).getrID() + "%" + roomtotalList.get(i).getTitle()
									+ "%" + roomtotalList.get(i).getUserCount() + "%"
									+ roomtotalList.get(i).getMasterName()

									+ roomtotalList.get(i).roomInUserList.size() + "-");
						}
					} else {
						roomListMessage = "-";
					}

					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw.println(Data.ROOMMAKE_OK + "|" + roomListMessage); // 룸리스트 새로고침
						waitUserList.get(i).pw.flush();
					}

					System.out.println("방퇴실동작 부분  -->>[대기실 인원수 ]" + waitUserList.size());
					String userline = ""; // 채팅창에
					for (int i = 0; i < waitUserList.size(); i++) {
						userline += (waitUserList.get(i).user.getIdName() + ":");
					}
					for (int i = 0; i < waitUserList.size(); i++) {
						waitUserList.get(i).pw
								.println(Data.EXITWAITROOM + "|" + user.getIdName() + "|님이 대기실에 입장하였습니다.|" + userline);// 대기방에
																														// 바꿔주고
						// Message
						// 전송;
						waitUserList.get(i).pw.flush();
					}

				} else if (line[0].compareTo(Data.CHATTINGSENDMESSAGE) == 0) // 채팅방에서 메세지 보내기
				{

					int roomUserSize = roomtotalList.get(roomtotalList.indexOf(priRoom)).roomInUserList.size();

					for (int i = 0; i < roomUserSize; i++) {
						roomtotalList.get(roomtotalList.indexOf(priRoom)).roomInUserList.get(i).pw
								.println(Data.CHATTINGSENDMESSAGE_OK + "|" + user.getIdName() + "|" + line[1]); // 채팅방
																												// 사람들에게
																												// 메세지
						roomtotalList.get(roomtotalList.indexOf(priRoom)).roomInUserList.get(i).pw.flush();
					}

				}

			} // while

			br.close();
			pw.close();
			socket.close();

		} catch (

		IOException io) {
			io.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}