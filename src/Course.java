
import java.util.TreeSet;

@SuppressWarnings("rawtypes")
public class Course implements Comparable{ 
	
	private String title;
	@SuppressWarnings("unused")
	private String department;
	@SuppressWarnings("unused")
	private String year;
	private String id;

	public Course(String id, String title, String department, String year) {
		
		this.title = title;
		this.department = department;
		this.year = year;
		this.id = id;
	}
	
	
public String getTitle() {
		return title;
	}


public String getId() {
	return id;
}



public static TreeSet<Post> readPostsFromDatabase(String courseid){
		
		TreeSet<Post> posts = DatabaseClass.readPosts(courseid);
		return posts;
	}


@Override
public int compareTo(Object o) {
	Course otherCourse = (Course) o;
	return this.title.compareTo(otherCourse.getTitle());
}
	
	


}
