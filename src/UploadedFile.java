import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

public class UploadedFile {
	
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
	
	public static TreeSet<UploadedFile> readFilesInfoFromDatabase(){
		
		TreeSet<UploadedFile> files = ;
		return files;
		
	}
	
	public static void uploadFileToDatabase(UploadedFile file){
		
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = simpleFormat.format(file.getUploadDate());
		
		DatabaseClass.writeToDatabase("INSERT INTO files (filename, username, date, courseid) " + "VALUES('" + file.getFileName() + "', '" + file.getUser() + "', '" + currentTime + "', '" + file.getCourseId() + "')");
		
	}
	
class FileCompatator implements Comparator<UploadedFile> {

	@Override
	public int compare(UploadedFile o1, UploadedFile o2) {
		
		return o1.getUploadDate().compareTo(o2.getUploadDate());
	}
	
	
}
	
	

}
