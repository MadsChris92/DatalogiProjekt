package Bartinator.SalesModule;

import Bartinator.Model.Product;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Callback;

/**
 * Det er et specifikt vare i indkøbs kurven, der kan være flere eksemplarer af samme vare per CartItem
 */
public class CartItem {
	private final Product mProduct;
	private IntegerProperty mQuantity;

	public CartItem(Product product, int quantity) {
		mProduct = product;
		mQuantity = new SimpleIntegerProperty(quantity);
	}

	@Override
	public String toString() {
		return String.format("%s x %d      DKK%.2f", getProduct().getName(), getQuantity(), getProduct().getPrice() * getQuantity());
	}

	public Product getProduct() {
		return mProduct;
	}

	public int getQuantity() {
		return mQuantity.get();
	}

	public IntegerProperty quantityProperty() {
		return mQuantity;
	}

	public void setQuantity(int quantity) {
		this.mQuantity.set(quantity);
	}

	public void add(int quantity) {
		setQuantity(mQuantity.get()+quantity);
	}

	public void subtract(int quantity) {
		add(-quantity);
	}

	/**
	 * Return the total price
	 * @return getProduct().getPrice()*getQuantity()
	 */
	public double getTotal() {
		return mProduct.getPrice()* mQuantity.get();
	}

	//Bliver brugt til at fortælle den ObservableList der er i Cashier hvilket variable den skal holde øje med for
	// at finde ud af om listen er blevet opdateret(i dette tilfælde er det mQuantity)
	public static Callback<CartItem, Observable[]> extractor() {
		return new Callback<CartItem, Observable[]>() {
			@Override
			public Observable[] call(CartItem param) {
				return new Observable[]{param.mQuantity};
			}
		};
	}
}
