package Bartinator.Model;

import javax.persistence.Entity;


@Entity
public class Admin extends User {
	public Admin(String addie) {
		super(addie);
	}

	public Admin() {}

	@Override
	public boolean isAdmin(){
		return true;
	}

	@Override
	public String toString() {
		return "Admin{} " + super.toString();
	}
}
