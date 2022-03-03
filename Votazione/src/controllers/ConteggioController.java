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
import javafx.scene.layout.BorderPane;

public class ConteggioController implements UserDao{
	
	String vinc;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button btnAvanti;

    @FXML
    private Label lblErr;

    @FXML
    private Label lblVinc;
    
    @FXML
    void initialize() throws IOException {
    	String mod = checkMod();
    	String modcalc = checkCalc();
    	calcoloVinc(mod, modcalc);
        displayVinc(mod, modcalc);
        cleanVote(mod);
    }
    
    //funzione che calcola il vincitore se è presente
    private void calcoloVinc(String mod, String modcalc) throws IOException{
    	
	}
    //funzione che mette a display il nome del vincitore o un messaggio di errore
	private void displayVinc(String mod, String modcalc) throws IOException {
    	
    }
	
	//funzione che resetta tutti i voti
	private void cleanVote(String mod) throws IOException{
		if(mod.equals("Voto categorico")) {
			//reset tabella votocategorico
			String sql = "update votocategorico set nvoti = 0 where idvotato != 0";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				int r = pr.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}else if(mod.equals("Referendum")) {
			//reset tabella votoreferendum
			String sql = "update votoreferendum set counter = 0 where voto != 0";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				int r = pr.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		}

	//funzione che verifica che modalità di calcolo vincitore è stata inserita
	private String checkCalc() throws IOException{
		String sql = "select modcalcolo from session";
		String mod = "";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet r = pr.executeQuery(sql);
			while(r.next()) {
				mod = r.getString("modcalcolo");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mod;
	}
	
	//funzione che verifica che modalità di voto è
	private String checkMod() throws IOException{
		String sql = "select modvoto from session";
		String mod = "";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet r = pr.executeQuery(sql);
			while(r.next()) {
				mod = r.getString("modvoto");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mod;
	}
	
    @FXML
    void handleAvanti(ActionEvent event) throws IOException {
    	pressOk();
    }
    
    private void pressOk() throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/afterLoginAmm.fxml");
    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
