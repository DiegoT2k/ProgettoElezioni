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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class VotazioneCatConPrefController implements UserDao{
	
	int sceltaCand = 0;
	int sceltaPart = 0;
	
	String votoPart="", votoCand;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnInvio;

    @FXML
    private RadioButton lblCand1;

    @FXML
    private RadioButton lblCand2;

    @FXML
    private RadioButton lblCand3;

    @FXML
    private RadioButton lblCand4;

    @FXML
    private RadioButton lblCand5;

    @FXML
    private RadioButton lblCand6;

    @FXML
    private RadioButton lblCand7;

    @FXML
    private RadioButton lblCand8;

    @FXML
    private RadioButton lblPart1;

    @FXML
    private RadioButton lblPart2;

    @FXML
    private RadioButton lblPart3;

    @FXML
    private RadioButton lblPart4;

    @FXML
    void handleC1(ActionEvent event) {
    	sceltaCand = 1;
    }

    @FXML
    void handleC2(ActionEvent event) {
    	sceltaCand = 7;
    }

    @FXML
    void handleC3(ActionEvent event) {
    	sceltaCand = 4;
    }

    @FXML
    void handleC4(ActionEvent event) {
    	sceltaCand = 6;
    	}

    @FXML
    void handleC5(ActionEvent event) {
    	sceltaCand = 2;
    }

    @FXML
    void handleC6(ActionEvent event) {
    	sceltaCand = 5;
    }

    @FXML
    void handleC7(ActionEvent event) {
    	sceltaCand = 3;
    }

    @FXML
    void handleC8(ActionEvent event) {
    	sceltaCand = 8;
    }

    @FXML
    void handleP1(ActionEvent event) {
    	sceltaPart = 1;
    }

    @FXML
    void handleP2(ActionEvent event) {
    	sceltaPart = 2;
    }

    @FXML
    void handleP3(ActionEvent event) {
    	sceltaPart = 3;
    }

    @FXML
    void handleP4(ActionEvent event) {
    	sceltaPart = 4;
    }
    
    @FXML
    private ToggleGroup votocandidato;

    @FXML
    private ToggleGroup votopartito;

    @FXML
    void handleInvio(ActionEvent event) throws IOException {
    	Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Desideri confermare l'invio del voto?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){	
			alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Voto Inviato");
			alert.setContentText("Grazie e Buona giornata!");
			alert.showAndWait();
			checkVoto();
		    pressInvio();
		}
    }
    
    //funzione che controlla non voto disgiunto
    private void checkVoto() throws IOException{
    	//prendo partito dal candidato votato
    	int part = checkPart();
    	if(sceltaCand == 0 || sceltaPart == 0) {
    		//voto nullo
    		System.out.println("VOTO NULLO");
    	}else if(sceltaPart != part) {
    		//voto disgiunto
    		System.out.println("VOTO DISGIUNTO");
    	}else {
    		//voto corretto
    		String sql = "update votocatconpref set nvoti = nvoti + 1 where idvotato = " + sceltaCand;
    		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    			PreparedStatement pr = conn.prepareStatement(sql);
    				){
    			int r = pr.executeUpdate(sql);
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    private int checkPart() throws IOException{
    	int part = 0;
		String sql = "select partito from candidati where idcand = " + sceltaCand;
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs= pr.executeQuery();
			while(rs.next()) {
				part = rs.getInt("partito");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return part;
    }
    
    private void pressInvio() throws IOException{
    	Main m = new Main();
    	m.changeScene("../gui/main.fxml");
    }
    
    private void riempiPart() throws IOException{
       	//seleziona partiti
    		String sql = "select nome from partiti";
    		String nome = "";
    		int i = 0;
    		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    				PreparedStatement pr = conn.prepareStatement(sql);
    					){
    				
    				ResultSet rs = pr.executeQuery();

    				while(rs.next()) {
    					i++;
    					nome = rs.getString("nome");
    					if(i == 1) {
    						lblPart1.setText(nome);
    						lblPart1.setVisible(true);    						
    					}else if(i == 2) {
	    					lblPart2.setText(nome);
	    					lblPart2.setVisible(true);   						
    					}else if(i == 3) {
	    					lblPart3.setText(nome);
	    					lblPart3.setVisible(true);  						
    					}else if(i == 4) {
	    					lblPart4.setText(nome);
	    					lblPart4.setVisible(true); 			
    					}
    				}
    				
    				rs.close();
    				
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    		 	
    }
    
    private void riempiCand() throws IOException{
       	//seleziona candidati
    		String sql = "select cognome from candidati order by partito";
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

    @FXML
    void initialize() throws IOException {
    	riempiPart();
    	riempiCand();

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}

