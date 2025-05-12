package com.example.io;

import com.example.graph.*;
import java.util.*;

public class InputData {
    private String startCity;
    private String endCity;
    private List<String> selectedAttractions;
    private Map<String, Attraction> attractions;
    private Graph roadNetwork;

    public InputData(String startCity, String endCity, List<String> selectedAttractions,
                     Map<String, Attraction> attractions, Graph roadNetwork) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.selectedAttractions = selectedAttractions;
        this.attractions = attractions;
        this.roadNetwork = roadNetwork;
    }

    public String getstartCity() {
        return startCity;
    }

    public String getendCity() {
        return endCity;
    }

    public List<String> getselectedAttractions() {
        return selectedAttractions;
    }

    public Map<String, Attraction> getattractions() {
        return attractions;
    }

    public Graph getroadNetwork() {
        return roadNetwork;
    }
}
