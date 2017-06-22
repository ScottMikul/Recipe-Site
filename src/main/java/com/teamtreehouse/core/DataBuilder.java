package com.teamtreehouse.core;

import com.teamtreehouse.control.Control;
import com.teamtreehouse.control.ControlRepository;
import com.teamtreehouse.device.Device;
import com.teamtreehouse.device.DeviceRepository;
import com.teamtreehouse.room.Room;
import com.teamtreehouse.room.RoomRepository;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by scott on 6/19/2017.
 */
@Component
public class DataBuilder implements ApplicationRunner {
    @Autowired
    DeviceRepository devices;

    @Autowired
    UserRepository users;

    @Autowired
    RoomRepository rooms;

    @Autowired
    ControlRepository controls;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User("bob",new String[]{"ROLE_USER"} , "password");
        User user2 = new User("Scotty",new String[]{"ROLE_USER","ROLE_ADMIN"} , "password");
        users.save(user);
        users.save(user2);

        Room livingRoom = new Room("Living room", 100,new ArrayList<>(),new ArrayList<>());

        Authentication auth = new UsernamePasswordAuthenticationToken(user2.getName(),user2.getPassword(), AuthorityUtils.createAuthorityList(user2.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        rooms.save(livingRoom);
        Device televisionDevice = new Device("television",livingRoom, new ArrayList<>());
        devices.save(televisionDevice);

        Control channel = new Control("Channel",televisionDevice,74, user );
        controls.save(channel);

        Control volume = new Control("Volume", televisionDevice, 23, user);
        controls.save(volume);

        SecurityContextHolder.clearContext();

    }
}