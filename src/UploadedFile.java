import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("rawtypes")
public class UploadedFile implements Comparable{
	
	private String fileName;
	private String user;
	private Date uploadDate;
	private String courseId;
	
	public UploadedFile(String fileName, String user, Date uploadDate, String courseId) {
		
		this.fileName = fileName;
		this.user = user;
		this.uploadDate = uploadDate;
		this.courseId = courseId;
		
	}

	public String getFileName() {
		return fileName;
	}

	public String getUser() {
		return user;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public String getCourseId() {
		return courseId;
	}
	
	public static ArrayList<UploadedFile> readFilesInfoFromDatabase(){
		
		ArrayList<UploadedFile> files = DatabaseClass.readFiles();
		return files;
		
	}
	
	public static void uploadFileToDatabase(UploadedFile file){
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleFormat.format(file.getUploadDate());
		
		DatabaseClass.writeToDatabase("INSERT INTO files (filename, username, date, courseid) " + "VALUES('" + file.getFileName() + "', '" + file.getUser() + "', '" + currentTime + "', '" + file.getCourseId() + "')");
		
	}

	@Override
	public int compareTo(Object o) {
		
		UploadedFile file = (UploadedFile) o;
		return -(this.getUploadDate().compareTo(file.getUploadDate()));
	}	

}
