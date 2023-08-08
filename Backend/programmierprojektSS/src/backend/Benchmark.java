package backend;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Benchmark {

	public static void main(String[] args) {
		// read parameters (parameters are expected in exactly this order)
		String graphPath = args[1];
		double lon = Double.parseDouble(args[3]);
		double lat = Double.parseDouble(args[5]);
		String quePath = args[7];
		int sourceNodeId = Integer.parseInt(args[9]);
		
		
		//TOY
//		String graphPath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\toy.txt";
//		double lat = 49.01;
//		double lon = 10.01;
//		String quePath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\Benchs\\toy.que";
		
		//GERMANY
//		String graphPath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\germany.fmi.txt";
//		double lat = 54.07449660000000334;
//		double lon = 13.90422190000000136;
//		System.out.println("The nearest Node should be: 404444.");
//		String quePath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\Benchs\\germany.que";
		
		//MV
//		String graphPath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\MV.fmi.txt";
//		double lat = 54.07449660000000335;
//		double lon = 13.90422190000000136;
//		String quePath = "C:\\Users\\Mara\\Documents\\Uni\\ProgrammierprojektSS\\Benchs\\MV.que";
		//Example node: 404444 1904809175 54.07449660000000335 13.90422190000000136 0
		
		

		// run benchmarks
		System.out.println("Reading graph file and creating graph data structure (" + graphPath + ")");
		long graphReadStart = System.currentTimeMillis();

		//Array for node
		int[] ids;
		double[] lats;
		double[] lons;
		int[] dikstraWeights;
		//Array for edges
		int[] edgeSrcs;
		int[] edgeTrgs;
		int[] edgeWeights;
		
		// Read graph here
		try (BufferedReader reader = new BufferedReader(new FileReader(graphPath));) {
			String line;
			
			//Skip first 5 lines
			for(int i=0; i<5; i++) {
				line = reader.readLine();
			}
			
			//Knoten und Kantenzahl
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
			
			for(int i = 0; i < numNodes; i++) {
				line = reader.readLine();
				String[] data = line.split(" ");
				
				ids[i] = Integer.valueOf(data[0]);
				lats[i] = Double.valueOf(data[2]);
				lons[i] = Double.valueOf(data[3]);
				dikstraWeights[i] = Integer.MAX_VALUE;
				
				if (i%1000000 == 0) {
					System.out.println(i);
				}
			}
			
			for(int i = 0; i < numEdges; i++) {
				line = reader.readLine();
				String[] data = line.split(" ");
				
				edgeSrcs[i] = Integer.valueOf(data[0]);
				edgeTrgs[i] = Integer.valueOf(data[1]);
				edgeWeights[i] = Integer.valueOf(data[2]);
				
				if (i%1000000 == 0) {
					System.out.println(i);
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		long graphReadEnd = System.currentTimeMillis();
		System.out.println("\tgraph read took " + (graphReadEnd - graphReadStart) + "ms");
		
		Graph myGraph = new Graph(lats, lons, dikstraWeights, edgeSrcs, edgeTrgs, edgeWeights);

//		System.out.println("Setting up closest node data structure...");
//		// TODO: set up closest node data structure here
		
		System.out.println("Finding closest node to coordinates " + lon + " " + lat);
		long nodeFindStart = System.currentTimeMillis();
		double[] coords = {0.0, 0.0};
		// Find closest node here and write coordinates into coords
		FindClosestNode dataStruc = new FindClosestNode(lats, lons);
		
		int closestNode = dataStruc.findNode(lat, lon);
		coords[1] = lats[closestNode];
		coords[0] = lons[closestNode];
		
		System.out.println("Closest Node ID is: " + closestNode + " Lat is: " + coords[1] + " Lon is: " + coords[0]);
		long nodeFindEnd = System.currentTimeMillis();
		System.out.println("\tfinding node took " + (nodeFindEnd - nodeFindStart) + "ms: " + coords[0] + ", " + coords[1]);
		
		
		
		System.out.println("Running one-to-one Dijkstras for queries in .que file " + quePath);
		long queStart = System.currentTimeMillis();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(quePath))) {
			String currLine;
			while ((currLine = bufferedReader.readLine()) != null) {
				int oneToOneSourceNodeId = Integer.parseInt(currLine.substring(0, currLine.indexOf(" ")));
				int oneToOneTargetNodeId = Integer.parseInt(currLine.substring(currLine.indexOf(" ") + 1));
				int oneToOneDistance = myGraph.dijkstraOneToOne(oneToOneSourceNodeId, oneToOneTargetNodeId);
				System.out.println(oneToOneDistance);
			}
		} catch (Exception e) {
			System.out.println("Exception...");
			e.printStackTrace();
		}
		long queEnd = System.currentTimeMillis();
		System.out.println("\tprocessing .que file took " + (queEnd - queStart) + "ms");
		

		//Get src node for oneToAllDijkstra
//		Scanner myObj = new Scanner(System.in);
//	    System.out.println("Enter source node id for the oneToAll-Dijkstra: ");
//	    int sourceNodeId = Integer.parseInt(myObj.nextLine());
	    
	    
		System.out.println("Computing one-to-all Dijkstra from node id " + sourceNodeId);
		long oneToAllStart = System.currentTimeMillis();
		// Run one-to-all Dijkstra here
		int[] distancesToSrcId = myGraph.dijkstraOneToAll(sourceNodeId);
		
		//System.out.println(Arrays.toString(distancesToSrcId));
		
		long oneToAllEnd = System.currentTimeMillis();
		System.out.println("\tone-to-all Dijkstra took " + (oneToAllEnd - oneToAllStart) + "ms");

		// ask user for a target node id
	    System.out.println("Enter target node id: ");
	    int targetNodeId = (new Scanner(System.in)).nextInt();
	    int oneToAllDistance = -42;
	    oneToAllDistance = distancesToSrcId[targetNodeId];
	    System.out.println("Distance from " + sourceNodeId + " to " + targetNodeId + " is " + oneToAllDistance);
		
		
	}

}
