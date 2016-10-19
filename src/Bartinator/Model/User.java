package Bartinator.Model;

import javax.persistence.*;


@Entity
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	@Column
	private String name;
	@Column
	private String username;
	@Column
	private int password;
	@Column
	private boolean adminAccess;

	public User(){};
	public User(String username) {
		this.username = username;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return false;
	}

	public boolean isAdminAccess() {
		return adminAccess;
	}

	public void setAdminAccess(boolean adminAccess) {
		this.adminAccess = adminAccess;
	}

	@Override
	public String toString() {
		return "User{" +
				"userid=" + userid +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", password=" + password +
				'}';
	}
}
