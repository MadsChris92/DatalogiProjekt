package Bartinator.Utility;

import Bartinator.DataAccessObjects.UserDataAccessObject;
import Bartinator.Model.User;

import java.io.IOException;

public class LoginVerifier {

    private User latestCheckedUser;

    public boolean verifyLogin(String username, String password, boolean adminSelected) throws IOException{

        boolean accessGranted;

        if(adminSelected){
			accessGranted = verifyAdminLogin(username, password);
        } else {
			accessGranted = verifyBartenderLogin(username, password);
        }
        if(accessGranted) {
            UserDataAccessObject.getInstance().setActiveUser(latestCheckedUser);
        }
        return accessGranted;
    }

    private boolean verifyBartenderLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        User user = UserDataAccessObject.getInstance().verifyUser(username, password);


        if(user != null && (user.isAdmin() || user.isBartender())){
            accessGranted = true;
            latestCheckedUser = user;
        }
        return accessGranted;
    }

    private boolean verifyAdminLogin(String username, String password) throws IOException {

        boolean accessGranted = false;

        User user = UserDataAccessObject.getInstance().verifyUser(username, password);


        if(user != null && user.isAdmin()){
            accessGranted = true;
            latestCheckedUser = user;
        }
        return accessGranted;
    }
}
