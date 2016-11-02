package Bartinator.Controller;

import Bartinator.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class bartenderController implements Initializable{

    @FXML public ListView cartView;
    @FXML public GridPane btnGrid;


    @Override public void initialize(URL location, ResourceBundle resources) {


    }


    public void handleCreateConsumer (ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/createConsumerView.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load createConsumer window!");
            e.printStackTrace();
        }
    }
    public void handleCheckOut(ActionEvent actionEvent) {



    }
    public void handleDelete(ActionEvent actionEvent) {
    }

}
