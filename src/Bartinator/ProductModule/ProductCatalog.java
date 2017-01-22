package Bartinator.ProductModule;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.Model.Category;
import Bartinator.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Bartinator.DataAccessObjects.MainDataAccessObject.fetch;

/**
 * Created by martin on 12/1/16.
 * Contains all the products and methods to get them
 */

//SINGLETON!!! <----
public class ProductCatalog {
	private static ProductCatalog instance = new ProductCatalog();
	public static ProductCatalog getInstance() {
		return instance;
	}

	//lokale variabler
	private ObservableList<ObservableProduct> mProducts = FXCollections.observableArrayList();
	private ObservableList<Category> mCategories = FXCollections.observableArrayList();
	private ProductDataAccessObject mProductDataAccessObject = ProductDataAccessObject.getInstance();
	//dette er en konstruktør
	private ProductCatalog() {
		refresh();
	}
	//henter produkterne og kategorierne
	public void refresh() {
		fetchProducts();
		fetchCategories();
	}

	//Rydder mProducts observable arrayet og henter produkterne ned igen, for at konvertere dem
	//til observable products og gemme dem i arrayet.
	private void fetchProducts(){
		mProducts.clear();
		List<Product> products = mProductDataAccessObject.getProducts();
		for(Product product : products){
			mProducts.add(new ObservableProduct(product));
		}
	}

	//Samme historie som med produkterne over.
	private void fetchCategories(){
		mCategories.clear();
		mCategories.addAll(mProductDataAccessObject.getCategories());
	}


	//.filtered sammenligner (med en lambda) resultatet af en af indholdets metoder. Således at man filtrere indholdet
	//af arrayet ved at spørge om en bestemt metode returnere et bestemt resultat. Her category.
	//Laves der ændringer i original arrayet, så vil ændringerne også ske i de associerede filtered arrays.
	public ObservableList<ObservableProduct> getProductsByCategory(Category category){
		return mProducts.filtered(observableProduct -> Objects.equals(observableProduct.getCategory(), category));
	} // Der er en liste den søge igennem og den tager alle de produkter som ikke har den kategori man leder efter
	// og smider væk og beholder kun dem der har den kategori

	public List<Category> getCategoriesByCategory(Category category){
		return mCategories.filtered(category1 -> Objects.equals(category1.getCategory(), category));
	}// det samme som overstående bare med kategoriere

	//mProducts.filtered() returnere en FilteredList<>. I ovenstående har vi castet til en observable igen
	//Nedenfor bruger vi det som en FilteredList.
	public ObservableProduct getProductById (int id){
		FilteredList<ObservableProduct> filtered = mProducts.filtered(observableProduct -> observableProduct.getId() == id);
		if(filtered.isEmpty()){
			return null;
		} else {
			return filtered.get(0);
		}
	}// filtere efter id (kig 2 metoder længere oppe hvis du er fucked)

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

	void createProduct(String name, Category category){
		Product product = new Product(name, category);
		mProductDataAccessObject.saveProduct(product);
		mProducts.add(new ObservableProduct(product));
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
