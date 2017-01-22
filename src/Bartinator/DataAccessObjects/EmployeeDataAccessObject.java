package Bartinator.DataAccessObjects;


import Bartinator.Model.Employee;

import java.io.IOException;
import java.util.List;

// Singleton
public class EmployeeDataAccessObject extends MainDataAccessObject{

	private static final EmployeeDataAccessObject instance = new EmployeeDataAccessObject();

	public static EmployeeDataAccessObject getInstance(){
		return instance;
	}

    public Employee fetchUserFromUsername(String username){
        // Henter en bruger baseret på brugernavn, hvis brugeren ikke findes returnes null
        // sql udgaven af det functionen gør er:
        // SELECT * FROM Employee WHERE Employee."username"=username;
        // hvis der er mere end én bruger med samme brugernavn, så vælger den det første resultat
        List<Employee> employees = (List<Employee>) fetch(Employee.class, "mUsername", username);
        Employee employee;
        if(employees.size() > 0) {
            if(employees.size() > 1) System.err.println("WARN: Multiple users with the same username");
            employee = employees.get(0);
        }else {
            employee = null; // employee not found
        }
        return employee;
    }

	public void saveEmployee(Employee employee){
		save(employee);
	}


    @SuppressWarnings("unchecked") // fjerner advarsel om at vi ikke tjekker om det vi får tilbage fra fetch metoden
	// rent faktisk er Employee objekter
	public  List<Employee> fetchAllUsers() throws IOException {
		List<Employee> employees = (List<Employee>)fetch(Employee.class);
		if(employees != null){
			return employees;
		}
		throw new IOException("Fetching users failed");
    }

    public boolean userExists(String username){
        return fetchUserFromUsername(username)!=null;
    }

	public void deleteEmployee(Employee employee) {
		remove(employee);
	}

	public void updateEmployee(Employee employee){
        update(employee);
    }
}
