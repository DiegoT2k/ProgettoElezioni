package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AfterLoginControllerAmm {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnOpen;

    @FXML
    void handleClose(ActionEvent event) throws IOException {
    	closeSession();
    }
    
    @FXML
    void handleOpen(ActionEvent event) throws IOException{
    	openSession();
    }
    
    @FXML
    public void handleLogOut(ActionEvent event) throws IOException{
    	checkLogOut();
    }

    private void checkLogOut() throws IOException{
    	Main m = new Main();
    	m.changeScene("main.fxml");
    }

    private void closeSession() throws IOException{
    	
    	/**
    	 * Controllo che la sessione sia effettivamente aperta
    	 */
    	
    	//Apertura pagina conteggio voti
    	Main m = new Main();
    	m.changeScene("conteggio.fxml");
    }
    
    private void openSession() throws IOException{
    	/**
    	 * Controllo che la sessione sia effettivamente chiusa
    	 */
    	
    	//Apertura sessione
    	Main m = new Main();
    	m.changeScene("modVoto.fxml");
    }
    
    @FXML
    void initialize() {
        assert btnLogOut != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";
        assert btnOpen != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";
        assert btnClose != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";

    }

}
