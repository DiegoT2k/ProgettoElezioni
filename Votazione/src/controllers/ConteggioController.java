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
    	int id = calcoloVinc(mod, modcalc);
    	if (mod.equals("Referendum")) {
    		displayRef(id);
    	}else {
    		displayVinc(id);
    	}
        cleanVote(mod);
    }
    
    private void displayRef(int vincitore) {
    	if (vincitore == 1) {
	    	lblVinc.setText("Il risultato del referendum è: Si");
	    	lblVinc.setVisible(true);
    	}else if (vincitore == 0) {
	    	lblVinc.setText("Il risultato del referendum è: No");
	    	lblVinc.setVisible(true);
    	}else {
    		lblErr.setVisible(true);
    	}
		
	}

	//funzione che calcola il vincitore se è presente
    private int calcoloVinc(String mod, String modcalc) throws IOException{
    	int vincitore=0;
    	if(mod.equals("Voto categorico") && modcalc.equals("Maggioranza")) {
    	    vincitore = CatMag(mod,modcalc);
    	}else if(mod.equals("Voto categorico") && modcalc.equals("Maggioranza assoluta")){
    	    vincitore = CatMagAss(mod, modcalc);
    	}else if(mod.equals("Referendum") && modcalc.equals("Quorum")){
    	    vincitore = RefQuorum(mod, modcalc);
    	}else if(mod.equals("Referendum") && modcalc.equals("Senza Quorum")){
    	    vincitore = RefNoQuorum(mod, modcalc);
    	}
    	return vincitore;

	}
    /**
     * 
    private int RefNoQuorum(String mod, String modcalc) {
    	int votiSi = 0, votiNo = 0;
    	String sql = "select * from votoreferendum where voto != 'Astensione' order by counter desc";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				System.out.println(rs.getString("voto"));
				System.out.println(rs.getInt("counter"));
				
				if(rs.getString("voto").equals("Si")) {
					votiSi = rs.getInt("counter");
				}else {
					votiNo = rs.getInt("counter");
				}	 
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		 System.out.println(votiSi);
		System.out.println(votiNo);
		if(votiSi>votiNo) {
			return 1;
		}else if(votiSi<votiNo){
			return 0;
		}else {
			return 2;
		}
		 
	}
     */

    private int RefNoQuorum(String mod, String modcalc) {
    	String sql = "select voto from votoreferendum where voto = 'Si' OR voto = 'No' order by counter desc limit 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("voto").equals("Si")) {
					return 1;
				}else {
					return 0;
				}	 
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private int RefQuorum(String mod, String modcalc) {
    	int votiTot=0,vincitore=2,nvotanti=0;
    	//Calcola se vince il si o il no
    	String sql = "select voto from votoreferendum where voto != 'Astensione' order by counter desc limit 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				System.out.println(rs.getString("voto"));
				if(rs.getString("voto").equals("Si")) {
					vincitore = 1;
				}else {
					vincitore = 0;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*//controllo quorum: num totale che possono votare
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
		String sql3 = "select sum(counter) from votoreferendum";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql3);
				){
			ResultSet rs = pr.executeQuery(sql3);
			while(rs.next()) { 
				votiTot=rs.getInt("sum(counter)");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if (votiTot>(nvotanti/2)) {
			return vincitore;
		}
		return 2;*/
		System.out.println(vincitore);
		return vincitore;
	}

	private int CatMagAss(String mod, String modcalc) {
		int voti = 0,votiTot=0,vincitore=1;
		String sql = "select idvotato from votocategorico where nvoti=(select MAX(nvoti) from votocategorico)";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			ResultSet rs = pr.executeQuery(sql);
			while(rs.next()) { 
				vincitore = rs.getInt("idvotato"); 
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
		return 0;
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
	    	lblVinc.setText("Il vincitore è: " + cognome);
	    	lblVinc.setVisible(true);
    	}else {
    		lblErr.setVisible(true);
    	}
		
    }
	
	private int CatMag(String mod, String modcalc) {
		int nvinc= 0;
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
				return 0;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return vincitore;
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
		
		return null;
	}
	
}
