package com.teamtreehouse.index;

/**
 * Created by scott on 3/4/2018.
 */
public class SignInWrapper {
    String username;
    String password;

    public SignInWrapper() {
    }

    public SignInWrapper(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
