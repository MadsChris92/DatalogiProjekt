package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.Model.Product;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

/**
 * Created by Martin on 28-11-2016.
 */
public class ButtonProduct extends StackPane {
	private Button mButton;
	private ToggleButton mFavorite;
	private Product mProduct;

	ButtonProduct(Product product) {
		mProduct = product;
		mButton = new Button(String.format("%s%n%f", product.getName(), product.getPrice()));
		mFavorite = new ToggleButton("*");
		mFavorite.setSelected(product.isFavorite());
		mFavorite.setOnAction(event -> {
			mProduct.setFavorite(mFavorite.isSelected());
			ProductDataAccessObject.getInstance().updateProduct(mProduct);
		});
		setAlignment(Pos.TOP_RIGHT);
		getChildren().add(mButton);
		getChildren().add(mFavorite);
	}

	public Button getButton() {
		return mButton;
	}

	public Product getProduct() {
		return mProduct;
	}

	public ToggleButton getFavorite() {
		return mFavorite;
	}
}
