import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class User{
	
	private String name;
	private String mail;
	private char[] password;
	private String courseNow;
	private ArrayList<Course> subscribedCourses;
	
	public User(String name, String mail, char[] password, String courseNow) {
		
			this.name = name;
			this.mail = mail;
			this.password = password;
			this.courseNow = courseNow;
			subscribedCourses = new ArrayList<Course>();

	}
	
	public String getName() {
		return name;
	}

	public String getMail() {
		return mail;
	}
	
	
	public char[] getPassword() {
		return password;
	}
	
	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean isPasswordCorrect(char [] aPassword){
		
		return Arrays.equals(aPassword, this.password);
	}
	
	public String getCourseNow() {
		return courseNow;
	}

	public void setCourseNow(String courseNow) {
		this.courseNow = courseNow;
	}
	
	public void setSubscribedCourses(ArrayList<Course> subscribedCourses) {
		this.subscribedCourses = subscribedCourses;
	}

	public boolean isSubscribedToCourse(Course selectedCourse){
		
		if(subscribedCourses.size() > 0)
			for(Course aCourse : subscribedCourses)
				if(aCourse.getId().equals(selectedCourse.getId()))
					return true;
		
		return false;
	}
	
	public void subscribeToCourse(Course selectedCourse){
		
		subscribedCourses.add(selectedCourse);
	}
	
	public void unsubscribeFromCourse(Course selectedCourse){
		
		Iterator<Course> iter = subscribedCourses.iterator();
		while (iter.hasNext()) {
		    Course str = iter.next();
		    if(str.equals(selectedCourse))
				iter.remove();
		}
		
	}
	

	public static void writeUserToDatabase(User user){
		
		DatabaseClass.writeToDatabase("INSERT INTO users (username, password, email, courseid) " + "VALUES('" + user.getName() + "', '" + String.valueOf(user.getPassword()) + "', '" + user.getMail() + "', '" + user.getCourseNow() + "')");
	}
	
	public static ArrayList<User> readUsersFromDatabase(){
		
		ArrayList<User> users;
		ArrayList<String> stringCourses;
		ArrayList<Course> courses;
		ArrayList<Course> userCourses;
		
		users = DatabaseClass.readUsers();
		courses = DatabaseClass.readAllCourses();
		for(User aUser : users){
			userCourses = new ArrayList<Course>();
			stringCourses = DatabaseClass.readSubscriptions(aUser.getName());
			for(Course aCourse : courses)
				for(String stringCour : stringCourses)
					if(stringCour.equals(aCourse.getId())){
						userCourses.add(aCourse);
						break;
					}
			aUser.setSubscribedCourses(userCourses);
		}
		return users;
		
	}
	
	public static SecureRandom getVertificationCode(){
		
		SecureRandom random = new SecureRandom();
		new BigInteger(130, random).toString(32);
		
		return random;
		   
		  
	}
	

}

