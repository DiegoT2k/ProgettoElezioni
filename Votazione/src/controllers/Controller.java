package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import dao.DaoUsername;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller implements UserDao{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAcc;
    
    @FXML
    private Button btnReg;

    @FXML
    private PasswordField lblPassword;

    @FXML
    private CheckBox chckPw;

    @FXML
    private TextField lblUsername;

    @FXML
    private TextField lblPwHidden;
    
    @FXML
    void handleCheck(ActionEvent event) {
    	if(chckPw.isSelected()) {
    		lblPwHidden.setText(lblPassword.getText());
    		lblPwHidden.setDisable(false);
    		lblPwHidden.setVisible(true);
    		lblPassword.setVisible(false);
    		lblPwHidden.setDisable(false);
    		return;
    	}
    	lblPassword.setText(lblPwHidden.getText());
    	lblPassword.setVisible(true);
    	lblPwHidden.setVisible(false);
    }

    @FXML
    public void handleAcc(ActionEvent event) throws IOException{
    	checkLogin();
    }

    @FXML
    public void handleReg(ActionEvent event) throws IOException{
    	Main m = new Main();
    	m.changeScene("../gui/registrazione.fxml");
    }
    
	private void checkLogin() throws IOException{
		Alert alert= new Alert(AlertType.ERROR);
    	Main m = new Main();
    	String username = lblUsername.getText();
    	String pw = lblPassword.getText();
    	
    	if((username.isEmpty())||(pw.isEmpty())) {
    		alert.setHeaderText(null);
    		alert.setContentText("Si prega di riempire tutti i campi richiesti");
    		alert.showAndWait();
    	}else {
    		boolean a = true;
	        DaoUsername dao = new DaoUsername();
	        List<User> user = dao.getUser();
	        
	        
	        for(User u: user) {
	        	
	        	if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("1")) {
	        		
	        		m.changeScene("../gui/afterLoginAmm.fxml"); //scena amministratore
	        		a=false;
	        		break;
	        		
	        	}else if(checkSession()) {//controlla che la sessione sia aperta
	
	        		if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("0") && checkVoto(u.username)) { 
		        		// elettore deve ancora votare
		        		//imposto che ha votato
		        		voto(username);
		        		m.changeScene("../gui/afterLoginElettore.fxml"); //scena elettore
		        		a=false;
		        		break;
	        		}else if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("0") && !checkVoto(u.username)) {
		        		// elettore ha già votato
	        			alert.setHeaderText(null);
		        		alert.setContentText("L'Utente ha già votato");
		        		alert.showAndWait();
		        		a=false;
		        		break;
	        		}
	        	} else {
	        		alert.setHeaderText(null);
	        		alert.setContentText("La sessione di voto è chiusa");
	        		alert.showAndWait();
	        		a=false;
	        		break;
	        	}
	        }
	        if(a){
	        	alert.setHeaderText(null);
        		alert.setContentText("Username o Password errati!");
        		alert.showAndWait();
	        }
    	}

    }
    //funzione che verifica lo stato della sessione
    private boolean checkSession() {
		String sql = "select session.state from session";
		int sessione = 0; 
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pr.executeQuery();

			while(rs.next()) {
				sessione = rs.getInt("state");
			}
			
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (sessione == 1) {
			return true;
		}else {
			return false;
		}
    }

    //metto che l'utente ha espresso il suo voto perchè ha fatto l'accesso
    private void voto(String username) throws IOException{
		String sql = "update userdata set voto = 1 where codfiscale = '" + username + "'";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int r = pr.executeUpdate(sql);	
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    //funzione per verificare se l'utente ha già votato o no, connessione al db
    public boolean checkVoto(String username) {
		String sql = "select userdata.voto from userdata where userdata.codfiscale = '" + username + "'";
		int voto = 0; 
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pr.executeQuery();

			while(rs.next()) {
				voto = rs.getInt("voto");
			}
			
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		if(voto == 1) { 
			return false;
    	}else {
			return true;
		}
    }
    
    public String MD5(String md5) {
    	   try {
    	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
    	        byte[] array = md.digest(md5.getBytes());
    	        StringBuffer sb = new StringBuffer();
    	        for (int i = 0; i < array.length; ++i) {
    	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
    	       }
    	        return sb.toString();
    	    } catch (java.security.NoSuchAlgorithmException e) {
    	    }
    	    return null;
    }

    @FXML
    void initialize() {
        assert btnAcc != null : "fx:id=\"btnAcc\" was not injected: check your FXML file 'main.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'main.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'main.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}
}
