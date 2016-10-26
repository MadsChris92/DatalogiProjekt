package Bartinator.DataAccessObjects;


import Bartinator.Model.User;

import java.util.List;

public class UserDataAccessObject extends MainDataAccessObject{

    public static List<User> users;



    @SuppressWarnings("unchecked")
    public static User fetchUserFromUsername(String username){
        // Henter en bruger baseret på brugernavn, hvis brugeren ikke findes returnes null
        // sql udgaven af det functionen gør er:
        // SELECT * FROM User WHERE User."username"=username;
        // hvis der er mere end én bruger med samme brugernavn, så vælger den det første resultat
        List<User> users = (List<User>) fetch(User.class, "username", username);
        User user;
        if(users.size() > 0) {
            if(users.size() > 1) System.out.println("Multiple users with the same username");
            user = users.get(0);
        }else {
            user = null; // user not found, or multiple users
        }
        return user;
    }


    public static List<User> getUsers() {
        if(users == null){

        }

        return users;
    }
    @SuppressWarnings("unchecked")
    private static List<User> fetchAllUsers(){


        return (List<User>)fetch(User.class);
    }

    public static boolean userExists(String username){
        return fetchUserFromUsername(username)!=null;
    }

    public static User verifyLogin(String username, String password) {
        User user = fetchUserFromUsername(username);
        if(user != null) {
            if (/* !password.equals("") && */ user.getPassword() == password.hashCode()) {
                return user;
            } else {
                System.out.println("incorrect password");
                return null;
            }
        } else {
            System.out.println("user not found");
            return null;
        }
    }

}
