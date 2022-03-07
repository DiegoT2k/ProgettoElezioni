package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ResourceBundle;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Elettore;
import model.User;

public class RegController implements UserDao{
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnReg;

    @FXML
    private TextField lblCodFiscale;

    @FXML
    private TextField lblCognome;

    @FXML
    private TextField lblComuneNascita;

    @FXML
    private DatePicker lblDataNascita;

    @FXML
    private RadioButton lblDonna;

    @FXML
    private TextField lblNazioneNascita;

    @FXML
    private TextField lblNome;

    @FXML
    private RadioButton lblUomo;

    @FXML
    private ToggleGroup sesso;


    @FXML
    void handleBack(ActionEvent event) throws IOException {
    	Main m = new Main();
    	m.changeScene("../gui/main.fxml");
    }

    @FXML
    void handleReg(ActionEvent event) throws IOException {
    	
    	//Elettore e = new Elettore(lblNome.getText(), lblCognome.getText(), lblCodFiscale.getText(), lblDataNascita.getAccessibleText(), lblComuneNascita.getText(), lblNazioneNascita.getText(), lblSesso.getText());
    	
    	//se rispetta tutte le condizioni per la creazione dell'elettore
    	//charge(e);
    	
    	Main m = new Main();
    	m.changeScene("../gui/main.fxml");
    }
    
    private void charge(Elettore e) throws IOException{
		String sql = "INSERT INTO `dbvotazione`.`userdata` (`nome`, `cognome`, `codfiscale`, `datan`, `comunen`, `nazionen`, `sesso`, `voto`) VALUES ( '" + e.nome + "' , '" + e.cognome + "' ,'" + e.codfiscale + "','" + e.datan + "', '" + e.comunen + "' , '" + e.nazionen + "' , '" + e.sesso + "' , " + e.voto+ ")";
		
			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
					PreparedStatement pr = conn.prepareStatement(sql);
						){
					int r = pr.executeUpdate(sql);
				} catch(Exception e1) {
					e1.printStackTrace();
				}
    
    }
    
    @FXML
    void initialize() {
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert btnReg != null : "fx:id=\"btnReg\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblCodFiscale != null : "fx:id=\"lblCodFiscale\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblCognome != null : "fx:id=\"lblCognome\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblComuneNascita != null : "fx:id=\"lblComuneNascita\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblDataNascita != null : "fx:id=\"lblDataNascita\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblNazioneNascita != null : "fx:id=\"lblNazioneNascita\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert lblNome != null : "fx:id=\"lblNome\" was not injected: check your FXML file 'registrazione.fxml'.";

    }

	@Override
	public List<User> getUser() {
		return null;
	}

}
