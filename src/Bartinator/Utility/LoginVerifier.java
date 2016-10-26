package Bartinator.Utility;


import Bartinator.Database.MainDAO;
import Bartinator.Model.User;

public class LoginVerifier {

    public boolean verifyLogin(String username, String password, boolean adminSelected) {

        boolean accessGranted;

        if(adminSelected){
			accessGranted = verifyAdminLogin(username, password);
        } else {
			accessGranted = verifyBartenderLogin(username, password);
        }

        return accessGranted;
    }

    private boolean verifyBartenderLogin(String username, String password) {

        boolean accessGranted = false;

		User user = MainDAO.verifyLogin(username, password);

		if(user != null && (user.isAdmin() || user.isBartender())){
            accessGranted = true;
        }
        return accessGranted;
    }

    private boolean verifyAdminLogin(String username, String password) {

        boolean accessGranted = false;

        User user = MainDAO.verifyLogin(username, password);

        if(user != null && user.isAdmin()){
            accessGranted = true;
        }
        return accessGranted;
    }
}
