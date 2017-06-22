package com.teamtreehouse.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by scott on 6/19/2017.
 */
@Component
public class DetailsService implements UserDetailsService{
    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = users.findByName(name);
        if(user==null){
            throw new UsernameNotFoundException("Could not find username"+ name);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
    }
}
