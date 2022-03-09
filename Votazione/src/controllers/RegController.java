package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.scene.control.Alert.AlertType;
import model.Elettore;
import model.Main;
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
    	LocalDate currentDate = LocalDate.now();
    	
    	String sesso="";
    	if (lblUomo.isSelected()) {
    		sesso = "m";
    	}else if (lblDonna.isSelected()) {
    		sesso = "f";
    	}
    	if((lblNome.getText().isBlank())||(lblCognome.getText().isBlank())||(lblCodFiscale.getText().isBlank())||(lblDataNascita.getValue().toString().isBlank())||(lblComuneNascita.getText().isBlank())||(lblNazioneNascita.getText().isBlank())||(lblNome.getText().isBlank())||(sesso.isBlank())) {
    		Alert alert= new Alert(AlertType.ERROR);
    		alert.setHeaderText("Registrazione incompleta");
    		alert.setContentText("Si prega di inserire tutte le informazioni richieste.");
    		alert.showAndWait();
    	}else {
    		if((currentDate.getYear()-lblDataNascita.getValue().getYear())<18) {
    			Alert alert= new Alert(AlertType.ERROR);
        		alert.setHeaderText("Utente minorenne");
        		alert.setContentText("Mi dispiace ma è necessario avere almeno 18 anni per registrarsi.");
        		alert.showAndWait();
    			
    		}else {
    			if((currentDate.getYear()-lblDataNascita.getValue().getYear())==18) {
    				if(currentDate.getMonthValue()<lblDataNascita.getValue().getMonthValue()) {
    					Alert alert= new Alert(AlertType.ERROR);
    	        		alert.setHeaderText("Utente minorenne");
    	        		alert.setContentText("Mi dispiace ma è necessario avere almeno 18 anni per registrarsi.");
    	        		alert.showAndWait();
    				} else {
    					if(currentDate.getMonthValue()==lblDataNascita.getValue().getMonthValue()) {
    						if(currentDate.getDayOfMonth()<lblDataNascita.getValue().getDayOfMonth()) {
    							Alert alert= new Alert(AlertType.ERROR);
    	    	        		alert.setHeaderText("Utente minorenne");
    	    	        		alert.setContentText("Mi dispiace ma è necessario avere almeno 18 anni per registrarsi.");
    	    	        		alert.showAndWait();
        					} else {
        						Elettore e = new Elettore(lblNome.getText(), lblCognome.getText(), lblCodFiscale.getText(), lblDataNascita.getValue().toString(), lblComuneNascita.getText(), lblNazioneNascita.getText(),sesso);
        				    	
        		    			//se rispetta tutte le condizioni per la creazione dell'elettore
        		    			charge(e);
        					}
    					} else {
	    					Elettore e = new Elettore(lblNome.getText(), lblCognome.getText(), lblCodFiscale.getText(), lblDataNascita.getValue().toString(), lblComuneNascita.getText(), lblNazioneNascita.getText(),sesso);
	    			    	
	    	    			//se rispetta tutte le condizioni per la creazione dell'elettore
	    	    			charge(e);
    					}
    				} 
    			} else {
    				Elettore e = new Elettore(lblNome.getText(), lblCognome.getText(), lblCodFiscale.getText(), lblDataNascita.getValue().toString(), lblComuneNascita.getText(), lblNazioneNascita.getText(),sesso);
    		    	
        			//se rispetta tutte le condizioni per la creazione dell'elettore
        			charge(e);
    			}
    		}
    		
    	}
    }
    
    private void charge(Elettore e) throws IOException{
    	boolean a = true;
    	if(!checkCodFiscale(e)) {
    		Alert alert= new Alert(AlertType.ERROR);
    		alert.setHeaderText("Errore!");
    		alert.setContentText("Il codice fiscale da lei inserito non è corretto ");
    		alert.showAndWait();
    	} else {
	    	while(a) {
		    	// Create the custom dialog.
		    	Dialog<Pair<String, String>> dialog = new Dialog<>();
		    	dialog.setTitle("Inserimento password");
		    	dialog.setHeaderText("Si prega di inserire la password");
		    	
		    	// Set the button types.
		    	ButtonType loginButtonType = new ButtonType("Conferma", ButtonData.OK_DONE);
		    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		    	
		    	// Create the username and password labels and fields.
		    	GridPane grid = new GridPane();
		    	grid.setHgap(10);
		    	grid.setVgap(10);
		    	grid.setPadding(new Insets(20, 150, 10, 10));
		
		    	PasswordField password = new PasswordField();
		    	password.setPromptText("Password");
		    	PasswordField confpassword = new PasswordField();
		    	confpassword.setPromptText("Conferma password");
		
		    	grid.add(new Label("Password:"), 0, 0);
		    	grid.add(password, 1, 0);
		    	grid.add(new Label("Conferma Password:"), 0, 1);
		    	grid.add(confpassword, 1, 1);
		    	
		    	dialog.getDialogPane().setContent(grid);
		    	
		    	// Enable/Disable login button depending on whether a username was entered.
		    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		    	loginButton.setDisable(true);
	
		    	// Do some validation (using the Java 8 lambda syntax).
		    	password.textProperty().addListener((observable, oldValue, newValue) -> {
		    	    loginButton.setDisable(newValue.trim().isEmpty());
		    	});
		    	
		    	// Convert the result to a username-password-pair when the login button is clicked.
		    	dialog.setResultConverter(dialogButton -> {
		    	    if (dialogButton == loginButtonType) {
		    	        return new Pair<>(password.getText(), confpassword.getText());
		    	    }
		    	    return null;
		    	});
		    	
				Optional<Pair<String, String>> result = dialog.showAndWait();
				if(result.isPresent()) {
		    		if(confpassword.getText().equals(password.getText())) {   
		    			String sql = "INSERT INTO `dbvotazione`.`userdata` (`nome`, `cognome`, `codfiscale`, `datan`, `comunen`, `nazionen`, `sesso`, `voto`) VALUES ( '" + e.nome + "' , '" + e.cognome + "' ,'" + e.codfiscale + "','" + e.datan + "', '" + e.comunen + "' , '" + e.nazionen + "' , '" + e.sesso + "' , " + e.voto+ ")";
		    			
		    			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
		    					PreparedStatement pr = conn.prepareStatement(sql);
		    						){
		    					int r = pr.executeUpdate(sql);
		    				} catch(Exception e1) {
		    					e1.printStackTrace();
		    			}
		    			String sql2 = "INSERT INTO `dbvotazione`.`credentials` (`username`, `password`, `amm`) VALUES ( '" + e.codfiscale + "' , '" + Controller.MD5(password.getText()) + "','0')";
		    			
		    			try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
		    					PreparedStatement pr = conn.prepareStatement(sql2);
		    						){
		    					int r = pr.executeUpdate(sql2);
		    				} catch(Exception e1) {
		    					e1.printStackTrace();
		    				}
		    			a = false;
		    			Alert alert= new Alert(AlertType.INFORMATION);
		        		alert.setHeaderText("Registrazione completata");
		        		alert.setContentText("Grazie e buona giornata!");
		        		alert.showAndWait();
		    	    	Main m = new Main();
		    	    	m.changeScene("../gui/main.fxml");
		    	    	
		    		} else {
		    			Alert alert= new Alert(AlertType.ERROR);
		        		alert.setHeaderText("Conferma password errata");
		        		alert.setContentText("Le due password sono diverse!");
		        		alert.showAndWait();
		    		}
		    	} else {
		    		a = false;
		    		Alert alert= new Alert(AlertType.ERROR);
		    		alert.setHeaderText("Registrazione incompleta");
		    		alert.setContentText("Si prega di inserire una password!");
		    		alert.showAndWait();
		    	}
	    	}
    	}
    }
    
    public /*@ pure @*/ boolean checkCodFiscale(Elettore e) {
		char[] cod = e.codfiscale.toCharArray();
		//check per cognome -> 3 lettere
		if (!checkCogn(cod))
			return false;
		//check per nome -> 3 lettere
		if (!checkName(cod))
			return false;
		//check per anno di nascita -> 2 numeri
		if (!checkAnno(cod))
			return false;
		//check per mese di nascita -> 1 lettera
		if (!checkMese(cod)) 
			return false;
		//check per giorno di nascita -> 2 numeri
		if(!checkGiorno(cod,e))
			return false;
		//check per comune di nascita -> 1 lettera + 3 numeri
		if(!checkCom(cod)) 
			return false;
		//check ultimo carattere -> 1 lettera
		if(!checkLast(cod)) 
			return false;
		//se tutte le verifiche sono corrette ritorno 'true', altrimenti esco prima con false
		return true;
	}
    
    public /*@ pure @*/ boolean checkLast(char[] cod) {
		if((cod[15]<'A') && (cod[15]>'Z'))
			return false;
		return true;
	}
    
    public /*@ pure @*/ boolean checkCom(char[] cod) {
		if((cod[11]<'A') && (cod[11]>'Z')) {
			return false;
		}
		if(!(lblNazioneNascita.getText().equals("Italia"))){
			if(cod[11] != 'Z')
				return false;
		}else if(lblNazioneNascita.getText().equals("Italia") && cod[11] == 'Z'){
			return false;
		}
		for(int i = 12; i < 15; i++) {
			if((cod[i]<'0') && (cod[i]>'9')) {
				return false;
			}
		}
		return true;
	}
    
    public /*@ pure @*/ boolean checkAnno(char[] cod) {
		int anno = lblDataNascita.getValue().getYear();
		int c=cod[7]-'0';
		if(c == (anno % 10)) {
			anno = anno / 10;
			c=cod[6]-'0';
			if(c == (anno % 10)) {
				return true;
			}
		}
		return false;
	}
    
    public /*@ pure @*/ boolean checkMese(char[] cod) {
		int mese = lblDataNascita.getValue().getMonthValue();
		switch(mese) {
		case 1:
			if(cod[8] == 'A')
				return true;
			break;
		case 2:
			if(cod[8] == 'B')
				return true;
			break;
		case 3:
			if(cod[8] == 'C')
				return true;
			break;
		case 4:
			if(cod[8] == 'D')
				return true;
			break;
		case 5:
			if(cod[8] == 'E')
				return true;
			break;
		case 6:
			if(cod[8] == 'H')
				return true;
			break;
		case 7:
			if(cod[8] == 'L')
				return true;
			break;
		case 8:
			if(cod[8] == 'M')
				return true;
			break;
		case 9:
			if(cod[8] == 'P')
				return true;
			break;
		case 10:
			if(cod[8] == 'R')
				return true;
			break;
		case 11:
			if(cod[8] == 'S')
				return true;
			break;
		case 12:
			if(cod[8] == 'T')
				return true;
			break;
		default:
			return false;
		}
		return false;
	}
    
    public /*@ pure @*/ boolean checkGiorno(char[] cod, Elettore e) {
		int gg = this.lblDataNascita.getValue().getDayOfMonth();
		String sesso = e.sesso;
		int c=cod[10]-'0';
		int d=cod[9]-'0';
		if(sesso == "m") {
			if(gg < 10) {
				if((cod[9] == '0') && (c == gg)){
					return true;
				}
			}else{
				if(c == gg % 10){
					gg = gg / 10;
					if(d == gg % 10)
						return true;
				}
			}
			return false;
		}else{ //nel caso sia donna
			if(gg < 10) {
				if((cod[9] == '4') && (c == gg)) {
					return true;
				}
			}else{
				if(c == gg % 10){
					gg = (gg / 10) + 4;
					if(d == gg % 10)
						return true;
				}
			}
			return false;
		}
	}
    
    public /*@ pure @*/ boolean checkCogn(char[] cod) {
		String surn = lblCognome.getText().toUpperCase();
		char[] surname = surn.toCharArray();
		int j = 0, k = 0;
		for(int i = 0; i < 3; i++) {
			while(j < surname.length) {
				if((surname[j] != 'A') && (surname[j] != 'E') && (surname[j] != 'I') && (surname[j] != 'O') && (surname[j] != 'U')) {
					if(cod[i] != surname[j])
						return false;
					j++;
					break;
				}
				j++;
			}
			if(j >= surname.length){
				while(k < surname.length) {
					if((surname[k] == 'A') && (surname[k] == 'E') && (surname[k] == 'I') && (surname[k] == 'O') && (surname[k] == 'U')) {
						if(cod[i] != surname[k])
							return false;
						k++;
						break;
					}
					k++;
				}
			}
			if((j >= surname.length) && (k >= surname.length)) {
				if(cod[i] != 'X')
					return false;
			}
		}
		return true;
	}
    
    public /*@ pure @*/ boolean checkName(char[] cod) {
		String nome = lblNome.getText().toUpperCase();
		char[] name = nome.toCharArray();
		int j = 0, k = 0, cons = 0;
		//conto # consonanti
		for(int x = 0; x < name.length; x++) {
			if((name[x] != 'A' ) && (name[x] != 'E') && (name[x] != 'I') && (name[x] != 'O') && (name[x] != 'U')) {
				cons++;
			}
		}
		if(cons <= 3) {
			for(int i = 3; i < 6; i++) {
				while(j < name.length) {
					if((name[j] != 'A') && (name[j] != 'E') && (name[j] != 'I') && (name[j] != 'O') && (name[j] != 'U')) {
						if(cod[i] != name[j])
							return false;
						j++;
						break;
					}
					j++;
				}
				if(j >= name.length){
					while(k < name.length) {
						if((name[k] == 'A') || (name[k] == 'E') || (name[k] == 'I') || (name[k] == 'O') || (name[k] == 'U')) {
							if(cod[i] != name[k])
								return false;
							k++;
							break;
						}
						k++;
					}
				}
				if((j >= name.length) && (k >= name.length)) {
					if(cod[i] != 'X')
						return false;
				}
			}
		}else {
			cons = 0;
			for(int i = 3; i < 6; i++) {
				while(j < name.length) {
					if((name[j] != 'A') && (name[j] != 'E') && (name[j] != 'I') && (name[j] != 'O') && (name[j] != 'U')) {
						cons++;
						if(cons != 2) {
							if(cod[i] != name[j]) 
								return false;
							j++;
							break;
						}
					}
					j++;
				}
			}
		}
		return true;
	}

	@FXML
    void initialize() {
		lblCodFiscale.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'registrazione.fxml'.";
        assert btnReg != null : "fx:id=\"btnReg\" was not injected: check your FXML file 'registrazione.fxml'.";
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
