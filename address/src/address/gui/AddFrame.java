package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;

public class AddFrame extends JFrame{
	
	private final static String TAG = "AddFrame : ";
	
	private AddFrame addFrame = this;
	private MainFrame mainFrame;
	private Container backgroundPanel;
	private JPanel addPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton addButton;
	private MemberService memberService = MemberService.getinstance();
	
	public AddFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initObject();
		initDesign();
		initListener();
		setVisible(true);
	}
	
	private void initObject() { // new
		backgroundPanel = getContentPane();
		addPanel = new JPanel();
		laName = new JLabel("이름");
		laPhone = new JLabel("전화번호");
		laAddress = new JLabel("주소");
		laGroup = new JLabel("그룹");
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);
		cbGroup = new JComboBox<>(GroupType.values());
		addButton = new JButton("추가하기");
	}
	private void initDesign() {
		setTitle("주소록 추가하기");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		addPanel.setLayout(new GridLayout(4,2));
		
		addPanel.add(laName);
		addPanel.add(tfName);
		addPanel.add(laPhone);
		addPanel.add(tfPhone);
		addPanel.add(laAddress);
		addPanel.add(tfAddress);
		addPanel.add(laGroup);
		addPanel.add(cbGroup);
		
		backgroundPanel.add(addPanel, BorderLayout.CENTER);
		backgroundPanel.add(addButton, BorderLayout.SOUTH);
	}
	private void initListener() {
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// 1. TF에 있는 값을 가져옴 2. 값을 Member에 담음
				System.out.println(TAG + "addButton리스너 : " + tfName.getText()); // log 확인
				Member member = Member.builder()
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						.groupType(GroupType.valueOf(cbGroup.getSelectedItem().toString())) // comboBox에서 선택되는 것 
						.build();
				int result = memberService.주소록추가(member);
				// 4. return값을 확인해서 로직을 직접 짜야함. (성공, 실패)
				if(result == 1) {
					// 5. 성공 = mainFrame에 값을 변경 (동기화)
					mainFrame.notifyUserList(); // UI 동기화하는 함수
					addFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					// 실패시
					JOptionPane.showMessageDialog(null, "주소록 추가에 실패하였습니다.");
				}

			}
		});
		
		addFrame.addWindowListener(new WindowAdapter() { // X 눌렀을 때 원래 창으로 돌아감
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
	}
}