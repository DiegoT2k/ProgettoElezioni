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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class AfterLoginControllerAmm implements UserDao{
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnOpen;
    
    @FXML
    void handleClose(ActionEvent event) throws IOException {
    	if(checkSessione()){
    		Alert alert= new Alert(AlertType.CONFIRMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("Desideri confermare la chiusura della sessione di voto?");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    			closeSession();	
    		}	
    	}else if(!checkSessione()) {
    		//messaggio sessione già aperta
    		Alert alert= new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("La sessione è già chiusa");
    		alert.showAndWait();
    	}
    }
    
    @FXML
    void handleOpen(ActionEvent event) throws IOException{
    	if(!checkSessione()){
    	    openSession();	
    	}else if(checkSessione()) {
    		//messaggio sessione già aperta
    		Alert alert= new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("La sessione è già aperta");
    		alert.showAndWait();
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

    private void closeSession() throws IOException{ 
		//setta session.state = 0 = chiusa
    	
		String sql = "update session set state = 0 where idsession = 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int rs = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    	//Apertura pagina conteggio voti
    	Main m = new Main();
    	m.changeScene("../gui/conteggio.fxml");
    }
        
    private void openSession() throws IOException{
    	/**
    	 * Controllo che la sessione sia effettivamente chiusa
    	 */
    	
    	//Apertura sessione
    	Main m = new Main();
    	m.changeScene("../gui/modVoto.fxml");
    }
    
    /**
     * metodo per verificare lo stato della sessione, connessione al db
     * Post-condizioni: restituisce false se la sessione è chiusa
     * 					restituisce true se la sessione è aperta
     */
    private boolean checkSessione() {
    	
		String sql = "select state from session";
		int state = 0; 
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pr.executeQuery();

			while(rs.next()) {
				state = rs.getInt("state");
			}
			
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(state == 0) //sessione chiusa 
			return false;
		else 	//state == 1 sessione aperta
			return true;
   
    }
    
    @FXML
    void initialize() {
        assert btnLogOut != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";
        assert btnOpen != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";
        assert btnClose != null : "fx:id=\"btnLogout\" was not injected: check your FXML file 'afterLoginAmm.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
