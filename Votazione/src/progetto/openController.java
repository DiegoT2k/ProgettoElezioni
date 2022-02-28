package progetto;

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

public class openController implements UserDao{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnOk;

    @FXML
    void handleOk(ActionEvent event) throws IOException {
    	//apri sessione, metti state = 1 = aperta
		String sql = "update session set state = 1 where idsession = 1";
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			int r = pr.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
    	Main m = new Main();
    	m.changeScene("afterLoginAmm.fxml");
    }

    @FXML
    void initialize() {
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'open.fxml'.";

    }

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}