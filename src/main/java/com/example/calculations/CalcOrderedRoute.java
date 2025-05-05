/**
* File Purpose: Simple sequential attraction visit planner
*
* Strategy:
* - Visits attractions in input order
* - Connects via the shortest path between points
*
* Pros:
* - Fast O(n) computation
* - Predictable output
*
* Cons:
* - May produce suboptimal total distance
*/
package com.example.calculations;

import com.example.graph.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalcOrderedRoute {
    // Calculate the path (including passing through scenic spots)
    public List<String> calculateRoute(String startingCity, String endingCity, List<String> attractions, Graph graph, Map<String, Attraction> attractionLocations) {
        long start = System.nanoTime();


        List<String> fullPath = new ArrayList<>();
        fullPath.add(startingCity);
        String currentCity = startingCity;

        // Calculate the shortest path from the starting point to each attraction
        for (String attraction : attractions) {
            String attractionLocation = attractionLocations.get(attraction).getLocation(); // Obtain the location of tourist attractions
            List<String> path = graph.dijkstra(currentCity, attractionLocation);
            fullPath.addAll(path.subList(1, path.size())); // Avoid duplicate cities
            currentCity = attractionLocation;
        }

        // Finally, from the last attraction to the finish line
        List<String> lastPath = graph.dijkstra(currentCity, endingCity);
        fullPath.addAll(lastPath.subList(1, lastPath.size()));

        System.out.println((System.nanoTime()-start)/1e6);

        return fullPath;
    }
}