package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import address.model.GroupType;

public class AddFrame extends JFrame{
	
	private JFrame addFrame = this;
	private JFrame mainFrame;
	private Container backgroundPanel;
	private JPanel addPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton addButton;
	
	public AddFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		initObject();
		initDesign();
		initListener();
		setVisible(true);
	}
	
	private void initObject() {
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
				addFrame.dispose();
			}
		});
		
	}
}