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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

public class VotazioneOrdController implements UserDao{
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label lblCand1;
    
    @FXML
    private Label lblCand2;
    
    @FXML
    private Label lblCand3;
    
    @FXML
    private Label lblCand4;
    
    @FXML
    private Label lblCand5;

    @FXML
    private Label lblCand6;

    @FXML
    private Label lblCand7;

    @FXML
    private Label lblCand8;
    
    @FXML
    private Spinner<Integer> spn1;
    
    @FXML
    private Spinner<Integer> spn2;
    
    @FXML
    private Spinner<Integer> spn3;
    
    @FXML
    private Spinner<Integer> spn4;
    
    @FXML
    private Spinner<Integer> spn5;

    @FXML
    private Spinner<Integer> spn6;

    @FXML
    private Spinner<Integer> spn7;

    @FXML
    private Spinner<Integer> spn8;
      
    @FXML
    void handle1(MouseEvent event) {

    }

    @FXML
    void handle2(MouseEvent event) {
    	
    }

    @FXML
    void handle3(MouseEvent event) {

    }

    @FXML
    void handle4(MouseEvent event) {

    }

    @FXML
    void handle5(MouseEvent event) {

    }

    @FXML
    void handle6(MouseEvent event) {

    }

    @FXML
    void handle7(MouseEvent event) {

    }

    @FXML
    void handle8(MouseEvent event) {
    	
    }
    
    @FXML
    private Button btnInvio;

    @FXML
    void handleInvio(ActionEvent event) throws IOException {
    	pressInvio();
    }
    
    private void pressInvio() throws IOException{   	
    	Main m = new Main();
    	m.changeScene("../gui/invioVoto.fxml");
    }
    
    /*private boolean checkVoti(){
    	i
    }*/
    
    @FXML
    void initialize() throws IOException{
    	riempiCampi();
    	initSpinner();

    }
    
    private void initSpinner() throws IOException{
    	SpinnerValueFactory<Integer> svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf7 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);
    	SpinnerValueFactory<Integer> svf8 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0);

    	spn1.setValueFactory(svf1);
        spn2.setValueFactory(svf2);
        spn3.setValueFactory(svf3);
        spn4.setValueFactory(svf4);
        spn5.setValueFactory(svf5);
        spn6.setValueFactory(svf6);
        spn7.setValueFactory(svf7);
        spn8.setValueFactory(svf8);
        
    }
    
    
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
    					}else if(i == 5) {
	    					lblCand5.setText(cognome);
	    					lblCand5.setVisible(true); 			
    					}else if(i == 6) {
	    					lblCand6.setText(cognome);
	    					lblCand6.setVisible(true); 			
    					}else if(i == 7) {
	    					lblCand7.setText(cognome);
	    					lblCand7.setVisible(true); 			
    					}else if(i == 8) {
	    					lblCand8.setText(cognome);
	    					lblCand8.setVisible(true); 			
    					}

    				}
    				
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

