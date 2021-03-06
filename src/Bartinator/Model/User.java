package Bartinator.Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {
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

	protected User(){};

	public User(String name, String username, int password, boolean adminAccess) {
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

	public boolean isAdmin() {
		return mAdminAccess;
	}

	public void giveAdminAccess(boolean adminAccess){
		mAdminAccess = adminAccess;
	}

	@Override
	public String toString() {
		return "User{" +
				"userid=" + mId +
				", name='" + mName + '\'' +
				", username='" + mUsername + '\'' +
				", password=" + mPassword +
				", isAdmin='" + mAdminAccess +
				"'}";
	}

	public List<Product> getFavorites() {
		return mFavorites;
	}
}
