package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import Bartinator.Model.Product;
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

	ProductButton(SalesController controller, Product product) {
		mProduct = product;
		mButton = new Button(String.format("%s%n%.2f", product.getName(), product.getPrice()));
		mButton.setOnAction(controller.handleProductBtn);
		mFavorite = new ToggleButton("*");
		Employee activeEmployee = EmployeeDataAccessObject.getInstance().getActiveEmployee();
		mFavorite.setSelected(activeEmployee.getFavorites().contains(product));
		mFavorite.setOnAction(event -> {
			if(mFavorite.isSelected()){
				activeEmployee.getFavorites().add(mProduct);
			} else {
				activeEmployee.getFavorites().remove(mProduct);
			}
			EmployeeDataAccessObject.getInstance().updateUser(activeEmployee);
		});
		setAlignment(Pos.TOP_RIGHT);
		getChildren().add(mButton);
		getChildren().add(mFavorite);

		mButton.setMinHeight(controller.mBtnRadius);
		mButton.setMinWidth(controller.mBtnRadius);
		mButton.setMaxHeight(controller.mBtnRadius);
		mButton.setMaxWidth(controller.mBtnRadius);
		mButton.setWrapText(true);
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
