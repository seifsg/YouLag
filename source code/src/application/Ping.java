package application;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Date;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Ping implements Runnable{

	private int result=-1;
	private String host;
	private int timeout;
	private volatile Thread t;
	private Label status;
	private boolean sound;
	private MediaPlayer player;
	private long current_ping = 0;
	private Label lping;
	private int lag_ping;
	private boolean after_button;
	
	public Ping(String h, int t,Label l,boolean s,Label lping,int lag_ping,boolean after_button){
		
		host = h;
		timeout = t;
		status = l;
		sound = s;
		this.lping = lping;
		this.lag_ping = lag_ping;
		this.after_button = after_button;
		
	}
	
	public int pingHost(String host, int port) {
		long start;
	    try (Socket socket = new Socket()) {
	    	start = System.currentTimeMillis();
	        socket.connect(new InetSocketAddress(host, port), 999);
	        
	        current_ping = System.currentTimeMillis() - start;
	        
	        Platform.runLater(()->lping.setText(current_ping+"ms"));
	        
	        System.out.println(current_ping+"ms");
	        
	        if(current_ping>=timeout)
	        	return 1;
	        else if(current_ping>=lag_ping)
	        	return 2;
	        else
	        	return 0;

	    } catch (IOException e) {
	    	
	        Platform.runLater(()->lping.setText("E"));       
	        
	        return 1; 
	    }
	}
	
	@Override
	public void run() {
		
		Date now = new Date();
		while(t!=null){
			int tmp_result = pingHost(host, 80);
			
			if(tmp_result == 0){

				
				Platform.runLater(()->status.setText("Online"));
		        System.out.println(now.toString() + " Connected");
				
		        if(result != tmp_result){
		        	playsound(0);
		        	result = 0;
		        }

				
			}else if(tmp_result == 1){
				
				Platform.runLater(()->status.setText("Offline"));
		        System.out.println(now.toString() + " Disconnected");
				
		        if(result != tmp_result){
		        	playsound(1);
		        	result = 1;
		        }

				
			}else if(tmp_result == 2){
				
				Platform.runLater(()->status.setText("Lagging"));
		        System.out.println(now.toString() + " Lag");
				
		        if(result != tmp_result){
		        	playsound(2);
		        	result = 2;
		        }
				
				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			System.out.println("Current ping: " + current_ping + "  Status: "+result + "  Max_ping: "+timeout);
			
		}
		
	}
	
	public void start(){
		
		t = new Thread(this,"ping");
		t.start();
		
	}
	
	public void stop(){
		Platform.runLater(()->status.setText("Paused"));
		t = null;
		
	}
	
	public int getResult(){
		return result;
	}
	
	public boolean isAlive(){
		
		try{
			if(t.isAlive()) return true;
		}catch(NullPointerException e){
			return false;
		}
		return false;
	}
	
	private void playsound(int result){

		if(sound && !after_button){
			try {

				Media medafile = null;
				
				switch(result){
				
					case 0: medafile = new Media(Ping.class.getResource("sound/connected.mp3").toURI().toString()); break;
					case 1: medafile = new Media(Ping.class.getResource("sound/disconnected.mp3").toURI().toString()); break;
					case 2: medafile = new Media(Ping.class.getResource("sound/lag.mp3").toURI().toString()); break;
					
				}
				
				player = new MediaPlayer(medafile);
				player.play();
				if(result == 2)
					player.play();
				
			} catch (URISyntaxException e) {

				e.printStackTrace();
			}
		}

		if(after_button) after_button = false;
	}
	
	public long get_current_ping(){
		return current_ping;
	}

	
	

}
