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
    // navn(string), username(string), password(int pga. hash), balance(double),  studentid(int). note til casper
/*
    public void handlePrintTest(){
        System.out.println(textFieldUserName.getText());
    }

    public void handleCreateConsumer() {
        Consumer testConsumer = new Consumer( );
        testConsumer.setName("Hans");
        testConsumer.setUsername("hans");
        testConsumer.setPassword("hund".hashCode());
        testConsumer.setBalance(100.00);
        Database.save(testConsumer);

    }
*/
}
