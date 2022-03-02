package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ConteggioController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnOk;
    
    @FXML
    private BorderPane handelOk;

    @FXML
    void handleOk(ActionEvent event) throws IOException {
    	pressOk();
    }

    @FXML
    void initialize() {
    	 assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'conteggio.fxml'.";
         assert handelOk != null : "fx:id=\"handelOk\" was not injected: check your FXML file 'conteggio.fxml'.";
    }
    
    private void pressOk() throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/afterLoginAmm.fxml");
    }
	
}
