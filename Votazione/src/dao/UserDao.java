package dao;

import java.util.List;

import model.User;

public interface UserDao {
	
	public final static String DBADDRESS = "jdbc:mysql://localhost:3306/dbvotazione?serverTimezone=UTC";
	
	public final static String USER = "root";  //myuser
	
	public final static String PWD = "progettoSweng";
	
	public List<User> getUser();
	
}
