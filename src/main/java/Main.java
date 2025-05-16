import com.example.calculations.*;
import com.example.graph.Attraction;
import com.example.graph.Graph;
import com.example.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputData input = InputHandler.readInput();

        //System.out.println("start: " + startCity);
        //System.out.println("Destination: " + endCity);
        //System.out.println("Attractions: " + Arrays.toString(attractionNames));

        String startCity = input.getstartCity();
        String endCity = input.getendCity();
        List<String> selectedAttractions = input.getselectedAttractions();
        Map<String, Attraction> attractions = input.getattractions();
        Graph roadNetwork = input.getroadNetwork();

        if (selectedAttractions.get(0).equals("Null") 
                || selectedAttractions.get(0).hashCode() == 0
        ) {
                List<String> caseNullPath = roadNetwork.dijkstra(startCity, endCity);
                RoutePrinter.printRoute("Shortest route:", caseNullPath, roadNetwork);
                return;
        }

        CalcOrderedRoute caseOrdered = new CalcOrderedRoute();
        List<String> caseOrderedPath = caseOrdered.calculateRoute(
                startCity, endCity, selectedAttractions, roadNetwork, attractions
        );
        RoutePrinter.printRoute("Shortest route (Ordered Calculation):", caseOrderedPath, roadNetwork);

        CalcBrute caseBrute = new CalcBrute();
        List<String> caseBrutePath = caseBrute.calculateRoute(
                startCity, endCity, selectedAttractions, roadNetwork, attractions
        );
        RoutePrinter.printRoute("Shortest route (Brute Force):", caseBrutePath, roadNetwork);

        CalcBitmaskDP caseDP = new CalcBitmaskDP();
        List<String> caseDPPath = caseDP.calculateRoute(
                startCity, endCity, selectedAttractions, roadNetwork, attractions
        );
        RoutePrinter.printRoute("Shortest route (Bitmask DP):", caseDPPath, roadNetwork);
    }
}

