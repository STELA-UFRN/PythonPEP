package br.ufrn.imd.fiware.security;

public class User {
	private String username;
	private String displayName;
	private String password;
	private String email;
	private String userId;
	
	public User() {
		this.displayName = "";
		this.email = "";
		this.password = "";
		this.userId = "";
		this.username = "";
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
