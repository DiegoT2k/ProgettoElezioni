package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class modCalcRefController implements UserDao{

	String calc = "";
	
	String ques=null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnBack;

    @FXML
    private Button btnOk;
    
    @FXML
    private TextField fldQues;

    @FXML
    private RadioButton lblNoQuorum;

    @FXML
    private RadioButton lblQuorum;

    @FXML
    private ToggleGroup voto;

    @FXML
    void handleBack(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/ModVoto.fxml");
    }
    
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
		ques=fldQues.getText();
		
		if(ques.equals("")) {
			Alert alert= new Alert(AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Si prega di inserire un quesito valido");
    		alert.showAndWait();
		} else {
			updateQues();
			
			if ((lblNoQuorum.isSelected())||(lblQuorum.isSelected())) {
				Alert alert= new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Desideri confermare l'apertura della sessione di voto?");
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					alert= new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setContentText("Sessione aperta");
					alert.showAndWait();
					//apri sessione, metti state = 1 = aperta
					String sql2 = "update session set state = 1 where idsession = 1";
					try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
						PreparedStatement pr = conn.prepareStatement(sql2);
							){
						int r = pr.executeUpdate(sql2);
					} catch(Exception e) {
						e.printStackTrace();
					}
					
			    	Main m = new Main();
			    	m.changeScene("../gui/afterLoginAmm.fxml");
				}		
			} else {
				Alert alert= new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
	    		alert.setContentText("Si prega di selezionare una modalità di calcolo del vincitore");
	    		alert.showAndWait();
			}
		}
    }
    
    //funzione che inserisce il quesito del referendum nel db
    private void updateQues() throws IOException{
    	String sql = "update session set domandaReferendum = '" + ques + "'";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int r = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void initialize() {
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert lblNoQuorum != null : "fx:id=\"lblNoQuorum\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert lblQuorum != null : "fx:id=\"lblQuorum\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'modCalcRef.fxml'.";
        assert fldQues != null : "fx:id=\"fldQues\" was not injected: check your FXML file 'modCalcRef.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
