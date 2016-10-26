package Bartinator.Model;

import javax.persistence.*;


@Entity
public abstract class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String name;
	@Column
	private String username;
	@Column
	private int password;

	public User(){};
	public User(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isBartender() {
		return false;
	}

	public boolean isAdmin() {
		return false;
	}

	@Override
	public String toString() {
		return "User{" +
				"userid=" + id +
				", name='" + name + '\'' +
				", username='" + username + '\'' +
				", password=" + password +
				'}';
	}
}
