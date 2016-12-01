package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Employee;
import Bartinator.Utility.AlertBoxes;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeeManagementController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public CheckBox adminCheckBox;

    public TableView<EmployeeAdapter> employeeTable;
	public TableColumn<EmployeeAdapter, Integer> IdCol;
	public TableColumn<EmployeeAdapter, String> nameCol;
    public TableColumn<EmployeeAdapter, String> usernameCol;
	public TableColumn<EmployeeAdapter, String> passwordCol;
	public TableColumn<EmployeeAdapter, Boolean> adminCol;

	private EmployeeDataAccessObject mUserDAO;
    private ObservableList<EmployeeAdapter> data;


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
			EmployeeAdapter employee = data.get(employeeTable.getSelectionModel().getFocusedIndex());
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
			EmployeeAdapter employeeAdapter = employeeTable.getItems().get(employeeTable.getSelectionModel().getFocusedIndex());
			if(!(passwordField.getText().equals(""))){
				employeeAdapter.setPassword(passwordField.getText().hashCode());
			}
			if(!(nameField.getText().equals(""))){
				employeeAdapter.setName(nameField.getText());
			}
			employeeAdapter.setAdminAccess(adminCheckBox.isSelected());
			mUserDAO.updateUser(employeeAdapter.toEmployee());

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
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/adminMenuView.fxml"));
			Main.theStage.setScene(new Scene(root, 800, 480));
		} catch (IOException e) {
			System.err.println("Failed to load adminMainMenu window!");
			e.printStackTrace();
		}
	}

	private void updateUserTableView() {
		try {
			List<EmployeeAdapter> adapters = new ArrayList<>();
			for(Employee employee : mUserDAO.fetchAllUsers()){
				adapters.add(new EmployeeAdapter(employee));
			}
			data = FXCollections.observableList(adapters);
		} catch (IOException e) {
			e.printStackTrace();
			AlertBoxes.displayErrorBox("Fetching Users", e.getMessage());
		}
		System.out.println(data.toString());
		employeeTable.setItems(data);
	}
}
