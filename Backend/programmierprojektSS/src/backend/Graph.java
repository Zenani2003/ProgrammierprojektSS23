package backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.BitSet;

public class Graph {
	final int numNodes;
	final int numEdges;

	//Arrays for node
	double[] nodeLats;
	double[] nodeLons;
	int[] dikstraWeights;
	
	//Other arrays
	int[] offsets;
	int[] adjacencyList;
	int[] edgeWeights;
	
	//TODO delete these two
	List<List<Double>> nodeList;
	List<List<Integer>> edgeAdjacencyList;
	

	public Graph(double[] lats, double[] lons, int[] dikstraWeights, int[] srcs, int[] trgs, int[] edgeWeights) {
		this.numNodes = lats.length;
		this.numEdges = srcs.length;
		
		this.nodeLats = lats;
		this.nodeLons = lons;
		this.dikstraWeights = dikstraWeights;
		
		//Erstelle offset Tabelle
		this.offsets = new int[numNodes + 1];
		// i aktueller Knoten
		// ii aktueller offset (wieviel Kanten hat aktueller Knoten)
		for (int i = 0, ii = 0; i < numNodes; i++) {
			//Jedes mal wenn src=i gleich der Zeile, dann erhöhe number of Edges für diesen Knoten
			while (ii < numEdges && srcs[ii] == i) {
				ii++;
			}
			offsets[i+1] = ii;
		}
		this.adjacencyList = trgs;
		this.edgeWeights = edgeWeights;
	}

	// GETTERS
	public int getNumNodes() {
		return this.numNodes;
	}

	public int getNumEdges() {
		return this.numEdges;
	}

	//List of all nodes with edge from index
	public int[] getAdjacencyList(int index) {
		return Arrays.copyOfRange(adjacencyList, offsets[index], offsets[index + 1]);
	}
	
	public int[] getOffsets(int index) {
		return IntStream.range(offsets[index], offsets[index +1]).toArray();
	}
	
	// Methods for nodeList
	public List<List<Double>> getNodeList() {
		return this.nodeList;
	}
	
	public double getNodLat(int index) {
		return nodeLats[index];
	}
	public double getNodeLon(int index) {
		return nodeLons[index];
	}
	public double getNodeDijkstraDist(int index) {
		return dikstraWeights[index];
	}
	public void setNodeDijkstraDist(int index, int newDist) {
		dikstraWeights[index] = newDist;
	}

	// Methods for edgeAdjacencyList
	public List<List<Integer>> getEdgeAdjacencyList() {
		return this.edgeAdjacencyList;
	}

	/*
	 * DIJKSTRA
	 */
	public int[] dijkstraOneToAll(int source) {
		
		// Liste mit allen Knoten und distanz auf unendlich
		int[] distances = new int[numNodes];
		Arrays.fill(distances, Integer.MAX_VALUE);
		
		Comparator<int[]> arrayComparator = new Comparator<int[]>() {
			@Override
			public int compare(int[] arr1, int[] arr2) {
				//Compare weights
				return Integer.compare(arr1[0], arr2[0]);
			}
		};

		PriorityQueue<int[]> minHeap = new PriorityQueue<int[]>(arrayComparator);
		distances[source] = 0;

		//Add source node to minHeap
		int[] pairSource1 = new int[2];
		pairSource1[0] = 0;
		pairSource1[1] = source;
		minHeap.add(pairSource1);
		

		while (!minHeap.isEmpty()) {
			
			//get minimum Dist from minHeap
			int[] currentMinDist = minHeap.poll();
			
			//Iterate durch alle Elemente in getOffsets & outgoingEdge = aktuelles Element i
			for (int outgoingEdge : getOffsets(currentMinDist[1])) {
				int adjacentNode = adjacencyList[outgoingEdge];
				int adjacentNodeWeight = edgeWeights[outgoingEdge];
				int cost = distances[currentMinDist[1]] + adjacentNodeWeight;
				if (cost < distances[adjacentNode]) {
					distances[adjacentNode] = cost;
					int[] newPair = new int[2];
					newPair[0] = cost;
					newPair[1] = adjacentNode;
					minHeap.add(newPair);
				}
			}
		}
		return distances;
	}
    // public int dijkstraOneToOne() {
	public int dijkstraOneToOne(int source, int destination) {
		return dijkstraOneToAll(source)[destination];

	}

}
