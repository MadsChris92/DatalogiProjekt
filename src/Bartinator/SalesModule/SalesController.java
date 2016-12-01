package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Category;
import Bartinator.Model.Product;
import Bartinator.Model.Employee;
import Bartinator.Model.ProductCatalog;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesController implements Initializable{

    @FXML public ListView<Product> mCartView;
    @FXML public GridPane mBtnGrid;

    private Cashier mCashier;
	private ProductCatalog mProductCatalog;
    private Category mSelectedCategory = null;
	private Employee mActiveEmployee = EmployeeDataAccessObject.getInstance().getActiveEmployee();

    int mBtnRadius = 90;


    @Override public void initialize(URL location, ResourceBundle resources) {
        mProductCatalog = ProductCatalog.getInstance();
        mCashier = new Cashier();

        displaySelectedCat();
        updateCartView();
    }

    private void displaySelectedCat() {
        System.out.println("Displaying: " + mSelectedCategory.getName());

        mBtnGrid.getChildren().clear();

        List<Node> buttons = createButtons();

        int rowCount = 0;
        int columnCount = 0;
        for (Node button: buttons) {
            mBtnGrid.add(button,columnCount,rowCount);
            columnCount++;
            if (columnCount >= 6){
                columnCount = 0;
                rowCount++;
            }
        }
        mBtnGrid.setAlignment(Pos.CENTER);
        ObservableList<RowConstraints> rowConstraints = mBtnGrid.getRowConstraints();
        for (RowConstraints rc: rowConstraints) {
            rc.setMaxHeight(mBtnRadius);
            rc.setValignment(VPos.CENTER);
        }
        ObservableList<ColumnConstraints> columnConstraints = mBtnGrid.getColumnConstraints();
        for (ColumnConstraints cc:columnConstraints) {
            cc.setMaxWidth(mBtnRadius);
            cc.setHalignment(HPos.CENTER);
        }
    }

    private List<Node> createButtons(){
		List<Node> buttons = new ArrayList<>();
		if(mSelectedCategory != null) {
			List<Product> products = mProductCatalog.getProductsByCategory(mSelectedCategory);
			buttons.add(new BackButton(this));
			System.out.println(products);
			for (Product product : products) {
				System.out.println("Fetched product: " + product.toString());
				buttons.add(new ProductButton(this, product));
			}
		} else {
			List<Category> categories = mProductCatalog.getCategories();
			for (Category category : categories) {
				System.out.println("Fetched category: " + category.toString());
				buttons.add(new CategoryButton(this, category));
			}

			for (Product product : mActiveEmployee.getFavorites()) {
				System.out.println("Fetched product: " + product.toString());
				buttons.add(new ProductButton(this, product));
			}
		}
		return buttons;
	}

    EventHandler<ActionEvent> handleProductBtn = new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
            Button btn = (Button) event.getSource();
			Product product = ((ProductButton)btn.getParent()).getProduct();
			mCashier.addProduct(product, 1);
			updateCartView();
        }
    };

	EventHandler<ActionEvent> handleBackBtn = new EventHandler<ActionEvent>() {
		@Override public void handle(ActionEvent event) {
			mSelectedCategory = mSelectedCategory.getCategory();
			displaySelectedCat();
		}
	};

    EventHandler<ActionEvent> handleCatBtn = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                CategoryButton button = (CategoryButton) event.getSource();
                mSelectedCategory = button.getCategory();
                displaySelectedCat();
            }
        };


	private void updateCartView() {
		mCartView.getItems().clear();
		mCartView.getItems().addAll(mCashier.);
	}

    public void handleCheckOut(ActionEvent actionEvent) {
        boolean success = mCashier.checkOut();
        if(!success){
            AlertBoxes.displayErrorBox("Payment Problem", "Consumer can't afford cart content");
        } else {
            if(AlertBoxes.displayConfirmationBox("Confirm Sale","Did you mean to checkout?")){
				mCashier.clearCart();
				updateCartView();
				mSelectedCategory = null;
				displaySelectedCat();
			}
        }

    }

    public void handleDelete(ActionEvent actionEvent) {
        ObservableList<Product> selectedStrings = mCartView.getSelectionModel().getSelectedItems();
        for (Product product: selectedStrings) {
            mCashier.removeProduct(product, 1);
        }
        updateCartView();
    }

    public void handleDeleteAll(ActionEvent actionEvent) {
		ObservableList<Product> selectedStrings = mCartView.getSelectionModel().getSelectedItems();
		for (Product product: selectedStrings) {
			mCashier.removeProduct(product);
		}
        updateCartView();
    }

    public void handleClearCart(ActionEvent actionEvent) {
        mCashier.clearCart();
        updateCartView();
    }

    public void handleLogOut(ActionEvent actionEvent) {
        if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")){
			Navigator.switchToLoginView();
        }
    }
}
