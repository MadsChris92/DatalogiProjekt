package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.UserDataAccessObject;
import Bartinator.Model.Employee;

import java.io.IOException;

public class LoginVerifier {

    private Employee mLatestCheckedEmployee;

    public boolean verifyLogin(String username, String password, boolean adminSelected) throws IOException{

        boolean accessGranted;

        if(adminSelected){
			accessGranted = verifyAdminLogin(username, password);
        } else {
			accessGranted = verifyBartenderLogin(username, password);
        }
        if(accessGranted) {
            UserDataAccessObject.getInstance().setActiveEmployee(mLatestCheckedEmployee);
        }
        return accessGranted;
    }

    private boolean verifyBartenderLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        Employee employee = UserDataAccessObject.getInstance().verifyUser(username, password);

        if(employee != null){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }

    private boolean verifyAdminLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        Employee employee = UserDataAccessObject.getInstance().verifyUser(username, password);


        if(employee != null && employee.isAdminAccess()){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }
}
