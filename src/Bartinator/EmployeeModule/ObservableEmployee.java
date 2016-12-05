package Bartinator.EmployeeModule;

import Bartinator.Model.Employee;
import Bartinator.Model.Product;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Martin on 30-11-2016.
 */

public class ObservableEmployee {
	private IntegerProperty mId;
	private StringProperty mName;
	private StringProperty mUsername;
	private IntegerProperty mPassword;
	private BooleanProperty mAdminAccess;
	private ListProperty<Product> mFavorites;

	public ObservableEmployee(Employee employee) {
		mId = new SimpleIntegerProperty(employee.getId());
		mName = new SimpleStringProperty(employee.getName());
		mUsername = new SimpleStringProperty(employee.getUsername());
		mPassword = new SimpleIntegerProperty(employee.getPassword());
		mAdminAccess = new SimpleBooleanProperty(employee.hasAdminAccess());
		mFavorites = new SimpleListProperty<>(FXCollections.observableArrayList(employee.getFavorites()));
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
	public ObservableList<Product> getFavorites() {
		return mFavorites.get();
	}
	public ListProperty<Product> favoritesProperty() {
		return mFavorites;
	}
	public void setFavorites(ObservableList<Product> favorites) {
		this.mFavorites.set(favorites);
	}

	public Employee toEmployee(){
		Employee employee = new Employee(getName(), getUsername(), getPassword(), hasAdminAccess());
		employee.setId(getId());
		employee.setFavorites(getFavorites());
		return employee;
	}
}