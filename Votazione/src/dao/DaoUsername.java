package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import controllers.User;

public class DaoUsername implements UserDao{
	
	@Override
	public List<User> getUser() {

		List<User> user = new ArrayList<User>();
		
		String sql = "select * from credentials";
		
		try(Connection conn = DriverManager.getConnection(DBADDRESS, USER, PWD);
			PreparedStatement pr = conn.prepareStatement(sql);
				){
			
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				user.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("amm")));
			}
			
			rs.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	

}
