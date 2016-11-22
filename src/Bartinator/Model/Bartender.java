package Bartinator.Model;

import javax.persistence.Entity;

@Entity
public class Bartender extends User {


	protected Bartender(){}

	@Override
	public String toString() {
		return "Bartender{} " + super.toString();
	}
}
