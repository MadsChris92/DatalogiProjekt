package Bartinator.DataAccessObjects;


import Bartinator.Model.User;

import java.io.IOException;
import java.util.List;

public class UserDataAccessObject extends MainDataAccessObject{

	private static final UserDataAccessObject instance = new UserDataAccessObject();

	public static UserDataAccessObject getInstance(){
		return instance;
	}

    private List<User> mUsers;
    private User mActiveUser;

    public static User fetchUserFromUsername(String username){
        // Henter en bruger baseret på brugernavn, hvis brugeren ikke findes returnes null
        // sql udgaven af det functionen gør er:
        // SELECT * FROM User WHERE User."username"=username;
        // hvis der er mere end én bruger med samme brugernavn, så vælger den det første resultat
        List<User> users = (List<User>) fetch(User.class, "username", username);
        User user;
        if(users.size() > 0) {
            if(users.size() > 1) System.out.println("Multiple mUsers with the same username");
            user = users.get(0);
        }else {
            user = null; // user not found, or multiple mUsers
        }
        return user;
    }

    public User getActiveUser() {
        return mActiveUser;
    }
    public void setActiveUser(User activeUser) {
        mActiveUser = activeUser;
    }
    @Deprecated
    public  List<User> getUsers() throws IOException {
        if(mUsers == null){
			fetchAllUsers();
        }
        return mUsers;
    }

    @SuppressWarnings("unchecked")
	public  List<User> fetchAllUsers() throws IOException {
		List<User> users = (List<User>)fetch(User.class);
		if(users != null){
			return users;
		}
		throw new IOException("Fetching mUsers failed");
    }

    public boolean userExists(String username){
        return fetchUserFromUsername(username)!=null;
    }

    public User verifyUser(String username, String password) throws IOException {
        User user = fetchUserFromUsername(username);
        if(user != null) {
            if (/* !password.equals("") && */ user.getPassword() == password.hashCode()) {
                return user;
            } else {
                throw new IOException("The Password was incorrect");
            }
        } else {
			throw new IOException("The user was not found");
        }
    }

}
