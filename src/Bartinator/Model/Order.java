package Bartinator.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private double mTotalPrice;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> mReceipt = new ArrayList<>();

    @Column
    private String mBartenderName;

    @Column
    private int mBartenderId;

    @Column
    private String mConsumerName;

    @Column
    private Integer mConsumerId;

    protected Order() {}

    public Order(double totalPrice, List<String> receipt, String bartenderName, int bartenderId) {
        mTotalPrice = totalPrice;
        mReceipt = receipt;
        mBartenderName = bartenderName;
        mBartenderId = bartenderId;
    }

    public Order(double totalPrice, List<String> receipt, String bartenderName, int bartenderId, String consumerName, Integer consumerId) {
        mTotalPrice = totalPrice;
        mReceipt = receipt;
        mBartenderName = bartenderName;
        mBartenderId = bartenderId;
        mConsumerName = consumerName;
        mConsumerId = consumerId;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }
    public List<String> getReceipt() {
        return mReceipt;
    }
    public String getBartenderName() {
        return mBartenderName;
    }
    public int getBartenderId() {
        return mBartenderId;
    }
    public String getConsumerName() {
        return mConsumerName;
    }
    public Integer getConsumerId() {
        return mConsumerId;
    }

    @Override public String toString() {
        String result = String.format("Order:%n");
        for (String s : mReceipt) {
            result += s;
            result += String.format("%n");
        }
        result += String.format("Total price: " + mTotalPrice + " DKK%n"
                + "Salesman: " + mBartenderName + "-" + mBartenderId + "%n"
                + "Consumer: " + mConsumerName + "-" + mConsumerId + "%n");
        return result;
    }
}
