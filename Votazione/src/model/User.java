package model;

public class User {
	public String username;
	public String password;
	public String amm;

	
	public User(String username, String password, String amm) {
		super();
		this.username = username;
		this.password = password;
		this.amm = amm;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAmm() {
		return amm;
	}
	public void setAmm(String amm) {
		this.amm = amm;
	}


	@Override
	public String toString() {
		return "[username = " + username + ", password = " + password + "]";
	}
	
}
