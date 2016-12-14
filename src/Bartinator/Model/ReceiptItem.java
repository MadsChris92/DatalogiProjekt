package Bartinator.Model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Yo
 */
@Embeddable
public class ReceiptItem {
	@Column(name = "amount")
	private int mAmount;
	@Column(name = "productName")
	private String mProductName;
	@Column(name = "price")
	private double mPrice;

	public ReceiptItem(int amount, String productName, double price) {
		mAmount = amount;
		mProductName = productName;
		mPrice = price;
	}

	public ReceiptItem() {}

	public int getAmount() {
		return mAmount;
	}

	public String getProductName() {
		return mProductName;
	}

	public double getPrice() {
		return mPrice;
	}

	public void setAmount(int amount) {
		mAmount = amount;
	}

	public void setProductName(String productName) {
		mProductName = productName;
	}

	public void setPrice(double price) {
		mPrice = price;
	}
}
