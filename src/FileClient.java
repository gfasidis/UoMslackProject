import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public abstract class FileClient {

	private static int PORT;
	private final static String IP = "uomslack.ddns.net";

	private static Socket connection = null;
	private static File file;
	
	private static ArrayList<Integer> fcPorts;
	
	public static void downloadFile(String fileName, String downloadPath) {
		
		try{
			
			connection = new Socket(IP, PORT);
			DataInputStream dataInput = new DataInputStream(connection.getInputStream());
			DataOutputStream dataOutput = new DataOutputStream(connection.getOutputStream());
			dataOutput.writeInt(1);
			dataOutput.flush();
			FileOutputStream fileOutput = new FileOutputStream(downloadPath);

			dataOutput.writeUTF(fileName);
			byte[] buffer = new byte[4096];

			int filesize = dataInput.readInt();
			int read = 0;
			int totalRead = 0;
			int remaining = filesize;

			while ((read = dataInput.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
				totalRead += read;
				remaining -= read;
				fileOutput.write(buffer, 0, read);
			}

			System.out.println("File " + fileName + " (" + totalRead + " bytes) downloaded");

			fileOutput.close();
			dataInput.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void uploadFile(File selectedFile) {
		
		try{
			connection = new Socket(IP, PORT);
			
			file = new File(selectedFile.getAbsolutePath());
			int fileSize = (int) file.length();
			String fileName = file.getName();
			
			DataOutputStream dataOutput = new DataOutputStream(connection.getOutputStream());
			dataOutput.writeInt(0);
			dataOutput.flush();
			FileInputStream fileInput = new FileInputStream(file);

			dataOutput.writeUTF(fileName);
			dataOutput.flush();
			dataOutput.writeInt(fileSize);
			dataOutput.flush();

			byte[] buffer = new byte[4096];

			while (fileInput.read(buffer) > 0)
				dataOutput.write(buffer);

			System.out.println("File " + fileName + " uploaded");

			fileInput.close();
			dataOutput.close();
			} catch (Exception e){
				e.printStackTrace();
			}
	}
	
	private static void generatePorts(){
		
		fcPorts = new ArrayList<>();
		int fPort = 20000;
		for(int i = 0; i < 10; i++)
			fcPorts.add(fPort + 0);
		
	}
	
	public static ArrayList<Integer> getPorts(){
		
		generatePorts();
		return fcPorts;
		
	}

	public static void setPORT(int pORT) {
		
		PORT = pORT;
	}
}