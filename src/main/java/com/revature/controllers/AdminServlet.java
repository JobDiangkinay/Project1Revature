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
						System.out.println(methodname);
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
		System.out.println(stringEmp);
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
				String result = "{" + "handleResult" + ":" + "true" + "}";
				String stringStat = new Gson().toJson(result);
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			} else {
				String result = "{" + "handleResult" + ":" + "false" + "}";
				String stringStat = new Gson().toJson(result);
				response.setContentType("text/html");
				response.getWriter().write(stringStat);
			}
		}
	}

	public void logOut(HttpSession session, HttpServletResponse response) throws IOException {
		if (session != null) {
			session.invalidate();
		}
		System.out.println("LogOut");
		response.sendRedirect("default.html");
	}
}
