package Bartinator.EmployeeModule;

import Bartinator.Model.Employee;
import javafx.beans.property.*;

/**
 * Created by Martin on 30-11-2016.
 */
public class EmployeeAdapter {
	private IntegerProperty mId;
	private StringProperty mName;
	private StringProperty mUsername;
	private IntegerProperty mPassword;
	private BooleanProperty mAdminAccess;

	public EmployeeAdapter(Employee employee) {
		mId = new SimpleIntegerProperty(employee.getId());
		mName = new SimpleStringProperty(employee.getName());
		mUsername = new SimpleStringProperty(employee.getUsername());
		mPassword = new SimpleIntegerProperty(employee.getPassword());
		mAdminAccess = new SimpleBooleanProperty(employee.hasAdminAccess());
	}

	public int getId() {
		return mId.get();
	}

	public IntegerProperty idProperty() {
		return mId;
	}

	public void setId(int id) {
		this.mId.set(id);
	}

	public String getName() {
		return mName.get();
	}

	public StringProperty nameProperty() {
		return mName;
	}

	public void setName(String name) {
		this.mName.set(name);
	}

	public String getUsername() {
		return mUsername.get();
	}

	public StringProperty usernameProperty() {
		return mUsername;
	}

	public void setUsername(String username) {
		this.mUsername.set(username);
	}

	public int getPassword() {
		return mPassword.get();
	}

	public IntegerProperty passwordProperty() {
		return mPassword;
	}

	public void setPassword(int password) {
		this.mPassword.set(password);
	}

	public boolean hasAdminAccess() {
		return mAdminAccess.get();
	}

	public BooleanProperty adminAccessProperty() {
		return mAdminAccess;
	}

	public void setAdminAccess(boolean adminAccess) {
		this.mAdminAccess.set(adminAccess);
	}

	public Employee toEmployee(){
		Employee employee = new Employee(getName(), getUsername(), getPassword(), hasAdminAccess());
		employee.setId(getId());
		return employee;
	}
}
