import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;


public class CoursesPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2754968370894029758L;

	private JLabel selectLabel;

	private JButton enterButton;
	private JButton backButton;

	private JPanel panel;
	private JScrollPane scrollPanel;

	private JList<String> coursesList;

	private ArrayList<Course> courses;
	private User selectedUser;

	public CoursesPage(String department, String year, User aUser) {

		//New Connection
		Connections.newConnection();
		selectedUser = aUser;

		panel = new JPanel();

		selectLabel = new JLabel("Choose course to chat");
		enterButton = new JButton("Enter chat");
		backButton = new JButton("Back to \"Login\"");
		
		coursesList = new JList<String>();
		scrollPanel = new JScrollPane(coursesList);
		scrollPanel.setPreferredSize(new Dimension(340, 150));
		
		courses = DatabaseClass.readCourses(department, year);

		DefaultListModel<String> coursesModel = new DefaultListModel<String>();
		for (Course aCourse : courses)
			coursesModel.addElement(aCourse.getTitle());
		coursesList.setModel(coursesModel);
		
		int start = 0;
		int end = 0;
		coursesList.setSelectionInterval(start, end);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(155)
							.addComponent(selectLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(172)
							.addComponent(enterButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(156)
							.addComponent(backButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(57)
							.addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(selectLabel)
					.addGap(18)
					.addComponent(scrollPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(enterButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(backButton)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		this.setContentPane(panel);

		// ButtonListeners
		LoginPageButtonListener loginPageListener = new LoginPageButtonListener();
		backButton.addActionListener(loginPageListener);

		EnterButtonListener gotoChat = new EnterButtonListener();
		enterButton.addActionListener(gotoChat);

		// KeyboardListener
		KeyboardListener keyboard = new KeyboardListener();
		coursesList.addKeyListener(keyboard);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setVisible(true);
		this.setFocusable(true);
		this.setResizable(false);
		this.setSize(450, 300);
		this.setTitle(department + " courses");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class LoginPageButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new LoginPage(selectedUser.getName(), String.valueOf(selectedUser.getPassword()));
			dispose();

		}

	}

	class EnterButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedCourse = (String) coursesList.getSelectedValue();
			Course course = null;
			for(Course aCourse : courses)
				if(aCourse.getTitle().equals(selectedCourse))
					course = aCourse;
			new CourseChat(selectedUser, course);
			dispose();

		}

	}

	class KeyboardListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {

			String selectedCourse = (String) coursesList.getSelectedValue();
			Course course = null;
			for(Course aCourse : courses)
				if(aCourse.getTitle().equals(selectedCourse))
					course = aCourse;
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				new CourseChat(selectedUser, course);
				dispose();
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				new LoginPage(selectedUser.getName(), String.valueOf(selectedUser.getPassword()));
				dispose();
			}

		

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	

}
