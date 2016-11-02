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
    private String mBartenderId;

    protected Order() {}

    public Order(double totalPrice, List<String> receipt, String bartenderName, String bartenderId) {
        mTotalPrice = totalPrice;
        mReceipt = receipt;
        mBartenderName = bartenderName;
        mBartenderId = bartenderId;
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
    public String getBartenderId() {
        return mBartenderId;
    }
}
