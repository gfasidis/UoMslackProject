import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Connections {

	private static boolean checkDatabaseConnection(int PORT){
		
	    Socket connection;
		ObjectOutputStream output;
	    ObjectInputStream input;
	    
		try {
			connection = new Socket();
			connection.connect(new InetSocketAddress(DatabaseClass.getIp(), PORT), 2000);
			System.out.println("Connected to server in port " + PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			output.writeInt(6);
			output.flush();
			input.close();
			output.close();
			connection.close();
			DatabaseClass.setPORT(PORT);
			return true;
		} catch (IOException e) {
			return false;
		}
		
		
	}
	
	private static boolean checkServerConnection(int PORT){
		
	    Socket connection;
		DataOutputStream output;
	    DataInputStream input;
	    
		try {
			connection = new Socket();
			connection.connect(new InetSocketAddress(FileClient.getIp(), PORT), 2000);
			System.out.println("Connected to server in port " + PORT);
			output = new DataOutputStream(connection.getOutputStream());
			output.flush();
			input = new DataInputStream(connection.getInputStream());
			output.writeInt(6);
			output.flush();
			input.close();
			output.close();
			connection.close();
			FileClient.setPORT(PORT);
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}
	
	public static void newConnection(){
		
		ArrayList<Integer> dpPorts = DatabaseClass.getPorts();
		ArrayList<Integer> fcPorts = FileClient.getPorts();
		boolean dbFLag = false;
		//boolean fcFlag = false;
		
		while(true){
			if(checkDatabaseConnection(DatabaseClass.randomPorts(dpPorts))){
				dbFLag = true;
				break;
			}
			
		}
		
		if(dbFLag){
			while(true){
				if(checkServerConnection(FileClient.randomPorts(fcPorts))){
					//fcFlag = true;
					break;
				}
			}
		}
	}
	
	public static boolean checkConnection(){
		
		ArrayList<Integer> dpPorts = DatabaseClass.getPorts();
		ArrayList<Integer> fcPorts = FileClient.getPorts();
		boolean dbFLag = false;
		boolean fcFlag = false;
		
		while(true){
			if(checkDatabaseConnection(DatabaseClass.randomPorts(dpPorts))){
				dbFLag = true;
				break;
			}
			
		}
		
		if(dbFLag){
			while(true){
				if(checkServerConnection(FileClient.randomPorts(fcPorts))){
					fcFlag = true;
					break;
				}
				
			}
		}
		return (dbFLag && fcFlag);
	}
	
}
