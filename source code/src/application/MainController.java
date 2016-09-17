package application;

import java.awt.Desktop;

import java.net.URI;
import java.net.URISyntaxException;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class MainController {
	@FXML private Label status;
	@FXML private Button pause_resume;
	@FXML private TextField host;
	@FXML private TextField timeout;
	@FXML private Button exit;
	@FXML private CheckBox sound_cb;
	@FXML private Label lping;
	@FXML private TextField lag_ping;
	private boolean sound;
	private boolean after_button_no_sound=false;
	
	private Ping ping;
	
	@FXML
	public void exit(ActionEvent event){
		
		Platform.exit();
		System.exit(0);
		
	}
	
	@FXML
	private void initialize() {
		sound = sound_cb.isSelected();
		ping = new Ping(host.getText(),new Integer(timeout.getText()),status,sound,lping,new Integer(lag_ping.getText()),after_button_no_sound);
		ping.start();
	}
	
	@FXML
	private void the_on_change_squad(KeyEvent event){
		
		
		ping.stop();
		pause_resume.setText("Resume");
		
	}
	
	
	@FXML
	private void text_fields_keyreleased_fnc(KeyEvent event){
		after_button_no_sound = true;
		the_pause_resume_button(null);
		
	}
	
	
	@FXML
	private void the_pause_resume_button(ActionEvent event){
		
		//validate input
		
		if(!timeout.getText().matches("(\\d+)?")){
			timeout.setText("0");
		}
		
		if(!lag_ping.getText().matches("(\\d+)?")){
			lag_ping.setText("0");
		}
		
		if(ping.isAlive()){
			
			ping.stop();
			
			pause_resume.setText("Resume");
			
		}else{
			
			if(fields_not_empty()){
				initialize();
				pause_resume.setText("Pause");
			}
			

			
		}

		
		
	}
	
	private boolean fields_not_empty() {
		
		if(host.getText().isEmpty() || 
				timeout.getText().isEmpty() ||
				lag_ping.getText().isEmpty()
		)
			return false;
		
		return true;
			
	}

	@FXML
	private void sound_cb(ActionEvent e){
		
		sound = sound_cb.isSelected();
		ping.stop();
		initialize();
		
	}
	
	private static void openWebpage(URI uri) {

	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	@FXML 
	private void open_my_website() throws URISyntaxException{
		String os = System.getProperty("os.name").toLowerCase();
		if(os.indexOf("win") >= 0)
			openWebpage(new URI("http://www.seifsg.com"));
		
	}

}
