import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Simplify {
	
	static int numVertices;
	static int numEdges;
	
	static Edge[] edges;
	
	static Simplify helper = new Simplify();
	
	static int K = 0;
	
	static int[] parent;
	
	static int numWays = 1;
	
	public static boolean merge(int a, int b){
		int aParent = findParent(a);
		int bParent = findParent(b);
		
		if(aParent != bParent){
			parent[aParent] = bParent;
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public static boolean inSameSet(int a, int b){
		int aParent = findParent(a);
		int bParent = findParent(b);
		
		if(aParent != bParent){
			parent[aParent] = bParent;
			return true;
		}
		else{
			return false;
		}
	}
	
	public static int findParent(int x){
		if(parent[x] == x){
			return x;
		}
		else{
			return parent[x] = findParent(parent[x]);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		
		edges = new Edge[numEdges*2];
		parent = new int[numEdges*2];
		
		for(int i = 0; i < numEdges; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int n = Integer.parseInt(inputData.nextToken());
			Edge e1 = helper.new Edge(a, b, n);
			Edge e2 = helper.new Edge(b, n, a);
			edges[K++] = e1;
			edges[K++] = e2;
		}
		Arrays.sort(edges);
		
		
		for(int edgeNum = 0; edgeNum < edges.length; edgeNum++){
			Edge[] SLE = new Edge[3];
			SLE[0] = edges[edgeNum];
			int i = 1;
			
			while(edges[edgeNum+1].length == SLE[0].length){
				SLE[i++] = edges[++edgeNum];
			}
			
			if(i == 2){
				if(inSameSet(SLE[0].endPointA, SLE[1].endPointB) &&
						inSameSet(SLE[0].endPointA, SLE[1].endPointB)){
					numWays *= 2;
				}
			}
			else if(i == 3){
				
				
				
				
				
				
				
				
				
			}
			
		}
		
		
	}
	
	class Edge implements Comparable<Edge>{
		int endPointA;
		int endPointB;
		
		int length;

		public int compareTo(Edge o) {
			return Integer.compare(length, o.length);
		}
		
		public Edge(int endPointA, int endPointB, int length){
			this.endPointA = endPointA;
			this.endPointB = endPointB;
			this.length = length;
		}
		
		
		
	}

}
