package Bartinator.Model;

import javax.persistence.Entity;

@Entity
public class Bartender extends User {
	@Override
	public boolean isBartender() {
		return true;
	}

	protected Bartender(){}

	public Bartender(String name, String username, String password) {
		super(name, username, password);
	}

	@Override
	public String toString() {
		return "Bartender{} " + super.toString();
	}
}
