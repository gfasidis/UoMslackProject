import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class FileClient {

	private final static int PORT = 20000;
	private final static String IP = "uomslack.ddns.net";

	private final static int FILE_SIZE = 10485760;
	
	private static Socket connection = null;
	private static FileInputStream fileInput = null;
	private static FileOutputStream fileOutput = null;
	private static BufferedInputStream bufferedInput = null;
	private static BufferedOutputStream bufferedOutput = null;
	private static OutputStream output = null;
	private static ObjectOutputStream objOutput = null;
	
	public static void downloadFile(String fileName, String downloadPath) {

		int bytesRead;
		int current = 0;
		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connecting...");
			objOutput = new ObjectOutputStream(connection.getOutputStream());
			objOutput.writeInt(0);
			objOutput.flush();

			// receive file
			objOutput.writeObject(fileName);
			objOutput.flush();
			byte[] mybytearray = new byte[FILE_SIZE];
			InputStream is = connection.getInputStream();
			fileOutput = new FileOutputStream(downloadPath);
			bufferedOutput = new BufferedOutputStream(fileOutput);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bufferedOutput.write(mybytearray, 0, current);
			bufferedOutput.flush();
			System.out.println("File " + downloadPath + " downloaded (" + current + " bytes read)");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOutput != null)
					fileOutput.close();
				if (bufferedOutput != null)
					bufferedOutput.close();
				if (connection != null)
					connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void uploadFile(File selectedFile) {
		try {
			connection = new Socket(IP, PORT);
			System.out.println("Connecting...");
			objOutput = new ObjectOutputStream(connection.getOutputStream());
			objOutput.writeInt(1);
			objOutput.flush();

			// send file
			File myFile = new File(selectedFile.getPath());
			byte[] mybytearray = new byte[(int) myFile.length()];
			objOutput.writeObject(selectedFile.getName());
			fileInput = new FileInputStream(myFile);
			bufferedInput = new BufferedInputStream(fileInput);
			bufferedInput.read(mybytearray, 0, mybytearray.length);
			output = connection.getOutputStream();
			System.out.println("Sending " + selectedFile.getAbsolutePath() + "(" + mybytearray.length + " bytes)");
			output.write(mybytearray, 0, mybytearray.length);
			output.flush();
			System.out.println("Done.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInput != null)
					fileInput.close();
				if (bufferedInput != null)
					bufferedInput.close();
				if (connection != null)
					connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}