package Bartinator;

import Bartinator.DataAccessObjects.MainDataAccessObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    public static Stage theStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        theStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/loginView.fxml"));
        theStage.setTitle("Bartinator");
        theStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
	}
    public static void main(String[] args) {

//		MainDataAccessObject.test();

        launch(args);

		MainDataAccessObject.stop();
	}

}