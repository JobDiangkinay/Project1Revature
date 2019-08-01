package com.revature.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;

public class EmployeeDao implements Dao{
	private Connection connection;

	public EmployeeDao(Connection connection) {
		this.connection = connection;
	}

	public boolean checkLogIn(String userName, String password) throws SQLException {
		int count = 0;
		try {
			PreparedStatement pStatement = connection
					.prepareStatement("select * from accountcredentials where username = ?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				count++;
				byte[] hashPassword = resultSet.getBytes("password");
				byte[] hashSalt = resultSet.getBytes("saltpass");
				try {
					MessageDigest md;
					md = MessageDigest.getInstance("SHA-512");
					md.update(hashSalt);
					
					byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
					 if(Arrays.equals(hashedPassword, hashPassword)) {
						 return true;
			            }else {
			            	return false;
			            }
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public Employee getEmployeeObject(String userName) throws SQLException {
		int count = 0;
		Employee curEmp = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select * from persons join accountcredentials on persons.username = accountcredentials.username where persons.username = ?");
			pStatement.setString(1, userName);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String passWord = resultSet.getString("password");
				String usertype = resultSet.getString("usertype");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String phoneNumber = resultSet.getString("phonenumber");
				String eMail = resultSet.getString("emailadd");
				count++;
				curEmp = new Employee(username, passWord, usertype, firstName, lastName, phoneNumber, eMail);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (count == 1) {
			return curEmp;
		}
		return curEmp;
	}

	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> allEmp = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"select * from persons join accountcredentials on persons.username = accountcredentials.username where accountcredentials.usertype = 'EMPLOYEE'");
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String passWord = resultSet.getString("password");
				String usertype = resultSet.getString("usertype");
				String firstName = resultSet.getString("firstname");
				String lastName = resultSet.getString("lastname");
				String phoneNumber = resultSet.getString("phonenumber");
				String eMail = resultSet.getString("emailadd");

				allEmp.add(new Employee(username, passWord, usertype, firstName, lastName, phoneNumber, eMail));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return allEmp;
	}

	public ArrayList<Reimbursement> getEmployeeReim(String curUsername) {
		ArrayList<Reimbursement> allReimburse = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection
					.prepareStatement("select * from reimbursements where username = ?");
			pStatement.setString(1, curUsername);
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				String reimId = resultSet.getString("pid");
				double amount = resultSet.getDouble("amount");
				String reimType = resultSet.getString("reimbursementtype");
				String reimStatus = resultSet.getString("reimbursementstatus");
				String reimDate = resultSet.getString("creationdate");
				String reimDesc = resultSet.getString("reimbursementdesc");
				String userName = resultSet.getString("username");
				String reimBase64 = resultSet.getString("reimbase64");
				String adminName = resultSet.getString("reimadmin");

				allReimburse.add(new Reimbursement(reimId, reimType, reimStatus, reimDate, reimDesc, amount, userName,
						reimBase64, adminName));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return allReimburse;
	}

	public ArrayList<Reimbursement> getAllReim() {
		ArrayList<Reimbursement> allReimburse = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement("select * from reimbursements");
			ResultSet resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				String reimId = resultSet.getString("pid");
				double amount = resultSet.getDouble("amount");
				String reimType = resultSet.getString("reimbursementtype");
				String reimStatus = resultSet.getString("reimbursementstatus");
				String reimDate = resultSet.getString("creationdate");
				String reimDesc = resultSet.getString("reimbursementdesc");
				String userName = resultSet.getString("username");
				String reimBase64 = resultSet.getString("reimbase64");
				String adminName = resultSet.getString("reimadmin");
				allReimburse.add(new Reimbursement(reimId, reimType, reimStatus, reimDate, reimDesc, amount, userName,
						reimBase64, adminName));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return allReimburse;
	}

	public boolean updateReimStatus(String reimId, boolean Accept, String adminName) {
		boolean status = false;
		int iD = Integer.parseInt(reimId);
		boolean isAccepted = Accept;
		try {
			if (isAccepted) {
				PreparedStatement pStatement = connection.prepareStatement(
						"update reimbursements set reimbursementstatus = 'Approved', reimadmin = ? where pid = ? ");
				pStatement.setString(1, adminName);
				pStatement.setInt(2, iD);

				pStatement.executeUpdate();
				status = true;
			} else {
				PreparedStatement pStatement = connection.prepareStatement(
						"update reimbursements set reimbursementstatus = 'Denied', reimadmin = ? where pid = ? ");
				pStatement.setString(1, adminName);
				pStatement.setInt(2, iD);
				pStatement.executeUpdate();
				status = false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return status;
	}

	public boolean updateEmpInfo(String userName, String editType, String editValue) {
		boolean status = false;
		try {
			if (editType.equals("eMail")) {
				PreparedStatement pStatement = connection
						.prepareStatement("update persons set emailadd = ? where username = ? ");
				pStatement.setString(1, editValue);
				pStatement.setString(2, userName);
				pStatement.executeUpdate();
				status = true;
			} else if (editType.equals("phoneNumber")) {
				PreparedStatement pStatement = connection
						.prepareStatement("update persons set phonenumber = ? where username = ? ");
				pStatement.setString(1, editValue);
				pStatement.setString(2, userName);
				pStatement.executeUpdate();
				status = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return status;
	}

	public boolean submitNewRequest(Reimbursement e) {
		boolean status = false;
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"insert into reimbursements(username, amount, reimbursementtype, reimbursementstatus, reimbursementdesc, creationdate, reimbase64) values(?, ?, ?, ?, ?, ?,?)");
			pStatement.setString(1, e.getUserId());
			pStatement.setDouble(2, e.getAmount());
			pStatement.setString(3, e.getReimbursementType());
			pStatement.setString(4, e.getStatus());
			pStatement.setString(5, e.getReimbursementDesc());
			pStatement.setString(6, e.getDateCreated());
			pStatement.setString(7, e.getImageBase64());
			// byte[] bytes = e.getImageBase64().getBytes();
			// pStatement.setBytes(7, bytes);
			pStatement.executeUpdate();
			status = true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return status;
		}
		return status;
	}

	public void insertEmployee(Employee addEmp) {
		try {
			PreparedStatement pStatement = connection
					.prepareStatement("insert into accountcredentials(usertype, username, password, saltpass) values(?, ?, ?, ?)");
			pStatement.setString(1, addEmp.getUserType());
			pStatement.setString(2, addEmp.getUserName());
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			MessageDigest md;
			try {
				// Creates hashed password
				md = MessageDigest.getInstance("SHA-512");
				md.update(salt);

				// This is stored in database in user
				byte[] hashedPassword = md.digest(addEmp.getPassword().getBytes(StandardCharsets.UTF_8));
				pStatement.setBytes(3, hashedPassword);
				pStatement.setBytes(4, salt);
			} catch (Exception ex) {

			}
			pStatement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		insertPerson(addEmp);
	}

	public void insertPerson(Employee addEmp) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(
					"insert into persons(username, emailadd, phonenumber, lastname, firstname) values(?, ?, ?, ?, ?)");
			pStatement.setString(1, addEmp.getUserName());
			pStatement.setString(2, addEmp.geteMail());
			pStatement.setString(3, addEmp.getPhoneNumber());
			pStatement.setString(4, addEmp.getLastName());
			pStatement.setString(5, addEmp.getFirstName());
			pStatement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
