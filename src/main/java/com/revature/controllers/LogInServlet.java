package com.revature.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.entities.EmployeeDao;
import com.revature.models.Employee;
import com.revature.utilities.ConnectionUtil;

/**
 * Servlet implementation class LogIn
 */
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String uName = request.getParameter("userName");
		String password = request.getParameter("password");
		System.out.println(uName+password);
		try {
			if (logInChecker(uName, password)) {
				setSession(request,response);
				Employee curEmp = getEmployee(uName);
				if (curEmp != null) {
					if (curEmp.getUserType().equals("EMPLOYEE")) {
						System.out.println(curEmp.getUserType());
						response.sendRedirect("Employee.html");
					} else if (curEmp.getUserType().equals("ADMIN")) {
						response.sendRedirect("Admin.html");
					}
				}

			} else {
				response.sendRedirect("default.html");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setSession(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, SQLException {
		String name = request.getParameter("userName");
		Employee curUser = getEmployee(name);
		HttpSession session = request.getSession(false);
		if (name == null) {
			if (session == null)
				name = getInitParameter("userName");
			else
				name = (String) session.getAttribute("userName");
		} else {
			session = request.getSession();
			session.setAttribute("userName", name);
			session.setAttribute("userType", curUser.getUserType());
		}

	}

	public boolean logInChecker(String uName, String pass) throws FileNotFoundException, SQLException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		boolean checker = empDao.checkLogIn(uName, pass);
		connection.close();
		return checker;
	}

	public Employee getEmployee(String uName) throws FileNotFoundException, SQLException {
		ConnectionUtil connection = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connection.getConnection());
		Employee curEmp = empDao.getEmployeeObject(uName);
		connection.close();
		return curEmp;
	}

}
