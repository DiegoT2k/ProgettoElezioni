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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import model.Main;
import model.User;

public class modVotoController implements UserDao{
	String modvoto= "";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnBack;
    
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
    void handleBack(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/AfterLoginAmm.fxml");
    }

    @FXML
    void handleOk(ActionEvent event) throws IOException {
    	if (modvoto.equals("")) {
        	Alert alert= new Alert(AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Si prega di selezionare una modalit� di voto");
    		alert.showAndWait();
    	}
    	pressOk();
    }
    @FXML
    void handleOrd(ActionEvent event) throws IOException {
    	modvoto = lblOrdinale.getText();
    }
    
    @FXML
    void handleCat(ActionEvent event) throws IOException {
    	modvoto = lblCategorico.getText();
    }
    
    @FXML
    void handleCatConPref(ActionEvent event) throws IOException {
    	modvoto = lblCategoricoConPref.getText();
    }
    
    @FXML
    void handleRef(ActionEvent event) throws IOException {
    	modvoto = lblReferendum.getText();
    }

    @FXML
    void initialize() {
    	 assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'modVoto.fxml'.";
    	 assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblCategorico != null : "fx:id=\"lblCategorico\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblCategoricoConPref != null : "fx:id=\"lblCategoricoConPref\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblOrdinale != null : "fx:id=\"lblOrdinale\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert lblReferendum != null : "fx:id=\"lblReferendum\" was not injected: check your FXML file 'modVoto.fxml'.";
         assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modVoto.fxml'.";
    }
    
    private void pressOk() throws IOException{    	
    	//query per impostare modvoto sul db
		String sql = "update session set modvoto = '" + modvoto + "' where idsession = 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int r = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    	Main m = new Main();
    	
    	if(modvoto.equals("Voto ordinale") || modvoto.equals("Voto categorico") || modvoto.equals("Voto categorico con preferenze")) {
    		m.changeScene("../gui/modCalcVoto.fxml");
    	}else if(modvoto.equals("Referendum")){
    		m.changeScene("../gui/modCalcRef.fxml");
    	}
    
    }
    
	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}
    
}

