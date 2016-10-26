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
    Category cat;

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    Stage stage = new Stage();
    public int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String name;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void actionHandler() throws IOException {
        System.out.println(ID);
        Parent root1;
        root1 = FXMLLoader.load(loginController.class.getResource("../View/btnCreateMenu.fxml"));
        stage.setScene(new Scene(root1, 500, 500));
        stage.initModality(Modality.APPLICATION_MODAL);
        //btnCreateController.productID = getID();
        stage.show();
    }


}
