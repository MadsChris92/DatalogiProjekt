package Bartinator.Controller;

import Bartinator.Model.Product;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class btnCreateController {

    public Label txt;
    float value;
    public Button btnDescription;
    public VBox descripContainer;
    private ArrayList<TextField> txtNameFields = new ArrayList<>();
    private ArrayList<TextField> txtPriceField = new ArrayList<>();
    ArrayList<TextField> txtCategoriesField = new ArrayList<>();
    boolean mistake;
    int count = 0;

    public void addDescrip(ActionEvent actionEvent) {
        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database

        TextField t = new TextField("Name!");
        txtNameFields.add(t);

        TextField t1 = new TextField("Price!");
        txtPriceField.add(t1);

        Button b = new Button("✓");
        b.setOnAction(event -> applyDescription());


        HBox container = new HBox();
        descripContainer.getChildren().addAll(t, t1,container);
        container.getChildren().addAll(b);
    }

    private void applyDescription() {
            try {
                System.out.println("test1");
                value = Float.parseFloat(txtPriceField.get(editorController.currentItem).getText());
                System.out.println("test");
            }catch (NumberFormatException ne){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Price must be a number");
                alert.showAndWait();
                mistake = true;
            }

            if(!mistake){
                editorController.products.get(editorController.currentItem).setPrice(value);
                editorController.products.get(editorController.currentItem).setName(txtNameFields.get(editorController.currentItem).getText());
 //               printProduct();
                editorController.products.get(editorController.currentItem).getB().setText("Name: [" + editorController.products.get(editorController.currentItem).getName()+"] "
                        +"\nPrice: [" + editorController.products.get(editorController.currentItem).getPrice() + "]");
                mistake = false;
            }
            else{
                mistake = false;
            }



    }
/*
    public void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println("test");
            System.out.println("Name: [" + editorController.products.get(i).getName()+"] "
                    +"Price: [" + editorController.products.get(i).getPrice() + "]");
        }
    }
    */
}
