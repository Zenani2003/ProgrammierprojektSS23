package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

public class Main {

    Graph myGraph;

    public static void main(String[] args) throws Exception {

        // Array for node
        int[] ids;
        double[] lats;
        double[] lons;
        int[] dikstraWeights;
        // Array for edges
        int[] edgeSrcs;
        int[] edgeTrgs;
        int[] edgeWeights;
        String graphPath = "\\Users\\julia\\Downloads\\Programmierprojekt\\Ganze\\Backend\\programmierprojektSS\\toy.fmi.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(graphPath));) {
            String line;

            // Skip first 5 lines
            for (int i = 0; i < 5; i++) {
                line = reader.readLine();
            }

            // Knoten und Kantenzahl
            int numNodes = Integer.valueOf(reader.readLine());
            int numEdges = Integer.valueOf(reader.readLine());

            ids = new int[numNodes];
            lats = new double[numNodes];
            lons = new double[numNodes];
            dikstraWeights = new int[numNodes];

            edgeSrcs = new int[numEdges];
            edgeTrgs = new int[numEdges];
            edgeWeights = new int[numEdges];

            System.out.printf("Node amount: %s \n", numNodes);
            System.out.printf("Edges amount: %s \n", numEdges);

            for (int i = 0; i < numNodes; i++) {
                line = reader.readLine();
                String[] data = line.split(" ");

                ids[i] = Integer.valueOf(data[0]);
                lats[i] = Double.valueOf(data[2]);
                lons[i] = Double.valueOf(data[3]);
                dikstraWeights[i] = Integer.MAX_VALUE;

                if (i % 1000000 == 0) {
                    System.out.println(i);
                }
            }

            for (int i = 0; i < numEdges; i++) {
                line = reader.readLine();
                String[] data = line.split(" ");

                edgeSrcs[i] = Integer.valueOf(data[0]);
                edgeTrgs[i] = Integer.valueOf(data[1]);
                edgeWeights[i] = Integer.valueOf(data[2]);

                if (i % 1000000 == 0) {
                    System.out.println(i);
                }
            }
        }

        var graph = new Graph(lats, lons, dikstraWeights, edgeSrcs, edgeTrgs, edgeWeights);
        var findClosestNode = new FindClosestNode(lats, lons);



        var server = new Server(graph,findClosestNode);
    


    }

}
