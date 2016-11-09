package Bartinator.Controller;

import Bartinator.DataAccessObjects.ConsumerDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Consumer;
import Bartinator.Utility.AlertBoxes;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class CreateConsumerController {
	public TextField firstNameField;
	public TextField lastNameField;
	public TextField studentIdField;
	public TextField balanceField;

	// navn(string), username(string), password(int pga. hash), balance(double),  studentid(int). note til casper


	public void handleCreateConsumer() {
		boolean success;
		if (validateField()) {
			Consumer consumer;
			int studentIdAsInt = Integer.parseInt(studentIdField.getText());
			if(balanceField.getCharacters().length() == 0) {
				consumer = new Consumer(studentIdAsInt,firstNameField.getText(),lastNameField.getText());
			} else {
				double balanceAsDouble = Double.parseDouble(balanceField.getText());
				consumer = new Consumer(studentIdAsInt,firstNameField.getText(),lastNameField.getText(),balanceAsDouble);
			}
			success = ConsumerDataAccessObject.getInstance().saveConsumer(consumer);
			if (!success){
				AlertBoxes.displayErrorBox("Consumer creation failed!", "Something went wrong while storing new consumer");
			}
		} else {
			success = false;
			AlertBoxes.displayErrorBox("Consumer creation failed!", "Fields are filled out incorrectly");
		}
		if (success) {
			returnToBartenderview();
		}
	}

	private boolean validateField() {
		boolean validation = true;
		//TODO: Validér lortet!!!

		return validation;
	}


	public void handleCancel(ActionEvent actionEvent) {
		returnToBartenderview();
	}

	private void returnToBartenderview(){
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/bartenderView2.fxml"));
			Main.theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.println("Failed to load bartender window!");
			e.printStackTrace();
		}
	}
}
