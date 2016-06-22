import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class CountyFair {
	
	static int numVertices;
	
	static int[][] time;
	
	static HashMap<Integer, Integer> sTNS = new HashMap<Integer, Integer>();
	
	static CountyFair b = new CountyFair();
	
	static Pair[] vertices;
	
	static int[] dp;
	
	static int startVertex;
	
	static int[] newOrdering;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numVertices = Integer.parseInt(reader.readLine());
		
		vertices = new Pair[numVertices];
		dp = new int[numVertices];
		
		for(int i = 0; i < numVertices; i++){
			Pair p = b.new Pair();
			
			p.time = Integer.parseInt(reader.readLine());
			p.number = i;
			vertices[i] = p;
			dp[i] = Integer.MIN_VALUE;
		}
		
		Arrays.sort(vertices);
		
		newOrdering = new int[numVertices];
		
		for(int i = 0 ; i < numVertices; i++){
			 newOrdering[vertices[i].number] = i;
		}
		
		
		time = new int[numVertices][numVertices];
		
		for(int i = 0; i < numVertices; i++){
			for(int j = 0; j < numVertices; j++){
				time[newOrdering[i]][newOrdering[j]] = Integer.parseInt(reader.readLine());
			}
		}
		
		int startVertex = newOrdering[0];
		
		dp[startVertex] = 1;
		
		for(int i = 0; i < numVertices; i++){
			if(time[0][vertices[i].number] < vertices[i].time){
				dp[i] = 1;
			}
		}
		
		for(int cV = 0; cV < numVertices; cV++){
			for(int pV = 0; pV < cV; pV++){
				if(vertices[pV].time + time[pV][cV] < vertices[cV].time){
					dp[cV] = Math.max(dp[cV], dp[pV] + 1);
				}
			}
		}
		
		int max = 0;
		
		for(int i : dp){
			max = Math.max(max, i);
		}
		System.out.println(max);
		
		
	}
	
	class Pair implements Comparable<Pair>{
		int time;
		int number;
		
		public int compareTo(Pair o) {
			return Integer.compare(time, o.time);
		}
		
		
		
	}

}
