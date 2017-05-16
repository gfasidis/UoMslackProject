import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;
import javax.swing.LayoutStyle.ComponentPlacement;


public class FileInfo extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7992872460789479937L;
	
	private JPanel panel;
	private JLabel labelFileName;
	private JLabel picLabel;
	
	private JButton downloadButton;
	private JLabel labelUser;
	private JLabel labelDate;

	
	public FileInfo(UploadedFile file){
		
		panel = new JPanel();
		downloadButton = new JButton("Download");
		labelFileName = new JLabel("Filemane: " + file.getFileName());
		labelUser = new JLabel("Uploaded by: " + file.getUser());
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleFormat.format(file.getUploadDate());
		labelDate = new JLabel("Date: " + date);
		
		
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("Icons/" + FilenameUtils.getExtension(file.getFileName()) + ".png"));
			picLabel = new JLabel(new ImageIcon(myPicture));
		} catch (IOException e) {
			try {
				myPicture = ImageIO.read(new File("Icons/file.png"));
				picLabel = new JLabel(new ImageIcon(myPicture));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
		
		
		this.setContentPane(panel);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(120)
							.addComponent(picLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(97)
							.addComponent(downloadButton))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(labelUser, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelFileName)
								.addComponent(labelDate, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(96, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(31)
					.addComponent(picLabel)
					.addGap(46)
					.addComponent(labelFileName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(labelUser)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(labelDate)
					.addGap(12)
					.addComponent(downloadButton)
					.addContainerGap(88, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		//ButtonListener
		DownloadButton downloadListener = new DownloadButton();
		downloadButton.addActionListener(downloadListener);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 3 - this.getSize().width / 2, dim.height / 3 - this.getSize().height / 2);
		this.setResizable(false);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(300, 300);
		this.setTitle("Info");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	}
	
	class DownloadButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
}
