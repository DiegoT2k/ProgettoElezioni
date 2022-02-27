package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ModCalcVotoController {

	String calc;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnOk;

    @FXML
    private RadioButton lblAss;

    @FXML
    private RadioButton lblMagg;

    @FXML
    private ToggleGroup voto;

    @FXML
    void handleAss(ActionEvent event) {
    	calc = lblAss.getText();
    }

    @FXML
    void handleMagg(ActionEvent event) {
    	calc = lblMagg.getText();
    }

    @FXML
    void handleOk(ActionEvent event) throws IOException {
    	System.out.println(calc);
    	pressOk();
    }

    private void pressOk() throws IOException{
    	Main m = new Main();
    	m.changeScene("open.fxml");
    }
    
    @FXML
    void initialize() {
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert lblAss != null : "fx:id=\"lblAss\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert lblMagg != null : "fx:id=\"lblMagg\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modCalcVoto.fxml'.";

    }

}