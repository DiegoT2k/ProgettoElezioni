package progetto;

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
import javafx.scene.control.Button;
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
    private Label lblMessage;

    @FXML
    private PasswordField lblPassword;

    @FXML
    private TextField lblUsername;
    
    @FXML
    void handlePassword(ActionEvent event) {

    }

    @FXML
    void handleReg(ActionEvent event) {

    }

    @FXML
    void handleUsername(ActionEvent event) {

    }

    @FXML
    public void handleAcc(ActionEvent event) throws IOException{
    	checkLogin();
    }
    
    private void checkLogin() throws IOException{
    		
    	Main m = new Main();
    	String username = lblUsername.getText();
    	String pw = lblPassword.getText();


        DaoUsername dao = new DaoUsername();
        List<User> user = dao.getUser();
        
        for(User u: user) {
        	
        	if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("1")) {
        		
        		m.changeScene("afterLoginAmm.fxml"); //scena amministratore
        		
        	}else if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("0") && checkVoto(u.username)) { 
        		// elettore deve ancora votare
        		
        		m.changeScene("afterLoginElettore.fxml"); //scena elettore
        		
        	}else if(MD5(pw).equals(u.password) && username.equals(u.username) && u.amm.equals("0") && !checkVoto(u.username)) {
        		// elettore ha già votato
        		
        		String messaggio = "L'utente ha già votato";
        		lblMessage.setText(messaggio);
        		lblMessage.setVisible(true);
        		
        	}else{
        		String messaggio = "Username o Password errati!";
        		lblMessage.setText(messaggio);
        		lblMessage.setVisible(true);
        	}
        	
        }

    }

    //funzione per verificare se l'utente ha già votato o no
    public boolean checkVoto(String username) {
		String sql = "select userdata.voto from userdata where userdata.codfiscale = '" + username + "'";
		String voto = ""; 
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pr.executeQuery();

			while(rs.next()) {
				voto = rs.getString("voto");
			}
			
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(voto.equals("1")) 
			return false;
		else 	
			return true;
		
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
        assert btnReg != null : "fx:id=\"btnReg\" was not injected: check your FXML file 'main.fxml'.";
        assert lblMessage != null : "fx:id=\"lblMessage\" was not injected: check your FXML file 'main.fxml'.";
        assert lblPassword != null : "fx:id=\"lblPassword\" was not injected: check your FXML file 'main.fxml'.";
        assert lblUsername != null : "fx:id=\"lblUsername\" was not injected: check your FXML file 'main.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}
}
