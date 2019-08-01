package com.revature.entities;

import java.util.ArrayList;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;

/**
 * 
 * An interface for Data Access Object
 *
 * @param <E> Generic type.
 */
public interface Dao {

	ArrayList<Employee> getAllEmployees();

	ArrayList<Reimbursement> getAllReim();

	void insertEmployee(Employee addEmp);

	boolean submitNewRequest(Reimbursement e);
}