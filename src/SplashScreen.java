import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.thehowtotutorial.splashscreen.JSplash;

public abstract class SplashScreen {
	
	private final static String IP = "uomslack.ddns.net";
	
	public static void startUOMslack(){
		
		
		try {
			JSplash splash = new JSplash(SplashScreen.class.getResource("logo.png"), true, true, false, "V1", null, Color.RED, Color.BLUE);
			splash.splashOn();
			splash.setProgress(20, "Loading...");
			Thread.sleep(2000);
			splash.setProgress(40, "Connecting to Database...");
			Thread.sleep(1000);
			if(checkConnection()){
				splash.setProgress(60, "Downloading data from Database...");
				Thread.sleep(1000);
				splash.setProgress(80, "Starting UoMslack...");
				Thread.sleep(500);
				new HomePage();
				splash.splashOff();
			}
			else{
				JOptionPane.showMessageDialog(null, "Something goes wrong. Check your intenert connection!", "Warning", JOptionPane.WARNING_MESSAGE);
				splash.splashOff();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private static boolean checkDatabaseConnection(int PORT){
		
	    Socket connection;
		ObjectOutputStream output;
	    ObjectInputStream input;
	    
		try {
			connection = new Socket();
			connection.connect(new InetSocketAddress(IP, PORT), 2000);
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
			connection.connect(new InetSocketAddress(IP, PORT), 2000);
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
	
	private static boolean checkConnection(){
		
		ArrayList<Integer> dpPorts = DatabaseClass.getPorts();
		ArrayList<Integer> fcPorts = FileClient.getPorts();
		boolean dbFLag = false;
		boolean fcFlag = false;
		
		for(int dPort : dpPorts)
			if(checkDatabaseConnection(dPort)){
				dbFLag = true;
				break;
			}
		
		if(dbFLag){
			for(int fPort : fcPorts)
				if(checkServerConnection(fPort)){
					fcFlag = true;
					break;
				}
		}
		
		return (dbFLag && fcFlag);
	}
	

}
