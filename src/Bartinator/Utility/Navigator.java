package Bartinator.Utility;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * Navigate between the different views
 */
public class Navigator {
	//Navigator er en singleton - Bruges til at skifte scene
	private static final Navigator instance = new Navigator();
	private Window mTheStage;

	//Denne static metode giver
	public static Navigator getInstance(){
		return instance;
	}
	private Stage theStage;

	private Navigator(){}

	public void setTheStage(Stage stage) {
		theStage = stage;
	}

	//Universel switchToView metode. Bruges af de specifikke
	private void switchToView(String view){
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(view));
			theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.printf("Failed to load %s!%n", view);
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	//Static metoder er overflødigt i singletons, fordi man kender instance.
	public static void switchToAdminView(){
		instance.switchToView("View/adminMenuView.fxml");
	}

	public static void switchToLoginView() {
		EmployeeDataAccessObject.getInstance().setActiveEmployee(null); // logging out, effectively
		instance.switchToView("View/loginView.fxml");
	}

	public static void switchToProductManagementView() {
		instance.switchToView("View/editProductView.fxml");
	}

	public static void switchToEmployeeManagementView() {
		instance.switchToView("View/employeeManagementView.fxml");
	}

	public static void switchToSalesView() {
		instance.switchToView("View/salesView.fxml");
	}

	public static void switchToOrderManagementView() {
		instance.switchToView("View/orderManageView.fxml");
	}

	public Window getTheStage() {
		return mTheStage;
	}
}
