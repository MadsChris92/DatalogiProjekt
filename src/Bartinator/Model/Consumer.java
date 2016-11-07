package Bartinator.Model;

import javax.persistence.*;


@Entity
public class Consumer {

	@Id
	private int mStudentID;

	@Column
	private String mName;

	@Column
	private double mBalance;

	protected Consumer() {}

	public Consumer(int studentID, String name) {
		mStudentID = studentID;
		mName = name;
		mBalance = 0.0;
	}

	public Consumer(int studentID, String name, double balance) {
		mStudentID = studentID;
		mName = name;
		mBalance = balance;
	}

	public int getStudentID() {
		return mStudentID;
	}

	public void setStudentID(int studentID) {
		mStudentID = studentID;
	}

	public double getBalance() {
		return mBalance;
	}

	public void setBalance(double balance) {
		mBalance = balance;
	}

	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}


	@Override public String toString() {
		return "Consumer{" +
				"mStudentID=" + mStudentID +
				", mName='" + mName + '\'' +
				", mBalance=" + mBalance +
				'}';
	}
	public boolean pay(double total) {
		if((getBalance()-total) >= 0){
			mBalance -= total;
			return true;
		}
		return false;
	}
}
