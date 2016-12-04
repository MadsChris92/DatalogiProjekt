package Bartinator.Model;

import Bartinator.DataAccessObjects.OrderDataAccessObject;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "aOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ReceiptItem> mReceipt = new ArrayList<>();

    @Column(name = "bartenderName")
    private String mBartenderName;

    @Column(name = "bartenderId")
    private int mBartenderId;

	@Column(name = "paymentType")
	private String mPaymentType;

	@Column(name = "returnChange")
	private double mChange;

	@Column(name = "totalPrice")
	private double mTotalPrice;

    @Basic
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
    private Date mTimestamp;


    protected Order() {}

    public Order(double totalPrice, List<ReceiptItem> receipt, String bartenderName, int bartenderId) {
        mTotalPrice = totalPrice;
        mReceipt = receipt;
        mBartenderName = bartenderName;
        mBartenderId = bartenderId;
        mTimestamp = OrderDataAccessObject.getInstance().getCurrentDate();
        mPaymentType = "Cash";
		mChange = 0;
	}

	public int getId() {
		return id;
	}
	public double getTotalPrice() {
        return mTotalPrice;
    }
    public List<ReceiptItem> getReceipt() {
        return mReceipt;
    }
    public String getBartenderName() {
        return mBartenderName;
    }
    public int getBartenderId() {
        return mBartenderId;
    }
    public Date getTimestamp() {
        return mTimestamp;
    }
	public String getPaymentType() {
		return mPaymentType;
	}
	public double getChange() {
		return mChange;
	}

	@Override public String toString() {
        String result = String.format("Order: %s%n", new SimpleDateFormat("dd/MM/yy HH:mm").format(mTimestamp));
        for (ReceiptItem item : mReceipt) {
            result += String.format("%2d x %-20s%5.2f%n", item.getAmount(), item.getProductName(), item.getPrice());
        }
        result += String.format("     --------------------     %n");
		result += String.format("Total price: %.2f DKK%nSalesman: %s-%d", mTotalPrice, mBartenderName, mBartenderId);
        return result;
    }

}
