package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InvioVotoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogout;

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
    	pressLogout();
    }

    private void pressLogout() throws IOException{
    	Main m = new Main();
    	m.changeScene("../gui/main.fxml");
    }
    
    
    @FXML
    void initialize() {
        assert btnLogout != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'invioVoto.fxml'.";

    }

}