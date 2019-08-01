package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.revature.entities.EmployeeDao;
import com.revature.models.Employee;
import com.revature.models.Person;
import com.revature.models.Reimbursement;
import com.revature.utilities.ConnectionUtil;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(AdminServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String methodname = request.getParameter("methodname");
		String commandType = request.getParameter("command");
		if (session != null) {
			String userType = (String) session.getAttribute("userType");
			if (userType.equals("ADMIN")) {
				if (methodname != null) {
					if (methodname.equals("loademp")) {
						displayEmployeeInfo(session, response);
					} else if (methodname.equals("loadAllReim")) {
						displayAllUserReim(response);
					} else if (methodname.equals("handleReim")) {
						String reimId = request.getParameter("reimId");
						String reimStat = request.getParameter("reimCode");
						String userName = (String) session.getAttribute("userName");
						if (reimStat.equals("ACCEPT")) {
							updateReimStatus(reimId, true, response, userName);
						} else if (reimStat.equals("DENY")) {
							updateReimStatus(reimId, false, response, userName);
						}
					}else if (methodname.equals("loadAllEmp")) {
						displayAllEmployees(response);
					}else if (methodname.equals("updateInfo")) {
						updateEmployeeInfo(session, request, response);
					}
				}
				if (commandType != null) {
					if (commandType.equals("LOGOUT")) {
						logOut(session, response);
					}
				}
			}
		}
	}
	
	public void updateEmployeeInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		String userName = (String) session.getAttribute("userName");
		String editType = request.getParameter("editType");
		String editValue = request.getParameter("editField");
		boolean status = empDao.updateEmpInfo(userName, editType, editValue);
		connection.close();
		response.setContentType("text/html");
		if (status)
			response.getWriter().write("true");
		else
			response.getWriter().write("false");
	}

	public void displayEmployeeInfo(HttpSession session, HttpServletResponse response) throws IOException {
		String curUserName = (String) session.getAttribute("userName");
		try {
			Employee emp = getEmployee(curUserName);
			Person curEmp = new Person(emp.getFirstName(), emp.getLastName(), emp.getPhoneNumber(), emp.geteMail());
			response.setContentType("text/html");
			response.getWriter().write(generateUserJson(curEmp));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Employee getEmployee(String uName) throws FileNotFoundException, SQLException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		Employee curEmp = empDao.getEmployeeObject(uName);
		connection.close();
		return curEmp;
	}

	public String generateUserJson(Person curEmp) {
		Person jsonEmp = curEmp;
		String stringEmp = new Gson().toJson(jsonEmp);
		return stringEmp;
	}

	public void displayAllUserReim(HttpServletResponse response) throws IOException {
		try {
			response.setContentType("text/html");
			response.getWriter().write(generateReimJson(getReimbursement()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void displayAllEmployees(HttpServletResponse response) throws IOException {
		try {
			response.setContentType("text/html");
			response.getWriter().write(generateAllEmpJson(getAllEmployees()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String generateReimJson(ArrayList<Reimbursement> allReim) {
		ArrayList<Reimbursement> curAllReim = allReim;
		String stringReim = new Gson().toJson(curAllReim);
		
		return stringReim;
	}
	
	public String generateAllEmpJson(ArrayList<Employee> allEmp) {
		ArrayList<Employee> curAllEmp = allEmp;
		String stringReim = new Gson().toJson(curAllEmp);
		return stringReim;
	}

	public ArrayList<Reimbursement> getReimbursement() throws FileNotFoundException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		ArrayList<Reimbursement> allReim = empDao.getAllReim();
		connection.close();
		return allReim;
	}
	
	public ArrayList<Employee> getAllEmployees() throws FileNotFoundException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		ArrayList<Employee> allReim = empDao.getAllEmployees();
		connection.close();
		return allReim;
	}

	public void updateReimStatus(String reimId, boolean Accept, HttpServletResponse response, String adminName) throws IOException {
		boolean isSuccess = false;
		boolean status = false;
		try {
			ConnectionUtil connection = new ConnectionUtil();
			EmployeeDao empDao = new EmployeeDao(connection.getConnection());
			status = empDao.updateReimStatus(reimId, Accept, adminName);
			connection.close();
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
		}
		if (isSuccess) {
			if (status) {
				String result = "true";
				String stringStat = new Gson().toJson(result);
				logger.info("Reimbursement ID: " + reimId + " has been accepted!");
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			} else {
				String result = "false";
				String stringStat = new Gson().toJson(result);
				logger.info("Reimbursement ID: " + reimId + " has been denied!");
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			}
		}
	}

	public void logOut(HttpSession session, HttpServletResponse response) throws IOException {
		if (session != null) {
			String userType = (String) session.getAttribute("userName");
			logger.info("Admin: " + userType + " has logged out");
			session.invalidate();
		}
		response.sendRedirect("default.html");
	}
}
