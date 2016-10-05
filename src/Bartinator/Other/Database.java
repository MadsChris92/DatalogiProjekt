package Bartinator.Other;


import Bartinator.Model.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    private String endpoint = "datalogiprojektruc2016-bartinator.chcbu6lph5q9.eu-central-1.rds.amazonaws.com";
    private MysqlDataSource dataSource;

    /// setupBartinator.users
    // http://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
    public void setup(String user, String password) {
        dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(endpoint);
    }

    public boolean verifyLogin(int userid, String password) {
        User user = getUser(userid);
        if (user.password == password.hashCode()) {
            System.out.println(user.name + " has logged in");
            return true;
        } else {
            System.out.println("incorrect password");
            return false;
        }
    }

    public boolean verifyLogin(String username, String password) {
        User user = getUser(username);
        if (user.password == password.hashCode()) {
            System.out.println(user.name + " has logged in");
            return true;
        } else {
            System.out.println("incorrect password");
            return false;
        }
    }

    private ResultSet queryDatabase(String query) {
        // TODO: 9/28/16 skift til hibernate måden at gøre ting på
        ResultSet rs = null;
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private User getUser(String username) {
        User user = new User();
        try {
            ResultSet rs = queryDatabase(String.format("SELECT * FROM Bartinator.users WHERE username='%s'", username));
            while (rs.next()) {
                user.username = username;
                user.name = rs.getString("name");
                user.userid = rs.getInt("id");
                user.password = rs.getInt("password");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User getUser(int userid) {
        User user = new User();
        try {
            ResultSet rs = queryDatabase(String.format("SELECT * FROM Bartinator.users WHERE id=%d", userid));
            while (rs.next()) {
                user.username = rs.getString("username");
                user.name = rs.getString("name");
                user.userid = userid;
                user.password = rs.getInt("password");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void test() {
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM Bartinator.users");

            while (rs.next()) {
                System.out.println(rs.getString("name"));
                if (rs.getInt("id") == 55069 && rs.getInt("password") == "hello world".hashCode()) {
                    System.out.println(rs.getString("name") + " has logged in");
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}