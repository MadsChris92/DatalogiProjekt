package Bartinator;

import Bartinator.Other.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {



    public static Stage theStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/loginView.fxml"));
        theStage.setTitle("Bartinator");
        theStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
		System.out.println("friend".hashCode());
	}
    public static void main(String[] args) {

        //launch(args);

    }

    public static void setPrimaryStage(Scene s){
        theStage.setScene(s);
    }

}