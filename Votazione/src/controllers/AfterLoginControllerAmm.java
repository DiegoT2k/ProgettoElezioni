package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private Label lblError;
    
    @FXML
    void handleClose(ActionEvent event) throws IOException {
    	if(checkSessione()){
    		closeSession();		
    	}else if(!checkSessione()) {
    		//messaggio sessione già aperta
    		String messaggio = "La sessione è già chiusa";
    		lblError.setText(messaggio);
    		lblError.setVisible(true);
    	}
    }
    
    @FXML
    void handleOpen(ActionEvent event) throws IOException{
    	if(!checkSessione()){
    	    openSession();	
    	}else if(checkSessione()) {
    		//messaggio sessione già aperta
    		String messaggio = "La sessione è già aperta";
    		lblError.setText(messaggio);
    		lblError.setVisible(true);
    	}
    }
    
    @FXML
    public void handleLogOut(ActionEvent event) throws IOException{
    	checkLogOut();
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
		resetVoti();
		
    	//Apertura pagina conteggio voti
    	Main m = new Main();
    	m.changeScene("../gui/conteggio.fxml");
    }
    
    private void resetVoti() throws IOException{
    	String sql = "update userdata set voto = 0";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int rs = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
