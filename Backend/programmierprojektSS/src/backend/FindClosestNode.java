package backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FindClosestNode {
	double[] nodeLats;
	double[] nodeLons;

	public FindClosestNode(double[] lats, double[] lons) {
		this.nodeLats = lats;
		this.nodeLons = lons;
	}

	public int findNode(double latitudeSource, double longitudeSource) {

		List<Integer> closeNodesList = new LinkedList<>();

		double[] sourceNode = new double[] { latitudeSource, longitudeSource };

		int numNodes = nodeLats.length;

		// Füge Knoten zu closeNodesList hinzu, wenn Distance [-00.1; 00.1]
		for (double radius = 00.1; closeNodesList.isEmpty(); radius += 00.1) {
			for (int i = 0; i < numNodes; i++) {

				double currentLat = nodeLats[i];
				double currentLong = nodeLons[i];

				double distLat = latitudeSource - currentLat;
				double distLong = longitudeSource - currentLong;

				if (distLat >= -radius && distLat <= radius && distLong >= -radius && distLong <= radius) {
					closeNodesList.add(i);
				}
			}
		}

		// System.out.println("There are " + closeNodesList.size() + " nodes in the closeNodesList.");

		double euklidischeDistanz = Integer.MAX_VALUE;
		double currentEuDist;
		int nearestNode = -1;

		// Search nearestNode from closeNodesList
		for (int j = 0; j < closeNodesList.size(); j++) {
			currentEuDist = (nodeLats[closeNodesList.get(j)] - sourceNode[0])
					* (nodeLats[closeNodesList.get(j)] - sourceNode[0])
					+ (nodeLons[closeNodesList.get(j)] - sourceNode[1])
							* (nodeLons[closeNodesList.get(j)] - sourceNode[1]);

			if (currentEuDist <= euklidischeDistanz) {
				euklidischeDistanz = currentEuDist;
				nearestNode = closeNodesList.get(j);
			}
		}
		return nearestNode;
	}
}
