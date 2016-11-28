package Bartinator.SalesModule;

import Bartinator.DataAccessObjects.ConsumerDataAccessObject;
import Bartinator.Main;
import Bartinator.Model.Consumer;
import Bartinator.Utility.AlertBoxes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class consumerManagementController implements Initializable{

    public TextField choosenIdField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField startBalanceField;

    public TableView consumerTable;
    public TableColumn IdCol;
    public TableColumn firstNameCol;
    public TableColumn lastNameCol;
    public TableColumn startBalanceCol;

    ConsumerDataAccessObject mConsumerDAO;
    ObservableList<Consumer> data;

    @Override public void initialize(URL location, ResourceBundle resources) {

        mConsumerDAO = ConsumerDataAccessObject.getInstance();
        updateConsumerTableView();
    }


    public void handleSaveConsumer(ActionEvent actionEvent) {
        Consumer consumer = null;
        if (startBalanceCol.getText().equals("")) {
            consumer = new Consumer(firstNameField.getText(),
                                    lastNameField.getText());
        } else {
            consumer = new Consumer(firstNameField.getText(),
                                    lastNameField.getText(),
                                    Integer.parseInt(startBalanceField.getText()));
        }
        mConsumerDAO.saveConsumer(consumer);
        updateConsumerTableView();
    }
    public void handleUpdateConsumer(ActionEvent actionEvent) {
        if(choosenIdField.getText().equals("")){
            AlertBoxes.displayErrorBox("No Consumer selected", "Please select a Consumer by ID, to update balance");
        } else {
            Consumer consumer = mConsumerDAO.fetchConsumerById(Integer.parseInt(choosenIdField.getText()));

            TextInputDialog balancePrompter = new TextInputDialog();
            balancePrompter.setTitle("Insert Currency");
            double presentedBalance = consumer.getBalance()/100;
            balancePrompter.setHeaderText("Current Balance: " + presentedBalance + " DKK");
            balancePrompter.setContentText("Please enter the amount to be inserted");
            Optional<String> result = balancePrompter.showAndWait();
            if(result.isPresent()){
                int storableBalance = (int)(Double.parseDouble(result.get())*100);
                consumer.insertToBalance(storableBalance);
                mConsumerDAO.updateConsumer(consumer);
            } else {
                AlertBoxes.displayInformationBox("No Changes", "No input was detected. There haven't been made any changes to the consumers balance");
            }
        }
    }
    public void handleDeleteConsumer(ActionEvent actionEvent) {
        if(mConsumerDAO.consumerExists(Integer.parseInt(choosenIdField.getText()))){
            mConsumerDAO.deleteConsumer(Integer.parseInt(choosenIdField.getText()));
        } else {
            AlertBoxes.displayErrorBox("Consumer doesn't exist", "The ID entered doesn't match any consumer in the database!");
        }
    }
    public void handleExit(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/bartenderView2.fxml"));
            Main.theStage.setScene(new Scene(root, 800, 480));
        } catch (IOException e) {
            System.err.println("Failed to load bartenderView window!");
            e.printStackTrace();
        }
    }

    private void updateConsumerTableView() {
    }
}
