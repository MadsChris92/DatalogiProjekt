package Bartinator.EmployeeModule;

import Bartinator.Model.Employee;

import java.io.IOException;

public class LoginVerifier {

    private final EmployeeRoster mEmployeeRoster = EmployeeRoster.getInstance();
    private ObservableEmployee mLatestCheckedEmployee;

    public boolean verifyLogin(String username, String password, boolean adminSelected) throws IOException{

        boolean accessGranted;

        if(adminSelected){
			accessGranted = verifyAdminLogin(username, password);
        } else {
			accessGranted = verifyBartenderLogin(username, password);
        }
		if(accessGranted) { //Gem den aktive bruger i EmployeeRoster, hvis der er adgang.
            EmployeeRoster.getInstance().setActiveEmployee(mLatestCheckedEmployee);
        }
        return accessGranted;
    }

    private boolean verifyBartenderLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        ObservableEmployee employee = EmployeeRoster.getInstance().verifyUser(username, password);

        if(employee != null){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }

    private boolean verifyAdminLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        ObservableEmployee employee = mEmployeeRoster.verifyUser(username, password);


        if(employee != null && employee.hasAdminAccess()){
            accessGranted = true;
            mLatestCheckedEmployee = employee;
        }
        return accessGranted;
    }
}
