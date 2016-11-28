package Bartinator.Controller;

import Bartinator.Main;
import Bartinator.Utility.AlertBoxes;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;


public class adminMainMenuController {

    public void handleProductManagementBtn(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../View/editorView.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load editor window!");
            e.printStackTrace();
        }
    }
    public void handleEmployeeManagementBtn(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../View/userManageMenu.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load userManageMenu window!");
            e.printStackTrace();
        }
    }
    public void handleStockManagementBtn(ActionEvent actionEvent) {
        AlertBoxes.displayErrorBox("Under Construction", "Sorry, this function is not yet available!");
    }
    public void handleLogOut(ActionEvent actionEvent) {
        if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")){
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("../View/loginView.fxml"));
                Main.theStage.setScene(new Scene(root, 800, 480));
            } catch (IOException e) {
                System.err.println("Failed to load loginView window!");
                e.printStackTrace();
            }
        }

    }
}
