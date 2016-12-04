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

    public ProductDataAccessObject(){}

    public List<Category> getCategories() {
        return (List<Category>) fetch(Category.class);
    }

    public List<Product> getProducts() {
        return (List<Product>) fetch(Product.class);
    }

    public List<Product> getProductsByCategory(Category category){
        return (List<Product>) fetch(Product.class, "category", category);
    }

    public List<Category> getCategoriesByCategory(Category category){
        return (List<Category>) fetch(Category.class, "category", category);
    }

	public void updateProduct(Product product) {
		update(product);
	}

	public void removeProduct(Product product){
        remove(product);
    }

	/**
	 * Renames an existing category and updates all the products and categories it contained
	 * @param category the category to rename
	 * @param newName the new name of the category
	 */
    public void renameCategory(Category category, String newName){
		// Because the name is the primary key of the category, it cant be readily changed, so the category is remade instead
	    Category renamedCategory = new Category();
	    renamedCategory.setName(newName);
	    renamedCategory.getColumns().addAll(category.getColumns());
	    renamedCategory.setCategory(category.getCategory());
        for(Category subCategory : getCategoriesByCategory(category)){
            subCategory.setCategory(renamedCategory);
            updateCategory(subCategory);
        }
        for(Product product : getProductsByCategory(category)){
            product.setCategory(renamedCategory);
            updateProduct(product);
        }
        saveCategory(renamedCategory);
        remove(category);
    }

    public void updateCategory(Category category) {
        update(category);
    }

    public void removeCategory(Category category) {
        for(Category category1 : getCategoriesByCategory(category)){
            removeCategory(category1);
        }
		for(Product product : getProductsByCategory(category)){
			remove(product);
		}
		remove(category);
	}

    public void saveProduct(Product product){
		save(product);
	}

    public void saveCategory(Category category) { save(category); }
}
