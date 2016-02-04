import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CowTelephones {
	
	
	static int K;
	static int N;
	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	static int ans;
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());
		for(int i = 0; i < N; i++){
			aList.add(new ArrayList<Integer>());
		}
		
		for(int i = 2; i <= N; i++){
			inputData = new StringTokenizer(reader.readLine());
			int a_i = Integer.parseInt(inputData.nextToken()) - 1;
			int b_i = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a_i).add(b_i);
			aList.get(b_i).add(a_i);
		}
		reader.close();
		dfs(0, 0);
		System.out.println(ans);
	}
	
	public static int dfs(int currentNode, int parent){
		int sum = 0;
		if(aList.get(currentNode).size() == 1){
			sum = 1;
		}
		for(int neighbor : aList.get(currentNode)){
			if(neighbor == parent){
				continue;
			}
			sum += dfs(neighbor, currentNode);	
		}
		
		if(sum > K*2){
			ans += K;
			return 0;
		}
		else{
			ans += sum/2;
			return sum % 2;
		}
		
	}
	

}
