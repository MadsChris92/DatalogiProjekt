package Bartinator.ProductModule;

import Bartinator.Model.Category;
import Bartinator.Model.Product;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class ObservableProduct {
    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private Map<String, String> descriptions = new HashMap<>();
    private Category category;

	public ObservableProduct(Product product) {
		this.id = new SimpleIntegerProperty(product.getId());
		this.name = new SimpleStringProperty(product.getName());
		this.price = new SimpleDoubleProperty(product.getPrice());
		this.descriptions = product.getDescriptions();
		this.category = product.getCategory();
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty() {
		return id;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getName() {
		return name.get();
	}
	public StringProperty nameProperty() {
		return name;
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public double getPrice() {
		return price.get();
	}
	public DoubleProperty priceProperty() {
		return price;
	}
	public void setPrice(double price) {
		this.price.set(price);
	}
	public Map<String, String> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setDescription(String key, String value){
        if(descriptions.containsKey(key)){
            descriptions.replace(key, value);
        } else {
            descriptions.put(key, value);
        }
    }

    public Product toProduct(){
		Product product = new Product();
		product.setId(getId());
		product.setName(getName());
		product.setPrice(getPrice());
		product.setDescriptions(getDescriptions());
		product.setCategory(getCategory());
		return product;
	}

	@Override
	public String toString() {
		return "ObservableProduct{" +
				"id=" + id +
				", name=" + name +
				", price=" + price +
				", descriptions=" + descriptions +
				", category=" + category +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ObservableProduct that = (ObservableProduct) o;

		if (getId() != that.getId()) return false;
		return getName().equals(that.getName());
	}
}
