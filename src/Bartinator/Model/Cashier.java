package Bartinator.Model;


import java.util.LinkedHashMap;
import java.util.Map;

public class Cashier {

   private Map<Product, Integer> mCart = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity){

        if(mCart.containsKey(product)){

        } else {
            mCart.put(product, quantity);
        }

    }

    public boolean removeProduct(Product product, int quantity){

    }




}
