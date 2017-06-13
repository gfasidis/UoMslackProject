import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;

public abstract class DatabaseClass {

	private static int PORT;
	private final static String IP = "uomslack.ddns.net";

	private static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	
	private static ArrayList<Integer> dbPorts;

	public static void writeToDatabase(String query) {

		String response;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(0);
			output.flush();
			System.out.println("You set the flag");
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			response = (String) input.readObject();
			System.out.println(response);

		} catch (IOException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static ArrayList<User> readUsers() {

		ArrayList<User> users = new ArrayList<User>();
		String username;
		String passString;
		String email;
		String status;
		String query;
		char[] password;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(1);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM users";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				username = (String) input.readObject();
				if (username.equals("stop"))
					break;
				passString = (String) input.readObject();
				password = passString.toCharArray();
				email = (String) input.readObject();
				status = (String) input.readObject();
				users.add(new User(username, email, password, status));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return users;
	}

	public static TreeSet<Post> readPosts(String selCourseid) {

		TreeSet<Post> posts = new TreeSet<Post>(new PostComparator());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String text;
		Date date;
		String username;
		String courseid;
		String query;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(2);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM posts WHERE courseid = '" + selCourseid + "'";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				text = (String) input.readObject();
				if (text.equals("stop"))
					break;
				date = format.parse((String) input.readObject());
				username = (String) input.readObject();
				courseid = (String) input.readObject();
				posts.add(new Post(text, date, username, courseid));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return posts;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Course> readCourses(String selDep, String selYear) {

		ArrayList<Course> courses = new ArrayList<Course>();
		String id;
		String title;
		String department;
		String year;
		String query;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(4);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM courses WHERE department = '" + selDep + "' AND year = '" + selYear + "'";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				id = (String) input.readObject();
				if (id.equals("stop"))
					break;
				title = (String) input.readObject();
				department = (String) input.readObject();
				year = (String) input.readObject();
				courses.add(new Course(id, title, department, year));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		Collections.sort(courses);
		return courses;
	}

	public static ArrayList<String> readSubscriptions(String username) {

		ArrayList<String> subscriptions = new ArrayList<String>();
		String courseid;
		String name;
		String query;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(5);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM subscribes WHERE username = '" + username + "'";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				name = (String) input.readObject();
				if (name.equals("stop"))
					break;
				courseid = (String) input.readObject();
				subscriptions.add(courseid);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return subscriptions;
	}
	
	public static ArrayList<Course> readAllCourses() {

		ArrayList<Course> courses = new ArrayList<Course>();
		String id;
		String title;
		String department;
		String year;
		String query;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(4);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM courses";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				id = (String) input.readObject();
				if (id.equals("stop"))
					break;
				title = (String) input.readObject();
				department = (String) input.readObject();
				year = (String) input.readObject();
				courses.add(new Course(id, title, department, year));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return courses;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<UploadedFile> readFiles() {

		ArrayList<UploadedFile> files = new ArrayList<UploadedFile>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String filename;
		Date date;
		String username;
		String courseid;
		String query;

		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(7);
			output.flush();
			System.out.println("You set the flag");
			query = "SELECT * FROM files";
			output.writeObject(query);
			output.flush();
			System.out.println("querie sent");
			while (true) {
				filename = (String) input.readObject();
				if (filename.equals("stop"))
					break;
				username = (String) input.readObject();
				date = format.parse((String) input.readObject());
				courseid = (String) input.readObject();
				files.add(new UploadedFile(filename, username, date, courseid));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				input.close();
				output.close();
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		Collections.sort(files);
		return files;
	}
	
	private static void generatePorts(){
		
		dbPorts = new ArrayList<>();
		int dPort = 10000;
		for(int i = 0; i < 10; i++)
			dbPorts.add(dPort + i);
		
	}
	
	public static ArrayList<Integer> getPorts(){
		
		generatePorts();
		return dbPorts;
		
	}

	public static void setPORT(int pORT) {
		
		PORT = pORT;
	}
	
	public static int randomPorts(ArrayList<Integer> ports) {
		
	    Random rand = new Random(System.currentTimeMillis()); 
	    Integer randomPort = ports.get(rand.nextInt(ports.size()));
	    return randomPort;
	}

	public static String getIp() {
		return IP;
	}
	
	
	
	
}
