package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.DataAccessObjects.UserDataAccessObject;
import Bartinator.Model.Product;
import Bartinator.Model.User;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

/**
 * Created by Martin on 28-11-2016.
 */
public class ProductButton extends StackPane {
	private Button mButton;
	private ToggleButton mFavorite;
	private Product mProduct;

	ProductButton(Product product) {
		mProduct = product;
		mButton = new Button(String.format("%s%n%f", product.getName(), product.getPrice()));
		mFavorite = new ToggleButton("*");
		User activeUser = UserDataAccessObject.getInstance().getActiveUser();
		mFavorite.setSelected(activeUser.getFavorites().contains(product));
		mFavorite.setOnAction(event -> {
			if(mFavorite.isSelected()){
				activeUser.getFavorites().add(mProduct);
			} else {
				activeUser.getFavorites().remove(mProduct);
			}
			UserDataAccessObject.getInstance().updateUser(activeUser);
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
