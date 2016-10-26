package Bartinator.Controller;

import Bartinator.Database.Database;
import Bartinator.Main;
import Bartinator.Model.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class bartenderController {

    public void handleConsumerBtn (ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/createConsumerView.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load createConsumer window!");
            e.printStackTrace();
        }
    }

}
