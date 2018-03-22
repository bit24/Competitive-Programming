import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BessieBirthdayBuffet {
	
	static int numVertices;
	
	static int edgeCost;

	static Vertex[] vertices;
	
	static BessieBirthdayBuffet solver = new BessieBirthdayBuffet();
	
	static int[][] cost;
	
	static int[][] aList;
	
	static int[] quality;
	
	static int[] dp;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("buffet.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("buffet.out")));
		
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		edgeCost = Integer.parseInt(inputData.nextToken());
		
		vertices = new Vertex[numVertices];
		
		for(int i = 0; i < numVertices; i++){
			vertices[i] = solver. new Vertex();
		}
		
		
		for(int i = 0; i < numVertices; i++){
			inputData = new StringTokenizer(reader.readLine());
			
			vertices[i].quality = Integer.parseInt(inputData.nextToken());
			
			int numNeighbors = Integer.parseInt(inputData.nextToken());
			
			for(int j = 0; j < numNeighbors; j++){
				vertices[i].aList.add(vertices[Integer.parseInt(inputData.nextToken())-1]);
			}
		}
		reader.close();
		
		Arrays.sort(vertices);
		
		for(int i = 0; i < numVertices; i++){
			vertices[i].vertexNumber = i;
		}
		
		cost = new int[numVertices][numVertices];
		
		aList = new int[numVertices][];
		
		for(int i = 0; i < numVertices; i++){
			aList[i] = new int[vertices[i].aList.size()];
			
			for(int j = 0; j < vertices[i].aList.size(); j++){
				aList[i][j] = vertices[i].aList.get(j).vertexNumber;
			}
		}
		
		quality = new int[numVertices];
		
		for(int i = 0; i < numVertices; i++){
			quality[i] = vertices[i].quality;
		}
		
		for(int startVertex = 0; startVertex < numVertices; startVertex++){
			int[] currentCosts = new int[numVertices];
			Arrays.fill(currentCosts, Integer.MAX_VALUE);
			currentCosts[startVertex] = 0;
			
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(startVertex);
			
			while(!queue.isEmpty()){
				int current = queue.remove();
				for(int neighbor : aList[current]){
					if(currentCosts[neighbor] > currentCosts[current] + edgeCost){
						currentCosts[neighbor] = currentCosts[current] + edgeCost;
						queue.add(neighbor);
					}
				}
			}
			cost[startVertex] = currentCosts;
		}
		
		int maxEnergy = 0;
		
		dp = new int[numVertices];
		for(int currentVertex = 0; currentVertex < numVertices; currentVertex++){
			int currentMax = quality[currentVertex];
			for(int previousVertex = 0; previousVertex < currentVertex; previousVertex++){
				currentMax = Math.max(currentMax, dp[previousVertex] - cost[previousVertex][currentVertex] + quality[currentVertex]);
			}
			dp[currentVertex] = currentMax;
			maxEnergy = Math.max(maxEnergy, currentMax);
		}
		
		
		printer.println(maxEnergy);
		printer.close();
	}
	
	class Vertex
	implements Comparable<Vertex> {
		int quality;
		int maxEnergy;
		int vertexNumber;

		Vertex() {
		}

		Vertex(int a) {
			this.quality = a;
		}

		public int compareTo(Vertex o) {
			return Integer.compare(quality, o.quality);
		}

		public boolean equals(Object other) {
			if (Vertex.class != other.getClass()) {
				return false;
			}
			Vertex oTuple = (Vertex) other;

			if (quality == oTuple.quality) {
				return true;
			}
			return false;
		}
		
		ArrayList<Vertex> aList = new ArrayList<Vertex>();

	}


}
