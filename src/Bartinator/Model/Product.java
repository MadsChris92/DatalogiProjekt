package Bartinator.Model;

/**
 * Created by Mads on 21-09-2016.
 */
public class Product {
    public String mName, mCategory;
    public float mPrice;

    public Product(String name, String category, float price) {
        mName = name;
        mCategory = category;
        mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }
}
