package Bartinator.Controller;

import Bartinator.Main;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class CreateConsumerController {
	public TextField textFieldName;
	public TextField textFieldUserName;
	public TextField textFieldPassword;
	public TextField textFieldBalance;
	public TextField textFieldStudentID;
	// navn(string), username(string), password(int pga. hash), balance(double),  studentid(int). note til casper


	public void handlePrintTest() {
		System.out.println(textFieldUserName.getText());
	}


	public void handleCreateConsumer() {

		if (validateField()) {
			//TODO: create consumer and store
		} else {
			//TODO: Throw a motherfucking error, bitch!
		}


	}
	private boolean validateField() {
		boolean validation = true;


		return validation;


	}


	public void handleCancel(ActionEvent actionEvent) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/bartenderView2.fxml"));
			Main.theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.println("Failed to load bartender window!");
			e.printStackTrace();
		}

	}
}


