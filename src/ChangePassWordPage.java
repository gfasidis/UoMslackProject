import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;


public class ChangePassWordPage extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 478533768553038765L;
	
	private JLabel label;
	private JPasswordField passwordField;
	private JButton button;
	
	private JPanel panel;
	
	private User selectedUser;
	
	public ChangePassWordPage(User selectedUser){
		
		this.selectedUser = selectedUser;
		
		panel = new JPanel();
		
		label = new JLabel("Insert new password");
		passwordField = new JPasswordField("Password", 10);
		passwordField.setToolTipText("Password must contain at least 8 characters");
		button = new JButton("Change Password");
		
		this.setContentPane(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(73)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(button)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label))))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(26)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24)
					.addComponent(button)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);

		Focus focusManager = new Focus();
		passwordField.addFocusListener(focusManager);
		
		KeyboardListener keyboard = new KeyboardListener();
		passwordField.addKeyListener(keyboard);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(300, 200);
		this.setTitle("Change Password");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        new HomePage();
				dispose();
		        }
		});
		
		
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			char[] password = passwordField.getPassword();
			if(password.length < 8)
				JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters", "Warning", JOptionPane.WARNING_MESSAGE);
			else if (Arrays.equals(password, selectedUser.getPassword()))
				JOptionPane.showMessageDialog(null, "Password is the same with your old password", "Warning", JOptionPane.WARNING_MESSAGE);
			else{
				//New Connection
				Connections.newConnection();
				selectedUser.setPassword(password);
				String query = "UPDATE users SET password = '" + String.valueOf(password) + "' WHERE username = '" + selectedUser.getName() + "'";
				DatabaseClass.writeToDatabase(query);
				JOptionPane.showMessageDialog(null, "Password change successfully");
				new LoginPage();
				dispose();
			}
			
		}
		
		
	}
	
	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

				passwordField.selectAll();
			

		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}
	
	class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {


			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				
				char[] password = passwordField.getPassword();
				if(password.length < 8)
					JOptionPane.showMessageDialog(null, "Password must contain at least 8 characters", "Warning", JOptionPane.WARNING_MESSAGE);
				else if (Arrays.equals(password, selectedUser.getPassword()))
					JOptionPane.showMessageDialog(null, "Password is the same with your old password", "Warning", JOptionPane.WARNING_MESSAGE);
				else{
					//New Connection
					Connections.newConnection();
					selectedUser.setPassword(password);
					String query = "UPDATE users SET password = '" + String.valueOf(password) + "' WHERE username = '" + selectedUser.getName() + "'";
					DatabaseClass.writeToDatabase(query);
					JOptionPane.showMessageDialog(null, "Password change successfully");
					new LoginPage();
					dispose();
				}
					
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
