package Bartinator.SalesModule;

import Bartinator.EmployeeModule.EmployeeRoster;
import Bartinator.EmployeeModule.ObservableEmployee;
import Bartinator.Model.*;
import Bartinator.ProductModule.ObservableProduct;
import Bartinator.ProductModule.ProductCatalog;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesController implements Initializable {

	@FXML
	public ListView<CartItem> mCartView;
	@FXML
	public GridPane mBtnGrid;
	@FXML
	public TextField sumField;

	private Cashier mCashier;
	private ProductCatalog mProductCatalog;
	private Category mSelectedCategory = null;
	ObservableEmployee mActiveEmployee = EmployeeRoster.getInstance().getActiveEmployee();

	int mBtnRadius = 90;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mProductCatalog = ProductCatalog.getInstance();
		mCashier = new Cashier();
		mCartView.setItems(mCashier.getObservableCart());
		displaySelectedCat();
		//updateCartView();
	}

	private void displaySelectedCat() {
		if (mSelectedCategory != null)
			System.out.println("Displaying: " + mSelectedCategory.getName());

		mBtnGrid.getChildren().clear();

		List<Node> buttons = createButtons();

		int rowCount = 0;
		int columnCount = 0;
		for (Node button : buttons) {
			mBtnGrid.add(button, columnCount, rowCount);
			columnCount++;
			if (columnCount >= 6) {
				columnCount = 0;
				rowCount++;
			}
		}
		mBtnGrid.setAlignment(Pos.CENTER);
		ObservableList<RowConstraints> rowConstraints = mBtnGrid.getRowConstraints();
		for (RowConstraints rc : rowConstraints) {
			rc.setMaxHeight(mBtnRadius);
			rc.setValignment(VPos.CENTER);
		}
		ObservableList<ColumnConstraints> columnConstraints = mBtnGrid.getColumnConstraints();
		for (ColumnConstraints cc : columnConstraints) {
			cc.setMaxWidth(mBtnRadius);
			cc.setHalignment(HPos.CENTER);
		}
	}

	private List<Node> createButtons() {
		List<Node> buttons = new ArrayList<>();
		List<Category> categories = mProductCatalog.getCategoriesByCategory(mSelectedCategory);
		ObservableList<ObservableProduct> products = mProductCatalog.getProductsByCategory(mSelectedCategory);

		for (Category category : categories) {
			System.out.println("Fetched category: " + category.toString());
			buttons.add(new CategoryButton(this, category));
		}
		for (ObservableProduct product : products) {
			System.out.println("Fetched product: " + product.toString());
			buttons.add(new ProductButton(this, product.toProduct()));
		}

		// Are we at the root page?
		if (mSelectedCategory == null) {
			// Then add the current employees pinned products
			for (Product product : mActiveEmployee.getFavorites()) {
				System.out.println("Fetched product: " + product.toString());
				buttons.add(new ProductButton(this, product));
			}
		} else {
			// Add the back button at the beginning if the current category isn't null
			buttons.add(0, new BackButton(this));
		}

		return buttons;
	}

	EventHandler<ActionEvent> handleProductBtn = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			Button btn = (Button) event.getSource();
			Product product = ((ProductButton) btn.getParent()).getProduct();
			mCashier.addProduct(product, 1);
			sumField.setText("dkk "+mCashier.getTotal());
		}
	};

	EventHandler<ActionEvent> handleBackBtn = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			mSelectedCategory = mSelectedCategory.getCategory();
			displaySelectedCat();
		}
	};

	EventHandler<ActionEvent> handleCatBtn = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			CategoryButton button = (CategoryButton) event.getSource();
			mSelectedCategory = button.getCategory();
			displaySelectedCat();
		}
	};

	public void handleCheckOut(ActionEvent actionEvent) {
		if (AlertBoxes.displayConfirmationBox("Confirm Sale", "Did you mean to checkout?")) {
			boolean success = mCashier.checkOut();
			if (!success) {
				AlertBoxes.displayErrorBox("Payment Problem", "Consumer can't afford cart contents");
			} else {
				mCashier.clearCart();
				sumField.setText("dkk "+mCashier.getTotal());
				mSelectedCategory = null;
				displaySelectedCat();
			}
		}

	}

	public void handleDelete(ActionEvent actionEvent) {
		ObservableList<CartItem> selectedItems = mCartView.getSelectionModel().getSelectedItems();
		for (CartItem item : selectedItems) {
			mCashier.removeProduct(item.getProduct(), 1);
			sumField.setText("dkk "+mCashier.getTotal());
		}
	}

	public void handleDeleteAll(ActionEvent actionEvent) {
		ObservableList<CartItem> selectedItems = mCartView.getSelectionModel().getSelectedItems();
		for (CartItem item : selectedItems) {
			mCashier.removeProduct(item.getProduct());
			sumField.setText("dkk "+mCashier.getTotal());
		}
	}

	public void handleClearCart(ActionEvent actionEvent) {
		mCashier.clearCart();
	}

	public void handleLogOut(ActionEvent actionEvent) {
		if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")) {
			Navigator.switchToLoginView();
		}
	}
}
