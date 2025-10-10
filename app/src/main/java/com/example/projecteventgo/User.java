package com.example.projecteventgo;

public class User {
    private String userid;
    private String username;
    private String email;
    private String password;
    public User() {
    }

    public User(String uid, String email,String username, String password) {
        this.userid = uid;
        this.email = email;
        this.password = password;
        this.username = username;

    }

    public String getUid() {
        return userid;
    }

    public void setUid(String uid) {
        this.userid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
