package Bartinator.Model;

import javax.persistence.*;

/**
 * Created by Casper on 19-10-2016.
 */
@Entity
public class Consumer extends User {
	@Column
	private double balance;
	@Column
	private int studentID;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	@Override
	public String toString() {
		return "Consumer{" +
				"balance=" + balance +
				", studentID=" + studentID +
				"} " + super.toString();
	}
}
