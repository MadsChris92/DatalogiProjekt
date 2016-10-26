package Bartinator.Model;


import java.util.LinkedHashMap;
import java.util.Map;

public class Cashier {

    private Map<Product, Integer> mCart = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {

        if (mCart.containsKey(product)) {
            for (Map.Entry<Product, Integer> p : mCart.entrySet()) {
                if (p.getKey().getID() == product.getID()) {
                    int currValue = mCart.get(product);
                    mCart.replace(product, currValue + quantity);
                }
            }
        } else {
            mCart.put(product, quantity);
        }
    }

    public boolean removeProduct(Product product) {
        if(mCart.containsKey(product)) {
            mCart.remove(product);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeProduct(Product product, int quantity) {
        if (mCart.containsKey(product)) {
            int currValue = mCart.get(product);
            mCart.replace(product, currValue - quantity);
            return true;
        } else {
            return false;
        }
    }

    public double checkOut(){
        double sum = 0;
        for (Map.Entry<Product,Integer> p : mCart.entrySet()) {
            sum += (p.getValue()*p.getKey().getPrice());
        }
        return sum;
    }

}
