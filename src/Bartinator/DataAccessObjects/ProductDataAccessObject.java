package Bartinator.DataAccessObjects;


import Bartinator.Model.Category;
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
    private List<Category> mCategorys;

    public ProductDataAccessObject(){
        mProducts = new ArrayList<Product>();
        mProducts.addAll((List<Product>) fetch(Product.class));
        mCategorys = new ArrayList<Category>();
        mCategorys.addAll((List<? extends Category>) fetch(Category.class));
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public List<Product> getProductsByCategory(Category category){
        List<Product> result = new ArrayList<>();
        for (Product p: mProducts) {
            if(p.getCat().equals(category))
                result.add(p);
        }
        return result;
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

    public List<Category> getCategorys() {
        return mCategorys;
    }
}
