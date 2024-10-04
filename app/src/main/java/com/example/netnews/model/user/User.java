package com.example.netnews.model.user;

public class User {
    private String user_name;
    private String user_email;

    public User(String user_name, String user_email) {
        this.user_name = user_name;
        this.user_email = user_email;
    }

    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
