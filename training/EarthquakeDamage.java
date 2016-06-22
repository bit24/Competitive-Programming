import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class EarthquakeDamage {
	
	static int P;
	static int C;
	static int N;
	static int[] reports;
	static boolean[] isPossible;
	
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	
	static int visitable = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		P = Integer.parseInt(inputData.nextToken());
		C = Integer.parseInt(inputData.nextToken());
		N = Integer.parseInt(inputData.nextToken());
		
		for(int i = 0; i < P; i++){
			aList.add(new ArrayList<Integer>());
		}
		
		for(int i = 0; i < C; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a_i = Integer.parseInt(inputData.nextToken()) - 1;
			int b_i = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a_i).add(b_i);
			aList.get(b_i).add(a_i);
		}
		reports = new int[N];
		for(int i = 0; i < N; i++){
			reports[i] = Integer.parseInt(reader.readLine()) - 1;
		}
		reader.close();
		isPossible = new boolean[P];
		
		for(int i = 0; i < isPossible.length; i++){
			isPossible[i] = true;
		}
		
		for(int currentReport : reports){
			isPossible[currentReport] = false;
			for(int neighbor : aList.get(currentReport)){
				isPossible[neighbor] = false;
			}
		}
		isPossible[0] = false;
		dfs(0);
		System.out.println(P - 1- visitable);
	}
	
	static void dfs(int currentNode){
		for(int neighbor : aList.get(currentNode)){
			if(isPossible[neighbor]){
				visitable++;
				isPossible[neighbor] = false;
				dfs(neighbor);
			}
		}
	}

}
