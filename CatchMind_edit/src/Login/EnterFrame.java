package Login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Game.GameFrame;
import Room.RoomFrame;
import Room.RoomMake;
import Server.Data;

public class EnterFrame extends JFrame implements ActionListener, KeyListener, Runnable {
	private JPasswordField pwT;
	private JTextField idT;
	private JButton idB, pwB, accessB, membershipB;
	private JLabel loginL, logoutL;
	private ImageIcon loginC, logoutC, modifiedC;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;

	MembershipB membershipF; // 회원가입
	RoomFrame RoomF; // 대기실
	RoomMake rMakeF; // 방만들기
	GameFrame chattingF;

	private boolean condition_Id = false; // ID 중복체크

	public EnterFrame() {

		network(); // 소켓 생성 

		membershipF = new MembershipB();
		RoomF = new RoomFrame(br, pw);
		rMakeF = new RoomMake();
		chattingF = new GameFrame();

		idB = new JButton("아이디");
		idT = new JTextField(15);
		pwB = new JButton("패스워드");
		pwT = new JPasswordField(15);
		pwT.setEchoChar('*');

		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(idB);
		p2.add(idT);
		p2.add(pwB);
		p2.add(pwT);

		membershipB = new JButton("회원가입");
		accessB = new JButton("입장");

		JPanel p3 = new JPanel();
		p3.add(membershipB);
		p3.add(accessB);

		loginC = new ImageIcon("img/login.png");
		loginL = new JLabel(loginC);

		JPanel p4 = new JPanel();
		p4.add(loginL);

		Container contentPane = this.getContentPane();
		contentPane.add("North", p2);
		contentPane.add("South", p3);
		contentPane.add("Center", p4);

		setVisible(true);
		setResizable(false);
		setBounds(400, 200, 1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		event();

	}

	public void event() {
		// -------------------- 회원가입 --------------------------------
		membershipB.addActionListener(this); // 회원가입(버튼)
		membershipF.calneB.addActionListener(this); // 회원가입 취소(로그인 화면으로)
		membershipF.joinB.addActionListener(this); // 회원가입 화면에서 join
		membershipF.idoverlapB.addActionListener(this);// 회원가입 화면 중복확인

		// -------------------- 로그인 ----------------------------------
		accessB.addActionListener(this); // 입장(Login)
		RoomF.exitB.addActionListener(this); // Room -> 로그인Page

		// --------------------- 메세지 ---------------------------------
		RoomF.sendB.addActionListener(this); // 대기방에서 전송
		RoomF.chattxt.addKeyListener(this); // 엔터치면 전송

		// ---------------------- 대기방 --------------------------------
		RoomF.makeB.addActionListener(this);
		rMakeF.makeB.addActionListener(this);
		rMakeF.canB.addActionListener(this);
		chattingF.exitB.addActionListener(this);
		chattingF.sendB.addActionListener(this);
		// ---------------------- 채팅방 --------------------------------
		chattingF.quizB.addActionListener(this);
		chattingF.loadB.addActionListener(this);
		chattingF.field.addKeyListener(this); // 엔터치면 전송

	}

	public void network() {

		// 소켓 생성
		try {
			socket = new Socket("localhost", 9500);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (UnknownHostException e) {
			System.out.println("서버를 찾을 수 없습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("서버와 연결되지 않았습니다.");
			e.printStackTrace();
			System.exit(0);
		}

		// 스레드 생성
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == membershipB) { // 메인 page -> 회원가입 버튼
			this.setVisible(true); // 메인화면 창 닫힘
			membershipF.setVisible(true); // 회원가입 창 열림

		} else if (e.getSource() == membershipF.joinB) { // 회원가입 page -> 가입 버튼

			String name = membershipF.nameT.getText(); // 회원가입 이름 받음
			String id = membershipF.idT.getText(); // 회원가입 아이디 받고 
			String pw1 = membershipF.pwT.getText(); // 비밀번호 받고 

			if (name.length() == 0 || id.length() == 0 || pw1.length() == 0) {
				JOptionPane.showMessageDialog(membershipF, "빈 칸을 입력해주세요."); 

			} else if (condition_Id == false) { // 아이디 중복 확인
				JOptionPane.showMessageDialog(membershipF, "ID 중복 확인해주세요.");

			} else {
				String line = "";
				line += (membershipF.idT.getText() + "%" + membershipF.pwT.getText() + "%"
						+ membershipF.nameT.getText());
				System.out.println(line);

				pw.println(Data.REGISTER + "|" + line);
				pw.flush();
				JOptionPane.showMessageDialog(membershipF, "회원 가입 완료!");
				membershipF.setVisible(false);
				this.setVisible(true);
				
				// 초기화
				membershipF.nameT.setText("");
				membershipF.idT.setText("");
				membershipF.pwT.setText("");
				condition_Id = false;
			}

		} else if (e.getSource() == membershipF.calneB) { // 회원가입 page -> 취소 버튼
			membershipF.setVisible(false); 
			this.setVisible(true);
			condition_Id = false;

		} else if (e.getSource() == membershipF.idoverlapB) { // 회원가입 페이지ID -> 중복 확인

			if (membershipF.idT.getText().length() == 0) {
				JOptionPane.showMessageDialog(membershipF, "아이디를 입력하세요");
			} else {
				pw.println(Data.IDSEARCHCHECK + "|" + membershipF.idT.getText());
				pw.flush();
			}

		} else if (e.getSource() == accessB) { // 메인 page -> 대기실 (Login했을 때)

			String id1 = idT.getText(); // 아이디, 비밀번호 값을 받고 
			String pwss = pwT.getText();

			if (id1.length() == 0 || pwss.length() == 0) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
			} else {
				String line = id1 + "%" + pwss;
				pw.println(Data.ENTERLOGIN + "|" + line);
				pw.flush();
			}
			// 초기화
			idT.setText("");
			pwT.setText("");

		} else if (e.getSource() == RoomF.exitB) { // 대기실 page -> 로그인 page (로그아웃)

			RoomF.setVisible(false); // 대기실 창 꺼지고
			this.setVisible(true); // 메인화면 창 나오고 

			pw.println(Data.EXITWAITROOM + "|" + "message"); // ~님이 퇴장하셨습니다 문구 뜸
			pw.flush();

		} else if (e.getSource() == RoomF.sendB) { // 대기실 page -> 채팅 messgae 전송

			String line = RoomF.chattxt.getText(); // 입력받는 문구 받음
			if (RoomF.chattxt.getText().length() != 0) { // 들어오는 문구가 있을 때
				pw.println(Data.SENDMESSAGE + "|" + line);
				pw.flush();
				
				RoomF.chattxt.setText(""); // 입력 값 비워둠 
			}

		} else if (e.getSource() == RoomF.makeB) { // 대기실 page -> 방 만들기 버튼
			RoomF.setVisible(true); // 대기실 사라지고
			rMakeF.setVisible(true); // 방 만들기 창 나오고 

		} else if (e.getSource() == rMakeF.makeB) { // 방 만들기 page -> 방 생성 버튼
			String title = rMakeF.tf.getText(); // 방 제목 입력 받고
			String userCount = (String) rMakeF.combo1.getSelectedItem(); // 인원 수 콤보박스 

			if (title.length() == 0) { 
				JOptionPane.showMessageDialog(rMakeF, "방 제목을 입력해주세요.");
			} else {
				String line = "";
				line += (title + "%" + userCount);
				pw.println(Data.ROOMMAKE + "|" + line);
				pw.flush();
				RoomF.setVisible(false); // 대기실 창 사라짐

				// 초기화 - 방 타이틀, 인원 수 
				rMakeF.tf.setText("");
				rMakeF.combo1.setSelectedIndex(0);
			}

		} else if (e.getSource() == rMakeF.canB) { // 방 만들기 page -> 취소 버튼
			rMakeF.setVisible(false); // 방 만들기 창 꺼지고
			RoomF.setVisible(true); // 대기방 뜨고
			
			rMakeF.tf.setText("");
			rMakeF.combo1.setSelectedIndex(0);

		} else if (e.getSource() == chattingF.exitB) { // 게임방 page -> 나가기 버튼
			chattingF.setVisible(false); // 게임방 창 꺼지고
			RoomF.setVisible(true); // 대기방 창 열리고

			pw.println(Data.EXITCHATTINGROOM + "|" + "Message");
			pw.flush();

			chattingF.partList.setText("asd"); // ??? 뭐지 

		} else if (e.getSource() == chattingF.sendB) { // 게임방 page -> 채팅 message 전송
			pw.println(Data.CHATTINGSENDMESSAGE + "|" + chattingF.field.getText());
			pw.flush();
			chattingF.field.setText("");

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) { // 키보드 이벤트 - 엔터 쳤을 때 대화 넘어감 
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (RoomF.chattxt.getText().length() != 0) {
				RoomF.sendB.doClick();
			} else if (chattingF.field.getText().length() != 0) {
				chattingF.sendB.doClick();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void run() {
		// 받는 쪽
		String line[] = null; // 파싱한 데이터를 배열에 담는다. 
		while (true) {
			try {
				line = br.readLine().split("\\|"); // 받는 데이터를 파싱
				if (line == null) { // 들어오는 데이터가 없으면 br, pw, 소켓 닫힘
					br.close();
					pw.close();
					socket.close();

					System.exit(0);

				} else if (line[0].compareTo(Data.IDSEARCHCHECK_OK) == 0) { // 회원가입 ID 중복 안됨
					JOptionPane.showMessageDialog(membershipF, "사용 가능합니다.");
					condition_Id = true;
				} else if (line[0].compareTo(Data.IDSEARCHCHECK_NO) == 0) { // 회원가입 ID 중복 됨
					JOptionPane.showMessageDialog(membershipF, "사용 불가능합니다.");
					condition_Id = false;

				} else if (line[0].compareTo(Data.ENTERLOGIN_OK) == 0) { // 로그인 성공

					this.setVisible(false); // 메인화면 사라지고
					RoomF.setVisible(true); // 대기창 뜨고 
					RoomF.chatarea.append(line[1] + line[2] + '\n'); // 대기창의 채팅방에 누가 입장했다고 문구 뜸 - MainHandler 에 있음 
					
					// 대기실 인원 수  
					String text[] = line[3].split(":"); // 
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist); // 대기실 인원 수를 화면에 뿌려줌 

				} else if (line[0].compareTo(Data.ENTERLOGIN_NO) == 0) { // 로그인 실패

					JOptionPane.showMessageDialog(this, "로그인에 실패했어요"); // 메인화면에 문구 뜸 
					System.out.println("로그인 실패");

				} else if (line[0].compareTo(Data.EXITWAITROOM) == 0) { // 대기실 -> 로그인 page (로그아웃)

					RoomF.chatarea.append(line[1] + line[2] + '\n'); // 대기실 창에 메세지 뜸 - MainHandler 연동 
					
					// 대기실 인원 수 나가기 
					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist); // 유저 리스트 화면에 뿌려줌  

				} else if (line[0].compareTo(Data.SENDMESSAGE_ACK) == 0) { // 대기실 message 보내기

					RoomF.chatarea.append("[" + line[1] + "] :" + line[2] + '\n'); // [유저 name] : message 

				} else if (line[0].compareTo(Data.ROOMMAKE_OK) == 0) { // 방 생성

					System.out.println("게임방 생성");
					String roomList[] = line[1].split("-"); // 방 생성시 대기실 화면에 나타나는 roomlist
					for (int i = 0; i < roomList.length; i++) {
						System.out.print(roomList[i] + "/");
					}

					String roomListDetail[]; // 방 세부 - 방 번호, 방 제목, 인원 수, 방장 (방장 나와야 함)
					System.out.println("RoomList size : " + roomList.length); // 방 몇 개인지 콘솔창에 뜸 

					RoomF.containPanelClear(); // 대기실 FRAME 컨테이너를 비우기 - 방 생성 동기화
					for (int i = 0; i < roomList.length; i++) {

						RoomF.dp[i].init(); // 게임방 리스트를 다시 생성하기 - DetailPanel = dp 
						roomListDetail = roomList[i].split("%");
						String userNumber = ""; // 한 방의 인원 수 
						
						if (roomListDetail.length == 6) { // 0, 1(방번호), 2, 3(방제목), 4, 5(인원수), 6(방장)

							userNumber += (roomListDetail[4] + "/" + roomListDetail[2]); // 들어가 있는 유저 수 / 내가 설정한 유저 수
							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // 방 번호
							RoomF.dp[i].labelArray[3].setText(roomListDetail[1]); // 방 제목 
							RoomF.dp[i].labelArray[5].setText(userNumber); // 인원 수 
							RoomF.dp[i].labelArray[6].setText("방장 : " + roomListDetail[3]); // 방장
						}
						
						System.out.println("userNumber : " + userNumber);

					}
					// 초기화 
					chattingF.area.setText("");
					chattingF.area1.setText("");
					rMakeF.setVisible(false); // 방 만들기 창 꺼지고 
					RoomF.setVisible(true); // 대기실 창 뜨고

				} else if (line[0].compareTo(Data.ROOMMAKE_OK1) == 0) { // 게임 방 생성 - 방장 입장

					rMakeF.setVisible(false); // 방 만들기 창 꺼지고 
					chattingF.area.setText(""); 
					chattingF.setVisible(true); // 게임 창 채팅 뜨고  
					chattingF.partList.setText(line[1] + "\n"); // 참여 인원 리스트

				} else if (line[0].compareTo(Data.ENTERROOM_OK1) == 0) { // 게임 방 유저 입장 

					System.out.println("유저가 게임방으로 입장");
					RoomF.setVisible(false); // 대기실 창 꺼지고 
					chattingF.area1.setText(""); 
					chattingF.area.setText("");
					chattingF.setVisible(true); // 게임 채팅 창 뜨고 

				} else if (line[0].compareTo(Data.ENTERROOM_USERLISTSEND) == 0) { // 게임방의 유저 리스트 새로고침

					String roomMember[] = line[1].split("%");// 게임방에 들어온 유저들 
					String lineList = "";
					for (int i = 0; i < roomMember.length; i++) {
						lineList += (roomMember[i] + "\n");
					}

					chattingF.partList.setText(lineList); // 참여 유저 리스트 동기화 
					chattingF.area1.append(line[2] + "\n"); // 참여 유저 이름 표시 

//					if (line.length == 4) {
//						String fileList[] = line[3].split("%");
//						chattingF.model.removeAllElements();
//						for (int i = 0; i < fileList.length; i++) {
//							chattingF.model.addElement(fileList[i]);
//						}
					}

//				} else 
				if (line[0].compareTo(Data.CHATTINGSENDMESSAGE_OK) == 0) {
					chattingF.area1.append("[" + line[1] + "] :" + line[2] + "\n");
				}

			} catch (IOException io) {
				io.printStackTrace();
			}

		} // while
	}

}