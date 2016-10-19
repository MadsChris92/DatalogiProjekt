package Bartinator.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class btnCreateController {

    public Label txt;
    public BorderPane mainContainer;
    private float value;
    public Button btnDescription;
    public VBox descripContainer;
    private TextField priceField, nameField;
    private boolean mistake, firstTimePressed = true;
    public static int productID;

    public void addDescrip(ActionEvent actionEvent) {
        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database
        if(firstTimePressed){
            nameField = new TextField("Name!");
            priceField = new TextField("Price!");
            Button b = new Button("✓");
            b.setMinSize(100,20);
            b.setOnAction(event -> applyDescription());
            descripContainer.getChildren().addAll(nameField, priceField);
            mainContainer.setBottom(b);
            firstTimePressed = false;
        }else{
            TextField extraDescription = new TextField();
            descripContainer.getChildren().add(extraDescription);
        }
    }

    private void showInfo(){
        nameField.setText(editorController.products.get(productID).getName());
    }

    private void applyDescription() {
        int currentItem = editorController.products.size() - 1;
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

                editorController.btnList.get(currentItem).setText("Name: [" + editorController.products.get(currentItem).getName()+"] "
                        +"\nPrice: [" + editorController.products.get(currentItem).getPrice() + "]");
                mistake = false;
            }
            else{
                mistake = false;
            }
    }

    private void printProduct(){
        for (int i = 0; i < editorController.products.size(); i++) {
            System.out.println("Name: [" + editorController.products.get(i).getName()+"] "
                    +"Price: [" + editorController.products.get(i).getPrice() + "]");
        }
    }
}
