package com.revature.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reimbursement {
	private String userId;
	private String ReimbursementId;
	private String ReimbursementType;
	private String Status;
	private String DateCreated;
	private String ReimbursementDesc;
	private String imageBase64;
	private double Amount;
	private String adminName;
	
	public Reimbursement(String reimbursementId, String reimbursementType, String status, String dateCreated,
			String reimbursementDesc,double amount, String userId, String imageBase64, String adminName) {
		super();
		this.ReimbursementId = reimbursementId;
		this.ReimbursementType = reimbursementType;
		this.Status = status;
		this.DateCreated = dateCreated;
		this.Amount = amount;
		this.ReimbursementDesc = reimbursementDesc;
		this.userId = userId;
		this.imageBase64 = imageBase64;
		this.adminName = adminName;
	}

	public Reimbursement(String userId, double amount, String reimbursementType, String reimbursementDesc, String imageBase64) {
		this.userId = userId;
		this.ReimbursementType = reimbursementType;
		this.Amount = amount;
		this.ReimbursementDesc = reimbursementDesc;
		this.DateCreated = getStringDate();
		this.Status = "Submitted";
		this.imageBase64 = imageBase64;
		this.adminName = null;
	}
	
	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	@Override
	public String toString() {
		return "Reimbursement [userId=" + userId + ", ReimbursementId=" + ReimbursementId + ", ReimbursementType="
				+ ReimbursementType + ", Status=" + Status + ", DateCreated=" + DateCreated + ", ReimbursementDesc="
				+ ReimbursementDesc + ", Amount=" + Amount + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReimbursementDesc() {
		return ReimbursementDesc;
	}

	public void setReimbursementDesc(String reimbursementDesc) {
		ReimbursementDesc = reimbursementDesc;
	}

	public String getReimbursementId() {
		return ReimbursementId;
	}

	public void setReimbursementId(String reimbursementId) {
		ReimbursementId = reimbursementId;
	}

	public String getReimbursementType() {
		return ReimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		ReimbursementType = reimbursementType;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(String dateCreated) {
		DateCreated = dateCreated;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}
	
	public String getStringDate() {
		Date dateobj = new Date();
		String sDate = new SimpleDateFormat("MM/dd/yyyy").format(dateobj);
		return sDate;
	}
	
}
