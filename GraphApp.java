/*
 ============================================================================
 Author      : Jakob Frank, Benjamin Bushnell
 Description : Graphs - Lab 2
 ============================================================================
 */
import java.io.*;

public class GraphApp {

    public static void main(String[] args) {
    	System.out.print("LABELS: ");
        Graph theGraph = new Graph();
        System.out.println("\n");
        System.out.println("Depth First Search Visits: ");
        theGraph.depthFirstSearch();
        System.out.println();
        System.out.println("");System.out.println("Breadth First Search Visits: ");
        theGraph.breadthFirstSearch();
        System.out.println("\n");
        System.out.println("Mininum Spanning Tree: ");
        theGraph.minimumSpanningTree(); 
        System.out.println("");
    }
    
}