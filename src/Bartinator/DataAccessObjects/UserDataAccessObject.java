package Bartinator.DataAccessObjects;


import Bartinator.Model.Employee;

import java.io.IOException;
import java.util.List;

public class UserDataAccessObject extends MainDataAccessObject{

	private static final UserDataAccessObject instance = new UserDataAccessObject();

	public static UserDataAccessObject getInstance(){
		return instance;
	}

    private Employee mActiveEmployee;

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

	public void saveUser(Employee employee){
		save(employee);
	}

    public Employee getActiveEmployee() {
        return mActiveEmployee;
    }
    public void setActiveEmployee(Employee activeEmployee) {
        mActiveEmployee = activeEmployee;
    }


    @SuppressWarnings("unchecked")
	public  List<Employee> fetchAllUsers() throws IOException {
		List<Employee> employees = (List<Employee>)fetch(Employee.class);
		if(employees != null){
			return employees;
		}
		throw new IOException("Fetching mUsers failed");
    }

    public boolean userExists(String username){
        return fetchUserFromUsername(username)!=null;
    }

    public Employee verifyUser(String username, String password) throws IOException {
        Employee employee = fetchUserFromUsername(username);
        if(employee != null) {
            if (/* !password.equals("") && */ employee.getPassword() == password.hashCode()) {
                return employee;
            } else {
                throw new IOException("The Password was incorrect");
            }
        } else {
			throw new IOException("The employee was not found");
        }
    }

	public void deleteUser(String username) {
		Employee employee = fetchUserFromUsername(username);
		remove(employee);
	}

	public void updateUser(Employee employee){
        update(employee);
    }
}
