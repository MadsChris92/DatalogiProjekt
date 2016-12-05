package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class EmployeeRoster {
	private static EmployeeRoster instance = new EmployeeRoster();
	public static EmployeeRoster getInstance() {
		return instance;
	}

	private EmployeeDataAccessObject mEmployeeDataAccessObject = EmployeeDataAccessObject.getInstance();

	private List<Employee> mEmployees;

	private EmployeeRoster() {
		try {
			mEmployees = mEmployeeDataAccessObject.fetchAllUsers();
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}

	public ObservableEmployee getEmployeeByUsername(String username) {
		for(Employee employee : mEmployees){
			if(employee.getUsername().equals(username)){
				return new ObservableEmployee(employee);
			}
		}
		return null;
	}

	public List<ObservableEmployee> getEmployees(){
		List<ObservableEmployee> employees = new ArrayList<>();
		for(Employee employee : mEmployees){
			employees.add(new ObservableEmployee(employee));
		}
		return employees;
	}
}
