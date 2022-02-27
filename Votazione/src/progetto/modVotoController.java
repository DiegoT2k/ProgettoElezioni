package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class modVotoController {
	String modvoto= "";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnOk;

    @FXML
    private RadioButton lblCategorico;

    @FXML
    private RadioButton lblCategoricoConPref;

    @FXML
    private RadioButton lblOrdinale;

    @FXML
    private RadioButton lblReferendum;
    
    @FXML
    private ToggleGroup voto;

    @FXML
    void handleOk(ActionEvent event) throws IOException {
    	pressOk();
    }
    @FXML
    void handleOrd(ActionEvent event) throws IOException {
    	modvoto= lblOrdinale.getText();
    }
    
    @FXML
    void handleCat(ActionEvent event) throws IOException {
    	modvoto= lblCategorico.getText();
    }
    
    @FXML
    void handleCatConPref(ActionEvent event) throws IOException {
    	modvoto= lblCategoricoConPref.getText();
    }
    
    @FXML
    void handleRef(ActionEvent event) throws IOException {
    	modvoto= lblReferendum.getText();
    }

    @FXML
    void initialize() {
    	 assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblCategorico != null : "fx:id=\"lblCategorico\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblCategoricoConPref != null : "fx:id=\"lblCategoricoConPref\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblOrdinale != null : "fx:id=\"lblOrdinale\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblReferendum != null : "fx:id=\"lblReferendum\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modVoto.fxml'.";
    }
    
    private void pressOk() throws IOException{    	
    	System.out.print(modvoto);
    	/*Main m = new Main();
    	m.changeScene("modCalcVoto");*/
    }
    
}

