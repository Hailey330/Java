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

import Chatting.GameFrame;
import Room.RoomFrame;
import Room.RoomMake;
import Server.Data;

public class EnterFrame extends JFrame implements ActionListener, KeyListener, Runnable {
	private JPasswordField pwT;
	private JTextField idT;// , pwT;
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

		network();

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
		chattingF.openB.addActionListener(this);
		chattingF.saveB.addActionListener(this);
		chattingF.loadB.addActionListener(this);
		chattingF.field.addKeyListener(this); // 엔터치면 전송

	}

	public void network() {

		// 소켓 생성
		try {
			socket = new Socket("192.168.0.61", 9500);
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
			this.setVisible(false);
			membershipF.setVisible(true);

		} else if (e.getSource() == membershipF.joinB) { // 회원가입 page -> 가입 버튼

			String name = membershipF.nameT.getText();
			String id = membershipF.idT.getText();
			String pw1 = membershipF.pwT.getText();

			if (name.length() == 0 || id.length() == 0 || pw1.length() == 0) {
				JOptionPane.showMessageDialog(this, "빈 칸을 입력해주세요.");

			} else if (condition_Id == false) { // 아이디 중복 확인
				JOptionPane.showMessageDialog(this, "ID 중복 확인해주세요.");

			} else {
				String line = "";
				line += (membershipF.idT.getText() + "%" + membershipF.pwT.getText() + "%"
						+ membershipF.nameT.getText());
				System.out.println(line);

				pw.println(Data.REGISTER + "|" + line);
				pw.flush();
				JOptionPane.showMessageDialog(this, "회원 가입 완료!");
				membershipF.setVisible(false);
				this.setVisible(true);

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
				JOptionPane.showMessageDialog(this, "아이디를 입력하세요");
			} else {
				pw.println(Data.IDSEARCHCHECK + "|" + membershipF.idT.getText());
				pw.flush();
			}

		} else if (e.getSource() == accessB) { // 메인 page -> 대기실 (Login)

			String id1 = idT.getText();
			String pwss = pwT.getText();

			if (id1.length() == 0 || pwss.length() == 0) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
			} else {
				String line = id1 + "%" + pwss;
				pw.println(Data.ENTERLOGIN + "|" + line);
				pw.flush();
			}
			idT.setText("");
			pwT.setText("");

		} else if (e.getSource() == RoomF.exitB) { // 대기실 page -> 로그인 page (로그아웃)

			RoomF.setVisible(false);
			this.setVisible(true);

			pw.println(Data.EXITWAITROOM + "|" + "message");
			pw.flush();

		} else if (e.getSource() == RoomF.sendB) { // 대기실 page -> 채팅 messgae 전송

			String line = RoomF.chattxt.getText();
			if (RoomF.chattxt.getText().length() != 0) {
				pw.println(Data.SENDMESSAGE + "|" + line);
				pw.flush();
				RoomF.chattxt.setText("");
			}

		} else if (e.getSource() == RoomF.makeB) { // 대기실 page -> 방 만들기 버튼
			RoomF.setVisible(false);
			rMakeF.setVisible(true);

		} else if (e.getSource() == rMakeF.makeB) { // 방 만들기 page -> 방 만들기 버튼
			String title = rMakeF.tf.getText();
			String userCount = (String) rMakeF.combo1.getSelectedItem();

			if (title.length() == 0) {
				JOptionPane.showMessageDialog(this, "방 제목을 입력해주세요.");
			} else {
				String line = "";
				line += (title + "%" + userCount);
				pw.println(Data.ROOMMAKE + "|" + line);
				pw.flush();

//					rMakeF.setVisible(false);
//					RoomF.setVisible(true);
				rMakeF.tf.setText("");
				rMakeF.combo1.setSelectedIndex(0);
			}

		} else if (e.getSource() == rMakeF.canB) { // 방 만들기 page -> 취소 버튼
			rMakeF.setVisible(false);
			RoomF.setVisible(true);
			rMakeF.tf.setText("");
			rMakeF.combo1.setSelectedIndex(0);

		} else if (e.getSource() == chattingF.exitB) { // 게임방 page -> 나가기 버튼

			chattingF.setVisible(false);
			RoomF.setVisible(true);
			chattingF.model.removeAllElements();

			pw.println(Data.EXITCHATTINGROOM + "|" + "Message");
			pw.flush();

			chattingF.partList.setText("asd");

		} else if (e.getSource() == chattingF.sendB) { // 게임방 page -> 채팅 message 전송
			pw.println(Data.CHATTINGSENDMESSAGE + "|" + chattingF.field.getText());
			pw.flush();
			chattingF.field.setText("");

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
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
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// 받는 쪽
		String line[] = null;
		while (true) {
			try {
				line = br.readLine().split("\\|");
				if (line == null) {
					br.close();
					pw.close();
					socket.close();

					System.exit(0);

				} else if (line[0].compareTo(Data.IDSEARCHCHECK_OK) == 0) { // 회원가입 ID 중복 안됨
					JOptionPane.showMessageDialog(this, "사용가능");
					condition_Id = true;
				} else if (line[0].compareTo(Data.IDSEARCHCHECK_NO) == 0) { // 회원가입 ID 중복 됨
					JOptionPane.showMessageDialog(this, "사용 불가능");
					condition_Id = false;

				} else if (line[0].compareTo(Data.ENTERLOGIN_OK) == 0) { // 로그인 성공

					this.setVisible(false);
					RoomF.setVisible(true);
					RoomF.chatarea.append(line[1] + line[2] + '\n');

					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist);

				} else if (line[0].compareTo(Data.ENTERLOGIN_NO) == 0) { // 로그인 실패

					JOptionPane.showMessageDialog(this, line[1]);
					System.out.println("로그인 실패");

				} else if (line[0].compareTo(Data.EXITWAITROOM) == 0) { // 대기실 -> 로그인 page (로그아웃)

					RoomF.chatarea.append(line[1] + line[2] + '\n');

					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist);

				} else if (line[0].compareTo(Data.SENDMESSAGE_ACK) == 0) { // 대기실 message 받음

					RoomF.chatarea.append("[" + line[1] + "] :" + line[2] + '\n');

				} else if (line[0].compareTo(Data.ROOMMAKE_OK) == 0) { // 방 생성

					System.out.println("게임방 생성");
					String roomList[] = line[1].split("-"); // 방 갯수
					for (int i = 0; i < roomList.length; i++) {
						System.out.print(roomList[i] + "/");
					}

					String roomListDetail[]; // 방 세부
					System.out.println("RoomList size : " + roomList.length);

					RoomF.containPanelClear(); // 대기실 FRAME 컨테이너를 비우기
					for (int i = 0; i < roomList.length; i++) {

						RoomF.dp[i].init(); // 게임방 리스트를 다시 생성하기
						roomListDetail = roomList[i].split("%");
						String userNumber = "";

//						if (roomListDetail.length == 8) { // 비공개방
//
//							userNumber += (roomListDetail[7] + "/" + roomListDetail[3]);
//
//							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // 방번호
//							RoomF.dp[i].labelArray[3].setText(roomListDetail[5]); // 방주제
//							RoomF.dp[i].labelArray[5].setText(userNumber); // 인원수
//							RoomF.dp[i].labelArray[7].setText(roomListDetail[1]); // 방제목
//							RoomF.dp[i].labelArray[8].setText("개설자 : " + roomListDetail[4]); // 개설자
//						} 
						
						if (roomListDetail.length == 6) { // 0, 1(방번호), 2, 3(방제목), 4, 5(인원수), 6(방장)

							userNumber += (roomListDetail[4] + "/" + roomListDetail[2]);
							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // 방 번호
							RoomF.dp[i].labelArray[3].setText(roomListDetail[1]); // 방 제목 
							RoomF.dp[i].labelArray[5].setText(userNumber); // 인원 수 
							RoomF.dp[i].labelArray[6].setText("방장 : " + roomListDetail[3]); // 방장
						}
						
						System.out.println("userNumber : " + userNumber);

					}
					chattingF.area.setText("");
					chattingF.area1.setText("");
					rMakeF.setVisible(false); // 대기방 화면 끄고
					RoomF.setVisible(true);

				} else if (line[0].compareTo(Data.ROOMMAKE_OK1) == 0) { // 게임 방 생성 - 방장 입장

					rMakeF.setVisible(false); // 방 만들기 화면 끄기
					chattingF.area.setText("");
					chattingF.setVisible(true);
					chattingF.partList.setText(line[1] + "\n");

				} else if (line[0].compareTo(Data.ENTERROOM_OK1) == 0) { // 방입장 입장하는 당사자

					System.out.println("입장화면 변환");
					RoomF.setVisible(false);
					chattingF.area1.setText("");
					chattingF.area.setText("");
					chattingF.setVisible(true);
//					System.out.println(line[2]);
//					String roomMember[] = line[2].split("%");//룸에 들어온사람들
//					chattingF.partList.append(line[1]); //자기 추가해주고
//					for (int i = 0; i < roomMember.length; i++) {
//						chattingF.partList.append(roomMember[i] + "\n");
//					}

				} else if (line[0].compareTo(Data.ENTERROOM_USERLISTSEND) == 0) { // 채팅방 리스트 새로고침

					String roomMember[] = line[1].split("%");// 룸에 들어온사람들
					String lineList = "";
					for (int i = 0; i < roomMember.length; i++) {
						lineList += (roomMember[i] + "\n");
					}

					chattingF.partList.setText(lineList);
					chattingF.area1.append(line[2] + "\n");

					if (line.length == 4) {
						String fileList[] = line[3].split("%");
						chattingF.model.removeAllElements();
						for (int i = 0; i < fileList.length; i++) {
							chattingF.model.addElement(fileList[i]);
						}
					}

				} else if (line[0].compareTo(Data.CHATTINGSENDMESSAGE_OK) == 0) {
					chattingF.area1.append("[" + line[1] + "] :" + line[2] + "\n");
				}

			} catch (IOException io) {
				io.printStackTrace();
			}

		} // while
	}

}