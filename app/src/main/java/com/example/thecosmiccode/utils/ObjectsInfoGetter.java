package com.example.thecosmiccode.utils;

import com.example.thecosmiccode.model.Object;
import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class ObjectsInfoGetter {
    private Voyage voyage;

    public ObjectsInfoGetter(Voyage voyage) {
        this.voyage = voyage;
    }

    public String getObjectNamesString() {
        StringBuilder objectNames = new StringBuilder();

        ArrayList<Object> objects = voyage.getObjects();
        for (int i = 0; i < objects.size(); i++) {
            objectNames.append(objects.get(i).getName()).append("\n");
        }

        return objectNames.toString();
    }

    public String getObjectWeightsString() {
        StringBuilder objectWeights = new StringBuilder();

        ArrayList<Object> objects = voyage.getObjects();
        for (int i = 0; i < objects.size(); i++) {
            objectWeights.append(objects.get(i).getWeight()).append("\n");
        }

        return objectWeights.toString();
    }

    public String getObjectCostsString() {
        StringBuilder objectCosts = new StringBuilder();

        ArrayList<Object> objects = voyage.getObjects();
        for (int i = 0; i < objects.size(); i++) {
            objectCosts.append(objects.get(i).getCost()).append("\n");
        }

        return objectCosts.toString();
    }

    public int getProfit() {
        int profit = 0;

        for (int i = 0; i < voyage.getObjects().size(); i++) {
            profit += voyage.getObjects().get(i).getCost();
        }

        return profit;
    }
}
