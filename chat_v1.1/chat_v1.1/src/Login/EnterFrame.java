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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Action.Protocol;
import CoControl.CoprocessFrame;
import Room.RoomFrame;
import Room.RoomMake;

public class EnterFrame extends JFrame implements ActionListener, KeyListener, Runnable, ListSelectionListener {
	private JPasswordField pwT;
	private JTextField idT;// , pwT;
	private JButton idB, pwB, accessB, /**searchidB, searchpwB,**/ membershipB;
	private JLabel loginL, logoutL;
	private ImageIcon loginC, logoutC, modifiedC;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;

	MembershipB menbersShipF; // �쉶�썝媛��엯
//	SearchidB searchF; // ID 李얘린
//	SearchpwB searchpwF; // PASSWORD 李얘린
	RoomFrame RoomF; // ��湲곗떎
	RoomMake rMakeF; // 諛⑸쭔�뱾湲�
	CoprocessFrame chattingF;

//	private String sNumber = "><^^"; // default �떆�겕由용꽆踰�
//  private boolean condition_S = false; // �씠硫붿씪 �씤利앺솗�씤
	private boolean condition_Id = false; // ID 以묐났泥댄겕
	private boolean condition_PW = false; // 鍮꾨�踰덊샇 �솗�씤

	public EnterFrame() {
		network();

		menbersShipF = new MembershipB();
//		searchF = new SearchidB();
//		searchpwF = new SearchpwB();
		RoomF = new RoomFrame(br, pw);
		rMakeF = new RoomMake();
		chattingF = new CoprocessFrame();
				
		idB = new JButton("�븘�씠�뵒");
		idT = new JTextField(15);
		pwB = new JButton("�뙣�뒪�썙�뱶");
		pwT = new JPasswordField(15);
		pwT.setEchoChar('*');

		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(idB);
		p2.add(idT);
		p2.add(pwB);
		p2.add(pwT);

//		searchidB = new JButton("�븘�씠�뵒 李얘린");
//		searchpwB = new JButton("鍮꾨�踰덊샇 李얘린");
		membershipB = new JButton("�쉶�썝媛��엯");
		accessB = new JButton("�엯�옣");

		JPanel p3 = new JPanel();
//		p3.add(searchidB);
//		p3.add(searchpwB);
		p3.add(membershipB);
		p3.add(accessB);

		loginC = new ImageIcon("loginButton.png");
		loginL = new JLabel(loginC);

		JPanel p4 = new JPanel();
		p4.add(loginL);

		Container contentPane = this.getContentPane();
		contentPane.add("Center", p2);
		contentPane.add("South", p3);
		contentPane.add("East", p4);

		setVisible(true);
		setResizable(false);
		setBounds(400, 200, 1000, 800);
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		event();

	}

	public void event() {
		// --------------------�쉶�썝媛��엯愿��젴----------------------------------
		membershipB.addActionListener(this); // �쉶�썝媛��엯(踰꾪듉)
		menbersShipF.calneB.addActionListener(this); // �쉶�썝媛��엯 痍⑥냼(濡쒓렇�씤�솕硫댁쑝濡�)
		menbersShipF.joinB.addActionListener(this); // �쉶�썝媛��엯 �솕硫댁뿉�꽌 join
		menbersShipF.idoverlapB.addActionListener(this);// �쉶�썝媛��엯 �솕硫� 以묐났�솗�씤

//		// --------------------ID李얘린愿��젴----------------------------------
//		searchidB.addActionListener(this); // �븘�씠�뵒 李얘린
//		searchF.joinB.addActionListener(this); // �븘�씠�뵒李얘린 (join踰꾪듉)
//		searchF.cancelB.addActionListener(this); // ID李얘린 痍⑥냼
//
//		// --------------------PW李얘린愿��젴----------------------------------
//		searchpwB.addActionListener(this); // PW 李얘린
//		searchpwF.cancleB.addActionListener(this); // PW李얘린 痍⑥냼

		// --------------------濡쒓렇�씤愿��젴----------------------------------
		accessB.addActionListener(this); // �엯�옣(Login)
		RoomF.exitB.addActionListener(this); // Room -> 濡쒓렇�씤Page

		// ---------------------硫붿꽭吏� 愿��젴---------------------------------
		RoomF.sendB.addActionListener(this); // ��湲곕갑�뿉�꽌 �쟾�넚
		RoomF.chattxt.addKeyListener(this); // �뿏�꽣移섎㈃ �쟾�넚
		
		// ----------------------諛� 愿��젴 ------------------------------------
		RoomF.makeB.addActionListener(this);
		rMakeF.makeB.addActionListener(this);
		rMakeF.canB.addActionListener(this);
		chattingF.exitB.addActionListener(this);
		chattingF.sendB.addActionListener(this);
		chattingF.field.addKeyListener(this); // �뿏�꽣移섎㈃ �쟾�넚
		
//		// ----------------------梨꾪똿諛� 愿��젴 ---------------------------------
//		chattingF.openB.addActionListener(this);
//		chattingF.saveB.addActionListener(this);
//		chattingF.loadB.addActionListener(this);
//		chattingF.list2.addListSelectionListener(this);
		//
	}

