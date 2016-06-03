/*ID: eric.ca1
LANG: JAVA
TASK: race3
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class race3 {
	
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static final ArrayList<Integer> EMPTY = new ArrayList<Integer>();

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("race3.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("race3.out")));
		
		for(int currentVertex = 0; true; currentVertex++){
			String input = reader.readLine().trim();
			if(input.equals("-1")){
				break;
			}
			aList.add(new ArrayList<Integer>());
			StringTokenizer inputData = new StringTokenizer(input);
			
			int nextInt = Integer.parseInt(inputData.nextToken());
			while(nextInt != -2){
				aList.get(currentVertex).add(nextInt);
				nextInt = Integer.parseInt(inputData.nextToken());
			}
		}
		reader.close();
		int numVertices = aList.size();
		
		ArrayList<Integer> unavoidableVertex = new ArrayList<Integer>();
		for(int candidate = 1; candidate < numVertices-1; candidate++){
			ArrayList<Integer> adjacencies = aList.get(candidate);
			aList.set(candidate, EMPTY);
			boolean[] visited = new boolean[numVertices];
			dfs(0, visited);
			if(!visited[numVertices-1]){
				unavoidableVertex.add(candidate);
			}
			aList.set(candidate, adjacencies);
		}
		
		ArrayList<Integer> splittingVertex = new ArrayList<Integer>();
		
		candidateLoop:
		for(int candidate : unavoidableVertex){
			ArrayList<Integer> adjacencies = aList.get(candidate);
			aList.set(candidate, EMPTY);
			boolean[] reachableFromStart = new boolean[numVertices];
			dfs(0, reachableFromStart);
			aList.set(candidate, adjacencies);
			
			boolean[] reachableFromEnd = new boolean[numVertices];
			dfs(candidate, reachableFromEnd);
			
			for(int testVertex = 0; testVertex < numVertices; testVertex++){
				if(testVertex == candidate){
					continue;
				}
				if(!(reachableFromStart[testVertex]^reachableFromEnd[testVertex])){
					continue candidateLoop;
				}
			}
			splittingVertex.add(candidate);
		}
		
		printer.print(unavoidableVertex.size());
		for(int i : unavoidableVertex){
			printer.print(" " + i);
		}
		printer.println();
		
		printer.print(splittingVertex.size());
		for(int i : splittingVertex){
			printer.print(" " + i);
		}
		
		printer.println();
		printer.close();
	}
	
	public static void dfs(int vertex, boolean[] visited){
		visited[vertex] = true;
		for(int n : aList.get(vertex)){
			if(!visited[n]){
				dfs(n, visited);
			}
		}
	}

}
