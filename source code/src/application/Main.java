package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	

	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("bla.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("YOU LAG");
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(Main.class.getResource("imgs/ntc.jpg").toURI().toString()));
			primaryStage.show();
			new DisplayTrayIcon(primaryStage);
			Platform.setImplicitExit(false);
			
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                	DisplayTrayIcon.showProgramIsMinimizedMsg();
                }
            });
            
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
