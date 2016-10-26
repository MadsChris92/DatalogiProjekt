package Bartinator.Controller;

import Bartinator.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
