package Bartinator.Model;

import javax.persistence.*;


@Entity
public class Consumer {

	@Id
	private int mStudentID;

	@Column
	private String mFirstName;

	@Column
	private String mLastName;

	@Column
	private double mBalance;

	protected Consumer() {}

	public Consumer(int studentID, String firstName, String lastName) {
		mStudentID = studentID;
		mFirstName = firstName;
		mLastName = lastName;
		mBalance = 0.0;
	}

	public Consumer(int studentID, String firstName, String lastName, double balance) {
		mStudentID = studentID;
		mFirstName = firstName;
		mLastName = lastName;
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

	public String getFirstName() {
		return mFirstName;
	}
	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}
	public String getLastName() {
		return mLastName;
	}
	public void setLastName(String lastName) {
		mLastName = lastName;
	}

	@Override public String toString() {
		return "Consumer{" +
				"mStudentID=" + mStudentID +
				", mFirstName='" + mFirstName + '\'' +
				", mLastName='" + mLastName + '\'' +
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
