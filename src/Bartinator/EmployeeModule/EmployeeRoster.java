package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.io.IOException;

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

	private ObservableList<ObservableEmployee> mEmployees;

	private Employee mActiveEmployee;

	private EmployeeRoster() {
		try {
			mEmployees = FXCollections.observableArrayList();
			for(Employee employee : mEmployeeDataAccessObject.fetchAllUsers()){
				ObservableEmployee observableEmployee = new ObservableEmployee(employee);
				observableEmployee.usernameProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.update(observableEmployee);
				});
				observableEmployee.nameProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.update(observableEmployee);
				});
				observableEmployee.passwordProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.update(observableEmployee);
				});
				observableEmployee.adminAccessProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.update(observableEmployee);
				});
				mEmployees.add(observableEmployee);
			}
		} catch (IOException exception){
			exception.printStackTrace();
		}
	}

	ObservableEmployee getEmployeeByUsername(String username) {
		for(ObservableEmployee employee : mEmployees){
			if(employee.getUsername().equals(username)){
				return employee;
			}
		}
		return null;
	}

	ObservableList<ObservableEmployee> getEmployees(){
		return mEmployees;
	}

	void update(ObservableEmployee employee) {
		mEmployeeDataAccessObject.updateUser(employee.toEmployee());
	}

	boolean employeeExists(String username) {
		return getEmployeeByUsername(username) != null;
	}

	void createEmployee(String username, String password, String name, boolean adminAccess) {
		Employee employee = new Employee(name, username, password.hashCode(), adminAccess);
		mEmployeeDataAccessObject.saveEmployee(employee);
		mEmployees.add(new ObservableEmployee(employee));
	}

	void deleteEmployee(String username) {
		ObservableEmployee observableEmployee = getEmployeeByUsername(username);
		mEmployeeDataAccessObject.deleteEmployee(observableEmployee.toEmployee());
		mEmployees.remove(observableEmployee);
	}

	class PasswordConverter extends StringConverter<Integer>{
		@Override
		public String toString(Integer integer) {
			return integer == 0 ? "no" : "yes";
		}

		@Override
		public Integer fromString(String string) {
			return string.hashCode();
		}
	}


	public Employee getActiveEmployee() {
		return mActiveEmployee;
	}
	public void setActiveEmployee(Employee activeEmployee) {
		mActiveEmployee = activeEmployee;
	}

	public Employee verifyUser(String username, String password) throws IOException {
		Employee employee = mEmployeeDataAccessObject.fetchUserFromUsername(username);
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
}
