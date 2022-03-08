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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.scene.control.Alert.AlertType;
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
