import java.awt.Color;
import javax.swing.JOptionPane;
import com.thehowtotutorial.splashscreen.JSplash;

public abstract class SplashScreen {
	
	public static void startUOMslack(){
		
		
		try {
			JSplash splash = new JSplash(SplashScreen.class.getResource("logo.png"), true, true, false, "V1", null, Color.RED, Color.BLUE);
			splash.splashOn();
			splash.setProgress(20, "Loading...");
			Thread.sleep(2000);
			splash.setProgress(40, "Connecting to Database...");
			Thread.sleep(1000);
			if(Connections.checkConnection()){
				splash.setProgress(60, "Downloading data from Database...");
				Thread.sleep(1000);
				splash.setProgress(80, "Starting UoMslack...");
				Thread.sleep(500);
				new HomePage();
				splash.splashOff();
			}
			else{
				JOptionPane.showMessageDialog(null, "Something went wrong. Check your intenert connection!", "Warning", JOptionPane.WARNING_MESSAGE);
				splash.splashOff();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
