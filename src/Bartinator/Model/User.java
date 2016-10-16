package Bartinator.Model;

import javax.persistence.*;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private int password;
    @Column
    private boolean adminAccess;

    public User() {}

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password=" + password +
                '}';
    }

    public static class UserBuilder{
        private User user;
        public UserBuilder(){
            user = new User();
        }
        public UserBuilder withName(String name){
            user.name = name;
            return this;
        }
        public UserBuilder withUsername(String username){
            user.username = username;
            return this;
        }
        public UserBuilder withPassword(int password){
            user.password = password;
            return this;
        }
        public UserBuilder withAdminAccess(boolean adminAccess){
            user.adminAccess = adminAccess;
            return this;
        }
        public User build(){
            return user;
        }
    }
}
