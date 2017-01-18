package Bartinator.EmployeeModule;

import Bartinator.DataAccessObjects.EmployeeDataAccessObject;
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
        if(accessGranted) { //Gem den aktive bruger i Employee DAO, hvis der er adgang.
            EmployeeDataAccessObject.getInstance().setActiveEmployee(mLatestCheckedEmployee);
        }
        return accessGranted;
    }

    private boolean verifyBartenderLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        Employee employee = EmployeeDataAccessObject.getInstance().verifyUser(username, password);

        if(employee != null){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }

    private boolean verifyAdminLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        Employee employee = EmployeeDataAccessObject.getInstance().verifyUser(username, password);


        if(employee != null && employee.hasAdminAccess()){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }
}
