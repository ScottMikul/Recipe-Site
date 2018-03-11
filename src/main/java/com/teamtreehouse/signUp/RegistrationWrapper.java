package com.teamtreehouse.signUp;

/**
 * Created by scott on 2/23/2018.
 */
public class RegistrationWrapper {
    private String username;
    private String password;
    private String cPassword;

    public RegistrationWrapper() {
    }

    public RegistrationWrapper(String username, String password, String cPassword) {
        this.username = username;
        this.password = password;
        this.cPassword = cPassword;
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

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }
}
