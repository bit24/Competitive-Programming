import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class MilkScheduling {
	
	static int maxTime;
	static int numVertices;
	static int numEdges;
	
	static int[] vertexCost;
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	
	static ArrayList<ArrayList<Integer>> rList = new ArrayList<ArrayList<Integer>>();
	
	static MilkScheduling bundle = new MilkScheduling();
	
	static int[] numReferences;
	
	static int[] cost;
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData  = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		numEdges = Integer.parseInt(inputData.nextToken());
		
		vertexCost = new int[numVertices];
		for(int i = 0; i < numVertices; i++){
			vertexCost[i] = Integer.parseInt(reader.readLine());
			aList.add(new ArrayList<Integer>());
			rList.add(new ArrayList<Integer>());
		}
		
		numReferences = new int[numVertices];
		for(int i = 0; i < numEdges; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a =Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a).add(b);
			rList.get(b).add(a);
			numReferences[b]++;
		}
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		for(int i = 0; i < numVertices; i++){
			if(numReferences[i] == 0){
				queue.add(i);
			}
		}
		
		int totalMax = 0;
		
		cost = new int[numVertices];
		while(!queue.isEmpty()){
			int current = queue.remove();
			int max = 0;
			for(int previous : rList.get(current)){
				max = Math.max(max, cost[previous]);
			}
			cost[current] = max + vertexCost[current];
			totalMax = Math.max(totalMax, cost[current]);
			
			for(int next : aList.get(current)){
				numReferences[next]--;
				if(numReferences[next] <= 0){
					queue.add(next);
				}
			}
		}
		System.out.println(totalMax);
		
	}
}
