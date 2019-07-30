package com.revature.models;

public class Employee extends Person{
	private String userName;
	private String password;
	private String userType;

	public Employee(String userName, String password, String userType, String firstName, String lastName, String phoneNumber, String eMail) {
		super(firstName, lastName, phoneNumber, eMail);
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}
	
	public Employee(String userName, String password, String userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getPassword() {
		return password;
	}

}
