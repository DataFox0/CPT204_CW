package com.example.io;

import com.example.graph.*;
import java.util.*;

public class InputHandler {
    public static InputData readInput() {
        // Read attractions and road data
        Map<String, Attraction> attractions = CSVReader.readAttractions("..\\resources\\attractions.csv");
        Graph roadNetwork = CSVReader.readRoads("..\\resources\\roads.csv");

        // Print the information of attraction
        // System.out.println("Attractions:");
        // for (Map.Entry<String, Attraction> entry : attractions.entrySet()) {
        //     System.out.println( entry.getValue());
        // }

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

        System.out.println("\nStart: " + startCity);
        System.out.println("Destination: " + endCity);
        System.out.print("Attractions: " + attractionNames[0]);
        for (int i = 1; i < attractionNames.length; i++) {
            System.out.print(", " + attractionNames[i]);
        }
        System.out.println('\n');

        return new InputData(startCity, endCity, selectedAttractions, attractions, roadNetwork);
    }
}
