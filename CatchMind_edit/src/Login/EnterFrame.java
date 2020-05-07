package Login;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Server.Data;
import net.nurigo.java_sdk.api.SenderID;
import Chatting.CoprocessFrame;
import Room.RoomFrame;
import Room.RoomMake;

public class EnterFrame extends JFrame implements ActionListener, Runnable, ListSelectionListener, KeyListener {
	private JPasswordField pwT;
	private JTextField idT;// , pwT;
	private JButton idB, pwB, accessB, membershipB;
	private JLabel loginL, logoutL;
	private ImageIcon loginC, logoutC, modifiedC;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;

	MembershipB membershipF; // ȸ������
	RoomFrame RoomF; // ����
	RoomMake rMakeF; // �游���
	CoprocessFrame chattingF;

//	private String sNumber = "><^^"; // default ��ũ���ѹ�
//	private boolean condition_S = false; // �̸��� ����Ȯ��
	private boolean condition_Id = false; // ID �ߺ�üũ

	public EnterFrame() {

		network();

		membershipF = new MembershipB();
		RoomF = new RoomFrame(br, pw);
		rMakeF = new RoomMake();
		chattingF = new CoprocessFrame();

		idB = new JButton("���̵�");
		idT = new JTextField(15);
		pwB = new JButton("�н�����");
		pwT = new JPasswordField(15);
		pwT.setEchoChar('*');

		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(idB);
		p2.add(idT);
		p2.add(pwB);
		p2.add(pwT);

		membershipB = new JButton("ȸ������");
		accessB = new JButton("����");

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
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		event();

	}

	public void event() {
		// -------------------- ȸ������ --------------------------------
		membershipB.addActionListener(this); // ȸ������(��ư)
		membershipF.calneB.addActionListener(this); // ȸ������ ���(�α��� ȭ������)
		membershipF.joinB.addActionListener(this); // ȸ������ ȭ�鿡�� join
		membershipF.idoverlapB.addActionListener(this);// ȸ������ ȭ�� �ߺ�Ȯ��

		// -------------------- �α��� ----------------------------------
		accessB.addActionListener(this); // ����(Login)
		RoomF.exitB.addActionListener(this); // Room -> �α���Page

		// --------------------- �޼��� ---------------------------------
		RoomF.sendB.addActionListener(this); // ���濡�� ����
		RoomF.chattxt.addKeyListener(this); // ����ġ�� ����

		// ---------------------- ���� --------------------------------
		RoomF.makeB.addActionListener(this);
		rMakeF.makeB.addActionListener(this);
		rMakeF.canB.addActionListener(this);
		chattingF.exitB.addActionListener(this);
		chattingF.sendB.addActionListener(this);
		// ---------------------- ä�ù� --------------------------------
		chattingF.openB.addActionListener(this);
		chattingF.saveB.addActionListener(this);
		chattingF.loadB.addActionListener(this);
		chattingF.list2.addListSelectionListener(this);
		chattingF.field.addKeyListener(this); // ����ġ�� ����

	}

