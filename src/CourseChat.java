import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

public class CourseChat extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1049943846376985150L;
	
	private JTextField usernameField;
	private JTextField usermailField;
	
	private JTextArea postField;
	private JTextArea friendsPosts;
	
	private JButton postButton;
	private JButton backButton;
	private JButton sendFilesButton;
	private JButton subscribeButton;
	
	private JLabel usersLabel;
	private JLabel filesLabel;
	
	private JPanel panel;
	private JScrollPane scrollPanelPost;
	private JScrollPane scrollPanelFriendsPosts;
	private JScrollPane scrollPaneUsers;
	private JScrollPane scrollPaneFiles;
	
	private JList<String> onlineUsers;
	private JList<String> filesList;
	
	private JFileChooser fileChooser;
	
	private ArrayList<User> users;
	private User selectedUser;
	
	private TreeSet<Post> posts;
	private Course selectedCourse;
	
	private ArrayList<UploadedFile> files;
	
	private DefaultListModel<String> online;
	private DefaultListModel<String> uplFiles;
	
	private final static int INTERVAL = 20000;
	
	
	
	public CourseChat(User aUser, Course selectedCourse) {
		
		
		this.selectedCourse = selectedCourse;
		selectedUser = aUser;
		
		//Update status for selectedUser
		selectedUser.setCourseNow(selectedCourse.getId());
		String query = "UPDATE users SET courseid = '" + selectedCourse.getId() +"' WHERE username = '" + selectedUser.getName() + "'";
	    DatabaseClass.writeToDatabase(query);
		
	    //Panel
		panel = new JPanel();
		
		//User's data, Back Button
		usernameField = new JTextField(aUser.getName(),8);
		usernameField.setEditable(false);
		usermailField = new JTextField(aUser.getMail(),13);
		usermailField.setEditable(false);
		backButton = new JButton("Back to \"Login\"");
		
		
		//Other users Posts
		friendsPosts = new JTextArea(10,25);
		friendsPosts.setLineWrap(true);
		friendsPosts.setEditable(false);
		postButton = new JButton("Sent");
		scrollPanelFriendsPosts = new JScrollPane(friendsPosts);
		scrollPanelFriendsPosts.setPreferredSize(new Dimension(422, 255));
		updatePosts();
		DefaultCaret caret = (DefaultCaret)friendsPosts.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		//Online users
		usersLabel = new JLabel("Online Users");
		onlineUsers = new JList<String>();
		scrollPaneUsers = new JScrollPane(onlineUsers);
		scrollPaneUsers.setPreferredSize(new Dimension(140, 130));
		online = new DefaultListModel<String>();
		updateOnlineUsers();
		
		//Files
		fileChooser = new JFileChooser();
		filesList = new JList<String>();
		filesLabel = new JLabel("Uploaded Files");
		scrollPaneFiles = new JScrollPane(filesList);
		scrollPaneFiles.setPreferredSize(new Dimension(140, 130));
		uplFiles = new DefaultListModel<String>();
		updateFiles();

		
		//Post Field
		postField = new JTextArea(10, 25);
		postField.setText("Write something here...");
		postField.setLineWrap(true);
		scrollPanelPost = new JScrollPane(postField);
		
		
		//Send files
		sendFilesButton = new JButton("Send File");
		
		//Subscribe / Unsubscribe
		subscribeButton = new JButton("Subscribe");
		if(selectedUser.isSubscribedToCourse(selectedCourse)){
			subscribeButton.setText("Unsubscribe");
			UnsubscribeListener unsubscribeListener = new UnsubscribeListener();
			subscribeButton.addActionListener(unsubscribeListener);
		}
		else{
			subscribeButton.setText("Subscribe");
			SubscribeListener subscribeListener = new SubscribeListener();
			subscribeButton.addActionListener(subscribeListener);
		}	
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(usermailField, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addGap(144))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(scrollPanelPost, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(postButton))
								.addComponent(scrollPanelFriendsPosts, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(41)
									.addComponent(usersLabel))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(scrollPaneUsers, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(28)
									.addComponent(subscribeButton)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(35)
									.addComponent(scrollPaneFiles, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(backButton))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(62)
											.addComponent(filesLabel, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addContainerGap(17, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sendFilesButton)
							.addGap(239))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(96)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(usersLabel)
								.addComponent(filesLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPaneFiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneUsers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(1)
									.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(usermailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(backButton)
									.addComponent(subscribeButton)))
							.addGap(21)
							.addComponent(scrollPanelFriendsPosts, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(postButton)
								.addComponent(sendFilesButton))
							.addGap(50))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(scrollPanelPost, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addGap(36))))
		);
		panel.setLayout(gl_panel);
		
		this.setContentPane(panel);
		
		//ButtonListeners
		BackButtonListener backListener = new BackButtonListener();
		backButton.addActionListener(backListener);
		
		PostButtonListener postListener = new PostButtonListener();
		postButton.addActionListener(postListener);
		
		SendFileButtonListener sendListener = new SendFileButtonListener();
		sendFilesButton.addActionListener(sendListener);
		
		//FocusListener
		Focus focusManager = new Focus();
		postField.addFocusListener(focusManager);
		
		//KeyboardListener
		KeyboardListener keyboard = new KeyboardListener();
		postField.addKeyListener(keyboard);
		
		//MouseListener
		ProfileListener mouseClickedUsers = new ProfileListener();
		onlineUsers.addMouseListener(mouseClickedUsers);
		
		FileInfoListener mouseClickedFiles = new FileInfoListener();
		filesList.addMouseListener(mouseClickedFiles);
		
		
		Timer timer = new Timer(INTERVAL, new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						
						updatePosts();
						updateOnlineUsers();
						updateFiles();
						
					}    
		});

		timer.start();
		

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/3-this.getSize().width/2, dim.height/3-this.getSize().height/2);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(800, 530);
		this.setTitle(selectedCourse.getTitle());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    			logOut();
		        }
		});
		
	}
		public void logOut(){
		
		 selectedUser.setCourseNow("");
	     online.removeAllElements();
	     onlineUsers.removeAll();
	     String query = "UPDATE users SET courseid = 'NA' WHERE username = '" + selectedUser.getName() + "'";
	     DatabaseClass.writeToDatabase(query);
	     new LoginPage(selectedUser.getName(),String.valueOf(selectedUser.getPassword()));
	     dispose();
		
	}
		
	public void updateOnlineUsers(){
		
		users = User.readUsersFromDatabase();
		online.removeAllElements();
        onlineUsers.removeAll();
        for(User user : users)
			if(user.getCourseNow().equals(selectedCourse.getId()) && !(user.getName().equals(selectedUser.getName())))
				online.addElement(user.getName());
		onlineUsers.setModel(online);
	}
	
	public void updatePosts(){
		
		posts = Course.readPostsFromDatabase(selectedCourse.getId());
		friendsPosts.setText("");
		for(Post aPost : posts){
			String text = aPost.getPostUser() + " at " + aPost.getPostDate() + ":\n" + aPost.getPostText() + "\n";
			friendsPosts.append(text);
			friendsPosts.append("--------------------------------------------------\n");
		}
		
	}
	
	public void updateFiles(){
		
		files = UploadedFile.readFilesInfoFromDatabase();
		uplFiles.removeAllElements();
		filesList.removeAll();
		for(UploadedFile file : files)
			if(file.getCourseId().equals(selectedCourse.getId()))
					uplFiles.addElement(file.getFileName());
		filesList.setModel(uplFiles);
		
	}



	class BackButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			logOut();
		}
		
		
	}
	
	class PostButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			String postText = postField.getText();
			Date timestamp = new Date();
			Post newpost = new Post(postText, timestamp, selectedUser.getName(),selectedCourse.getId());
			postField.setText("");
			Post.writePostToDatabse(newpost);
			
			//Add new post
			updatePosts();
			JScrollBar vertical = scrollPanelFriendsPosts.getVerticalScrollBar();
			vertical.setValue(vertical.getMaximum());
			
			ArrayList<String> to = new ArrayList<String>();
			for(User user : users)
				if(user.isSubscribedToCourse(selectedCourse) && !(user.getName().equals(selectedUser.getName())) && !(user.getCourseNow().equals(selectedCourse.getId())))
					to.add(user.getMail());
			if(to.size()>0)
				MailClass.subscribeMail(to, selectedCourse.getTitle(), newpost);
			
			
		}
		
		
	}
	
	class SendFileButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			int returnVal = fileChooser.showDialog(CourseChat.this, "Send");
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				
				FileClient.uploadFile(file);
				Date timestamp = new Date();
				UploadedFile.uploadFileToDatabase(new UploadedFile(file.getName(), selectedUser.getName(), timestamp, selectedCourse.getId()));
				String postText = "Just added a new file in this course!\n"
						+ "File: " + file.getName();
				Post newpost = new Post(postText, timestamp, selectedUser.getName(),selectedCourse.getId());
				postField.setText("");
				Post.writePostToDatabse(newpost);
				updateFiles();
				
				//Add new post
				updatePosts();
				JScrollBar vertical = scrollPanelFriendsPosts.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				
				ArrayList<String> to= new ArrayList<String>();
				for(User user : users)
					if(user.isSubscribedToCourse(selectedCourse) && !(user.getName().equals(selectedUser.getName())) && !(user.getCourseNow().equals(selectedCourse.getId())))
						to.add(user.getMail());
				if(to.size()>0)
					MailClass.subscribeMail(to, selectedCourse.getTitle(), newpost);
				
			}  
		}
	}
	
	class Focus implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
				postField.selectAll();
		}

		@Override
		public void focusLost(FocusEvent e) {

		}

	}
	
	class KeyboardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				
				String postText = postField.getText();
				Date timestamp = new Date();
				Post newpost = new Post(postText, timestamp, selectedUser.getName(),selectedCourse.getId());
				postField.setText("");
				Post.writePostToDatabse(newpost);
				
				//Add new post
				updatePosts();
				JScrollBar vertical = scrollPanelFriendsPosts.getVerticalScrollBar();
				vertical.setValue(vertical.getMaximum());
				
				ArrayList<String> to= new ArrayList<String>();
				for(User user : users)
					if(user.isSubscribedToCourse(selectedCourse) && !(user.getName().equals(selectedUser.getName())) && !(user.getCourseNow().equals(selectedCourse.getId())))
						to.add(user.getMail());
				if(to.size()>0)
					MailClass.subscribeMail(to, selectedCourse.getTitle(), newpost);
				
			}
				
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				postField.setText("");
			}
				

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

	}
	
	class ProfileListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount() == 2) {
			    
				String mail = "N/A";
				String name = (String) onlineUsers.getSelectedValue();
				for(User user : users){
					if(user.getName().equals(name)){
						mail = user.getMail();
						break;
					}
				}
				new UserProfile(name, mail);
				
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
	
	class FileInfoListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount() == 2) {
			    
				UploadedFile selectedFile = null;
				String fileName = (String) filesList.getSelectedValue();
				for(UploadedFile file : files)
					if(file.getFileName().equals(fileName) && file.getCourseId().equals(selectedCourse.getId())){
						selectedFile = file;
						break;
					}
				new FileInfo(selectedFile);
				
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
	
	class SubscribeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			selectedUser.subscribeToCourse(selectedCourse);
			JOptionPane.showMessageDialog(null, "You have successfully subscribed to " + selectedCourse.getTitle() + " course!");
			
			String query = "INSERT INTO subscribes (username, courseid) " + "VALUES ('" + selectedUser.getName() + "', '" + selectedCourse.getId() + "')";
			DatabaseClass.writeToDatabase(query);
			users = User.readUsersFromDatabase();
			
			subscribeButton.setText("Unsubscribe");
			UnsubscribeListener unsubscribeListener = new UnsubscribeListener();
			subscribeButton.addActionListener(unsubscribeListener);
		}
		
	}
	
	class UnsubscribeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedUser.unsubscribeFromCourse(selectedCourse);
			JOptionPane.showMessageDialog(null, "You have successfully unsubscribed from " + selectedCourse.getTitle() + " course!");
			
			String query = "DELETE FROM subscribes WHERE username = '" + selectedUser.getName() + "' AND courseid = '" + selectedCourse.getId() + "'";
			DatabaseClass.writeToDatabase(query);
			users = User.readUsersFromDatabase();
			
			subscribeButton.setText("Subscribe");
			SubscribeListener subscribeListener = new SubscribeListener();
			subscribeButton.addActionListener(subscribeListener);
		}
		
	}
}
