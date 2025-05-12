/**
 * File Purpose: Models a city road network as undirected weighted graph
 *
 * Core Functionality:
 * - Stores city connections using adjacency lists
 * - Implements Dijkstra's shortest path algorithm
 * - Calculates total distance for given path
 *
 * Usage Example:
 * 1. Add edges with addEdge(city1, city2, distance)
 * 2. Find paths with dijkstra(startCity, endCity)
 */
package com.example.graph;


import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public Map<String, Map<String, Integer>> getadjList() {
        return adjList;
    }

    // Add roads between cities
    public void addEdge(String city1, String city2, int distance) {
        adjList.putIfAbsent(city1, new HashMap<>());
        adjList.putIfAbsent(city2, new HashMap<>());
        adjList.get(city1).put(city2, distance);
        adjList.get(city2).put(city1, distance);  // Undirected graph
    }

    // Calculate the shortest path from the starting point to the endpoint using Dijkstra's algorithm
    public List<String> dijkstra(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String city : adjList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
            previous.put(city, null);
        }

        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (current.equals(end)) break;

            for (Map.Entry<String, Integer> neighbor : adjList.get(current).entrySet()) {
                String neighborCity = neighbor.getKey();
                int weight = neighbor.getValue();
                int newDist = distances.get(current) + weight;

                if (newDist < distances.get(neighborCity)) {
                    distances.put(neighborCity, newDist);
                    previous.put(neighborCity, current);
                    pq.add(neighborCity);
                }
            }
        }

        // Rebuilding the Path
        List<String> path = new LinkedList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // Calculate the total distance of the path
    public int calculateTotalDistance(List<String> path, Graph graph) {
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i + 1);
            totalDistance += graph.adjList.get(city1).get(city2);
        }
        return totalDistance;
    }
}
