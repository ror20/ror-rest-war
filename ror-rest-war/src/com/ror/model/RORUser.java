package com.ror.model;

public class RORUser {

	private String userName;
	private String userId;
	private String emailId;
	private String password;
	
	public RORUser(){
		
	}

	public RORUser(String userName, String userId, String emailId, String password) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
