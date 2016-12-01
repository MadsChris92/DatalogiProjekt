package Bartinator.Utility;

import Bartinator.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by martin on 12/1/16.
 */
public class Navigator {
	private static final Navigator instance = new Navigator();
	private Stage theStage;

	public void setTheStage(Stage stage) {
		theStage = stage;
	}

	public static Navigator getInstance(){
		return instance;
	}

	private void switchToView(String view){
		try {
			Parent root = FXMLLoader.load(getClass().getResource(view));
			theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.printf("Failed to load %s!%n", view);
			e.printStackTrace();
		}
	}

	public static void switchToAdminView(){
		instance.switchToView("/View/adminMenuView.fxml");
	}

	public static void switchToLoginView() {
		instance.switchToView("View/loginView.fxml");
	}

	public static void switchToProductManagementView() {
		instance.switchToView("View/editProductView.fxml");
	}


	public static void switchToEmployeeManagementView() {
		instance.switchToView("View/employeeManagementView.fxml");
	}

	public static void switchToSalesView() {
		instance.switchToView("/View/salesView.fxml");
	}
}
