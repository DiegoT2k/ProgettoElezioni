package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AfterLoginControllerElettore {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnOpen;

    @FXML
    void handleOpen(ActionEvent event) throws IOException{
    	checkVoto();
    }
    
    //funzione dopo aver premuto il tasto 'vota'
    private void checkVoto() throws IOException{
    	 Main m = new Main();
    	 
    	 m.changeScene("votazione.fxml");
    	 
    }
    
    
    
    @FXML
    public void handleLogOut(ActionEvent event) throws IOException{
    	checkLogOut();
    }

    private void checkLogOut() throws IOException{
    	Main m = new Main();
    	m.changeScene("main.fxml");
    }
    
    
    @FXML
    void initialize() {
        assert btnLogOut != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginElettore.fxml'.";
        assert btnOpen != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginElettore.fxml'.";

    }

}
