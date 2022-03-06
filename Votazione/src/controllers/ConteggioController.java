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
import javafx.geometry.Pos;
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
    private Label lblErrQuorum;

    @FXML
    private Label lblVinc;
    
    @FXML
    void initialize() throws IOException {
    	String mod = checkMod();
    	String modcalc = checkCalc();
    	int id = calcoloVinc(mod, modcalc);
    	if (mod.equals("Referendum")) {
    		displayRef(id);
    	}else {
    		displayVinc(id);
    	}
        //cleanVote(mod);
    }
    
    private void displayRef(int vincitore) {
    	if (vincitore == 1) {
	    	lblVinc.setText("Il risultato del referendum �: Si");
	    	lblVinc.setVisible(true);
    	}else if (vincitore == 0) {
	    	lblVinc.setText("Il risultato del referendum �: No");
	    	lblVinc.setVisible(true);
    	}else {
    		lblErr.setVisible(true);
    	}
		
	}

	//funzione che calcola il vincitore se � presente
    private int calcoloVinc(String mod, String modcalc) throws IOException{
    	int vincitore=0;
    	if(mod.equals("Voto categorico") && modcalc.equals("Maggioranza")) {
    	    vincitore = CatMag();
    	}else if(mod.equals("Voto categorico") && modcalc.equals("Maggioranza assoluta")){
    	    vincitore = CatMagAss();
    	}else if(mod.equals("Referendum") && modcalc.equals("Quorum")){
    	    vincitore = RefQuorum();
    	}else if(mod.equals("Referendum") && modcalc.equals("Senza Quorum")){
    	    vincitore = RefNoQuorum();
    	}else if(mod.equals("Voto categorico con preferenze") && modcalc.equals("Maggioranza")){
    	    //vincitore = CatPrefMag();
    	}else if(mod.equals("Voto categorico con preferenze") && modcalc.equals("Maggioranza assoluta")){
    	    //vincitore = CatPrefMagAss();
    	}else if(mod.equals("Voto ordinale") && modcalc.equals("Maggioranza")){
    	    vincitore = OrdMag();
    	}else if(mod.equals("Voto ordinale") && modcalc.equals("Maggioranza assoluta")){
    	    vincitore = OrdMagAss();
    	}
    	return vincitore;

	}
    
    private int RefNoQuorum() {
    	int vincitore = 2,nvinc = 0;
    	String sql = "select voto from votoreferendum where counter = (select max(counter) from votoreferendum)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				nvinc++;
				if (nvinc==1) {
					if(rs.getString("voto").equals("Si")) {
						vincitore = 1;
					}else {
						vincitore = 0;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(nvinc>1) {
			return 2;
		}else {
			return vincitore;
		}	 
    }

	private int RefQuorum() {
		int vincitore = 2,nvinc = 0;
    	String sql = "select voto from votoreferendum where counter = (select max(counter) from votoreferendum)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				nvinc++;
				if (nvinc==1) {
					if(rs.getString("voto").equals("Si")) {
						vincitore = 1;
					}else {
						vincitore = 0;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(nvinc>1) {
			return 2;
		}else {
			int nvotanti=0,votiTot=0;
			//controllo quorum: num totale che possono votare
			String sql2 = "select count(*) from credentials where amm!='1'";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql2);
					){
				ResultSet rs = pr.executeQuery(sql2);
				while(rs.next()) { 
					nvotanti=rs.getInt("count(*)");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			//controllo quorum: num di voti tot
			String sql3 = "select count(*) from userdata where voto='1'";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql3);
					){
				ResultSet rs = pr.executeQuery(sql3);
				while(rs.next()) { 
					votiTot=rs.getInt("count(*)");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (votiTot>(nvotanti/2)) {
				return vincitore;
			}
			lblErrQuorum.setVisible(true);
			return 2;
		}
	}

	private int CatMagAss() {
		int voti = 0, votiTot = 0, vincitore = 1,nvinc = 0;
		String sql = "select idvotato from votocategorico where nvoti=(select MAX(nvoti) from votocategorico)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				vincitore=rs.getInt("idvotato");
				nvinc++;
			}
			if (nvinc>1) {
				lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza");
				lblErrQuorum.setVisible(true);
				lblErr.setVisible(true);
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		String sql2 = "select max(nvoti) from votocategorico";
    	try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    		PreparedStatement pr = conn.prepareStatement(sql2);
    			){
    		ResultSet rs = pr.executeQuery(sql2);
    		while(rs.next()) { 
    			voti = rs.getInt("max(nvoti)");
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	String sql3 = "select sum(nvoti) from votocategorico";
    	try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    		PreparedStatement pr = conn.prepareStatement(sql3);
    			){
    		ResultSet rs = pr.executeQuery(sql3);
    		while(rs.next()) { 
    			votiTot = rs.getInt("sum(nvoti)");
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
		if(voti>(votiTot/2)) {
			return vincitore;
		}
		lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza assoluta");
		lblErrQuorum.setVisible(true);
		lblErr.setVisible(true);
		return 0;
	}

	private int OrdMagAss() {
		int voti = 0, votiTot = 0, vincitore = 1,nvinc = 0;
		String sql = "select idcandvotato from votoordinale where voti = (select MIN(voti) from votoordinale)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				vincitore=rs.getInt("idcandvotato");
				nvinc++;
			}
			if (nvinc>1) {
				lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza");
				lblErrQuorum.setVisible(true);
				lblErr.setVisible(true);
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		String sql2 = "select min(voti) from votoordinale";
    	try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    		PreparedStatement pr = conn.prepareStatement(sql2);
    			){
    		ResultSet rs = pr.executeQuery(sql2);
    		while(rs.next()) { 
    			voti = rs.getInt("min(voti)");
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	String sql3 = "select sum(voti) from votoordinale";
    	try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
    		PreparedStatement pr = conn.prepareStatement(sql3);
    			){
    		ResultSet rs = pr.executeQuery(sql3);
    		while(rs.next()) { 
    			votiTot = rs.getInt("sum(voti)");
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
		if(voti<(votiTot/2)) {
			return vincitore;
		}
		lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza assoluta");
		lblErrQuorum.setVisible(true);
		lblErr.setVisible(true);
		return 0;
	}
	
	private int OrdMag() {
		int nvinc = 0;
		int vincitore = 1;
		String sql = "select idcandvotato from votoordinale where voti = (select MIN(voti) from votoordinale)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				vincitore=rs.getInt("idcandvotato");
				nvinc++;
			}
			if (nvinc>1) {
				lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza");
				lblErrQuorum.setVisible(true);
				lblErr.setVisible(true);
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return vincitore;
	}
	
	private int CatMag() {
		int nvinc = 0;
		int vincitore = 1;
		String sql = "select idvotato from votocategorico where nvoti=(select MAX(nvoti) from votocategorico)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				vincitore=rs.getInt("idvotato");
				nvinc++;
			}
			if (nvinc>1) {
				lblErrQuorum.setText("Nessun candidato ha ottenuto la maggioranza");
				lblErrQuorum.setVisible(true);
				lblErr.setVisible(true);
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return vincitore;
	}
	
	//funzione che mette a display il nome del vincitore o un messaggio di errore
	private void displayVinc(int vincitore) throws IOException {
    	if (vincitore!=0) {
    		String cognome = "";
			String sql = "select cognome from candidati where idcand = " + vincitore;
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				ResultSet rs = pr.executeQuery(sql);
				while(rs.next()) { cognome = rs.getString("cognome"); }
			} catch(Exception e) {
				e.printStackTrace();
			}
	    	lblVinc.setText("Il vincitore �: " + cognome);
	    	lblVinc.setVisible(true);
    	}else {
    		lblErr.setVisible(true);
    	}
		
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
		}else if(mod.equals("Voto ordinale")) {
			//reset tabella votoordinale
			String sql = "update votoordinale set voti = 0 where idcandvotato != 0";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				int r = pr.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}			
		}else if(mod.equals("Voto categorico con preferenze")) {
			//reset tabella votocatconpref
			String sql = "update votocatconpref set nvoti = 0 where idvotato != 0";
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
				PreparedStatement pr = conn.prepareStatement(sql);
					){
				int r = pr.executeUpdate(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		}

	//funzione che verifica che modalit� di calcolo vincitore � stata inserita
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
	
	//funzione che verifica che modalit� di voto �
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
		
		return null;
	}
	
}
