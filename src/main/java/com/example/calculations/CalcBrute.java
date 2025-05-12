/**
* File Purpose: Solves tourist route via brute-force permutation
*
* Algorithm:
* - Checks all possible attraction visit orders
* - Validates against road network
* - Time Complexity: O(n!)
*/
package com.example.calculations;

import com.example.graph.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalcBrute {
    public List<String> calculateRoute(String startingCity, String endingCity, List<String> attractions, Graph graph, Map<String,Attraction> attractionLocations) {
        long startTime = System.nanoTime();

        Set<String> allCityNames = new HashSet<>();
        allCityNames.add(startingCity);
        allCityNames.add(endingCity);
        for (String attraction : attractions) {
            allCityNames.add(attractionLocations.get(attraction).getLocation());
        }
        allCityNames.addAll(graph.getadjList().keySet());

        List<String> allCities = new ArrayList<>(allCityNames);
        int n = allCities.size();

        Map<String, Integer> cityToIndex = new HashMap<>();
        Map<Integer, String> indexToCity = new HashMap<>();
        for (int i = 0; i < n; i++) {
            cityToIndex.put(allCities.get(i), i);
            indexToCity.put(i, allCities.get(i));
        }

        // Precompute all-pairs shortest paths
        Map<String, Map<String, List<String>>> allPaths = new HashMap<>();
        for (String from : allCities) {
            allPaths.put(from, new HashMap<>());
            for (String to : allCities) {
                if (from.equals(to)) {
                    allPaths.get(from).put(to, new ArrayList<>(List.of(from)));
                } else {
                    List<String> path = graph.dijkstra(from, to);
                    allPaths.get(from).put(to, path);
                }
            }
        }

        List<String> requiredLocations = new ArrayList<>();
        for (String attr : attractions) {
            requiredLocations.add(attractionLocations.get(attr).getLocation());
        }

        List<String> bestPath = null;
        int minTotalDist = Integer.MAX_VALUE;

        for (List<String> perm : permutations(requiredLocations)) {
            List<String> candidatePath = new ArrayList<>();
            int totalDist = 0;
            String current = startingCity;

            for (String next : perm) {
                List<String> segment = allPaths.get(current).get(next);
                totalDist += calculateTotalDistance(segment, graph);
                if (!candidatePath.isEmpty()) segment = segment.subList(1, segment.size());
                candidatePath.addAll(segment);
                current = next;
            }

            List<String> finalSegment = allPaths.get(current).get(endingCity);
            totalDist += calculateTotalDistance(finalSegment, graph);
            if (!candidatePath.isEmpty()) finalSegment = finalSegment.subList(1, finalSegment.size());
            candidatePath.addAll(finalSegment);

            if (totalDist < minTotalDist) {
                minTotalDist = totalDist;
                bestPath = candidatePath;
            }
        }

        System.out.println((System.nanoTime()-startTime)/1e6);

        return bestPath;
    }

    public int calculateTotalDistance(List<String> path, Graph graph) {
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i + 1);
            totalDistance += graph.getadjList().get(city1).get(city2);
        }
        return totalDistance;
    }

    private List<List<String>> permutations(List<String> items) {
        List<List<String>> result = new ArrayList<>();
        permute(items, 0, result);
        return result;
    }

    private void permute(List<String> items, int start, List<List<String>> result) {
        if (start == items.size()) {
            result.add(new ArrayList<>(items));
            return;
        }
        for (int i = start; i < items.size(); i++) {
            Collections.swap(items, i, start);
            permute(items, start + 1, result);
            Collections.swap(items, i, start);
        }
    }
}
