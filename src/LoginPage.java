import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;


public class LoginPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8414601686306861436L;
	
	private JTextField usernameField;
	private JPasswordField passwordField;

	private JButton enterButton;
	private JButton welcomePageButton;

	private JPanel panel;
	private JScrollPane departmentsScrollPanel;
	private JScrollPane yearScrollPanel;

	private JList<String> yearList;
	private JList<String> uomList;

	private ArrayList<User> users;

	public LoginPage() {

		//New Connection
		Connections.newConnection();
		users = User.readUsersFromDatabase();
		
		panel = new JPanel();
		
		yearList = new JList<String>();
		uomList = new JList<String>();
		uomList.setPreferredSize(new Dimension(310, 150));
		departmentsScrollPanel = new JScrollPane(uomList);
		yearScrollPanel = new JScrollPane(yearList);

		usernameField = new JTextField("Username", 8);
		passwordField = new JPasswordField("Password", 8);
		
		enterButton = new JButton("Go to courses");
		welcomePageButton = new JButton("Back to \"Welcome Page\"");

		DefaultListModel<String> yearModel = new DefaultListModel<String>();
		yearModel.addElement("1ο Έτος");
		yearModel.addElement("2ο Έτος");
		yearModel.addElement("3ο Έτος");
		yearModel.addElement("4ο Έτος");
		yearList.setModel(yearModel);

		DefaultListModel<String> departmentsModel = new DefaultListModel<String>();
		departmentsModel.addElement("Βαλκανικών, Σλαβικών και Ανατολικών Σπουδών");
		departmentsModel.addElement("Διεθνών και Ευρωπαϊκών Σπουδών");
		departmentsModel.addElement("Εκπαιδευτικής και Κοινωνικής Πολιτικής");
		departmentsModel.addElement("Εφαρμοσμένης Πληροφορικής");
		departmentsModel.addElement("Λογιστικής και Χρηματοοικονομικής");
		departmentsModel.addElement("Μουσικής Επιστήμης και Τέχνης");
		departmentsModel.addElement("Οικονομικών Επιστημών");
		departmentsModel.addElement("Οργάνωσης και Διοίκησης Επιχειρήσεων");
		uomList.setModel(departmentsModel);
		
		int start = 0;
	    int end = 0;
	    uomList.setSelectionInterval(start, end);
	    yearList.setSelectionInterval(start, end);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addComponent(departmentsScrollPanel, 0, 0, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(yearScrollPanel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(29))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(160)
					.addComponent(enterButton)
					.addContainerGap(159, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(129)
					.addComponent(welcomePageButton)
					.addContainerGap(128, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(117)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(116, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addComponent(departmentsScrollPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(enterButton)
							.addGap(18)
							.addComponent(welcomePageButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(79)
							.addComponent(yearScrollPanel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		this.setContentPane(panel);

		//ButtonListener
		EnterButtonListener enterListener = new EnterButtonListener();
		enterButton.addActionListener(enterListener);

		HomePageEnterButtonListener welcomePageListener = new HomePageEnterButtonListener();
		welcomePageButton.addActionListener(welcomePageListener);
		
		//FocusListener
		Focus focusManager = new Focus();
		usernameField.addFocusListener(focusManager);
		passwordField.addFocusListener(focusManager);

		// KeyboardListener
		KeyboardListener keyboard = new KeyboardListener();
		yearList.addKeyListener(keyboard);
		uomList.addKeyListener(keyboard);
		usernameField.addKeyListener(keyboard);
		passwordField.addKeyListener(keyboard);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(450, 323);
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public LoginPage(String username, String password) {


		//New Connection
		Connections.newConnection();
		users = User.readUsersFromDatabase();
		
		panel = new JPanel();
		
		yearList = new JList<String>();
		uomList = new JList<String>();
		uomList.setPreferredSize(new Dimension(310, 150));
		departmentsScrollPanel = new JScrollPane(uomList);
		yearScrollPanel = new JScrollPane(yearList);

		usernameField = new JTextField(username, 8);
		usernameField.setEditable(false);
		passwordField = new JPasswordField(password, 8);
		passwordField.setEditable(false);
		
		enterButton = new JButton("Go to courses");
		welcomePageButton = new JButton("Back to \"Welcome Page\"");
		
		DefaultListModel<String> yearModel = new DefaultListModel<String>();
		yearModel.addElement("1ο Έτος");
		yearModel.addElement("2ο Έτος");
		yearModel.addElement("3ο Έτος");
		yearModel.addElement("4ο Έτος");
		yearList.setModel(yearModel);

		DefaultListModel<String> departmentsModel = new DefaultListModel<String>();
		departmentsModel.addElement("Βαλκανικών, Σλαβικών και Ανατολικών Σπουδών");
		departmentsModel.addElement("Διεθνών και Ευρωπαϊκών Σπουδών");
		departmentsModel.addElement("Εκπαιδευτικής και Κοινωνικής Πολιτικής");
		departmentsModel.addElement("Εφαρμοσμένης Πληροφορικής");
		departmentsModel.addElement("Λογιστικής και Χρηματοοικονομικής");
		departmentsModel.addElement("Μουσικής Επιστήμης και Τέχνης");
		departmentsModel.addElement("Οικονομικών Επιστημών");
		departmentsModel.addElement("Οργάνωσης και Διοίκησης Επιχειρήσεων");
		uomList.setModel(departmentsModel);
		
		int start = 0;
	    int end = 0;
	    uomList.setSelectionInterval(start, end);
	    yearList.setSelectionInterval(start, end);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup().addGap(30)
								.addComponent(departmentsScrollPanel, 0, 0, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(yearScrollPanel, GroupLayout.PREFERRED_SIZE, 78,
										GroupLayout.PREFERRED_SIZE)
								.addGap(29))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addGap(116)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(129, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING,
								gl_panel.createSequentialGroup().addGap(160).addComponent(enterButton)
										.addContainerGap(159, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup().addGap(129)
								.addComponent(welcomePageButton).addContainerGap(128, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel
								.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup().addGap(11)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(usernameField, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(passwordField, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(departmentsScrollPanel, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(enterButton).addGap(18)
												.addComponent(welcomePageButton))
										.addGroup(gl_panel.createSequentialGroup().addGap(79).addComponent(
												yearScrollPanel, GroupLayout.PREFERRED_SIZE, 77,
												GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(26, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		this.setContentPane(panel);

		// ButtonListeners
		EnterButtonListener enterListener = new EnterButtonListener();
		enterButton.addActionListener(enterListener);

		HomePageEnterButtonListener welcomePageListener = new HomePageEnterButtonListener();
		welcomePageButton.addActionListener(welcomePageListener);

		// FocusListener
		Focus focusManager = new Focus();
		usernameField.addFocusListener(focusManager);
		passwordField.addFocusListener(focusManager);

		// KeyboardListener
		KeyboardListener keyboard = new KeyboardListener();
		yearList.addKeyListener(keyboard);
		uomList.addKeyListener(keyboard);
		usernameField.addKeyListener(keyboard);
		passwordField.addKeyListener(keyboard);
	

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(450, 320);
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class EnterButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String name = usernameField.getText();
			char[] password = passwordField.getPassword();
			User selectedUser = null;
			boolean found = false;
			for (User aUser : users)
				if (aUser.getName().equals(name) && aUser.isPasswordCorrect(password)) {
					found = true;
					selectedUser = aUser;
					break;
				}

			if (found) {
				String selectedDepartment = (String) uomList.getSelectedValue();
				String selectedYear = (String) yearList.getSelectedValue();
				new CoursesPage(selectedDepartment, selectedYear.substring(0, 1), selectedUser);
				dispose();
			} else
				JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Warning", JOptionPane.WARNING_MESSAGE);

		}

	}

	class HomePageEnterButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new HomePage();
			dispose();

		}

	}

	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (e.getSource() == usernameField) {
				usernameField.selectAll();
			} else if (e.getSource() == passwordField)
				passwordField.selectAll();

		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}

	class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

			String name = usernameField.getText();
			char[] password = passwordField.getPassword();
			User selectedUser = null;
			boolean found = false;

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				for (User aUser : users)
					if (aUser.getName().equals(name) && aUser.isPasswordCorrect(password)) {
						found = true;
						selectedUser = aUser;
						break;
					}

				if (found) {
					String selectedDepartment = (String) uomList.getSelectedValue();
					String selectedYear = (String) yearList.getSelectedValue();
					new CoursesPage(selectedDepartment, selectedYear.substring(0, 1), selectedUser);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Warning", JOptionPane.WARNING_MESSAGE);

			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}
}
