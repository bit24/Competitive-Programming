import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Wormholes {
	
	static int numFarms;
	
	static int numVertices = 0;
	
	static int numBiPaths;
	static int numWormholes;
	
	static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	
	static Wormholes b = new Wormholes();
	
	static int[] distance;
	
	static ArrayList<String> output = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		numFarms = Integer.parseInt(reader.readLine());
		for(int currentFarm = 0; currentFarm < numFarms; currentFarm++){
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			numVertices = Integer.parseInt(inputData.nextToken());
			numBiPaths = Integer.parseInt(inputData.nextToken());
			numWormholes = Integer.parseInt(inputData.nextToken());
			
			for(int i = 0; i < numBiPaths; i++){
				inputData = new StringTokenizer(reader.readLine());
				int s = Integer.parseInt(inputData.nextToken()) - 1;
				int e = Integer.parseInt(inputData.nextToken()) - 1;
				int time = Integer.parseInt(inputData.nextToken());
				edgeList.add(b.new Edge(s, e, time));
				edgeList.add(b.new Edge(e, s, time));
				
			}
			
			for(int i = 0; i < numWormholes; i++){
				inputData = new StringTokenizer(reader.readLine());
				int s = Integer.parseInt(inputData.nextToken()) - 1;
				int e = Integer.parseInt(inputData.nextToken()) - 1;
				int time = -1*Integer.parseInt(inputData.nextToken());
				edgeList.add(b.new Edge(s, e, time));
			}
			
			distance = new int[numVertices];
			
			for(int i = 0; i < numVertices-1; i++){
				for(Edge currentEdge : edgeList){
					if(distance[currentEdge.startVertex] + currentEdge.distance < distance[currentEdge.endVertex]){
						distance[currentEdge.endVertex] = distance[currentEdge.startVertex] + currentEdge.distance;
					}
				}
			}
			
			boolean hasCycle = false;
			for(Edge currentEdge : edgeList){
				if(distance[currentEdge.startVertex] + currentEdge.distance < distance[currentEdge.endVertex]){
					hasCycle = true;
					break;
				}
			}
			
			if(hasCycle){
				output.add("YES");
			}
			else{
				output.add("NO");
			}

			edgeList.clear();
			
			Arrays.fill(distance, 0);
		}
		
		for(String s : output){
			System.out.println(s);
		}
		
	}
	
	public class Edge{
		int startVertex;
		int endVertex;
		int distance;
		
		public Edge(int s, int e, int d){
			startVertex = s;
			endVertex = e;
			distance = d;
		}
		
		public Edge(int s, int e){
			startVertex = s;
			endVertex = e;
		}
	}

}
