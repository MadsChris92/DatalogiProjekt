package Bartinator.Controller;

import Bartinator.Model.ButtonCustom;
import Bartinator.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class editorController {

    Stage popUpStage;
    public Button addItemBtn;
    public VBox btnContainer;
    public Button removeItemBtn;
    static int currentItem;

    ArrayList<Button> btnList = new ArrayList<Button>();
    static ArrayList<Product> products = new ArrayList<>();

    public void addItemHandler(ActionEvent actionEvent) throws IOException {
        ButtonCustom b = new ButtonCustom();
        b.setOnAction(e -> itemHandler(b));
        btnContainer.getChildren().add(b);
        btnList.add(b);
        Product p = new Product();
        products.add(p);
        currentItem = products.size() - 1;
        System.out.println(""+currentItem);
        setBtnPreference();
        startPopUp();
    }

    private void itemHandler(Button b) {
        b.setText("afjkælsfkslkf.");
        System.out.println("fads");
    }

    public void startPopUp() throws IOException {
        Parent root1;
        popUpStage = new Stage();
        root1 = FXMLLoader.load(loginController.class.getResource("../View/btnCreateMenu.fxml"));
        popUpStage.setScene(new Scene(root1, 500, 500));
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.show();
    }


    private void setBtnPreference(){
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setB(btnList.get(i));
            products.get(i).getB().setMinSize(100,100);
        }
    }
}
