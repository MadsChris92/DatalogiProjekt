package Bartinator.Controller;

import Bartinator.Other.Product;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class btnCreateController {

    public Label txt;
    public Button btnDescription;
    public VBox descripContainer;
    String txtName;
    int count = 0;

    public void addDesciption(MouseEvent mouseEvent) {
        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database
        Product p = new Product();
        Label l = new Label("_________________________________");
        TextField t = new TextField("Name!");
        txtName = t.getText();
        Button b = new Button("✓");
        b.setOnAction(event -> applyDescription());
        Label l1 = new Label("");
        HBox container = new HBox();
        editorController.products.add(p);

        descripContainer.getChildren().addAll(l, container, t);
        container.getChildren().addAll(b);
    }

    private void applyDescription() {
        editorController.products.get(count).setName(txtName);
        count++;
        printProduct();
    }

    public void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println(editorController.products.get(i).getName());

        }
    }
}
