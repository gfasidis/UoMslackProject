import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VerificationPage extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7294381761850197205L;
	
	private JLabel label;
	private JTextField verifCode;
	private JButton button;
	private JPanel panel;
	
	private String correctCode;
	private String username;
	private char[] password;
	private String email;
	
	private User selectedUser;
	
	
	public VerificationPage(String correctCode, String username, char[] password, String email) {
		
		this.correctCode = correctCode;
		this.username = username;
		this.password = password;
		this.email = email;
		
		panel = new JPanel();
		
		label = new JLabel("Insert verification code");
		verifCode = new JTextField("Verification Code", 12);
		
		button = new JButton("Authenticate");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(73)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(verifCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(15)
							.addComponent(button))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(label)))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(label)
					.addGap(26)
					.addComponent(verifCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(button)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		this.setContentPane(panel);
		
		AuthenticateButtonListener newuserlistener = new AuthenticateButtonListener();
		button.addActionListener(newuserlistener);
		
		Focus focusManager = new Focus();
		verifCode.addFocusListener(focusManager);
		
		KeyboardListenerNewUser keyboard = new KeyboardListenerNewUser();
		verifCode.addKeyListener(keyboard);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(300, 200);
		this.setTitle("Verification Code");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}

	
	public VerificationPage(User selectedUser, String correctCode) {
		
		this.selectedUser = selectedUser;
		this.correctCode = correctCode;
		
		panel = new JPanel();
		
		label = new JLabel("Insert verification code");
		verifCode = new JTextField("Verification Code", 12);
		button = new JButton("Authenticate");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(73)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(verifCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(15)
							.addComponent(button))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(label)))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(label)
					.addGap(26)
					.addComponent(verifCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(button)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		this.setContentPane(panel);
		
		ChangePasswordButtonListener listener = new ChangePasswordButtonListener();
		button.addActionListener(listener);
		
		Focus focusManager = new Focus();
		verifCode.addFocusListener(focusManager);
		
		KeyboardListenerChangePass changePass = new KeyboardListenerChangePass();
		verifCode.addKeyListener(changePass);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(300, 200);
		this.setTitle("Verification Code");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	class AuthenticateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			//New Connection
			Connections.newConnection();
			String userCode = verifCode.getText();
			if(correctCode.equals(userCode)){
				JOptionPane.showMessageDialog(null, "User " + " \"" + username + "\"" + " added successfully");
				User.writeUserToDatabase(new User(username, email, password, "NA"));
				new LoginPage(username, String.valueOf(password));
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Verification code is wrong", "Warning", JOptionPane.WARNING_MESSAGE);

		}

	}
	
	class ChangePasswordButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String userCode = verifCode.getText();
			if(correctCode.equals(userCode)){
				new ChangePassWordPage(selectedUser);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Verification code is wrong", "Warning", JOptionPane.WARNING_MESSAGE);

		}

	}
	
	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

				verifCode.selectAll();
			

		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}
	
	class KeyboardListenerChangePass implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				
			String userCode = verifCode.getText();
			if(correctCode.equals(userCode)){
				new ChangePassWordPage(selectedUser);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Verification code is wrong", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	class KeyboardListenerNewUser implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				//New Connection
				Connections.newConnection();
				String userCode = verifCode.getText();
				if(correctCode.equals(userCode)){
					JOptionPane.showMessageDialog(null, "User " + " \"" + username + "\"" + " added successfully");
					User.writeUserToDatabase(new User(username, email, password, "NA"));
					new LoginPage(username, String.valueOf(password));
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Verification code is wrong", "Warning", JOptionPane.WARNING_MESSAGE);
				
				}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

}
