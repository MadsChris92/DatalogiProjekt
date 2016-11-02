package Bartinator.Controller;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Cashier;
import Bartinator.Model.Category;
import Bartinator.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class bartenderController implements Initializable{

    @FXML public ListView cartView;
    @FXML public GridPane btnGrid;

    Cashier mCashier;
    ProductDataAccessObject mProductDAO;
    String selectedCat = null;


    @Override public void initialize(URL location, ResourceBundle resources) {
        mProductDAO = ProductDataAccessObject.getInstance();
        mCashier = new Cashier();
        btnGrid.setGridLinesVisible(true);

        displaySelectedCat();

    }
    private void displaySelectedCat() {
        System.out.println("Displaying: " + selectedCat);

        List<Button> buttons = createBtnList();
        int rowCount = 0;
        int colomnCount = 0;
        for (Button btn: buttons) {
            System.out.println("Adding Button to C: " + colomnCount + " Row: " + rowCount);
            btnGrid.add(btn,colomnCount,rowCount);
            colomnCount++;
            if (colomnCount >= 4){
                colomnCount = 0;
                rowCount++;
            }
        }



    }
    private List<Button> createBtnList() {
        System.out.println("Button create called!!!");
        List<Button> buttons = new ArrayList<>();
        if(selectedCat != null) {
            List<Product> products = mProductDAO.getProductsByCategory(selectedCat);
            buttons.add(new Button("<-"));
            for (Product p : products) {
                System.out.println("Fetched product: " + p.toString());
                Button btn = new Button(p.getName() + "-" + p.getId());
                btn.setOnAction(handleProductBtn);
                buttons.add(btn);
            }
        } else if(selectedCat == null) {
            List<Category> categorys = mProductDAO.getCategories();
            for (Category c : categorys) {
                System.out.println("Fetched product: " + c.toString());
                Button btn = new Button(c.getName());
                btn.setOnAction(handleCatBtn);
                buttons.add(btn);
            }
        }
        return buttons;
    }

    EventHandler<ActionEvent> handleProductBtn = new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
            Button btn = (Button) event.getSource();
            if (btn.getText() == "<-") {
                selectedCat = null;
                displaySelectedCat();
            } else {
                String[] strings = btn.getText().split("-");
                Product p = mProductDAO.getProductById(Integer.parseInt(strings[1]));
                mCashier.addProduct(p, 1);
            }
        }
    };

        EventHandler<ActionEvent> handleCatBtn = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Button btn = (Button) event.getSource();
                selectedCat = btn.getText();
                displaySelectedCat();
            }
        };



    public void handleCreateConsumer (ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/createConsumerView.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load createConsumer window!");
            e.printStackTrace();
        }
    }
    public void handleCheckOut(ActionEvent actionEvent) {



    }
    public void handleDelete(ActionEvent actionEvent) {
    }

}
