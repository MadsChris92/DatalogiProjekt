package Bartinator.ProductModule;

import Bartinator.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;


public class EditorController2 {
    public Button categoryEdit;
    public Button categoryAdd;
    public HBox bottomContainer;
    public Button productEdit;


    @FXML
    public void initialize(){
        editorModel em = new editorModel();
        em.setCatAdd(categoryAdd);
        em.setProductEdit(productEdit);
        em.setCatEdit(categoryEdit);
        em.setContainer(bottomContainer);
        productEdit.setOnAction(event -> {
            try {
                editProducts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void editProducts() throws IOException {
        System.out.println("test");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/editProductView.fxml"));
        Main.theStage.setScene(new Scene(root, 800, 480));
    }
}
