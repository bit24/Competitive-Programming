import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class VacationPlanning {
	
	static int numVertices;
	static int numHubs;
	static int numEdges;
	static int numRequests;
	static long[][] toDistance;
	static long[][] fromDistance;
	
	static ArrayList<ArrayList<Pair>> aList = new ArrayList<ArrayList<Pair>>();
	static ArrayList<ArrayList<Pair>> rAList = new ArrayList<ArrayList<Pair>>();
		
	static VacationPlanning b = new VacationPlanning();
	
	static int[] hubs;
		
	static boolean[] isHub;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("vacationgold.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("vacationgold.out")));
    StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		numHubs = Integer.parseInt(inputData.nextToken());
		numRequests = Integer.parseInt(inputData.nextToken());
		
		for(int i = 0; i < numVertices; i++){
			aList.add(new ArrayList<Pair>());
			rAList.add(new ArrayList<Pair>());
		}
		
		for(int i = 0; i < numEdges; i++){
			inputData = new StringTokenizer(reader.readLine());
			int s = Integer.parseInt(inputData.nextToken()) - 1;
			int e = Integer.parseInt(inputData.nextToken()) - 1;
			int d = Integer.parseInt(inputData.nextToken());
			aList.get(s).add(b.new Pair(e, d));
			rAList.get(e).add(b.new Pair(s, d));
		}
		
		hubs = new int[numHubs];
		isHub = new boolean[numVertices];
		
		for(int i = 0; i < numHubs; i++){
			int hub =  Integer.parseInt(reader.readLine()) - 1;
			hubs[i] = hub;
			isHub[hub] = true;
		}
		reader.close();	
      
		toDistance = new long[numHubs][numVertices];
		fromDistance = new long[numHubs][numVertices];
		
		for(int i = 0; i < numHubs; i++){
			long[] distance = toDistance[i];
			int currentHub = hubs[i];
			Arrays.fill(distance, Long.MAX_VALUE);
			distance[currentHub] = 0;
			PriorityQueue<State> queue = new PriorityQueue<State>();
			queue.add(b.new State(currentHub, 0));
			
			while(!queue.isEmpty()){
				State cS = queue.remove();
				int cV = cS.vertex;
				long cD = cS.distance;
				
				if(cD > distance[cV]){
					continue;
				}
				
				boolean sHub = isHub[cV];
				
				for(Pair neighborP : rAList.get(cV)){
					int neighbor = neighborP.value;
					if(!(sHub || isHub[neighbor])){
						continue;
					}
					long cost = neighborP.cost + cD;
					if(cost < distance[neighbor]){
						distance[neighbor] = cost;
						queue.add(b.new State(neighbor, cost));
					}
				}
			}
			
			distance = fromDistance[i];
			Arrays.fill(distance, Long.MAX_VALUE);
			distance[currentHub] = 0;
			queue = new PriorityQueue<State>();
			queue.add(b.new State(currentHub, 0));
			while(!queue.isEmpty()){
				State cS = queue.remove();
				int cV = cS.vertex;
				long cD = cS.distance;
				boolean sHub = isHub[cV];
				
				if(cD > distance[cV]){
					continue;
				}

				for(Pair neighborP : aList.get(cV)){
					int neighbor = neighborP.value;
					if(!(sHub || isHub[neighbor])){
						continue;
					}
					long cost = neighborP.cost + cD;
					if(cost < distance[neighbor]){
						distance[neighbor] = cost;
						queue.add(b.new State(neighbor, cost));
					}
				}
			}
		}
	
		int numPossible = 0;
		int total = 0;
		
		for(int currentRequest = 0; currentRequest < numRequests; currentRequest++){
			inputData = new StringTokenizer(reader.readLine());
			int start = Integer.parseInt(inputData.nextToken()) - 1;
			int end = Integer.parseInt(inputData.nextToken()) - 1;
			long best = Long.MAX_VALUE;
			for(int currentHub = 0; currentHub < numHubs; currentHub++){
				if(toDistance[currentHub][start] == Long.MAX_VALUE || fromDistance[currentHub][end] == Long.MAX_VALUE){
					continue;
				}
				best = Math.min(best, toDistance[currentHub][start] + fromDistance[currentHub][end]);
			}
			if(best != Long.MAX_VALUE){
				numPossible++;
				total += best;
			}
		}
		
		printer.println(numPossible);
		printer.println(total);
		printer.close();
	}
	
	class State implements Comparable<State>{
		int vertex;
		long distance;
		
		public State(int v, long d){
			vertex = v;
			distance = d;
		}

		public int compareTo(State o) {
			return Long.compare(distance, o.distance);
		}
	}
	
	class Pair{
		int value;
		long cost;
		
		public Pair(int n, long c){
			value = n;
			cost = c;
		}
	}
}
