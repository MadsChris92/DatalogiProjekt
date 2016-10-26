package Bartinator.DataAccessObjects;


import Bartinator.Model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class ProductDataAccessObject extends MainDataAccessObject {

    private static final ProductDataAccessObject instance = new ProductDataAccessObject();

    public static ProductDataAccessObject getInstance(){
        return instance;
    }

    private List<Product> mProducts;

    private ProductDataAccessObject(){
        mProducts = new ArrayList<Product>();
        mProducts.addAll((Collection<? extends Product>) fetch(Product.class));
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void saveProducts(){

        List<Product> currentDBproducts = new ArrayList<Product>();
        currentDBproducts.addAll((Collection<? extends Product>) fetch(Product.class));
        for (Product p :mProducts) {
            if(!currentDBproducts.contains(p)){
                save(p);
            }
        }



    }
}
