package Bartinator.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class btnCreateController {

    public Button btn;
    public Label txt;
    public Button btnDescription;
    public VBox descripContainer;


    public void applyChanges(MouseEvent mouseEvent) {
        System.out.println("test");
        txt.setText("dasdas");
    }

    public void addDesciption(MouseEvent mouseEvent) {
        Label l = new Label("Test");
        TextField t = new TextField("Enter description here!");
        Button b = new Button("Add");
        Label l1 = new Label("");
        descripContainer.getChildren().addAll(l,t,b,l1);
    }
}
