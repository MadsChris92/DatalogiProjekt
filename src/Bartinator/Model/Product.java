package Bartinator.Model;

import Bartinator.Controller.loginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class Product {
    Stage popUpStage;
    static int currentID;

    public int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String name, category;
    private float price;
    private Button b;

    public Button getB() {
        return b;
    }

    public void setB(Button b) {
        this.b = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void actionHandler() throws IOException {
        System.out.println(ID);

        startPopUp();
    }

    public void startPopUp() throws IOException {
        Parent root1;
        popUpStage = new Stage();
        root1 = FXMLLoader.load(loginController.class.getResource("../View/btnCreateMenu.fxml"));
        popUpStage.setScene(new Scene(root1, 500, 500));
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.show();
    }


}
