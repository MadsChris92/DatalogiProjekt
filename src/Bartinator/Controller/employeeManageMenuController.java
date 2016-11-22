package Bartinator.Controller;

import Bartinator.DataAccessObjects.UserDataAccessObject;
import Bartinator.Model.User;
import Bartinator.Utility.AlertBoxes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class employeeManageMenuController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField nameField;
    public CheckBox adminCheckBox;

    public TableView employeeTable;
	public TableColumn IdCol;
	public TableColumn nameCol;
    public TableColumn usernameCol;
	public TableColumn passwordCol;
	public TableColumn adminCol;

	UserDataAccessObject mUserDAO;
    ObservableList<User> data;


    @Override public void initialize(URL location, ResourceBundle resources) {

        mUserDAO = UserDataAccessObject.getInstance();
		updateUserTableView();

    }

    public void handleSaveUser(ActionEvent actionEvent) {
		User user = new User(nameField.getText(),
							 usernameField.getText(),
							 passwordField.getText().hashCode(),
							 adminCheckBox.isSelected());
		mUserDAO.saveUser(user);
		updateUserTableView();
    }
	private void updateUserTableView() {
		try {
			//TODO: Fetcher metode returnere null???
			data = FXCollections.observableList(mUserDAO.fetchAllUsers());
		} catch (IOException e) {
			e.printStackTrace();
			AlertBoxes.displayErrorBox("Fetching Users", e.getMessage());
		}

		IdCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("mId"));
		nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("mName"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("mUsername"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("mPassword"));
		adminCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("mAdminAccess"));


		// nameCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>(User.isA));

		employeeTable.setItems(data);
	}

	public void handleDelete(ActionEvent actionEvent) {
		if(mUserDAO.userExists(usernameField.getText())){
			mUserDAO.deleteUser(usernameField.getText());
		} else {
			AlertBoxes.displayErrorBox("User doesn't exist", "The username entered doesn't match any user in the database!");
		}
    }

}
