package com.example.thecosmiccode.model;

import java.io.Serializable;

public class Object implements Serializable {
    private String name;
    private int weight;
    private int cost;

    public Object(String name, int weight, int cost) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
