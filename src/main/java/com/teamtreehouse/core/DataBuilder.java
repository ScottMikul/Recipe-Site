package com.teamtreehouse.core;


import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by scott on 6/19/2017.
 */
@Component
public class DataBuilder implements ApplicationRunner {
    @Autowired
    UserRepository users;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User("bob",new String[]{"ROLE_USER"} , "password");
        User user2 = new User("Scotty",new String[]{"ROLE_USER","ROLE_ADMIN"} , "password");
        users.save(user);
        users.save(user2);

    }
}