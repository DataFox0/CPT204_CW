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
            reader.readLine();  // 忽略第一行（表头）

            while ((line = reader.readLine()) != null) {
                // 以逗号分割每一行数据
                String[] values = line.split(",");
                
                // 假设 CSV 格式是 name, location
                String name = values[0].trim();  // 景点名称
                String location = values[1].trim();  // 景点位置

                // 创建 Attraction 对象并将其放入 Map
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
