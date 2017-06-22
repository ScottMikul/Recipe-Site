package com.teamtreehouse.control;

import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by scott on 6/20/2017.
 */
@Component
@RepositoryEventHandler(Control.class)
public class ControlEventHandler {

    private final UserRepository repo;

    @Autowired
    public ControlEventHandler(UserRepository repo) {
        this.repo = repo;
    }

    @HandleBeforeSave
    @HandleBeforeCreate
    public void setLastModifiedBy(Control control){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repo.findByName(username);
        control.setLastModifiedBy(user);
    }
}
