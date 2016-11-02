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
    private List<Category> mCategories;

    public ProductDataAccessObject(){
        mProducts = (List<Product>) fetch(Product.class);
        mCategories = (List<Category>) fetch(Category.class);
    }

    public List<Category> getCategories() {
        return mCategories;
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

    public void saveCategories(){
        List<Category> currentDBcategories = new ArrayList<Category>();
        currentDBcategories.addAll((List<Category>) fetch(Category.class));
        for (Category p :mCategories) {
            if(!currentDBcategories.contains(p)){
                save(p);
            }
        }
    }

	public void refresh() {
		mProducts = (List<Product>) fetch(Product.class);
		mCategories = (List<Category>) fetch(Category.class);
	}

	public void updateProduct(Product product) {
		remove(product);
		save(product);
	}
}
