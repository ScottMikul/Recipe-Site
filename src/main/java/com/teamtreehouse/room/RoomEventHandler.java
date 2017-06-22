package com.teamtreehouse.room;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by scott on 6/20/2017.
 */
public class RoomEventHandler {
    private final RoomRepository rooms;

    @Autowired
    public RoomEventHandler(RoomRepository rooms) {
        this.rooms = rooms;
    }

}
