package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.DataAccessObjects.UserDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Category;
import Bartinator.Model.Product;
import Bartinator.Model.User;
import Bartinator.Utility.AlertBoxes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class bartenderController implements Initializable{

    @FXML public ListView mCartView;
    @FXML public GridPane mBtnGrid;

    Cashier mCashier;
    ProductDataAccessObject mProductDAO;
    String selectedCat = null;
	User activeUser = UserDataAccessObject.getInstance().getActiveUser();

    int mBtnRadius = 90;


    @Override public void initialize(URL location, ResourceBundle resources) {
        mProductDAO = ProductDataAccessObject.getInstance();
        mCashier = new Cashier();

        displaySelectedCat();
        updateCartView();
    }
    private void displaySelectedCat() {
        System.out.println("Displaying: " + selectedCat);

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
		if(selectedCat != null) {
			List<Product> products = mProductDAO.getProductsByCategory(selectedCat);
			Button backBtn = styleBtn(new Button("<-"));
			backBtn.setOnAction(handleProductBtn);
			buttons.add(backBtn);
			System.out.println(products);
			for (Product product : products) {
				System.out.println("Fetched product: " + product.toString());
				ProductButton productButton = new ProductButton(product);
				Button button = styleBtn(productButton.getButton());
				button.setOnAction(handleProductBtn);
				buttons.add(productButton);
			}
		} else {
			List<Category> categorys = mProductDAO.getCategories();
			for (Category c : categorys) {
				System.out.println("Fetched category: " + c.toString());
				Button btn = styleBtn(new Button(c.getName()));
                btn.setStyle("-fx-base: #f5efb9;");
				btn.setOnAction(handleCatBtn);
				buttons.add(btn);
			}

			for (Product product : activeUser.getFavorites()) {
				System.out.println("Fetched product: " + product.toString());
				ProductButton productButton = new ProductButton(product);
				Button button = styleBtn(productButton.getButton());
				button.setOnAction(handleProductBtn);
				buttons.add(productButton);
			}
		}
		return buttons;
	}
    private Button styleBtn(Button button) {
        button.setMinHeight(mBtnRadius);
        button.setMinWidth(mBtnRadius);
        button.setMaxHeight(mBtnRadius);
        button.setMaxWidth(mBtnRadius);
        return button;
    }

    EventHandler<ActionEvent> handleProductBtn = new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
            Button btn = (Button) event.getSource();
            if (btn.getText().equals("<-")) {
                selectedCat = null;
                displaySelectedCat();
            } else {
                Product product = ((ProductButton)btn.getParent()).getProduct();
                mCashier.addProduct(product, 1);
                updateCartView();
            }
        }
    };
    private void updateCartView() {
        mCartView.getItems().clear();
        mCartView.getItems().addAll(mCashier.getObservableCart());
    }
    EventHandler<ActionEvent> handleCatBtn = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Button btn = (Button) event.getSource();
                selectedCat = btn.getText();
                displaySelectedCat();
            }
        };

    public void handleCheckOut(ActionEvent actionEvent) {
        boolean succes = mCashier.checkOut();
        if(!succes){
            AlertBoxes.displayErrorBox("Payment Problem", "Consumer can't effort cart content");
        } else {
            AlertBoxes.displayInformationBox("Succes!","The sale have been proceeded with succes!");
            mCashier.clearCart();
            updateCartView();
            selectedCat = null;
            displaySelectedCat();
        }

    }
    public void handleDelete(ActionEvent actionEvent) {
        ObservableList<String> selectedStrings = mCartView.getSelectionModel().getSelectedItems();
        for (String s: selectedStrings) {
            String[] splittedString = s.split("-");
            Product p = mProductDAO.getProductById(Integer.parseInt(splittedString[1]));
            mCashier.removeProduct(p, 1);
        }
        updateCartView();
    }

    public void handleDeleteAll(ActionEvent actionEvent) {
        ObservableList<String> selectedStrings = mCartView.getSelectionModel().getSelectedItems();
        for (String s: selectedStrings) {
            String[] splittedString = s.split("-");
            Product p = mProductDAO.getProductById(Integer.parseInt(splittedString[1]));
            mCashier.removeProduct(p);
        }
        updateCartView();
    }
    public void handleClearCart(ActionEvent actionEvent) {
        mCashier.clearCart();
        updateCartView();
    }
    public void handleLogOut(ActionEvent actionEvent) {
        if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")){
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/loginView.fxml"));
                Main.theStage.setScene(new Scene(root, 800, 480));
            } catch (IOException e) {
                System.err.println("Failed to load loginView window");
                e.printStackTrace();
            }
        }
    }
}
