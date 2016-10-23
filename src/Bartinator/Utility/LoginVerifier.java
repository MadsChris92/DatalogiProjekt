package Bartinator.Utility;


public class LoginVerifier {

    public boolean verifyLogin(String username, String password, boolean adminSelected) {

        boolean accessGranted = false;

        if(adminSelected){
           accessGranted = verifyAdminLogin(username, password);
        } else {
            accessGranted = verifyBartenderLogin(username, password);
        }

        return accessGranted;
    }
    private boolean verifyBartenderLogin(String username, String password) {

        boolean accessGranted = false;

        if(true){
            accessGranted = true;
            //TODO: Test for Bartender login
        }
        return accessGranted;
    }
    private boolean verifyAdminLogin(String username, String password) {

        boolean accessGranted = false;

        if(true){
            accessGranted = true;
            //TODO: Test for Admin login
        }
        return accessGranted;
    }
}