	public void network() {

		// �냼耳� �깮�꽦
		try {
			socket = new Socket("192.168.0.64", 9500);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (UnknownHostException e) {
			System.out.println("�꽌踰꾨�� 李얠쓣 �닔 �뾾�뒿�땲�떎");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("�꽌踰꾩� �뿰寃곗씠 �븞�릺�뿀�뒿�땲�떎");
			e.printStackTrace();
			System.exit(0);
		}

		// �씠踰ㅽ듃

		// �뒪�젅�뱶 �깮�꽦
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == membershipB) { // 硫붿씤�럹�씠吏� -----------> �쉶�썝媛��엯踰꾪듉
			this.setVisible(false);
			menbersShipF.setVisible(true);
		} else if (e.getSource() == menbersShipF.joinB) { // �쉶�썝媛��엯�럹�씠吏� -----------> 媛��엯踰꾪듉

			String name = menbersShipF.nameT.getText();
			String id = menbersShipF.idT.getText();
			String pw1 = menbersShipF.pwT.getText();
			String pw2 = menbersShipF.pwT2.getText();
//			String ageYear = (String) menbersShipF.ageYearC.getSelectedItem();
//			String ageMonth = (String) menbersShipF.ageMonthC.getSelectedItem();
//			String ageDay = (String) menbersShipF.ageDayC.getSelectedItem();
//			String tel = (String) menbersShipF.telC.getSelectedItem();
//			String tel2 = menbersShipF.tel2T.getText();
//			String tel3 = menbersShipF.tel3T.getText();

			if (name.length() == 0 || id.length() == 0 || pw1.length() == 0 ||pw2.length() == 0  /**|| tel2.length() == 0 || tel3.length() == 0 **/) {
				JOptionPane.showMessageDialog(this, "鍮덇컙�쓣 �엯�젰�빐二쇱꽭�슂");
			} else if (/**condition_S &&**/ condition_PW && condition_Id) { // -> �븘�씠�뵒 以묐났�솗�씤, 鍮꾨�踰덊샇 �솗�씤 �맂嫄�

				String line = "";
				line += (menbersShipF.idT.getText() + "%" + menbersShipF.pwT.getText() + "%" 
						+ menbersShipF.nameT.getText()); // + "%" + menbersShipF.ageYearC.getSelectedItem()
//						+ menbersShipF.ageMonthC.getSelectedItem() + menbersShipF.ageDayC.getSelectedItem() + "%"
//						+ menbersShipF.telC.getSelectedItem() + "" + menbersShipF.tel2T.getText()
//						+ menbersShipF.tel3T.getText());
				System.out.println(line);

				pw.println(Protocol.REGISTER + "|" + line);
				pw.flush();
				JOptionPane.showMessageDialog(this, "�쉶�썝媛��엯 �셿猷�");
				menbersShipF.setVisible(false);
				this.setVisible(true);

				menbersShipF.nameT.setText("");
				menbersShipF.idT.setText("");
				menbersShipF.pwT.setText("");
//				menbersShipF.ageYearC.setSelectedIndex(0);
//				menbersShipF.ageMonthC.setSelectedIndex(0);
//				menbersShipF.ageDayC.setSelectedIndex(0);
//				menbersShipF.telC.setSelectedIndex(0);
//				menbersShipF.tel2T.setText("");
//				menbersShipF.tel3T.setText("");

//				condition_S = false;
				condition_Id = false;
				condition_PW = false;				
//				sNumber = "><^^";

			} else if (!condition_Id /**&& condition_S**/) {
				JOptionPane.showMessageDialog(this, "ID 以묐났�솗�씤 �빐二쇱꽭�슂");
			} 
			  else if (!condition_PW) {
				JOptionPane.showMessageDialog(this, "PW �솗�씤 �빐二쇱꽭�슂");
			} 
		} else if (e.getSource() == menbersShipF.calneB) { // �쉶�썝媛��엯�럹�씠吏� -----------> 痍⑥냼
			menbersShipF.setVisible(false);
			this.setVisible(true);
			//condition_S = false;
//			sNumber = "><^^";

		} else if (e.getSource() == menbersShipF.idoverlapB) { // �쉶�썝媛��엯 �럹�씠吏�ID -----------> 以묐났�솗�씤

			if (menbersShipF.idT.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "�븘�씠�뵒 �엯�젰�븯�꽭�슂");
			} else {
				pw.println(Protocol.IDSEARCHCHECK + "|" + menbersShipF.idT.getText());
				pw.flush();
			}

//		} 
//		  else if (e.getSource() == searchpwB) { // 硫붿씤�럹�씠吏� -----------> 鍮꾨쾲李얘린 踰꾪듉
//			this.setVisible(false);
//			searchpwF.setVisible(true);
//		} else if (e.getSource() == searchidB) { // 硫붿씤�럹�씠吏� -----------> �븘�씠�뵒 李얘린
//			this.setVisible(false);
//			searchF.setVisible(true);
//		} else if (e.getSource() == searchF.joinB) { // ID 李얘린 -----------> �솗�씤
//			String name = searchF.nameT.getText();
//			String ageYear = (String) searchF.ageYearC.getSelectedItem();
//			String ageMonth = (String) searchF.ageMonthC.getSelectedItem();
//			String ageDay = (String) searchF.ageDayC.getSelectedItem();
//			String tel2 = searchF.tel2T.getText();
//			String tel3 = searchF.tel3T.getText();

//			if (name.length() == 0) { // || tel2.length() == 0 || tel3.length() == 0) {
//				JOptionPane.showMessageDialog(this, "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂");
//			} else if (condition_S) {
//				String line = "";
//
//				line += (searchF.nameT.getText() + "%" + searchF.ageYearC.getSelectedItem()
//						+ searchF.ageMonthC.getSelectedItem() + searchF.ageDayC.getSelectedItem() + "%"
//						+ searchF.telC.getSelectedItem() + "" + searchF.tel2T.getText() + searchF.tel3T.getText());
//				System.out.println(line);
//
//				pw.println(Protocol.IDSEARCH + "|" + line);
//				pw.flush();
//
//				searchF.nameT.setText("");
//				searchF.ageYearC.setSelectedIndex(0);
//				searchF.ageMonthC.setSelectedIndex(0);
//				searchF.ageDayC.setSelectedIndex(0);
//				searchF.telC.setSelectedIndex(0);
//				searchF.tel2T.setText("");
//				searchF.tel3T.setText("");
//				condition_S = false;
//				sNumber = "><^^";
//			} else if (!condition_S) {
//				JOptionPane.showMessageDialog(this, "�씠硫붿씪 �씤利앹쓣 �빐二쇱꽭�슂");
//			}

//		} else if (e.getSource() == searchF.cancelB) { // ID李얘린�럹�씠吏� -----------> ID李얘린 痍⑥냼
//
//			searchF.setVisible(false);
//			this.setVisible(true);
//			searchF.nameT.setText("");
//			searchF.ageYearC.setSelectedIndex(0);
//			searchF.ageMonthC.setSelectedIndex(0);
//			searchF.ageDayC.setSelectedIndex(0);
//			searchF.telC.setSelectedIndex(0);
//			searchF.tel2T.setText("");
//			searchF.tel3T.setText("");
//			condition_S = false;
//			sNumber = "><^^";

		} else if (e.getSource() == accessB) { // 硫붿씤�럹�씠吏� --> ��湲곕갑 (Login)

			String id = idT.getText();
			String pwss = pwT.getText();

			if (id.length() == 0 || pwss.length() == 0) {
				JOptionPane.showMessageDialog(this, "鍮덉뭏�쓣 �엯�젰�빐二쇱꽭�슂");
			} else {
				String line = id + "%" + pwss;
				pw.println(Protocol.ENTERLOGIN + "|" + line);
				pw.flush();
			}
			idT.setText("");
			pwT.setText("");

		} 
//		  else if (e.getSource() == searchpwF.cancleB) { // PW李얘린�럹�씠吏� -->PW 李얘린 痍⑥냼
//			searchpwF.setVisible(false);
//			this.setVisible(true);
//		} 
		  else if (e.getSource() == RoomF.exitB) { // ��湲곗떎 -> 濡쒓렇�씤Page (濡쒓렇�븘�썐)

			RoomF.setVisible(false);
			this.setVisible(true);

			pw.println(Protocol.EXITWAITROOM + "|" + "message");
			pw.flush();

		} else if (e.getSource() == RoomF.sendB) // ��湲곗떎 �럹�씠吏� -----------> MESSAGE �쟾�넚
		{
			String line = RoomF.chattxt.getText();
			if (RoomF.chattxt.getText().length() != 0) {
				pw.println(Protocol.SENDMESSAGE + "|" + line);
				pw.flush();
				RoomF.chattxt.setText("");
			}

		} else if (e.getSource() == RoomF.makeB) {
			RoomF.setVisible(false);
			rMakeF.setVisible(true);
		} else if (e.getSource() == rMakeF.makeB) { // 諛⑸쭔�뱾湲� �럹�씠吏� -----> 諛⑸쭔�뱾湲� 踰꾪듉
			String title = rMakeF.tf.getText();
//			String rPassword = rMakeF.pf.getText();
			String userCount = (String) rMakeF.combo1.getSelectedItem();
//			String subject = (String) rMakeF.combo.getSelectedItem();
//			int condition = rMakeF.cb.isSelected() ? 1 : 0;

			if (title.length() == 0) {
				JOptionPane.showMessageDialog(this, "�젣紐⑹쓣 �엯�젰�빐二쇱꽭�슂");
			} 
//			  else {
//				if (condition == 1 && rPassword.length() == 0) // PW瑜� �벖�떎怨좏뻽�넃怨� �븞�꽔�쓣�븣
//				{
//					JOptionPane.showMessageDialog(this, "鍮꾨�踰덊샇�쓣 �엯�젰�빐二쇱꽭�슂");
//				} else if (condition == 1 && rPassword.length() != 0) {// PW瑜� �벖�떎怨좏뻽�넃怨� �꽔��寃쎌슦
//
//					String line = "";
//					line += (title + "%" + rPassword + "%" + userCount + "%" + subject + "%" + condition);
//					pw.println(Protocol.ROOMMAKE + "|" + line);
//					pw.flush();

//					rMakeF.setVisible(false);
//					RoomF.setVisible(true);

//					rMakeF.tf.setText("");
//					rMakeF.pf.setText("");
//					rMakeF.combo1.setSelectedIndex(0);
//					rMakeF.combo.setSelectedIndex(0);
//					rMakeF.cb.setSelected(false);

//				} else if (condition == 0 && rPassword.length() != 0) {
//					JOptionPane.showMessageDialog(this, "鍮꾨�踰덊샇 �궗�슜�쓣 �꽑�깮�빐二쇱꽭�슂.");
////				} 
//				  else if (condition == 0) // 怨듦컻諛�
//				{
					String line = "";
					line += (title + "%" + userCount + "%" /**+ subject + "%" + condition**/);
					pw.println(Protocol.ROOMMAKE + "|" + line);
					pw.flush();

//					rMakeF.setVisible(false);
//					RoomF.setVisible(true);
					rMakeF.tf.setText("");
//					rMakeF.pf.setText("");
					rMakeF.combo1.setSelectedIndex(0);
//					rMakeF.combo.setSelectedIndex(0);
//					rMakeF.cb.setSelected(false);
//				}

//			}

		} else if (e.getSource() == rMakeF.canB) { // 諛⑸쭔�뱾湲고럹�씠吏� ----> 痍⑥냼踰꾪듉
			rMakeF.setVisible(false);
			RoomF.setVisible(true);
			rMakeF.tf.setText("");
//			rMakeF.pf.setText("");
			rMakeF.combo1.setSelectedIndex(0);
//			rMakeF.combo.setSelectedIndex(0);
//			rMakeF.cb.setSelected(false);
		} else if (e.getSource() == chattingF.exitB) { // 梨꾪똿諛⑹뿉�꽌 �굹媛�湲� 踰꾪듉

			chattingF.setVisible(false);
			RoomF.setVisible(true);
			chattingF.model.removeAllElements();

			pw.println(Protocol.EXITCHATTINGROOM + "|" + "Message");
			pw.flush();

			chattingF.partList.setText("asd");

		} else if (e.getSource() == chattingF.sendB) {
			pw.println(Protocol.CHATTINGSENDMESSAGE + "|" + chattingF.field.getText()); // 硫붿꽭吏�瑜� 蹂대깂
			pw.flush();
			chattingF.field.setText("");
		} 
//		  else if (e.getSource() == chattingF.openB) // 梨꾪똿諛⑹뿉�꽌 ------> �궡而댄꽣 �뙆�씪 �뿴湲�
//		{
//			chattingF.openDialog();
//			chattingF.fileRead();
//
//		} else if (e.getSource() == chattingF.saveB) // 梨꾪똿諛⑹뿉�꽌 ------> �궡而댄꽣 �뙆�씪���옣
//		{
//			chattingF.fileSave();
//			chattingF.fileWrite();
////			listUpload();
//			chattingF.openB.setEnabled(true);
//			chattingF.saveB.setEnabled(true);
//			chattingF.loadB.setEnabled(true);
//			chattingF.deleteB.setEnabled(false);
//			chattingF.exitB.setEnabled(true);

//		} 
//	      else if (e.getSource() == chattingF.loadB) {
//			chattingF.openDialog();
//			pw.println(Protocol.CHATTINGFILESEND_SYN + "|" + chattingF.file.getName());
//			pw.flush();
//		}
	}
	
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
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
	public void valueChanged(ListSelectionEvent arg0) {
		System.out.println("Listlistioner");
		for (int i = 0; i < chattingF.model.getSize(); i++) {
			if (chattingF.list2.isSelectedIndex(i)) {
//				chattingF.fileSave();
				pw.println(Protocol.CHATTINGFILEDOWNLOAD_SYN + "|" + chattingF.list2.getSelectedValue());
				pw.flush();
			}
		}
	}

	@Override
	public void run() {
		// 諛쏅뒗履�
		String line[] = null;
		while (true) {
			try {
				line = br.readLine().split("\\|");
				if (line == null) {
					br.close();
					pw.close();
					socket.close();

					System.exit(0);
				} else if (line[0].compareTo(Protocol.IDSEARCHCHECK_OK) == 0) { // �쉶�썝媛��엯 ID 以묐났 �븞�맖
					JOptionPane.showMessageDialog(this, "�궗�슜媛��뒫");
					condition_Id = true;
				} else if (line[0].compareTo(Protocol.IDSEARCHCHECK_NO) == 0) { // �쉶�썝媛��엯 ID 以묐났 �맖
					JOptionPane.showMessageDialog(this, "�궗�슜 遺덇��뒫");
					condition_Id = false;
				} /**else if (line[0].compareTo(Protocol.IDSEARCH_OK) == 0) // ID 李얘린 湲곗〈�뿉 �엳�쓬
				{
					JOptionPane.showMessageDialog(this, line[1]);
					searchF.setVisible(false);
					this.setVisible(true);
				} else if (line[0].compareTo(Protocol.IDSEARCH_NO) == 0) // ID媛� �뾾�쓬
				{
					JOptionPane.showMessageDialog(this, line[1]);
					searchF.setVisible(false);
					this.setVisible(true);
				}**/ else if (line[0].compareTo(Protocol.ENTERLOGIN_OK) == 0) // 濡쒓렇�씤 �꽦怨�
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

				} else if (line[0].compareTo(Protocol.ENTERLOGIN_NO) == 0) // 濡쒓렇�씤 �떎�뙣
				{
					JOptionPane.showMessageDialog(this, line[1]);
					System.out.println("濡쒓렇�씤�떎�뙣");
				} else if (line[0].compareTo(Protocol.EXITWAITROOM) == 0) // 濡쒓렇�븘�썐 [��湲곗떎 -> 濡쒓렇�씤�럹�씠吏�]
				{
					RoomF.chatarea.append(line[1] + line[2] + '\n');

					String text[] = line[3].split(":");
					String userlist = "";
					for (int i = 0; i < text.length; i++) {
						userlist += (text[i] + "\n");
					}
					RoomF.usertxt.setText(userlist);

				} else if (line[0].compareTo(Protocol.SENDMESSAGE_ACK) == 0) // �꽌踰꾨줈 硫붿꽭吏� 諛쏆쓬 [��湲곗떎]
				{
					RoomF.chatarea.append("[" + line[1] + "] :" + line[2] + '\n');

				} else if (line[0].compareTo(Protocol.ROOMMAKE_OK) == 0) // 諛⑸쭔�뱾�뼱吏�
				{
					System.out.println("�씠嫄� �릺�깘?");
					String roomList[] = line[1].split("-"); // 諛� 媛��닔
					for (int i = 0; i < roomList.length; i++) {
						System.out.print(roomList[i] + "/");
					}

					String roomListDetail[]; // 諛⑹꽭遺�
					System.out.println("RoomList size : " + roomList.length);

					RoomF.containPanelClear(); // 猷� �봽�젅�엫�뿉 而⑦뀒�씠�꼫瑜� 鍮꾩썙二쇨퀬
					for (int i = 0; i < roomList.length; i++) {

						RoomF.dp[i].init(); // 諛⑸━�뒪�듃瑜� 諛쏆�嫄곕줈 �떎�떆 �깮�꽦�빐二쇨퀬
						roomListDetail = roomList[i].split("%");
						String userNumber = "";

//						if (roomListDetail.length == 8) // 鍮꾧났媛쒕갑
//						{
//							userNumber += (roomListDetail[7] + "/" + roomListDetail[3]);
//
//							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // 諛⑸쾲�샇
//							RoomF.dp[i].labelArray[3].setText(roomListDetail[5]); // 諛⑹＜�젣
//							RoomF.dp[i].labelArray[5].setText(userNumber); // �씤�썝�닔
//							RoomF.dp[i].labelArray[7].setText(roomListDetail[1]); // 諛⑹젣紐�
//							RoomF.dp[i].labelArray[8].setText("媛쒖꽕�옄 : " + roomListDetail[4]); // 媛쒖꽕�옄
//						} 
						  if (roomListDetail.length == 6) // 怨듦컻諛�
						{
							userNumber += (roomListDetail[6] + "/" + roomListDetail[2]);
							RoomF.dp[i].labelArray[1].setText(roomListDetail[0]); // 諛⑸쾲�샇
//							RoomF.dp[i].labelArray[3].setText(roomListDetail[5]); // 諛⑹＜�젣
							RoomF.dp[i].labelArray[5].setText(userNumber); // �씤�썝�닔
							RoomF.dp[i].labelArray[7].setText(roomListDetail[1]); // 諛⑹젣紐�
							RoomF.dp[i].labelArray[8].setText("媛쒖꽕�옄 : " + roomListDetail[3]); // 媛쒖꽕�옄
						}
						System.out.println("userNumber : " + userNumber);

					}
					chattingF.area.setText("");
					chattingF.area1.setText("");
					rMakeF.setVisible(false); // ��湲곕갑 �솕硫� �걚怨�
					RoomF.setVisible(true);

				} else if (line[0].compareTo(Protocol.ROOMMAKE_OK1) == 0) // 諛⑸쭔�뱾�뼱吏� (留뚮뱺 �떦�궗�옄) // �엯�옣
				{
					rMakeF.setVisible(false); // ��湲곕갑 �솕硫� �걚怨�
					chattingF.area.setText("");
					chattingF.setVisible(true);
					chattingF.partList.setText(line[1] + "\n");

				} else if (line[0].compareTo(Protocol.ENTERROOM_OK1) == 0) // 諛⑹엯�옣 �엯�옣�븯�뒗 �떦�궗�옄
				{
					System.out.println("�엯�옣�솕硫� 蹂��솚");
					RoomF.setVisible(false);
					chattingF.area1.setText("");
					chattingF.area.setText("");
					chattingF.setVisible(true);
//					System.out.println(line[2]);
//					String roomMember[] = line[2].split("%");//猷몄뿉 �뱾�뼱�삩�궗�엺�뱾
//					chattingF.partList.append(line[1]); //�옄湲� 異붽��빐二쇨퀬
//					for (int i = 0; i < roomMember.length; i++) {
//						chattingF.partList.append(roomMember[i] + "\n");
//					}

				} else if (line[0].compareTo(Protocol.ENTERROOM_USERLISTSEND) == 0) // 梨꾪똿諛� 由ъ뒪�듃 �깉濡쒓퀬移�
				{

					String roomMember[] = line[1].split("%");// 猷몄뿉 �뱾�뼱�삩�궗�엺�뱾
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

				} else if (line[0].compareTo(Protocol.CHATTINGSENDMESSAGE_OK) == 0) {
					chattingF.area1.append("[" + line[1] + "] :" + line[2] + "\n");
				} 
				  /**else if (line[0].compareTo(Protocol.CHATTINGFILESEND_SYNACK) == 0) {

					pw.println(Protocol.CHATTINGFILESEND_FILE + "|" + chattingF.file.length());
					pw.flush();

					OutputStream os = socket.getOutputStream();

					System.out.println("�뙆�씪 蹂대궡湲� �떆�옉 !!!");
					// 蹂대궪 �뙆�씪�쓽 �엯�젰 �뒪�듃由� 媛앹껜 �깮�꽦
					FileInputStream fis = new FileInputStream(chattingF.file.getAbsoluteFile());

					// �뙆�씪�쓽 �궡�슜�쓣 蹂대궦�떎
					byte[] b = new byte[512];
					int n;
					while ((n = fis.read(b, 0, b.length)) > 0) {
						os.write(b, 0, n);
						System.out.println(n + "bytes 蹂대깂 !!!");
					}

					// �냼耳볦뿉�꽌 蹂대궪 異쒕젰 �뒪�듃由쇱쓣 援ы븳�떎.
				} else if (line[0].compareTo(Protocol.CHATTINGFILESEND_FILEACK) == 0) {

					String[] fileList = line[1].split("%");

					chattingF.model.removeAllElements();
					for (int i = 0; i < fileList.length; i++) {
						chattingF.model.addElement(fileList[i]);
					}

				} else if (line[0].compareTo(Protocol.CHATTINGFILEDOWNLOAD_SEND) == 0) { // �뙆�씪�쓣 諛쏆쓬
					String path = chattingF.file.getAbsolutePath();

					FileOutputStream fos = new FileOutputStream(path);
					InputStream is = socket.getInputStream();

					System.out.println("�뙆�씪 �떎�슫濡쒕뱶 �떆�옉 !!!");

					// 蹂대궡�삩 �뙆�씪 �궡�슜�쓣 �뙆�씪�뿉 ���옣

					byte[] b = new byte[512];

					int n = 0;
					long filesize = Long.parseLong(line[1]);

					while ((n = is.read(b, 0, b.length)) > 0) {

						fos.write(b, 0, n);
						System.out.println("N:" + n);
						System.out.println(n + "bytes �떎�슫濡쒕뱶 !!!");
						n += n;
						if (n >= filesize)
							break;
					}

					fos.close();
					System.out.println("�뙆�씪 �떎�슫濡쒕뱶 �걹 !!!");
				}**/

			} catch (IOException io) {
				io.printStackTrace();
			}

		} // while
	}
}