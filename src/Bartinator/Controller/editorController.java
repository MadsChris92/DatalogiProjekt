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

    static ArrayList<Button> btnList = new ArrayList<Button>();
    static ArrayList<Product> products = new ArrayList<>();
    static Product currentProduct;

    int btnID = 0;

    public void addItemHandler(ActionEvent actionEvent) throws IOException {
        btnID++;
        ButtonCustom b = new ButtonCustom();

        btnContainer.getChildren().add(b);
        btnList.add(b);
        Product p = new Product();
        b.setOnAction(e -> {
            try {
                p.actionHandler();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        p.setID(btnID-1);
        currentProduct = p;
        products.add(p);
        setBtnPreference();
        startPopUp();
    }

    private void itemHandler(Button b, int ID) {
        System.out.println(ID);
 //       popUpStage.show();
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
            btnList.get(i).setMinSize(100,100);
            //products.get(i).setB(btnList.get(i));
            //products.get(i).getB().setMinSize(100,100);
        }
    }
}
