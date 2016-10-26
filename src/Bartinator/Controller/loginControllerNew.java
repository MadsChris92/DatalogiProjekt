package Bartinator.Controller;


import Bartinator.Main;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.LoginVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginControllerNew implements Initializable {

    LoginVerifier mVerifier;

    @FXML TextField mUsernameField;
    @FXML TextField mPasswordField;
    @FXML CheckBox mAdminCheckBox;

    @Override public void initialize(URL location, ResourceBundle resources) {

        mVerifier = new LoginVerifier();
        mAdminCheckBox.setSelected(true);
    }

    public void handleAdminLogin(ActionEvent actionEvent) {
        boolean accessGranted = mVerifier.verifyLogin(mUsernameField.getText(),mPasswordField.getText(),mAdminCheckBox.isSelected());

        if(accessGranted && mAdminCheckBox.isSelected()){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../View/adminMainMenu.fxml"));
                Main.theStage.setScene(new Scene(root, 800, 480));
            } catch (IOException e) {
                System.err.println("Failed to load adminMainMenu window!");
                e.printStackTrace();
            }
        } else if(accessGranted && !mAdminCheckBox.isSelected()){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../View/bartenderView.fxml"));
                Main.theStage.setScene(new Scene(root, 800, 480));
            } catch (IOException e) {
                System.err.println("Failed to load bartenderView window!");
                e.printStackTrace();
            }
        } else if(!accessGranted){
            AlertBoxes.displayErrorBox("Login failed!", "Sorry, the login information you entered, doesn't give access to the requested system");
        }
    }

}