	public void network() {

		// ���� ����
		try {
			socket = new Socket("localhost", 9500);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (UnknownHostException e) {
			System.out.println("������ ã�� �� �����ϴ�.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("������ ������� �ʾҽ��ϴ�.");
			e.printStackTrace();
			System.exit(0);
		}

		// ������ ����
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == membershipB) { // ���� page -> ȸ������ ��ư
			this.setVisible(false);
			membershipF.setVisible(true);

		} else if (e.getSource() == membershipF.joinB) { // ȸ������ page -> ���� ��ư

			String name = membershipF.nameT.getText();
			String id = membershipF.idT.getText();
			String pw1 = membershipF.pwT.getText();

			if (name.length() == 0 || id.length() == 0 || pw1.length() == 0) {
				JOptionPane.showMessageDialog(this, "�� ĭ�� �Է����ּ���.");

			} else if (condition_Id == false) { // ���̵� �ߺ� Ȯ��
				JOptionPane.showMessageDialog(this, "ID �ߺ� Ȯ�����ּ���.");

			} else {
				String line = "";
				line += (membershipF.idT.getText() + "%" + membershipF.pwT.getText() + "%"
						+ membershipF.nameT.getText());
				System.out.println(line);

				pw.println(Data.REGISTER + "|" + line);
				pw.flush();
				JOptionPane.showMessageDialog(this, "ȸ�� ���� �Ϸ�!");
				membershipF.setVisible(false);
				this.setVisible(true);

				membershipF.nameT.setText("");
				membershipF.idT.setText("");
				membershipF.pwT.setText("");

				condition_Id = false;
			}

		} else if (e.getSource() == membershipF.calneB) { // ȸ������ page -> ��� ��ư
			membershipF.setVisible(false);
			this.setVisible(true);
			condition_Id = false;

		} else if (e.getSource() == membershipF.idoverlapB) { // ȸ������ ������ID -> �ߺ� Ȯ��

			if (membershipF.idT.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "���̵� �Է��ϼ���");
			} else {
				pw.println(Data.IDSEARCHCHECK + "|" + membershipF.idT.getText());
				pw.flush();
			}

		} else if (e.getSource() == accessB) { // ���� page -> ���� (Login)

			String id1 = idT.getText();
			String pwss = pwT.getText();

			if (id1.length() == 0 || pwss.length() == 0) {
				JOptionPane.showMessageDialog(this, "���̵�� ��й�ȣ�� �Է����ּ���.");
			} else {
				String line = id1 + "%" + pwss;
				pw.println(Data.ENTERLOGIN + "|" + line);
				pw.flush();
			}
			idT.setText("");
			pwT.setText("");

//		} else if (e.getSource() == searchpwF.cancleB) { // PWã�������� -->PW ã�� ���
//			searchpwF.setVisible(false);
//			this.setVisible(true);

		} else if (e.getSource() == RoomF.exitB) { // ���� page -> �α��� page (�α׾ƿ�)

			RoomF.setVisible(false);
			this.setVisible(true);

			pw.println(Data.EXITWAITROOM + "|" + "message");
			pw.flush();

		} else if (e.getSource() == RoomF.sendB) { // ���� page -> ä�� messgae ����

			String line = RoomF.chattxt.getText();
			if (RoomF.chattxt.getText().length() != 0) {
				pw.println(Data.SENDMESSAGE + "|" + line);
				pw.flush();
				RoomF.chattxt.setText("");
			}

		} else if (e.getSource() == RoomF.makeB) { // ���� page -> �� ����� ��ư
			RoomF.setVisible(false);
			rMakeF.setVisible(true);

		} else if (e.getSource() == rMakeF.makeB) { // �� ����� page -> �� ����� ��ư
			String title = rMakeF.tf.getText();
//			String rPassword = rMakeF.pf.getText();
			String userCount = (String) rMakeF.combo1.getSelectedItem();
//			String subject = (String) rMakeF.combo.getSelectedItem();
//			int condition = rMakeF.cb.isSelected() ? 1 : 0;

			if (title.length() == 0) {
				JOptionPane.showMessageDialog(this, "�� ������ �Է����ּ���.");
			} else {
//				if (condition == 1 && rPassword.length() == 0) // PW�� ���ٰ��߳��� �ȳ�����
//				{
//					JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���");
//				} else if (condition == 1 && rPassword.length() != 0) {// PW�� ���ٰ��߳��� �������
//
//					String line = "";
//					line += (title + "%" + rPassword + "%" + userCount + "%" + subject + "%" + condition);
//					pw.println(Data.ROOMMAKE + "|" + line);
//					pw.flush();

//					rMakeF.setVisible(false);
//					RoomF.setVisible(true);

//					rMakeF.tf.setText("");
//					rMakeF.pf.setText("");
//					rMakeF.combo1.setSelectedIndex(0);
//					rMakeF.combo.setSelectedIndex(0);
//					rMakeF.cb.setSelected(false);

//				} else if (condition == 0 && rPassword.length() != 0) {
//					JOptionPane.showMessageDialog(this, "��й�ȣ ����� �������ּ���.");

				String line = "";
				line += (title + "%" + userCount);
//					+ subject + "%" + condition);
				pw.println(Data.ROOMMAKE + "|" + line);
				pw.flush();

//					rMakeF.setVisible(false);
//					RoomF.setVisible(true);
				rMakeF.tf.setText("");
//					rMakeF.pf.setText("");
				rMakeF.combo1.setSelectedIndex(0);
				rMakeF.combo.setSelectedIndex(0);
//					rMakeF.cb.setSelected(false);
			}

		} else if (e.getSource() == rMakeF.canB) { // �� ����� page -> ��� ��ư
			rMakeF.setVisible(false);
			RoomF.setVisible(true);
			rMakeF.tf.setText("");
//			rMakeF.pf.setText("");
			rMakeF.combo1.setSelectedIndex(0);
			rMakeF.combo.setSelectedIndex(0);
//			rMakeF.cb.setSelected(false);

		} else if (e.getSource() == chattingF.exitB) { // ���ӹ� page -> ������ ��ư

			chattingF.setVisible(false);
			RoomF.setVisible(true);
			chattingF.model.removeAllElements();

			pw.println(Data.EXITCHATTINGROOM + "|" + "Message");
			pw.flush();

			chattingF.partList.setText("asd");

		} else if (e.getSource() == chattingF.sendB) { // ���ӹ� page -> ä�� message ����
			pw.println(Data.CHATTINGSENDMESSAGE + "|" + chattingF.field.getText());
			pw.flush();
			chattingF.field.setText("");

		} else if (e.getSource() == chattingF.openB) // ���ӹ� page -> �� ���� ���� ����
		{
			chattingF.openDialog();
			chattingF.fileRead();

		} else if (e.getSource() == chattingF.saveB) // ä�ù濡�� ------> ������ ��������
		{
			chattingF.fileSave();
			chattingF.fileWrite();
//			listUpload();
			chattingF.openB.setEnabled(true);
			chattingF.saveB.setEnabled(true);
			chattingF.loadB.setEnabled(true);
			chattingF.deleteB.setEnabled(false);
			chattingF.exitB.setEnabled(true);

		} else if (e.getSource() == chattingF.loadB) {
			chattingF.openDialog();
			pw.println(Data.CHATTINGFILESEND_SYN + "|" + chattingF.file.getName());
			pw.flush();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			RoomF.sendB.doClick();
			chattingF.sendB.doClick();
		} 
		
		}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	

//	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		System.out.println("Listlistioner");
		for (int i = 0; i < chattingF.model.getSize(); i++) {
			if (chattingF.list2.isSelectedIndex(i)) {
				chattingF.fileSave();
				pw.println(Data.CHATTINGFILEDOWNLOAD_SYN + "|" + chattingF.list2.getSelectedValue());
				pw.flush();
			}
		}
	}

	@Override
	public void run() {
		// �޴���
		String line[] = null;
		while (true) {
			try {
				line = br.readLine().split("\\|");
				if (line == null) {
					br.close();
					pw.close();
					socket.close();

					System.exit(0);
				} else if (line[0].compareTo(Data.IDSEARCHCHECK_OK) == 0) { // ȸ������ ID �ߺ� �ȵ�
					JOptionPane.showMessageDialog(this, "��밡��");
					condition_Id = true;
				} else if (line[0].compareTo(Data.IDSEARCHCHECK_NO) == 0) { // ȸ������ ID �ߺ� ��
					JOptionPane.showMessageDialog(this, "��� �Ұ���");
					condition_Id = false;
				}

//				else if (line[0].compareTo(Data.IDSEARCH_OK) == 0) // ID ã�� ������ ����
//				{
//					JOptionPane.showMessageDialog(this, line[1]);
//					searchF.setVisible(false);
//					this.setVisible(true);
//				} else if (line[0].compareTo(Data.IDSEARCH_NO) == 0) // ID�� ����
//				{
//					JOptionPane.showMessageDialog(this, line[1]);
//					searchF.setVisible(false);
//					this.setVisible(true);
//				}

				else if (line[0].compareTo(Data.ENTERLOGIN_OK) == 0) // �α��� ����
				{
					this.setVisible(false);
					RoomF.setVisible(true);
					RoomF.chatarea.append(line[1] + line[2] + '\n');

					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist);

				} else if (line[0].compareTo(Data.ENTERLOGIN_NO) == 0) // �α��� ����
				{
					JOptionPane.showMessageDialog(this, line[1]);
					System.out.println("�α��� ����");
				} else if (line[0].compareTo(Data.EXITWAITROOM) == 0) // �α׾ƿ� [���� -> �α���������]
				{
					RoomF.chatarea.append(line[1] + line[2] + '\n');

					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist);

				} else if (line[0].compareTo(Data.SENDMESSAGE_ACK) == 0) // ������ �޼��� ���� [����]
				{
					RoomF.chatarea.append("[" + line[1] + "] :" + line[2] + '\n');

				} else if (line[0].compareTo(Data.ROOMMAKE_OK) == 0) // �游�����
				{
					System.out.println("�̰� �ǳ�?");
					String roomList[] = line[1].split("-"); // �� ����
					for (int i = 0; i < roomList.length; i++) {
						System.out.print(roomList[i] + "/");
					}

					String roomListDetail[]; // �漼��
					System.out.println("RoomList size : " + roomList.length);

					RoomF.containPanelClear(); // �� �����ӿ� �����̳ʸ� ����ְ�
					for (int i = 0; i < roomList.length; i++) {

						RoomF.dp[i].init(); // �渮��Ʈ�� �����ŷ� �ٽ� �������ְ�
						roomListDetail = roomList[i].split("%");
						String userNumber = "";

						if (roomListDetail.length == 8) // �������
						{
							userNumber += (roomListDetail[7] + "/" + roomListDetail[3]);

							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // ���ȣ
							RoomF.dp[i].labelArray[3].setText(roomListDetail[5]); // ������
							RoomF.dp[i].labelArray[5].setText(userNumber); // �ο���
							RoomF.dp[i].labelArray[7].setText(roomListDetail[1]); // ������
							RoomF.dp[i].labelArray[8].setText("������ : " + roomListDetail[4]); // ������
						} else if (roomListDetail.length == 7) // ������
						{
							userNumber += (roomListDetail[6] + "/" + roomListDetail[2]);
							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // ���ȣ
							RoomF.dp[i].labelArray[3].setText(roomListDetail[5]); // ������
							RoomF.dp[i].labelArray[5].setText(userNumber); // �ο���
							RoomF.dp[i].labelArray[7].setText(roomListDetail[1]); // ������
							RoomF.dp[i].labelArray[8].setText("������ : " + roomListDetail[3]); // ������
						}
						System.out.println("userNumber : " + userNumber);

					}
					chattingF.area.setText("");
					chattingF.area1.setText("");
					rMakeF.setVisible(false); // ���� ȭ�� ����
					RoomF.setVisible(true);

				} else if (line[0].compareTo(Data.ROOMMAKE_OK1) == 0) // �游����� (���� �����) // ����
				{
					rMakeF.setVisible(false); // ���� ȭ�� ����
					chattingF.area.setText("");
					chattingF.setVisible(true);
					chattingF.partList.setText(line[1] + "\n");

				} else if (line[0].compareTo(Data.ENTERROOM_OK1) == 0) // ������ �����ϴ� �����
				{
					System.out.println("����ȭ�� ��ȯ");
					RoomF.setVisible(false);
					chattingF.area1.setText("");
					chattingF.area.setText("");
					chattingF.setVisible(true);
//					System.out.println(line[2]);
//					String roomMember[] = line[2].split("%");//�뿡 ���»����
//					chattingF.partList.append(line[1]); //�ڱ� �߰����ְ�
//					for (int i = 0; i < roomMember.length; i++) {
//						chattingF.partList.append(roomMember[i] + "\n");
//					}

				} else if (line[0].compareTo(Data.ENTERROOM_USERLISTSEND) == 0) // ä�ù� ����Ʈ ���ΰ�ħ
				{

					String roomMember[] = line[1].split("%");// �뿡 ���»����
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
				} else if (line[0].compareTo(Data.CHATTINGFILESEND_SYNACK) == 0) {

					pw.println(Data.CHATTINGFILESEND_FILE + "|" + chattingF.file.length());
					pw.flush();

					OutputStream os = socket.getOutputStream();

					System.out.println("���� ������ ���� !!!");
					// ���� ������ �Է� ��Ʈ�� ��ü ����
					FileInputStream fis = new FileInputStream(chattingF.file.getAbsoluteFile());

					// ������ ������ ������
					byte[] b = new byte[512];
					int n;
					while ((n = fis.read(b, 0, b.length)) > 0) {
						os.write(b, 0, n);
						System.out.println(n + "bytes ���� !!!");
					}

					// ���Ͽ��� ���� ��� ��Ʈ���� ���Ѵ�.
				} else if (line[0].compareTo(Data.CHATTINGFILESEND_FILEACK) == 0) {

					String[] fileList = line[1].split("%");

					chattingF.model.removeAllElements();
					for (int i = 0; i < fileList.length; i++) {
						chattingF.model.addElement(fileList[i]);
					}

				} else if (line[0].compareTo(Data.CHATTINGFILEDOWNLOAD_SEND) == 0) { // ������ ����
					String path = chattingF.file.getAbsolutePath();

					FileOutputStream fos = new FileOutputStream(path);
					InputStream is = socket.getInputStream();

					System.out.println("���� �ٿ�ε� ���� !!!");

					// ������ ���� ������ ���Ͽ� ����

					byte[] b = new byte[512];

					int n = 0;
					long filesize = Long.parseLong(line[1]);

					while ((n = is.read(b, 0, b.length)) > 0) {

						fos.write(b, 0, n);
						System.out.println("N:" + n);
						System.out.println(n + "bytes �ٿ�ε� !!!");
						n += n;
						if (n >= filesize)
							break;
					}

					fos.close();
					System.out.println("���� �ٿ�ε� �� !!!");
				}

			} catch (IOException io) {
				io.printStackTrace();
			}
		} // while
	}

}