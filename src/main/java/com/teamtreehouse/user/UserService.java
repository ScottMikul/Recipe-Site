package com.teamtreehouse.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by scott on 2/27/2018.
 */
@Service
public class UserService {
    @Autowired
    UserRepository users;

    public void RegisterUser(User user){
        users.save(user);
    }

    public User findUser(User user){
        return users.findByName(user.getName());
    }
}
