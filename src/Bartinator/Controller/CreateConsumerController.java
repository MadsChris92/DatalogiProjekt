package Bartinator.Controller;

import Bartinator.DataAccessObjects.MainDataAccessObject;
import Bartinator.Model.Consumer;
import javafx.scene.control.TextField;


public class CreateConsumerController {
    public TextField textFieldName;
    public TextField textFieldUserName;
    public TextField textFieldPassword;
    public TextField textFieldBalance;
    public TextField textFieldStudentID;
    // navn(string), username(string), password(int pga. hash), balance(double),  studentid(int). note til casper

    public void handlePrintTest(){
        System.out.println(textFieldUserName.getText());
    }

    public void handleCreateConsumer() {
        try {
            Consumer testConsumer = new Consumer(Integer.parseInt(textFieldStudentID.getText()),textFieldName.getText());
        }catch (Exception e) {
            System.err.println("Failed to load create consumer!");
            e.printStackTrace();
        }
    }


}
