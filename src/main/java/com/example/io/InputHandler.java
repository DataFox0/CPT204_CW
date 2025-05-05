package com.example.io;

import com.example.graph.*;

import java.io.InputStream;
import java.util.*;

public class InputHandler {
    public static InputData readInput() {
        // Read attractions and road data
        // Map<String, Attraction> attractions = CSVReader.readAttractions(
        //     InputHandler.class.getClassLoader().getResource("attractions.csv").getPath()
        // );
        // Graph roadNetwork = CSVReader.readRoads(
        //     InputHandler.class.getClassLoader().getResource("roads.csv").getPath()
        // );

        // 使用 getResourceAsStream 读取文件
        InputStream attractionsStream = InputHandler.class.getClassLoader().getResourceAsStream("attractions.csv");
        InputStream roadsStream = InputHandler.class.getClassLoader().getResourceAsStream("roads.csv");

        if (attractionsStream == null || roadsStream == null) {
            System.out.println("Files not found!");
        }
        // 使用 InputStream 处理文件内容
        Map<String, Attraction> attractions = CSVReader.readAttractions(attractionsStream);
        Graph roadNetwork = CSVReader.readRoads(roadsStream);
        



        // Print the information of attraction
        System.out.println("Attractions:");
        for (Map.Entry<String, Attraction> entry : attractions.entrySet()) {
            System.out.println( entry.getValue());
        }

        // Read user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the starting city: ");
        String startCity = scanner.nextLine();
        System.out.print("Enter the destination city: ");
        String endCity = scanner.nextLine();
        System.out.print("Enter the attractions (comma-separated): ");
        String[] attractionNames = scanner.nextLine().split(",");
        scanner.close();

        List<String> selectedAttractions = new ArrayList<>();
        for (String name : attractionNames) {
            selectedAttractions.add(name.trim());
        }

        return new InputData(startCity, endCity, selectedAttractions, attractions, roadNetwork);
    }
}
