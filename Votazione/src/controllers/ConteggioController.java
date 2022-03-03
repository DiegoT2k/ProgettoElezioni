package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ConteggioController {
	
	String vinc;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button btnAvanti;

    @FXML
    private Label lblErr;

    @FXML
    private Label lblVinc;
    
    @FXML
    void initialize() throws IOException {
    	calcoloVinc();
        displayVinc();
    }
    //funzione che calcola il vincitore se è presente
    private void calcoloVinc() {
    	
	}
    //funzione che mette a display il nome del vincitore o un messaggio di errore
	private void displayVinc() throws IOException {
    	
    }

    @FXML
    void handleAvanti(ActionEvent event) throws IOException {
    	pressOk();
    }
    
    private void pressOk() throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/afterLoginAmm.fxml");
    }
	
}
