package Bartinator.Controller;

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

    public void addDesciption(MouseEvent mouseEvent) {

        // TODO: Label tekst skal kunne defineres af brugeren + information skal sendes til database
        Label l = new Label("_________________________________");
        TextField t = new TextField("Enter description here!");
        Button b = new Button("✓");
        Button b1 = new Button("+");
        Label l1 = new Label("");
        HBox container = new HBox();

        descripContainer.getChildren().addAll(l,container,t);
        container.getChildren().addAll(b1,b);
        b1.setOnAction(event -> addParameter());
    }

    private void addParameter() {
        TextField t1 = new TextField("Enter description here!");
        descripContainer.getChildren().add(t1);
    }
}
