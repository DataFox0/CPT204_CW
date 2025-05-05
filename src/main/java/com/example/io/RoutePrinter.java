package com.example.io;

import com.example.graph.*;
import java.util.List;

public class RoutePrinter {
    public static void printRoute(String title, List<String> path, Graph roadNetwork) {
        System.out.println(title);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Total distance: " + roadNetwork.calculateTotalDistance(path, roadNetwork) + " miles");
        System.out.println();
    }
}
