package Bartinator;

import Bartinator.DataAccessObjects.MainDataAccessObject;
import Bartinator.DataAccessObjects.ProductDataAccessObject;
import Bartinator.Model.Category;
import Bartinator.Model.Employee;
import Bartinator.Utility.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
		//Navigator er en singleton - Bruges til at skifte scene
		Navigator.getInstance().setTheStage(primaryStage);
		Navigator.switchToLoginView();
		primaryStage.setTitle("Bartinator");
        primaryStage.show();
	}
    public static void main(String[] args) {

		//MainDataAccessObject.test();
		launch(args);

		MainDataAccessObject.stop();
	}
}