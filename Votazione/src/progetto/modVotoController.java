package progetto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class modVotoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnOk;

    @FXML
    void handleOk(ActionEvent event) throws IOException{
    	//pressOk();
    }
    
/**
 * 
 *     private void pressOk() throws IOException{
    	Main m = new Main();
    	m.changeScene("modCalcVoto");
    }
 */

    @FXML
    void initialize() {
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modVoto.fxml'.";

    }
    
}
