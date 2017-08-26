import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class CanadaTourSolver {
		
	static int[][] dp;
	static boolean[][] edge;
	
	static int n;
	static int v;
	
	static HashMap<String, Integer> index = new HashMap<String, Integer>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		n = Integer.parseInt(inputData.nextToken());
		v = Integer.parseInt(inputData.nextToken());
		dp = new int[n+1][n+1];
		edge = new boolean[n+1][n+1];
		for(int i = 0; i <= n; i++){
			for(int j = 0; j <= n; j++){
				dp[i][j] = -1;
			}
		}
		for(int i = 0; i < n; i++){
			String str = reader.readLine().trim();
			index.put(str, i);
		}
		for(int i = 0; i < v; i++){
			inputData = new StringTokenizer(reader.readLine());
			int ind0 = index.get(inputData.nextToken());
			int ind1 = index.get(inputData.nextToken());
			edge[ind0][ind1] = true;
			edge[ind1][ind0] = true;
		}
		int ans = getDp(0, 0) - 1;
		if(ans <= 0){
			ans = 1;
		}
		System.out.println(ans);
	}
	
	public static int getDp(int a, int b){
		if(a > b){
			int temp = a;
			a = b;
			b = temp;
		}
		if(dp[a][b] != -1){
			return dp[a][b];
		}
		if(a == n-1 && b == n-1){
			return dp[a][b] = 1;
		}
		
		if(a == b && a != 0){
			return dp[a][b] = -9999999;
		}
		for(int i = a+1; i < n; i++){
			if(edge[a][i]){
				dp[a][b] = Math.max(dp[a][b], 1+getDp(i, b));;
			}
		}
		return dp[a][b];
	}
	
}
