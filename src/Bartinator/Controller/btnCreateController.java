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
    ArrayList<TextField> txtNameFields = new ArrayList<>();
    ArrayList<TextField> txtPriceField = new ArrayList<>();
    ArrayList<TextField> txtCategoriesField = new ArrayList<>();
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
                value = Float.parseFloat(txtPriceField.get(editorController.amountOfProducts).getText());
            }catch (NumberFormatException ne){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            }

            editorController.products.get(editorController.amountOfProducts).setPrice(value);
            editorController.products.get(editorController.amountOfProducts).setName(txtNameFields.get(editorController.amountOfProducts).getText());
            printProduct();


    }

    public void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println("test");
            System.out.println("Name: [" + editorController.products.get(i).getName()+"] "
                    +"Price: [" + editorController.products.get(i).getPrice() + "]");

        }
        editorController.amountOfProducts++;
    }


}
