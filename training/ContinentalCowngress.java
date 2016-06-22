import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ContinentalCowngress {
	
	static int numBills;
	static int numCows;
	static int[][] neighbors;
	static ArrayList<ArrayList<Integer>> visitable = new ArrayList<ArrayList<Integer>>();
	static boolean[] visited = new boolean[numBills];

	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		
		numBills = Integer.parseInt(inputData.nextToken());
		numCows = Integer.parseInt(inputData.nextToken());
		
		neighbors = new int[numCows][2];
		for(int i = 0; i < numCows; i++){
			inputData = new StringTokenizer(reader.readLine());
			int decision = Integer.parseInt(inputData.nextToken());
			
			decision *= inputData.nextToken().equals("Y") ? 1 : 2;
			
			neighbors[i][0] = decision;	
			
			decision = Integer.parseInt(inputData.nextToken());
			
			decision *= inputData.nextToken().equals("Y") ? 1 : 2;
			
			neighbors[i][1] = decision;	
		}
		visited = new boolean[numBills*2];
	}
	
	public static void DFS(int start){
		Arrays.fill(visited, false);
		visited[start] = true;
		for(int i = 0; i < 2; i++){
			if(!visited[neighbors[start][i]]){
				visitable.get(start).add(neighbors[start][i]);
				DFS(neighbors[start][i]);
			}
		}
		
	}
	
	
}
