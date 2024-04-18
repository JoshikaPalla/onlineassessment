package com.example.demo.model;


public class User {
	private long userId;
	private String userName;
	private String userEmailId;
	private String userPassword;
	private String userRole;
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	
	public User() {
		super();
	}
	
	
	public User(String userName, String userEmailId, String userPassword, String userRole) {
		super();
		this.userName = userName;
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userEmailId=" + userEmailId + ", userPassword="
				+ userPassword + ", userRole=" + userRole + "]";
	}
	
	
	
	

}
