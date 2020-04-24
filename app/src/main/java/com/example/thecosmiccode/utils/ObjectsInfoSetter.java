package com.example.thecosmiccode.utils;

import com.example.thecosmiccode.model.Object;

import java.util.ArrayList;

public class ObjectsInfoSetter {
    private String objectNames;
    private String objectWeights;
    private String objectCosts;

    public ObjectsInfoSetter(String objectNames, String objectWeights, String objectCosts) {
        this.objectNames = objectNames;
        this.objectWeights = objectWeights;
        this.objectCosts = objectCosts;
    }

    public ArrayList<Object> getObjectsList() {
        ArrayList<Object> objects = new ArrayList<>();

        ArrayList<String> names = new ArrayList<>();

        while (!objectNames.equals("")) {
            String name = objectNames.substring(0, objectNames.indexOf("\n"));
            names.add(name);
            objectNames = objectNames.substring((name + "\n").length());
        }

        ArrayList<Integer> weights = new ArrayList<>();

        while (!objectWeights.equals("")) {
            String weight = objectWeights.substring(0, objectWeights.indexOf("\n"));
            weights.add(Integer.parseInt(weight));
            objectWeights = objectWeights.substring((weight + "\n").length());
        }

        ArrayList<Integer> costs = new ArrayList<>();

        while (!objectCosts.equals("")) {
            String cost = objectCosts.substring(0, objectCosts.indexOf("\n"));
            costs.add(Integer.parseInt(cost));
            objectCosts = objectCosts.substring((cost + "\n").length());
        }

        int size = names.size();
        for (int i = 0; i < size; i++) {
            objects.add(new Object(names.get(i), weights.get(i), costs.get(i)));
        }

        return objects;
    }
}
