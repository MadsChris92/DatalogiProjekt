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
    TextField priceField, nameField;
    private ArrayList<TextField> txtNameFields = new ArrayList<>();
    private ArrayList<TextField> txtPriceField = new ArrayList<>();
    ArrayList<TextField> txtCategoriesField = new ArrayList<>();
    private boolean mistake;
    int currentItem = 0;

    public void addDescrip(ActionEvent actionEvent) {
        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database

        nameField = new TextField("Name!");


        priceField = new TextField("Price!");

        Button b = new Button("✓");
        b.setOnAction(event -> applyDescription());

        HBox container = new HBox();
        descripContainer.getChildren().addAll(nameField, priceField,container);
        container.getChildren().addAll(b);
    }

    private void applyDescription() {
        currentItem = editorController.products.size() - 1;
            try {
                value = Float.parseFloat(priceField.getText());
            }catch (NumberFormatException ne){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Price must be a number");
                alert.showAndWait();
                mistake = true;
            }

            if(!mistake){
                editorController.currentProduct.setPrice(value);
                editorController.currentProduct.setName(nameField.getText());
                printProduct();

                editorController.products.get(currentItem).getB().setText("Name: [" + editorController.products.get(currentItem).getName()+"] "
                        +"\nPrice: [" + editorController.products.get(currentItem).getPrice() + "]");
                mistake = false;
            }
            else{
                mistake = false;
            }
    }

    public void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println("Name: [" + editorController.products.get(i).getName()+"] "
                    +"Price: [" + editorController.products.get(i).getPrice() + "]");
        }
    }

}
