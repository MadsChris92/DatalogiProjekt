package Bartinator.Controller;

import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Category;
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

    ProductDataAccessObject mProductDAO;
    String selectedCat = null;



    @Override public void initialize(URL location, ResourceBundle resources) {
        mProductDAO = ProductDataAccessObject.getInstance();

        displaySelectedCat();



    }
    private void displaySelectedCat() {

    }
    private List<Button> createBtnList(String selectedCat) {
        List<Button> buttons = new ArrayList<>();
        if(selectedCat != null) {
            //TODO: Vis produkter i bestemt category

        } else {
            List<Category> categorys = mProductDAO.getCategorys();

            for (Category c : categorys) {
                Button btn = new Button(c.getName());
                btn.setOnAction(handleCatBtn);
                buttons.add(btn);
            }
        }
        return buttons;
    }

    EventHandler<ActionEvent> handleCatBtn = new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
            Button btn = (Button) event.getSource();
            selectedCat = btn.getText();
        }
    };



    public void handleCreateConsumer (ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/createConsumerView.fxml"));
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
