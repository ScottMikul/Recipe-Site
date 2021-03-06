package com.teamtreehouse.core;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Created by scott on 3/20/2018.
 */
public  abstract class BaseUserController {
    public String getSessionUsername(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  String)
            return "";
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public boolean isLoggedInUSer(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  String)
            return false;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
