package com.teamtreehouse.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.device.Device;
import com.teamtreehouse.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by scott on 6/19/2017.
 */
@Entity
public class Room extends BaseEntity{

    private String name;
    @Max(value = 1000, message = "Area must be less than 1000 sq/ft")
    private int area;
    @OneToMany(mappedBy = "room")
    private List<Device> devices;
    @ManyToMany
    @JsonIgnore
    private List<User> administrators;

    protected Room(){
        super();
    }

    public Room(String name, int area, List<Device> devices, List<User> administrators) {
        this();
        this.name = name;
        this.area = area;
        this.devices = devices;
        this.administrators = administrators;
    }

    public void addDevice(Device device){
        devices.add(device);
    }

    public void addAdministrator(User user){
        administrators.add(user);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<User> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }
}
