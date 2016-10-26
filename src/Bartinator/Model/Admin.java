package Bartinator.Model;

import javax.persistence.Entity;

/**
 * Created by Martin on 19-10-2016.
 */

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
