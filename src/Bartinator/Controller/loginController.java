package Bartinator.Controller;


import Bartinator.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class loginController {

    public Button loginBtn;


    public void handleLogin(ActionEvent actionEvent) {
        System.out.println("o/");
        Parent root1 = null;
        try {
            root1 = FXMLLoader.load(getClass().getResource("../View/editor.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Main.theStage.setScene(new Scene(root1, 700, 600));
    }
}
