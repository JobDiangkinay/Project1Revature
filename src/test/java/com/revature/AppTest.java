package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.revature.controllers.LogInServlet;
import com.revature.entities.EmployeeDao;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.utilities.ConnectionUtil;

/**
 * Unit test Reimbursement System.
 */
public class AppTest {

	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void checkLogIn() throws FileNotFoundException, SQLException {
		ConnectionUtil connectionUtil = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connectionUtil.getConnection());
		boolean correctLogIn = empDao.checkLogIn("JobEmployee", "passjob");
		assertTrue(correctLogIn);
		connectionUtil.close();
	}

	@Test
	public void checkFalseLogIn() throws FileNotFoundException, SQLException {
		ConnectionUtil connectionUtil = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connectionUtil.getConnection());
		boolean falseLogIn = empDao.checkLogIn("JobEmployee", "jobPass");
		assertFalse(falseLogIn);
		connectionUtil.close();
	}

	@Test
	public void getSpecificEmployee() throws FileNotFoundException, SQLException {
		LogInServlet login = new LogInServlet();
		Employee testEmp = login.getEmployee("JobEmployee");
		String testEmpFirst = testEmp.getFirstName();
		String EmpFirst = "Juan Gabriel";
		assertEquals(testEmpFirst, EmpFirst);
	}

	@Test
	public void checkIfReimbursementIsEmpty() throws FileNotFoundException {
		ConnectionUtil connectionUtil = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connectionUtil.getConnection());
		ArrayList<Reimbursement> allReim = empDao.getAllReim();
		assertNotNull(allReim);
	}

	@Test
	public void checkIfEmployeeIsEmpty() throws FileNotFoundException {
		ConnectionUtil connectionUtil = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connectionUtil.getConnection());
		ArrayList<Employee> allEmp = empDao.getAllEmployees();
		assertNotNull(allEmp);
	}

	@Test
	public void checkIfEmployeeReimbursementListReturnsValue() throws FileNotFoundException {
		ConnectionUtil connectionUtil = new ConnectionUtil();
		EmployeeDao empDao = new EmployeeDao(connectionUtil.getConnection());
		ArrayList<Reimbursement> allEmpReim = empDao.getEmployeeReim("JobEmployee");
		assertNotNull(allEmpReim);
	}
	
	@Test
	public void getSpecificAdmin() throws FileNotFoundException, SQLException {
		LogInServlet login = new LogInServlet();
		Employee testEmp = login.getEmployee("JobAdmin");
		String testEmpFirst = testEmp.getFirstName();
		String EmpFirst = "Job";
		assertEquals(testEmpFirst, EmpFirst);
	}

}
