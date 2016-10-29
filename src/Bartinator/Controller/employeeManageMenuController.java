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
    public TableColumn nameCol;
    public TableColumn usernameCol;




    UserDataAccessObject mUserDAO;
    ObservableList<User> data;


    @Override public void initialize(URL location, ResourceBundle resources) {

        mUserDAO = UserDataAccessObject.getInstance();
        try {
            //TODO: Fetcher metode returnere null???
            data = FXCollections.observableList(mUserDAO.fetchAllUsers());
        } catch (IOException e) {
            e.printStackTrace();
            AlertBoxes.displayErrorBox("Fetching Users", e.getMessage());
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
       // nameCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>(User.isA));

        employeeTable.setItems(data);
    }


    public void handleSaveUser(ActionEvent actionEvent) {
        if (adminCheckBox.isSelected()){
            //TODO Lav ADMIN OG GEM
        } else if(!adminCheckBox.isSelected()){
            //TODO Lav Bartender og Gem
        }


    }
    public void handleDelete(ActionEvent actionEvent) {

    }

}
