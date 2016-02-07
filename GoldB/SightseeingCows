import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SightseeingCows {
	static SightseeingCows b = new SightseeingCows();

	static int numVertices;
	static int numEdges;
	
	static int[] fun;
	
	static Edge[] edges;
	static double[] cost;

	static int[] length;
	
	static double errorPermitted = 0.0000001;
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		
		fun = new int[numVertices];
		edges = new Edge[numEdges];
		cost = new double[numVertices];

		length = new int[numEdges];
		
		for(int i = 0; i < numVertices; i++){
			fun[i] = Integer.parseInt(reader.readLine());
		}
		
		for(int i = 0; i < numEdges; i++){
			inputData = new StringTokenizer(reader.readLine());
			int s = Integer.parseInt(inputData.nextToken()) - 1;
			int e = Integer.parseInt(inputData.nextToken()) - 1;
			int t = Integer.parseInt(inputData.nextToken());
			edges[i] = b . new Edge(s, e);
			length[i] = t;
		}
		
		System.out.printf("%.2f", 1/binarySearch());

		
	}
	
	public static boolean isPossible(double answer){
		Arrays.fill(cost, 0);
		for(int i = 0; i < numEdges; i++){
			Edge currentEdge = edges[i];
			currentEdge.cost = length[i] - answer*fun[currentEdge.eVertex];
		}
		
		for(int i = 0; i < numVertices; i++){
			for(Edge currentEdge : edges){
				if(cost[currentEdge.sVertex] + currentEdge.cost < cost[currentEdge.eVertex]){
					cost[currentEdge.eVertex] = cost[currentEdge.sVertex] + currentEdge.cost;
				}
				
			}
		}
		
		for(Edge currentEdge : edges){
			if(cost[currentEdge.sVertex] + currentEdge.cost < cost[currentEdge.eVertex]){
				return true;
			}
			
		}
		return false;
				
	}
	
	public static double binarySearch(){
		double low = 0;
		double mid = 0;
		double high = 1000;
		while(high - low > errorPermitted){
			mid = (high + low) / 2;
			if(isPossible(mid)){
				high = mid;
			}
			else{
				low = mid;
			}
		}
		return low;
	}
	
	class Edge{
		int sVertex;
		int eVertex;
		double cost;
		
		public Edge(int s, int e){
			sVertex = s;
			eVertex = e;
		}
	}
}
