package progetto;

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
import javafx.scene.control.RadioButton;

public class VotazioneCatController implements UserDao{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton lblCand1;
	
    @FXML
    private RadioButton lblCand2;

    @FXML
    private RadioButton lblCand3;

    @FXML
    private RadioButton lblCand4;

    private void riempiCampi() throws IOException{
       	//seleziona candidati
    		String sql = "select cognome from candidati";
    		String cognome = "";
    		int i = 0;
    		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    				PreparedStatement pr = conn.prepareStatement(sql);
    					){
    				
    				ResultSet rs = pr.executeQuery();

    				while(rs.next()) {
    					i++;
    					cognome = rs.getString("cognome");
    					if(i == 1) {
	    					lblCand1.setText(cognome);
	    					lblCand1.setVisible(true);    						
    					}else if(i == 2) {
	    					lblCand2.setText(cognome);
	    					lblCand2.setVisible(true);   						
    					}else if(i == 3) {
	    					lblCand3.setText(cognome);
	    					lblCand3.setVisible(true);  						
    					}else if(i == 4) {
	    					lblCand4.setText(cognome);
	    					lblCand4.setVisible(true); 			
    					}

    				}
    				
    				rs.close();
    				
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		 	
    }

    @FXML
    private Button btnInvio;

    @FXML
    void handleInvio(ActionEvent event) throws IOException {
    	pressInvio();
    }
    
    private void pressInvio() throws IOException{
    	Main m = new Main();
    	m.changeScene("invioVoto.fxml");
    }
    
    @FXML
    void initialize() throws IOException {
    	riempiCampi();
        assert btnInvio != null : "fx:id=\"btnInvio\" was not injected: check your FXML file 'votazioneCat.fxml'.";
        assert lblCand1 != null : "fx:id=\"lblCand1\" was not injected: check your FXML file 'votazioneCat.fxml'.";
        assert lblCand2 != null : "fx:id=\"lblCand2\" was not injected: check your FXML file 'votazioneCat.fxml'.";
        assert lblCand3 != null : "fx:id=\"lblCand3\" was not injected: check your FXML file 'votazioneCat.fxml'.";
        assert lblCand4 != null : "fx:id=\"lblCand4\" was not injected: check your FXML file 'votazioneCat.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
