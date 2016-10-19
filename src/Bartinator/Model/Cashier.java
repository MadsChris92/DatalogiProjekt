package Bartinator.Model;


import Bartinator.Controller.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cashier {

    private Map<Product, Integer> mCart = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {

        if (mCart.containsKey(product)) {

            //TODO: Kan gøres mere simpelt -> Tjek dokumentationen Map
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

    public int checkOut(){
        int sum = 0;
        for (Map.Entry<Product,Integer> p : mCart.entrySet()) {
            sum += (p.getValue()*p.getKey().getPrice());
        }
        return sum;
    }

}
