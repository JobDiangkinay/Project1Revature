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
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(EmployeeServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String commandType = request.getParameter("command");
		String methodname = request.getParameter("methodname");
		if (session != null) {
			String userType = (String) session.getAttribute("userType");
			if (userType.equals("EMPLOYEE")) {
				if (methodname != null) {
					if (methodname.equals("loademp")) {
						displayEmployeeInfo(session, response);
					} else if (methodname.equals("loadAllReim")) {
						displayAllUserReim(session, response);
					} else if (methodname.equals("newReim")) {
						submitNewReim(session, request, response);
					} else if (methodname.equals("updateInfo")) {
						updateEmployeeInfo(session, request, response);
					}
				}
				if (commandType != null) {
					if (commandType.equals("LOGOUT")) {
						logOut(session, response);
					}
				}
			}
		} else {
			logOut(session, response);
			return;
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

	public void displayAllUserReim(HttpSession session, HttpServletResponse response) throws IOException {
		try {
			response.setContentType("text/html");
			response.getWriter().write(generateReimJson(getReimbursement(session)));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void logOut(HttpSession session, HttpServletResponse response) throws IOException {
		if (session != null) {
			String userType = (String) session.getAttribute("userName");
			logger.info("Employee: " + userType + " has logged out");
			session.invalidate();
		}
		response.sendRedirect("default.html");
	}

	public String generateUserJson(Person curEmp) {
		Person jsonEmp = curEmp;
		String stringEmp = new Gson().toJson(jsonEmp);
		return stringEmp;
	}

	public String generateReimJson(ArrayList<Reimbursement> allReim) {
		ArrayList<Reimbursement> curAllReim = allReim;
		String stringReim = new Gson().toJson(curAllReim);
		return stringReim;
	}

	public Employee getEmployee(String uName) throws SQLException, IOException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		Employee curEmp = empDao.getEmployeeObject(uName);
		connection.close();
		return curEmp;
	}

	public void submitNewReim(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		String userName = (String) session.getAttribute("userName");
		String newAmount = request.getParameter("newAmount");
		double doubAmount = Double.parseDouble(newAmount);
		String newDesc = request.getParameter("newDesc");
		String newType = request.getParameter("newType");
		Reimbursement newReim = null;

		try {
			String newImg = request.getParameter("newImg");
			newReim = new Reimbursement(userName, doubAmount, newType, newDesc, newImg);
		}catch(Exception ex) {
			newReim = new Reimbursement(userName, doubAmount, newType, newDesc, null);
		}
		boolean status = empDao.submitNewRequest(newReim);
		connection.close();
		response.setContentType("text/html");
		if (status)
			response.getWriter().write("true");
		else
			response.getWriter().write("false");
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

	public ArrayList<Reimbursement> getReimbursement(HttpSession session) throws IOException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		String curUserName = (String) session.getAttribute("userName");
		ArrayList<Reimbursement> allReim = empDao.getEmployeeReim(curUserName);
		connection.close();
		return allReim;
	}

}
