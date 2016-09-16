package application;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;

import javafx.application.Platform;
import javafx.stage.Stage;



public class DisplayTrayIcon {
	
	static TrayIcon trayicon;
	static Stage stage;
	private static boolean firsttime = true;
	
	DisplayTrayIcon(final Stage stage){
		showtrayicon();
		DisplayTrayIcon.stage = stage;
	}
	
	public static void showtrayicon(){
		
		if(!SystemTray.isSupported()){
			return;
		}
		trayicon = new TrayIcon(CreateIcon("imgs/ntc.jpg","Tray Icon"));
		trayicon.setImageAutoSize(true);
		final SystemTray tray = SystemTray.getSystemTray();
		
		
        ActionListener showListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stage.show();
                    }
                });
            }
        };
        
        trayicon.addActionListener(showListener);
        
        
		try{
			tray.add(trayicon);
		}catch(AWTException awte){
			
		}
	}

	protected static Image CreateIcon(String path, String desc){
		
		URL imageURL = DisplayTrayIcon.class.getResource(path);
		return new ImageIcon(imageURL,desc).getImage();
		
	}
	
    public static void showProgramIsMinimizedMsg() {
    	if(firsttime){
	        trayicon.displayMessage("YOU LAG",
	        						"This program is now minimized and running in the background.",
	        						TrayIcon.MessageType.INFO);
	        firsttime = false;
    	}
    	
        

    }
	
}
