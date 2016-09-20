package Bartinator.Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class btnCreateController {

    public Button btn;
    public Label txt;


    public void applyChanges(MouseEvent mouseEvent) {
        System.out.println("test");
        txt.setText("dasdas");
    }
}
