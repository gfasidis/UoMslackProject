import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AuthenticateUser extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8124397589681632732L;

	private JLabel label;
	
	private JTextField usernameField;
	private JTextField emailField;
	
	private JButton button;
	private JButton backButton;
	
	private JPanel panel;
	
	
	private ArrayList<User> users;
	
	public AuthenticateUser() {
		
		//New Connection
		Connections.newConnection();
				
		users = User.readUsersFromDatabase();
		
		panel = new JPanel();
		
		label = new JLabel("Insert username and email to send you verification code");
		usernameField = new JTextField("Username", 10);
		emailField = new JTextField("example@uom.edu.gr", 13);
		button = new JButton("Send email");
		backButton = new JButton("Cancel");
		
		this.setContentPane(panel);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(label)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(123)
								.addComponent(button)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(136)
							.addComponent(backButton)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addComponent(button)
					.addGap(12)
					.addComponent(backButton)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	
		SendMailButtonListener mailListener = new SendMailButtonListener();
		button.addActionListener(mailListener);
		
		BackButtonListener backListener = new BackButtonListener();
		backButton.addActionListener(backListener);
		
		Focus focusManager = new Focus();
		usernameField.addFocusListener(focusManager);
		emailField.addFocusListener(focusManager);
		
		KeyboardListener keyboard = new KeyboardListener();
		usernameField.addKeyListener(keyboard);
		emailField.addKeyListener(keyboard);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(370, 237);
		this.setTitle("Authenticate User");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	class SendMailButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String name = usernameField.getText();
			String email = emailField.getText();
			SecureRandom vertifCode;
			String code;
			User selectedUser = null;

			boolean find = false;

			for (User user : users) {
				if (user.getName().equals(name)) {
					find = true;
					selectedUser = user;
					break;
				}
			}

			if (find) {
				vertifCode = User.getVertificationCode();
				code = String.valueOf(vertifCode).substring(27);
				MailClass.vertificationMail(email, code);
				new VerificationPage(selectedUser, code);
				dispose();
			} else
				JOptionPane.showMessageDialog(null, "Your username or email is wrong ", "Warning", JOptionPane.WARNING_MESSAGE);
			

		}

	}
	
	class BackButtonListener implements ActionListener {

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
			} else if (e.getSource() == emailField) 
				emailField.selectAll();

		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}
	
	class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {


			if (e.getKeyCode() == KeyEvent.VK_ENTER) {

				String name = usernameField.getText();
				String email = emailField.getText();
				SecureRandom vertifCode;
				String code;
				User selectedUser = null;

				boolean find = false;

				for (User user : users) {
					if (user.getName().equals(name)) {
						find = true;
						selectedUser = user;
						break;
					}
				}

				if (find) {
					vertifCode = User.getVertificationCode();
					code = String.valueOf(vertifCode).substring(27);
					MailClass.vertificationMail(email, code);
					new VerificationPage(selectedUser, code);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "Your username or email is wrong ", "Warning", JOptionPane.WARNING_MESSAGE);
				
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
