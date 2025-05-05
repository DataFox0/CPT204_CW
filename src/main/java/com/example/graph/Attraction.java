/**
 * File Purpose: Represents a tourist attraction with its location
 */
package com.example.graph;

public class Attraction {
    private String name;
    private String location;

    public Attraction(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return name + " (" + location + ")";
    }
}
