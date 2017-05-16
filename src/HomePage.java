import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class HomePage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5005227444972792780L;
	
	private JButton newUserButton;
	private JButton loginButton;

	private JTextField usernameField;
	private JTextField emailField;
	private JPasswordField passwordField;

	private JPanel panel;

	private JLabel newUserLabel;
	private JLabel existingUserLabel;
	private JLabel forgotPassLabel;

	private ArrayList<User> users;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HomePage() {

		panel = new JPanel();

		newUserButton = new JButton("Create new User");
		loginButton = new JButton("Existing User");

		usernameField = new JTextField("Username", 10);
		emailField = new JTextField("example@uom.edu.gr", 13);
		passwordField = new JPasswordField("Password", 10);
		passwordField.setToolTipText("Password must contain at least 8 characters");

		newUserLabel = new JLabel("If you don't have an account create it now");
		existingUserLabel = new JLabel("else choose your \"Existing User\"");
		forgotPassLabel = new JLabel("Forgot your password?");
		
		forgotPassLabel.setForeground(Color.BLUE);
		Font font = forgotPassLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		forgotPassLabel.setFont(font.deriveFont(attributes));


		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addGap(52)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(32)
								.addComponent(existingUserLabel))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(2)
									.addComponent(newUserLabel))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(61)
							.addComponent(newUserButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(52)
							.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(64)
							.addComponent(forgotPassLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(72)
							.addComponent(loginButton)))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(16)
					.addComponent(newUserLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(newUserButton)
					.addGap(23)
					.addComponent(existingUserLabel)
					.addGap(12)
					.addComponent(loginButton)
					.addGap(12)
					.addComponent(forgotPassLabel)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		gl_panel.linkSize(SwingConstants.VERTICAL, new Component[] {newUserButton, loginButton, usernameField, emailField, passwordField, newUserLabel, existingUserLabel});
		panel.setLayout(gl_panel);

		this.setContentPane(panel);

		// ButtonListeners
		LoginButtonListener loginListener = new LoginButtonListener();
		loginButton.addActionListener(loginListener);

		NewUserButtonListener userButtonListener = new NewUserButtonListener();
		newUserButton.addActionListener(userButtonListener);

		// FocusListener
		Focus focusManager = new Focus();
		usernameField.addFocusListener(focusManager);
		emailField.addFocusListener(focusManager);
		passwordField.addFocusListener(focusManager);

		// KeyboardListener
		KeyboardListener keyboard = new KeyboardListener();
		usernameField.addKeyListener(keyboard);
		emailField.addKeyListener(keyboard);
		passwordField.addKeyListener(keyboard);
		
		// LabelListener
		ForgotPasswordListener mouse = new ForgotPasswordListener();
		forgotPassLabel.addMouseListener(mouse);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setFocusable(true);
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(370, 320);
		this.setTitle("UoMslack");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class LoginButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new LoginPage();
			dispose();

		}

	}

	class NewUserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			users = User.readUsersFromDatabase();

			String name = usernameField.getText();
			String email = emailField.getText();
			char[] password = passwordField.getPassword();
			SecureRandom vertifCode;
			String code;

			boolean find = false;

			for (User user : users) {
				if (user.getName().equalsIgnoreCase(name) || user.getMail().equals(email)) {
					find = true;
					break;
				}
			}

			if (!find && email.endsWith("@uom.edu.gr") && (password.length >= 8)) {
				vertifCode = User.getVertificationCode();
				code = String.valueOf(vertifCode).substring(27);
				MailClass.vertificationMail(email, code);
				new VerificationPage(code, name, password, email);
				dispose();
			} else if (!email.endsWith("@uom.edu.gr"))
				JOptionPane.showMessageDialog(null, "Your email " + " \"" + email + "\"" + " is wrong", "Warning", JOptionPane.WARNING_MESSAGE);
			else if (password.length < 8)
				JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters", "Warning", JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Username " + " \"" + name + "\"" + " or email already exists", "Warning", JOptionPane.WARNING_MESSAGE);

		}

	}

	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (e.getSource() == usernameField) {
				usernameField.selectAll();
			} else if (e.getSource() == emailField) {
				emailField.selectAll();
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
			
			users = User.readUsersFromDatabase();

			String name = usernameField.getText();
			String email = emailField.getText();
			char[] password = passwordField.getPassword();
			SecureRandom vertifCode;
			String code;

			boolean find = false;

			for (User user : users) {
				if (user.getName().equalsIgnoreCase(name) || user.getMail().equals(email)) {
					find = true;
					break;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!find && email.endsWith("@uom.edu.gr") && (password.length >= 8)) {
					vertifCode = User.getVertificationCode();
					code = String.valueOf(vertifCode).substring(27);
					MailClass.vertificationMail(email, code);
					new VerificationPage(code, name, password, email);
					dispose();
				} else if (!email.endsWith("@uom.edu.gr"))
					JOptionPane.showMessageDialog(null, "Your email " + " \"" + email + "\"" + " is wrong", "Warning", JOptionPane.WARNING_MESSAGE);
				else if (password.length < 8)
					JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters", "Warning", JOptionPane.WARNING_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Username " + " \"" + name + "\"" + " or email already exists", "Warning", JOptionPane.WARNING_MESSAGE);

			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}
	
	class ForgotPasswordListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			new AuthenticateUser();
			dispose();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
	}
}
