package com.example.thecosmiccode.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Voyage implements Serializable {
    private long id;
    private String user;
    private String name;
    private ArrayList<Object> objects;

    public Voyage(long id, String name, String user, ArrayList<Object> objects) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.objects = objects;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", objects=" + objects +
                '}';
    }
}
