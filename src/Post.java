import java.text.SimpleDateFormat;
import java.util.*;


public class Post {
	
	private String postText;
	private Date postDate;
	private String postUser;
	private String courseId;
	
	public Post(String postText, Date postDate, String postUser, String courseId) {
		this.postText = postText;
		this.postDate = postDate;
		this.postUser = postUser;
		this.courseId = courseId;
	}


	public String getPostText() {
		return postText;
	}

	public Date getPostDate() {
		return postDate;
	}

	public String getPostUser() {
		return postUser;
	}

	public String getCourseId() {
		return courseId;
	}
	
	public static void writePostToDatabse(Post post){
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleFormat.format(post.getPostDate());
		
		String query = "INSERT INTO posts (text, date, username, courseid) " + "VALUES('" + post.getPostText() + "', '" + date + "', '" + post.getPostUser() + "', '" + post.getCourseId() +"')";
		DatabaseClass.writeToDatabase(query);
		
	}
	

}


class PostCompatator implements Comparator<Post> {

	@Override
	public int compare(Post o1, Post o2) {
		
		return o1.getPostDate().compareTo(o2.getPostDate());
	}
	
	
}
