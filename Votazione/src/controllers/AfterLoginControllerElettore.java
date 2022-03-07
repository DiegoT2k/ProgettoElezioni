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

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class AfterLoginControllerElettore implements UserDao{
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnOpen;

    @FXML
    void handleOpen(ActionEvent event) throws IOException{
    	checkVoto();
    }
    
    //funzione dopo aver premuto il tasto 'vota'
    private void checkVoto() throws IOException{
    	Main m = new Main();
    	
    	//verifica di che modalità di voto si tratta per cambiare scena
		String sql = "select modvoto from session";
		String mod = "";
		
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				
				ResultSet rs = pr.executeQuery();

				while(rs.next()) {
					mod = rs.getString("modvoto");
				}
				
				rs.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		
		if(mod.equals("Voto ordinale")) {
			m.changeScene("../gui/votazioneOrd.fxml");
		}else if(mod.equals("Voto categorico")) {
			m.changeScene("../gui/votazioneCat.fxml");
		}else if(mod.equals("Voto categorico con preferenze")) {
			m.changeScene("../gui/votazioneCatConPref.fxml");
		}else if(mod.equals("Referendum")) {
			m.changeScene("../gui/votazioneRef.fxml");	
		}
    	 
    }
    
    
    
    @FXML
    public void handleLogOut(ActionEvent event) throws IOException{
    	Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Desideri confermare il LogOut?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			checkLogOut();
		}
    }

    private void checkLogOut() throws IOException{
    	Main m = new Main();
    	m.changeScene("../gui/main.fxml");
    }
    
    
    @FXML
    void initialize() {
        assert btnLogOut != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginElettore.fxml'.";
        assert btnOpen != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginElettore.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
