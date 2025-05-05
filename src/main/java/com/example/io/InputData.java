package com.example.io;

import com.example.graph.*;
import java.util.*;

public class InputData {
    public String startCity;
    public String endCity;
    public List<String> selectedAttractions;
    public Map<String, Attraction> attractions;
    public Graph roadNetwork;

    public InputData(String startCity, String endCity, List<String> selectedAttractions,
                     Map<String, Attraction> attractions, Graph roadNetwork) {
        this.startCity = startCity;
        this.endCity = endCity;
        this.selectedAttractions = selectedAttractions;
        this.attractions = attractions;
        this.roadNetwork = roadNetwork;
    }
}
