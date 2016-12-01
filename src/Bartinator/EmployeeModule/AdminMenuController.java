package Bartinator.EmployeeModule;

import Bartinator.Utility.AlertBoxes;
import Bartinator.Utility.Navigator;
import javafx.event.ActionEvent;


public class AdminMenuController {

    public void handleProductManagementBtn(ActionEvent actionEvent) {
        Navigator.switchToProductManagementView();
    }
    public void handleEmployeeManagementBtn(ActionEvent actionEvent) {
        Navigator.switchToEmployeeManagementView();
    }
    public void handleStockManagementBtn(ActionEvent actionEvent) {
        AlertBoxes.displayErrorBox("Under Construction", "Sorry, this function is not yet available!");
    }
    public void handleLogOut(ActionEvent actionEvent) {
        if (AlertBoxes.displayConfirmationBox("Logging out!", "Are you sure, you want to log out?")){
            Navigator.switchToLoginView();
        }

    }
}
