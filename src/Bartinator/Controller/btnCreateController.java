package Bartinator.Controller;

import Bartinator.Model.Product;
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
    public Button btnDescription;
    public VBox descripContainer;
    ArrayList<TextField> txtNameFields = new ArrayList<>();
    ArrayList<TextField> txtPriceField = new ArrayList<>();
    int count = 0;

    public void addDesciption(MouseEvent mouseEvent) {
        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database
        Product p = new Product();
        Label l = new Label("_________________________________");

        TextField t = new TextField("Name!");
        txtNameFields.add(t);

        TextField t1 = new TextField("Price!");
        txtPriceField.add(t1);

        Button b = new Button("✓");
        b.setOnAction(event -> applyDescription());


        HBox container = new HBox();
        editorController.products.add(p);
        descripContainer.getChildren().addAll(l, container, t, t1);
        container.getChildren().addAll(b);
    }

    private void applyDescription() {
        if(count >= editorController.products.size()){
            count--;
        }else {
            Float value = Float.parseFloat(txtPriceField.get(count).getText());
            editorController.products.get(count).setPrice(value);
            editorController.products.get(count).setName(txtNameFields.get(count).getText());
            printProduct();
        }
        count++;
    }

    public void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println(editorController.products.get(i).getName() + editorController.products.get(i).getPrice());

        }
    }
}
