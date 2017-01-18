package Bartinator.EmployeeModule;

import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;


public class EmployeeManagementController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public CheckBox adminCheckBox;

    public TableView<ObservableEmployee> employeeTable;
	public TableColumn<ObservableEmployee, Integer> IdCol;
	public TableColumn<ObservableEmployee, String> nameCol;
    public TableColumn<ObservableEmployee, String> usernameCol;
	public TableColumn<ObservableEmployee, Integer> passwordCol;
	public TableColumn<ObservableEmployee, Boolean> adminCol;

	//private EmployeeDataAccessObject mUserDAO;
    private EmployeeRoster mEmployeeRoster = EmployeeRoster.getInstance();


    @Override public void initialize(URL location, ResourceBundle resources) {

		//Konfigurer cellerne i tabellen
		IdCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(param.getValue().getId()));
		nameCol.setCellValueFactory(param -> param.getValue().nameProperty());
		usernameCol.setCellValueFactory(param -> param.getValue().usernameProperty());
		passwordCol.setCellValueFactory(param -> param.getValue().passwordProperty().asObject());
		adminCol.setCellValueFactory(param -> param.getValue().adminAccessProperty());

		nameCol.setEditable(true);
		usernameCol.setEditable(true);
		passwordCol.setEditable(true);
		adminCol.setEditable(true);

		//Cellerne bliver til text felter.
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		passwordCol.setCellFactory(TextFieldTableCell.forTableColumn(mEmployeeRoster.new PasswordConverter()));
		adminCol.setCellFactory(param -> new CheckBoxTableCell<>());

		employeeTable.setEditable(true);

		//Laver en listener, som spørger om en bestemt er valgt i tabellen
		employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//Og opdatere så tekst felterne i manage user fanen.
			if(newValue != null){
				usernameField.setText(newValue.getUsername());
				passwordField.setText("");
				nameField.setText(newValue.getName());
				adminCheckBox.setSelected(newValue.hasAdminAccess());
			} else {
				usernameField.setText("");
				passwordField.setText("");
				nameField.setText("");
				adminCheckBox.setSelected(false);
			}
		});

		//Putter data i tabellen.
		employeeTable.setItems(mEmployeeRoster.getEmployees());
    }

	public void handleSaveUser(ActionEvent actionEvent) {
		String username = usernameField.getText();
		if (mEmployeeRoster.employeeExists(username)) {
			//Brugeren eksistere ikke, vil du opdatere den eksisterende?
			if (AlertBoxes.displayConfirmationBox("Employee Already exists", "The user, you are trying to create, already exists in the database. Do you wish to update the existing user instead?")) {
				ObservableEmployee observableEmployee = mEmployeeRoster.getEmployeeByUsername(username);
				if(!(passwordField.getText().equals(""))){
					observableEmployee.setPassword(passwordField.getText().hashCode());
				}
				observableEmployee.setName(nameField.getText());
				observableEmployee.setAdminAccess(adminCheckBox.isSelected());
				mEmployeeRoster.update(observableEmployee);
			}
		} else { //Hvis brugeren ikke eksistere -> lav en ny.
			String password = passwordField.getText();
			String name = nameField.getText();
			boolean adminAccess = adminCheckBox.isSelected();
			mEmployeeRoster.createEmployee(username, password, name, adminAccess);
		}
    }

	public void handleDelete(ActionEvent actionEvent) {
		String username = usernameField.getText();
		if(mEmployeeRoster.employeeExists(username)){
			mEmployeeRoster.deleteEmployee(username);
		} else {
			AlertBoxes.displayErrorBox("Employee doesn't exist", "The username entered doesn't match any user in the database!");
		}
	}


	public void handleExit(ActionEvent actionEvent) {
		Navigator.switchToAdminView();
	}
}
