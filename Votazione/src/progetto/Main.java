package progetto;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stg;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	stg = primaryStage;
    	primaryStage.setResizable(false);
    	
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                
        primaryStage.setTitle("Sistema elettorale");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
        
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    
    public static void main(String[] args) {
    	
        launch(args);
    }
    
}
