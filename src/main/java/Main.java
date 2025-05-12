import com.example.calculations.*;
import com.example.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputData input = InputHandler.readInput();

        //System.out.println("start: " + startCity);
        //System.out.println("Destination: " + endCity);
        //System.out.println("Attractions: " + Arrays.toString(attractionNames));

        if (input.selectedAttractions.get(0).equals("Null") 
                || input.selectedAttractions.get(0).hashCode() == 0
        ) {
                List<String> caseNullPath = input.roadNetwork.dijkstra(input.startCity, input.endCity);
                RoutePrinter.printRoute("Shortest route:", caseNullPath, input.roadNetwork);
                return;
        }

        CalcOrderedRoute caseOrdered = new CalcOrderedRoute();
        List<String> caseOrderedPath = caseOrdered.calculateRoute(
                input.startCity, input.endCity, input.selectedAttractions, input.roadNetwork, input.attractions
        );
        RoutePrinter.printRoute("Shortest route (Ordered Calculation):", caseOrderedPath, input.roadNetwork);

        CalcBrute caseBrute = new CalcBrute();
        List<String> caseBrutePath = caseBrute.calculateRoute(
                input.startCity, input.endCity, input.selectedAttractions, input.roadNetwork, input.attractions
        );
        RoutePrinter.printRoute("Shortest route (Brute Force):", caseBrutePath, input.roadNetwork);

        CalcBitmaskDP caseDP = new CalcBitmaskDP();
        List<String> caseDPPath = caseDP.calculateRoute(
                input.startCity, input.endCity, input.selectedAttractions, input.roadNetwork, input.attractions
        );
        RoutePrinter.printRoute("Shortest route (Bitmask DP):", caseDPPath, input.roadNetwork);

        CalcMST caseMST = new CalcMST();
        List<String> caseMSTPath = caseMST.calculateRoute(
                input.startCity, input.endCity, input.selectedAttractions, input.roadNetwork, input.attractions
        );
        RoutePrinter.printRoute("Shortest route (MST):", caseMSTPath, input.roadNetwork);
    }
}

