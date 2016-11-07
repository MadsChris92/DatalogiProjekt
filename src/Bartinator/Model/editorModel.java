package Bartinator.Model;

import Bartinator.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class editorModel {
    private Button productEdit, catAdd, catEdit;
    private Button catAccept;
    private HBox container;
    private Label catLabel;
    private TextField catText;
    public static ArrayList<Category> categories = new ArrayList<>();

    public void setContainer(HBox container) {
        this.container = container;
    }

    public void setProductEdit(Button productEdit) {
        this.productEdit = productEdit;
    }

    public void setCatAdd(Button catAdd) {
        this.catAdd = catAdd;
        catAdd.setOnAction(event -> createAddMenu());
    }

    public void setCatEdit(Button catEdit) {
        this.catEdit = catEdit;
    }

    private void createAddMenu() {
        catLabel = new Label("Name of Category:");
        catText = new TextField();
        catLabel.setMinWidth(150);
        catLabel.setMinHeight(50);
        catAccept = new Button("Accept");
        catAccept.setMinWidth(150);
        catAccept.setMinHeight(25);
        container.getChildren().addAll(catLabel, catText, catAccept);
        catAccept.setOnAction( event -> accepted());
    }

    private void accepted() {
        if(catText.getLength() == 0){
            catLabel.setText("Cannot be null");
        }else{
            Category c = new Category();
            categories.add(c);
            c.setName(catText.getText());
            container.getChildren().removeAll(catAccept,catLabel,catText);
            System.out.println(catText.getText());
        }
    }

    private void editProducts() throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("../Bartinator.View/editProducts.fxml"));
//        Main.theStage.setScene(new Scene(root, 800, 480));
    }


}
