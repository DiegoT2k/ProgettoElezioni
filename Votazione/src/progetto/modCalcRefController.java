package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class modCalcRefController {

	String calc;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnOk;

    @FXML
    private RadioButton lblNoQuorum;

    @FXML
    private RadioButton lblQuorum;

    @FXML
    private ToggleGroup voto;

    @FXML
    void handleNoQ(ActionEvent event) {
    	calc = lblNoQuorum.getText();
    }

    @FXML
    void handleQuorum(ActionEvent event) {
    	calc = lblQuorum.getText();
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
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert lblNoQuorum != null : "fx:id=\"lblNoQuorum\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert lblQuorum != null : "fx:id=\"lblQuorum\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modCalcRef.fxml'.";

    }
    
}
