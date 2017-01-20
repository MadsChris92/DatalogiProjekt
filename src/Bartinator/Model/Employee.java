package Bartinator.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Employee class provides a definition for what needs to be known about an employee, in the context
 * of this point-of-sale system. lol
 */

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mId;
	@Column
	private String mName;
	@Column
	private String mUsername;
	@Column
	private int mPassword;
	@Column
	private boolean mAdminAccess;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Product> mFavorites = new ArrayList<>();

	protected Employee(){}

	public Employee(String name, String username, int password, boolean adminAccess) {
		mName = name;
		mUsername = username;
		mPassword = password;
		mAdminAccess = adminAccess;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String username) {
		mUsername = username;
	}

	public int getPassword() {
		return mPassword;
	}

	public void setPassword(int password) {
		mPassword = password;
	}

	public boolean hasAdminAccess() {
		return mAdminAccess;
	}

	public void setAdminAccess(boolean adminAccess){
		mAdminAccess = adminAccess;
	}

	public List<Product> getFavorites() {
		return mFavorites;
	}

	public void setFavorites(List<Product> favorites) {
		mFavorites = favorites;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"userid=" + mId +
				", name='" + mName + '\'' +
				", username='" + mUsername + '\'' +
				", password=" + mPassword +
				", adminAccess='" + mAdminAccess +
				"'}";
	}
}