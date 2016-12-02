package Bartinator.Model;

import Bartinator.DataAccessObjects.ProductDataAccessObject;

import java.util.ArrayList;
import java.util.List;

import static Bartinator.DataAccessObjects.MainDataAccessObject.fetch;

/**
 * Created by martin on 12/1/16.
 * Contains all the products and methods to get them
 */
public class ProductCatalog {
	private static ProductCatalog instance = new ProductCatalog();
	public static ProductCatalog getInstance() {
		return instance;
	}

	private List<Product> mProducts = new ArrayList<>();
	private List<Category> mCategories = new ArrayList<>();
	private ProductDataAccessObject mProductDataAccessObject = ProductDataAccessObject.getInstance();

	private ProductCatalog() {
		fetchProducts();
		fetchCategories();
	}

	private void fetchProducts(){
		mProducts.clear();
		mProducts.addAll(mProductDataAccessObject.getProducts());
	}

	private void fetchCategories(){
		mCategories.clear();
		mCategories.addAll(mProductDataAccessObject.getCategories());
	}



	public List<Product> getProductsByCategory(Category category){
		List<Product> result = new ArrayList<>();
		for (Product product : mProducts) {
			if (product.getCategory().equals(category)){
				result.add(product);
			}
		}
		return result;
	}

	public Product getProductById (int id){
		for (Product product : mProducts) {
			if(product.getId() == id){
				return product;
			}
		}
		return null;
	}

	public void saveProducts(){
		List<Product> currentDBproducts = new ArrayList<Product>();
		currentDBproducts.addAll(mProductDataAccessObject.getProducts());
		for (Product product :mProducts) {
			if(!currentDBproducts.contains(product)){
				mProductDataAccessObject.updateProduct(product);
			} else {
				mProductDataAccessObject.saveProduct(product);
			}
		}
	}

	public void saveCategories(){
		List<Category> currentDBcategories = new ArrayList<Category>();
		currentDBcategories.addAll(getCategories());
		for (Category category :mCategories) {
			if(currentDBcategories.contains(category)){
				mProductDataAccessObject.updateCategory(category);
			} else {
				mProductDataAccessObject.saveCategory(category);
			}
		}
	}

	public void updateProduct(Product product) {
		mProductDataAccessObject.updateProduct(product);
	}

	public void removeProduct(Product product){
		mProductDataAccessObject.removeProduct(product);
	}

	public void updateCategory(Category activeCategory) {
		mProductDataAccessObject.updateCategory(activeCategory);
	}

	public void removeCategory(Category category) {
		for(Product product : getProductsByCategory(category)){
			mProductDataAccessObject.removeProduct(product);
		}
		mProductDataAccessObject.removeCategory(category);
	}

	public void saveProduct(Product product){
		mProductDataAccessObject.saveProduct(product);
	}

	public List<Category> getCategories() {
		return mCategories;
	}

	public List<Product> getProducts() {
		return mProducts;
	}

}
