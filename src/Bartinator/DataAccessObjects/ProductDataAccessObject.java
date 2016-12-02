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

    public List<Product> getProductsByCategory(String category){
        List<Product> result = new ArrayList<>();
        for (Product p : mProducts) {
            if (p.getCategory().getName().equals(category)){
                result.add(p);
            }
        }
        return result;
    }

    public Product getProductById (int id){
        for (Product p : mProducts) {
            if(p.getId() == id){
                return p;
            }
        }
        return null;
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

	public List<Category> getCategoriesByCategory(Category category){
		List<Category> result = new ArrayList<>();
		for (Category category1 : mCategories) {
			if (category1.getCategory().equals(category)){
				result.add(category1);
			}
		}
		return result;
	}

    public void saveCategories(){
        List<Category> currentDBcategories = new ArrayList<Category>();
        currentDBcategories.addAll((List<Category>) fetch(Category.class));
        for (Category category :mCategories) {
            if(currentDBcategories.contains(category)){
                update(category);
            } else {
                save(category);
            }
        }
    }

	public void refresh() {
		mProducts.clear();
        mProducts.addAll((List<Product>) fetch(Product.class));
        mCategories.clear();
		mCategories.addAll((List<Category>) fetch(Category.class));
	}

	public void updateProduct(Product product) {
		update(product);
	}

	public void removeProduct(Product product){
        remove(product);
    }

    public void renameCategory(Category category, String newName){

    }

    public void updateCategory(Category category) {
        update(category);
    }

    public void removeCategory(Category category) {
		for(Product product : getProductsByCategory(category.getName())){
			remove(product);
		}
		remove(category);
	}

    public void saveProduct(Product product){
		save(product);
	}

    public void saveCategory(Category category) { save(category); }
}
