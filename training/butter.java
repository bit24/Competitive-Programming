import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*ID: eric.ca1
 LANG: JAVA
 TASK: butter
 */
public class butter {

	public static butter helper = new butter();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("butter.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("butter.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		

		int numCows = Integer.parseInt(inputData.nextToken());

		int numVertices = Integer.parseInt(inputData.nextToken());

		int numEdges = Integer.parseInt(inputData.nextToken());

		int[] cowLocations = new int[numCows];

		for (int currentCow = 0; currentCow < numCows; currentCow++) {
			cowLocations[currentCow] = Integer.parseInt(reader.readLine().trim());
		}
		
		Vertex[] vertices = new Vertex[numVertices+1];
		
		for(int currentVertex = 1; currentVertex <= numVertices; currentVertex++){
			vertices[currentVertex] = helper. new Vertex(0, 0);
		}
		
		for(int currentPath = 0; currentPath < numEdges; currentPath++){
			inputData = new StringTokenizer(reader.readLine());
			
			int node1 = Integer.parseInt(inputData.nextToken());
			int node2 = Integer.parseInt(inputData.nextToken());
			int length = Integer.parseInt(inputData.nextToken());
			
			vertices[node1].neighbors.add(vertices[node2]);
			vertices[node1].neighborDistances.add(length);
			
			vertices[node2].neighbors.add(vertices[node1]);
			vertices[node2].neighborDistances.add(length);
			
		}
		reader.close();
		
		int minTotLength = Integer.MAX_VALUE;
		for(int possibleSugarLocation = 1; possibleSugarLocation <= numVertices; possibleSugarLocation++){
			int possibleValue = getTotalDistance(cowLocations, vertices[possibleSugarLocation], vertices);
			if(possibleValue < minTotLength){
				minTotLength = possibleValue;
			}
		}
		
		printer.println(minTotLength);
		printer.close();

	}

	public static int getTotalDistance(int[] cows, Vertex source, Vertex[] vertices) {
		for (Vertex currentVertex : vertices) {
			if(currentVertex == null){
				continue;
			}
			currentVertex.distance = Integer.MAX_VALUE;
		}
		source.distance = 0;

		PriorityQueue<Vertex> processingQueue = new PriorityQueue<Vertex>();
		
		processingQueue.add(source);

		while (!processingQueue.isEmpty()) {
			Vertex currentVertex = processingQueue.remove();
			for(int currentNeighborIndex = 0; currentNeighborIndex < currentVertex.neighbors.size(); currentNeighborIndex++){
				Vertex neighbor = currentVertex.neighbors.get(currentNeighborIndex);
				int neighborDistance = currentVertex.neighborDistances.get(currentNeighborIndex);
				
				int possibleValue = currentVertex.distance + neighborDistance;
				
				if(neighbor.distance > possibleValue){
					neighbor.distance = possibleValue;
					processingQueue.remove(neighbor);
					processingQueue.add(neighbor);
				}
				
			}
		}
		
		int totalDistance = 0;
		for(int cowIndex : cows){
			totalDistance += vertices[cowIndex].distance;
		}
		return totalDistance;
	}

	class Vertex implements Comparable<Vertex> {
		int vertex;
		int distance;
		LinkedList<Vertex> neighbors = new LinkedList<Vertex>();
		LinkedList<Integer> neighborDistances = new LinkedList<Integer>();

		public Vertex(int vertex) {
			this.vertex = vertex;
		}

		public Vertex(int vertex, int distance) {
			this.vertex = vertex;
			this.distance = distance;

		}

		public int compareTo(Vertex o) {
			if (distance > o.distance) {
				return 1;
			}
			if (distance < o.distance) {
				return -1;
			}
			return 0;
		}

	}

}
