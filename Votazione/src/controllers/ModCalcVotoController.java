package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ResourceBundle;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ModCalcVotoController implements UserDao{

	String calc;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnBack;

    @FXML
    private Button btnOk;

    @FXML
    private RadioButton lblAss;

    @FXML
    private RadioButton lblMagg;

    @FXML
    private ToggleGroup voto;
    

    @FXML
    void handleBack(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/ModVoto.fxml");
    }
    
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
    	pressOk();
    }

    private void pressOk() throws IOException{
    	//query per impostare modcalcolo sul db
		String sql = "update session set modcalcolo = '" + calc + "' where idsession = 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int r = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if ((calc.equals("Maggioranza"))||(calc.equals("Maggioranza assoluta"))) {
			Main m = new Main();
			m.changeScene("../gui/open.fxml");
		}
    	
    }
    
    @FXML
    void initialize() {
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
    	assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert lblAss != null : "fx:id=\"lblAss\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert lblMagg != null : "fx:id=\"lblMagg\" was not injected: check your FXML file 'modCalcVoto.fxml'.";
        assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modCalcVoto.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}


}