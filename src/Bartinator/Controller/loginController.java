package Bartinator.Controller;


import Bartinator.Main;
import Bartinator.Model.User;
import Bartinator.Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
@Deprecated
public class loginController {

    public Button adminLoginBtn;
    public Label feedackField;
    public TextField usernameField;
    public PasswordField passwordField;

    private static User currentUser;
    final private static User debugUser = new User.UserBuilder().withAdminAccess(true).withName("addie").withUsername("addie").build();



    public void handleAdminLogin(ActionEvent actionEvent) {
        //TODO: Extract nedestående til metode for begge login typer
        User user = verifyLogin(usernameField.getText(), passwordField.getText());
        if(user != null){
            System.out.println("login good");
            currentUser = user;
            if(user.isAdminAccess()){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../View/editor.fxml"));
                    Main.theStage.setScene(new Scene(root, 800, 480));
                } catch (IOException e) {
                    System.err.println("Failed to load editor window!");
                    feedackField.setText("login was successful");
                    e.printStackTrace();
                }
            } else {
                //TODO: bartender login
                feedackField.setText("Bartender login was successful");
            }

        } else {
            System.out.println("login bad");
            feedackField.setText("Incorrect username or password");
        }
    }

    private User verifyLogin(String username, String password) {
		if(username.length() == 0) return debugUser; //TODO: Lav en exception når folk prøver at logge ind uden brugernavn
        return Database.verifyLogin(username, password);
    }

    public void handleBarLogin(ActionEvent actionEvent) {

        //TODO: Åben Bartender Scenen
    }
}
