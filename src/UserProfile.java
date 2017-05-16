import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UserProfile extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -330124265076588248L;
	
	private JLabel nameLabel;
	private JLabel mailLabel;
	
	private JPanel panel;
	private JLabel picLabel;
	private JLabel labelusername;
	private JLabel labelemail;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public UserProfile(String aName, String aMail){
		
		panel = new JPanel();
		
		nameLabel = new JLabel(aName);
		mailLabel = new JLabel(aMail);
		
		mailLabel.setForeground(Color.BLUE);
		Font font = mailLabel.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		mailLabel.setFont(font.deriveFont(attributes));
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("Icons/logo.png"));
			picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(141, 94, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labelusername = new JLabel("Username:");
		labelemail = new JLabel("email:");
		
		
		this.setContentPane(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(labelemail)
								.addComponent(labelusername))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(mailLabel)
								.addComponent(nameLabel)))
						.addComponent(picLabel, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(picLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelusername)
						.addComponent(nameLabel))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelemail)
						.addComponent(mailLabel))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		
		//MouseListener
		MailListener sendmail = new MailListener();
		mailLabel.addMouseListener(sendmail);
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(300, 300);
		this.setTitle(aName);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	class MailListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		String email = mailLabel.getText();
		String url = "mailto:" + email;
		try {
			MailClass.openWebpage(new URL(url).toURI());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			
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
