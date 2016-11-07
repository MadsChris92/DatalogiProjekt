package Bartinator.Controller;

import Bartinator.Main;
import Bartinator.Utility.LoginVerifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 07-11-2016.
 */
public class testController {

	public Pane contentPane;
	public HBox serverProgressCover;

	@FXML
	public void initialize() {


	}

	public void gotoEditProduct(ActionEvent actionEvent) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/editProductView.fxml"));
			contentPane.getChildren().clear();
			contentPane.getChildren().add(root);
		} catch (IOException e) {
			System.err.println("Failed to load loginView window!");
			e.printStackTrace();
		}
	}

	public void gotoManageUser(ActionEvent actionEvent) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/userManageMenu.fxml"));
			contentPane.getChildren().clear();
			contentPane.getChildren().add(root);
		} catch (IOException e) {
			System.err.println("Failed to load loginView window!");
			e.printStackTrace();
		}
	}

	public void logOut(ActionEvent actionEvent) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
			//contentPane.getChildren().add(root);
			Main.theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.println("Failed to load loginView window!");
			e.printStackTrace();
		}
	}
}
