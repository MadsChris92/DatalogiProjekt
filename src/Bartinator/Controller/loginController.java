package Bartinator.Controller;


import Bartinator.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {

    public Button adminLoginBtn;
    public Label feedackField;
    public TextField usernameField;
    public PasswordField passwordField;


    public void handleAdminLogin(ActionEvent actionEvent) {
        System.out.println("o/");

        //TODO: Extract nedestående til metode for begge login typer
        if(verifyLogin(usernameField.getText(), passwordField.getText())){
            System.out.println("login good");

            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(getClass().getResource("../View/editor.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.theStage.setScene(new Scene(root1, 700, 600));
        } else {
            System.out.println("login bad");
            feedackField.setText("incorrect username or password");
        }
    }

    private boolean verifyLogin(String username, String password) {
        System.out.println(username+ ", " +password);
        //TODO: Tjek databasen over brugere
        return !username.equals("wrong");
    }

    public void handleBarLogin(ActionEvent actionEvent) {

        //TODO: Åben Bartender Scenen
    }
}
