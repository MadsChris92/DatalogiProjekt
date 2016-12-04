package Bartinator.Model;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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

	private ObservableList<ObservableProduct> mProducts = FXCollections.observableArrayList();
	private ObservableList<Category> mCategories = FXCollections.observableArrayList();
	private ProductDataAccessObject mProductDataAccessObject = ProductDataAccessObject.getInstance();

	private ProductCatalog() {
		refresh();
	}

	public void refresh() {
		fetchProducts();
		fetchCategories();
	}

	private void fetchProducts(){
		mProducts.clear();
		List<Product> products = mProductDataAccessObject.getProducts();
		for(Product product : products){
			mProducts.add(new ObservableProduct(product));
		}
	}

	private void fetchCategories(){
		mCategories.clear();
		mCategories.addAll(mProductDataAccessObject.getCategories());
	}



	public ObservableList<ObservableProduct> getProductsByCategory(Category category){
		return mProducts.filtered(observableProduct -> Objects.equals(observableProduct.getCategory(), category));
	}

	public List<Category> getCategoriesByCategory(Category category){
		return mCategories.filtered(category1 -> Objects.equals(category1.getCategory(), category));
	}

	public ObservableProduct getProductById (int id){
		FilteredList<ObservableProduct> filtered = mProducts.filtered(observableProduct -> observableProduct.getId() == id);
		if(filtered.isEmpty()){
			return null;
		} else {
			return filtered.get(0);
		}
	}

	public void saveProducts(){
		List<Product> currentDBproducts = new ArrayList<Product>();
		currentDBproducts.addAll(mProductDataAccessObject.getProducts());
		for (ObservableProduct observableProduct : mProducts) {
			Product product = observableProduct.toProduct();
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
		for (Category category : mCategories) {
			if(currentDBcategories.contains(category)){
				mProductDataAccessObject.updateCategory(category);
			} else {
				mProductDataAccessObject.saveCategory(category);
			}
		}
	}

	public void updateProduct(ObservableProduct observableProduct) {
		mProductDataAccessObject.updateProduct(observableProduct.toProduct());
	}

	public void removeProduct(ObservableProduct observableProduct){
		mProducts.remove(observableProduct);
		mProductDataAccessObject.removeProduct(observableProduct.toProduct());
	}

	public void updateCategory(Category category) {
		mProductDataAccessObject.updateCategory(category);
	}

	public void removeCategory(Category category) {
		for(ObservableProduct observableProduct : getProductsByCategory(category)){
			mProducts.remove(observableProduct);
			mProductDataAccessObject.removeProduct(observableProduct.toProduct());
		}
		mCategories.remove(category);
		mProductDataAccessObject.removeCategory(category);
	}

	public void saveCategory(Category category) {
		mCategories.add(category);
		mProductDataAccessObject.saveCategory(category);
	}

	public void renameCategory(Category category, String newName){
		mProductDataAccessObject.renameCategory(category, newName);
		category.setName(newName);
	}

	public Category getCategoryByName(String name) {
		for(Category category : mCategories){
			if(category.getName().equals(name)){
				return category;
			}
		}
		return null;
	}

	public void saveProduct(ObservableProduct observableProduct){
		mProducts.add(observableProduct);
		mProductDataAccessObject.saveProduct(observableProduct.toProduct());
	}

	public ObservableList<Category> getCategories() {
		return mCategories;
	}

	public ObservableList<ObservableProduct> getProducts() {
		return mProducts;
	}

	public boolean contains(Category category) {
		return mCategories.contains(category);
	}
}
