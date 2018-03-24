package com.teamtreehouse.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by scott on 3/19/2018.
 */
@Service
public class UserLoggedInService{
    @Autowired
    private AuthenticationManager mAuthenticationManager;

    @Autowired
    private UserDetailsService mUserDetailsService;

    public String getLoggedInUsername(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(isLoggedIn()){
            return ((UserDetails)userDetails).getUsername();
        }
        return "";
    }

    private boolean isLoggedIn(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if(userDetails instanceof UserDetails){
            return true;
        }
        return false;
    }

    public void logIn(User user){
        //check if logged in
        if(isLoggedIn()){
            return;
        }
        else{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword());
            mAuthenticationManager.authenticate(token);
        }
    }
}
