package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import Bartinator.Model.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.io.IOException;

public class EmployeeRoster {
	private static EmployeeRoster instance = new EmployeeRoster();
	public static EmployeeRoster getInstance() {
		return instance;
	}

	private EmployeeDataAccessObject mEmployeeDataAccessObject = EmployeeDataAccessObject.getInstance();

	private ObservableList<ObservableEmployee> mEmployees;

	private ObservableEmployee mActiveEmployee;

	private EmployeeRoster() {
		try {
			//Lav en observable Arraylist og hent alle users
			mEmployees = FXCollections.observableArrayList();
			for(Employee employee : mEmployeeDataAccessObject.fetchAllUsers()){

				//For hver user i databasen, lav en observable user
				ObservableEmployee observableEmployee = new ObservableEmployee(employee);

				//For hver af disse 4 properties, add en listener, som opdatere propertien i databasen,
				// når der sker en ændring.
				observableEmployee.usernameProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.updateEmployee(observableEmployee);
				});
				observableEmployee.nameProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.updateEmployee(observableEmployee);
				});
				observableEmployee.passwordProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.updateEmployee(observableEmployee);
				});
				observableEmployee.adminAccessProperty().addListener((observable, oldValue, newValue) -> {
					System.out.printf("%s -> %s%n", oldValue, newValue);
					EmployeeRoster.this.updateEmployee(observableEmployee);
				});

				//Add denne observable user til observableUser Arraylisten og slut for-løkken
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

	void updateEmployee(ObservableEmployee employee) {
		mEmployeeDataAccessObject.updateEmployee(employee.toEmployee());
	}

	boolean employeeExists(String username) {
		return getEmployeeByUsername(username) != null;
	}

	/**
	 * Create an Employee object, save it to the database. Then convert it to an ObservableEmployee which is then added
	 * to the mEmployees List.
	 * @param username Username of the employye to create
	 * @param password The password of the Employee to create
	 * @param name The name of the Employee to create
	 * @param adminAccess Whether the newly created employee has admin rights.
	 */
	void createEmployee(String username, String password, String name, boolean adminAccess) {
		Employee employee = new Employee(name, username, password.hashCode(), adminAccess);
		mEmployeeDataAccessObject.saveEmployee(employee);
		mEmployees.add(new ObservableEmployee(employee));
	}


	/**
	 * Select an Employee by username, and delete them by removing them from the database as well as the mEmployees list
	 * @param username The username of the user to delete.
	 */
	void deleteEmployee(String username) {
		ObservableEmployee observableEmployee = getEmployeeByUsername(username);
		mEmployeeDataAccessObject.deleteEmployee(observableEmployee.toEmployee());
		mEmployees.remove(observableEmployee);
	}

	//Inner class, som bruges til at convertere password.
	class PasswordConverter extends StringConverter<Integer>{
		@Override
		public String toString(Integer integer) {
			return integer == 0 ? "no" : "yes";  //EN if-else bandit
		}

		@Override
		public Integer fromString(String string) {
			return string.hashCode();
		}
	}


	public ObservableEmployee getActiveEmployee() {
		return mActiveEmployee;
	}
	public void setActiveEmployee(ObservableEmployee activeEmployee) {
		if(mActiveEmployee != null) {
			mActiveEmployee.favoritesProperty().removeListener(mListChangeListener);
		}
		mActiveEmployee = activeEmployee;
		if(mActiveEmployee != null) {
			mActiveEmployee.favoritesProperty().addListener(mListChangeListener);
		}
	}

	public ObservableEmployee verifyUser(String username, String password) throws IOException {
		Employee employee = mEmployeeDataAccessObject.fetchUserFromUsername(username);
		if(employee != null) {
			if (/* !password.equals("") && */ employee.getPassword() == password.hashCode()) {
				return new ObservableEmployee(employee);
			} else {
				throw new IOException("The Password was incorrect");
			}
		} else {
			throw new IOException("The employee was not found");
		}
	}

	private ObservableListChangeListener mListChangeListener = new ObservableListChangeListener();
	private class ObservableListChangeListener implements ChangeListener<ObservableList<Product>> {
		@Override
		public void changed(ObservableValue<? extends ObservableList<Product>> observable, ObservableList<Product> oldValue, ObservableList<Product> newValue) {
			EmployeeRoster.this.updateEmployee(mActiveEmployee);
		}
	}
}
