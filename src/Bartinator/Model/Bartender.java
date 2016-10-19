package Bartinator.Model;

import javax.persistence.Entity;

/**
 * Created by Martin on 19-10-2016.
 */

@Entity
public class Bartender extends User {
	@Override
	public boolean isBartender() {
		return true;
	}

	@Override
	public String toString() {
		return "Bartender{} " + super.toString();
	}
}
