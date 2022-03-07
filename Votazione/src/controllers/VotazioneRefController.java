package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import model.User;
import dao.UserDao;

public class VotazioneRefController implements UserDao{
	
	String scelta="";
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton lblNo;

    @FXML
    private Button btnVoto;

    @FXML
    private RadioButton lblAstensione;

    @FXML
    private Label lblDomanda;

    @FXML
    private RadioButton lblSi;

    @FXML
    private ToggleGroup voto;

    @FXML
    void handleAstensione(ActionEvent event) {
    	scelta=lblAstensione.getText();
    }

    @FXML
    void handleNo(ActionEvent event) {
    	scelta=lblNo.getText();
    }

    @FXML
    void handleSi(ActionEvent event) {
    	scelta=lblSi.getText();
    }

    @FXML
    void handleVoto(ActionEvent event) throws IOException {
    	Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Desideri confermare l'invio del voto?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){	
			pressInvio();
		}
    }
    
    private void pressInvio() throws IOException{
    	if(!scelta.equals("")) {
    		if(!scelta.equals("Astensione")) {
    			String sql = "update votoreferendum set counter=counter+1 where voto='"+ scelta +"'";
	        	try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
	        			PreparedStatement pr = conn.prepareStatement(sql);
	        				){
	        		int rs = pr.executeUpdate();	
	        	} catch(Exception e) {
	        		e.printStackTrace();
	        	}
    		}
    		Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Voto Inviato");
			alert.setContentText("Grazie e Buona giornata!");
			alert.showAndWait();
    		Main m = new Main();
    		m.changeScene("../gui/main.fxml");
    	}else {
    		Alert alert= new Alert(AlertType.ERROR);
			alert.setHeaderText("Voto non selezionato");
			alert.setContentText("Prego selezionare una delle 3 opzioni");
			alert.showAndWait();
    	}
    }

    @FXML
    void initialize() {
    	rempiDom();
        assert lblNo != null : "fx:id=\"No\" was not injected: check your FXML file 'votazioneRef.fxml'.";
        assert btnVoto != null : "fx:id=\"btnVoto\" was not injected: check your FXML file 'votazioneRef.fxml'.";
        assert lblAstensione != null : "fx:id=\"lblAstensione\" was not injected: check your FXML file 'votazioneRef.fxml'.";
        assert lblDomanda != null : "fx:id=\"lblDomanda\" was not injected: check your FXML file 'votazioneRef.fxml'.";
        assert lblSi != null : "fx:id=\"lblSi\" was not injected: check your FXML file 'votazioneRef.fxml'.";
        assert voto != null : "fx:id=\"voto\" was not injected: check your FXML file 'votazioneRef.fxml'.";

    }

	private void rempiDom() {
		String sql = "select domandaReferendum from session";
		String dom="";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				
				ResultSet rs = pr.executeQuery();
				while(rs.next()) {
					dom=rs.getString("domandaReferendum");
				}
				
				lblDomanda.setText(dom);
				
				rs.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
