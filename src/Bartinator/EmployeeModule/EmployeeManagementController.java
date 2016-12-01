package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Model.Employee;
import Bartinator.Model.EmployeeRoster;
import Bartinator.Model.ObservableEmployee;
import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.net.URL;
import java.util.List;
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
	public TableColumn<ObservableEmployee, String> passwordCol;
	public TableColumn<ObservableEmployee, Boolean> adminCol;

	private EmployeeDataAccessObject mUserDAO;
    private ObservableList<ObservableEmployee> data;


    @Override public void initialize(URL location, ResourceBundle resources) {

        mUserDAO = EmployeeDataAccessObject.getInstance();

		IdCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(param.getValue().getId()));
		nameCol.setCellValueFactory(param -> param.getValue().nameProperty());
		usernameCol.setCellValueFactory(param -> param.getValue().usernameProperty());
		//passwordCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("password"));
		passwordCol.setCellValueFactory(param -> {
			if(param.getValue().getPassword()==0)
				return new SimpleStringProperty("no");
			else
				return new SimpleStringProperty("yes");
		});
		adminCol.setCellValueFactory(param -> param.getValue().adminAccessProperty());
		adminCol.setCellFactory(param -> new CheckBoxTableCell<>());
		adminCol.setOnEditCommit(event -> event.getRowValue().setAdminAccess(event.getNewValue()));
		employeeTable.setOnMouseClicked(event -> {
			ObservableEmployee employee = data.get(employeeTable.getSelectionModel().getFocusedIndex());
			usernameField.setText(employee.getUsername());
			passwordField.setText("");
			nameField.setText(employee.getName());
			adminCheckBox.setSelected(employee.hasAdminAccess());
		});

		updateUserTableView();
    }

    public void handleSaveUser(ActionEvent actionEvent) {
		if (mUserDAO.userExists(usernameField.getText())) {
			//AlertBoxes.displayErrorBox("Employee Already exists", "The user, you are trying to create, already exists in the database. " +
			//												  "You can choose to update the existing user instead.");
			handleUpdateUser(actionEvent);
		} else {
			Employee employee = new Employee(nameField.getText(),
					usernameField.getText(),
					passwordField.getText().hashCode(),
					adminCheckBox.isSelected());
			mUserDAO.saveUser(employee);
			updateUserTableView();
		}
    }

	public void handleUpdateUser(ActionEvent actionEvent) {
		if (mUserDAO.userExists(usernameField.getText())) {
			//Employee employee = mUserDAO.fetchUserFromUsername(usernameField.getText());
			ObservableEmployee observableEmployee = employeeTable.getItems().get(employeeTable.getSelectionModel().getFocusedIndex());
			if(!(passwordField.getText().equals(""))){
				observableEmployee.setPassword(passwordField.getText().hashCode());
			}
			if(!(nameField.getText().equals(""))){
				observableEmployee.setName(nameField.getText());
			}
			observableEmployee.setAdminAccess(adminCheckBox.isSelected());
			mUserDAO.updateUser(observableEmployee.toEmployee());

			//updateUserTableView();
		} else {
			AlertBoxes.displayErrorBox("Employee doesn't exist", "The user, you are trying to update, doesn't exist in the database. " +
															 "You can choose to create the new user by pressing the save button.");
		}
	}

	public void handleDelete(ActionEvent actionEvent) {
		if(mUserDAO.userExists(usernameField.getText())){
			mUserDAO.deleteUser(usernameField.getText());
			updateUserTableView();
		} else {
			AlertBoxes.displayErrorBox("Employee doesn't exist", "The username entered doesn't match any user in the database!");
		}
	}


	public void handleExit(ActionEvent actionEvent) {
		Navigator.switchToAdminView();
	}

	private void updateUserTableView() {
		List<ObservableEmployee> employees = EmployeeRoster.getInstance().getEmployees();
		data = FXCollections.observableList(employees);
		employeeTable.setItems(data);


		System.out.println(data.toString());
	}
}
