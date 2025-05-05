/**
* File Purpose: Solves tourist route planning using DP with bitmask
*
* Algorithm:
* - Bitmask DP solution for TSP variant
* - Time Complexity: O(2^n * n^2)
*
* Input Requirements:
* - Valid city names in Graph
* - Existing attractions in location map
*
* Output: Ordered list of cities to visit
*/
package com.example.calculations;

import com.example.graph.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalcBitmaskDP {
    public List<String> calculateRoute(String startingCity, String endingCity, List<String> attractions, Graph graph, Map<String, Attraction> attractionLocations) {
        long startTime = System.nanoTime();

        Set<String> allCityNames = new HashSet<>();
        allCityNames.add(startingCity);
        allCityNames.add(endingCity);
        for (String attraction : attractions) {
            allCityNames.add(attractionLocations.get(attraction).getLocation());

        }
        allCityNames.addAll(graph.adjList.keySet());

        List<String> allCities = new ArrayList<>(allCityNames);
        int n = allCities.size();

        Map<String, Integer> cityToIndex = new HashMap<>();
        Map<Integer, String> indexToCity = new HashMap<>();
        for (int i = 0; i < n; i++) {
            cityToIndex.put(allCities.get(i), i);
            indexToCity.put(i, allCities.get(i));
        }

        int k = attractions.size();
        int[] requiredIdx = new int[k];
        for (int i = 0; i < k; i++) {
            String attraction = attractions.get(i);
            String location = attractionLocations.get(attraction).getLocation();
            requiredIdx[i] = cityToIndex.get(location);
        }

        int start = cityToIndex.get(startingCity);
        int end = cityToIndex.get(endingCity);

        // Step 1: All-pairs shortest paths
        int[][] dist = new int[n][n];

        @SuppressWarnings("unchecked")
        List<String>[][] path = (List<String>[][]) new List[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = new ArrayList<>();
            }
        }
        

        for (int i = 0; i < n; i++) {
            String from = allCities.get(i);
            for (int j = 0; j < n; j++) {
                String to = allCities.get(j);
                if (i == j) {
                    dist[i][j] = 0;
                    path[i][j] = new ArrayList<>();
                    path[i][j].add(from);
                } else {
                    List<String> p = graph.dijkstra(from, to);
                    path[i][j] = p;
                    dist[i][j] = graph.calculateTotalDistance(p, graph);
                }
            }
        }

        // Step 2: DP
        int[][] dp = new int[1 << k][n];
        int[][] prev = new int[1 << k][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
        for (int[] row : prev) Arrays.fill(row, -1);
        dp[0][start] = 0;

        for (int mask = 0; mask < (1 << k); mask++) {
            for (int u = 0; u < n; u++) {
                if (dp[mask][u] == Integer.MAX_VALUE) continue;

                for (int i = 0; i < k; i++) {
                    if ((mask & (1 << i)) != 0) continue;
                    int v = requiredIdx[i];
                    int nextMask = mask | (1 << i);
                    int cost = dp[mask][u] + dist[u][v];
                    if (cost < dp[nextMask][v]) {
                        dp[nextMask][v] = cost;
                        prev[nextMask][v] = u;
                    }
                }
            }
        }

        int fullMask = (1 << k) - 1;
        int bestCost = Integer.MAX_VALUE;
        int lastNode = -1;

        for (int u = 0; u < n; u++) {
            if (dp[fullMask][u] != Integer.MAX_VALUE) {
                int cost = dp[fullMask][u] + dist[u][end];
                if (cost < bestCost) {
                    bestCost = cost;
                    lastNode = u;
                }
            }
        }

        // Step 3: Reconstruct the path
        List<Integer> nodes = new ArrayList<>();
        int mask = fullMask;
        int curr = lastNode;
        while (mask != 0) {
            nodes.add(curr);
            int prevNode = prev[mask][curr];
            int usedBit = -1;
            for (int i = 0; i < k; i++) {
                if ((mask & (1 << i)) != 0 && requiredIdx[i] == curr) {
                    usedBit = i;
                    break;
                }
            }
            if (usedBit != -1) mask ^= (1 << usedBit);
            curr = prevNode;
        }
        nodes.add(start);
        Collections.reverse(nodes);
        nodes.add(end);

        // Step 4: Assemble full path
        List<String> fullPath = new ArrayList<>();
        for (int i = 0; i < nodes.size() - 1; i++) {
            int u = nodes.get(i);
            int v = nodes.get(i + 1);
            List<String> segment = path[u][v];
            if (i > 0) segment = segment.subList(1, segment.size());
            fullPath.addAll(segment);
        }

        System.out.println((System.nanoTime()-startTime)/1e6);

        return fullPath;
    }
}
