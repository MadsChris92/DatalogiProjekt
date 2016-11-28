package Bartinator.Model;

import javax.persistence.*;


@Entity
public class Consumer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mConsumerID;

	@Column
	private String mFirstName;

	@Column
	private String mLastName;

	@Column
	private int mBalance;

	protected Consumer() {}

	public Consumer(String firstName, String lastName) {
		mFirstName = firstName;
		mLastName = lastName;
		mBalance = 0;
	}

	public Consumer(String firstName, String lastName, int balance) {
		mFirstName = firstName;
		mLastName = lastName;
		mBalance = balance;
	}

	public int getConsumerID() {
		return mConsumerID;
	}

	public void setConsumerID(int consumerID) {
		mConsumerID = consumerID;
	}

	public int getBalance() {
		return mBalance;
	}

	public void setBalance(int balance) {
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
				"mConsumerID=" + mConsumerID +
				", mFirstName='" + mFirstName + '\'' +
				", mLastName='" + mLastName + '\'' +
				", mBalance=" + mBalance +
				'}';
	}
	public boolean pay(int total) {
		if((getBalance()-total) >= 0){
			mBalance -= total;
			return true;
		}
		return false;
	}

	public void insertToBalance(int money){
		mBalance += money;
	}
}
