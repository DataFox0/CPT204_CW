/**
 * File Purpose: Loads tourism data from CSV files
 *
 * Static Methods:
 * - readAttractions(): Parses attraction locations
 * - readRoads(): Builds city road network
 *
 * Note: Throws IOException for file operations
 */
package com.example.io;

import com.example.graph.*;
import java.io.*;
import java.util.*;

public class CSVReader {

    public static Map<String, Attraction> readAttractions(String filename) {
        Map<String, Attraction> attractions = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    String name = values[0].trim();
                    String location = values[1].trim();
                    attractions.put(name, new Attraction(name, location));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attractions;
    }

    public static Map<String, Attraction> readAttractions(InputStream inputStream) {
        Map<String, Attraction> attractions = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            // Skip the header line (if any)
            reader.readLine();  // Ignore the first line (header)

            while ((line = reader.readLine()) != null) {
                // Separate each row of data with commas
                String[] values = line.split(",");
                
                // Assuming the CSV format is name and location
                String name = values[0].trim();  // Scenic spot name
                String location = values[1].trim();  // Location of tourist attractions

                // Create an Attraction object and place it in a Map
                Attraction attraction = new Attraction(name, location);
                attractions.put(name, attraction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attractions;
    }


    // Read the roads.csv file and return the graph structure (roads and distances between cities)
    public static Graph readRoads(String filename) {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String city1 = values[0].trim();
                    String city2 = values[1].trim();
                    int distance = Integer.parseInt(values[2].trim());
                    graph.addEdge(city1, city2, distance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static Graph readRoads(InputStream inputStream) {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            br.readLine();  // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String city1 = values[0].trim();  // City A
                    String city2 = values[1].trim();  // City B
                    int distance = Integer.parseInt(values[2].trim());  // Distance

                    // Add the road (edge) between city1 and city2 with the given distance
                    graph.addEdge(city1, city2, distance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
