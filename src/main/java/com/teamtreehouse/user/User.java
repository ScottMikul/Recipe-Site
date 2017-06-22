package com.teamtreehouse.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtreehouse.core.BaseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;

/**
 * Created by scott on 6/19/2017.
 */
@Entity
public class User extends BaseEntity {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private String name;
    @JsonIgnore
    private String [] roles;
    @JsonIgnore
    private String password;

    protected User(){
        super();
    }

    public User(String name, String[] roles, String password) {
        this();
        this.name = name;
        this.roles = roles;
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
