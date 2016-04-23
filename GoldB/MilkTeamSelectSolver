import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MilkTeamSelectSolver {

	static int numCows;
	static int requiredProduction;
	static int[] production;
	static ArrayList<Integer>[] children;
	static int[] rank;

	static long[][][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numCows = Integer.parseInt(inputData.nextToken());
		requiredProduction = Integer.parseInt(inputData.nextToken());
		production = new int[numCows + 1];
		children = new ArrayList[numCows+1];
		for(int i = 0; i <= numCows; i++){
			children[i] = new ArrayList<Integer>();
		}
		
		for (int i = 1; i <= numCows; i++) {
			inputData = new StringTokenizer(reader.readLine());
			production[i] = Integer.parseInt(inputData.nextToken());
			children[Integer.parseInt(inputData.nextToken())].add(i);
		}

		rank = new int[numCows+1];
		calculateRank(0);
		dp = new long[2][numCows + 1][numCows + 1];

		for (int i = 0; i <= numCows; i++) {
			Arrays.fill(dp[0][i], Integer.MIN_VALUE/8);
			Arrays.fill(dp[1][i], Integer.MIN_VALUE/8);
		}
		
		computeDP(0);
		int ans = 0;
		for(ans = numCows-1; ans >= 0; ans--){
			if(dp[0][0][ans] >= requiredProduction){
				break;
			}
		}
		System.out.println(ans);
	}

	
	public static void computeDP(int currentNode) {
	dp[0][currentNode][0] = 0;
        dp[1][currentNode][0] = production[currentNode];

	int maxRelations = 0;

        for (int childIndex = 0; childIndex < children[currentNode].size(); childIndex++ ) {
			int childNum = children[currentNode].get(childIndex);
			computeDP(childNum);
			maxRelations += rank[childNum] + 1;
			for (int numRelations = maxRelations; numRelations >= 0; numRelations--) {
				for (int allowance = 0; allowance <= numRelations; allowance++) {
					dp[0][currentNode][numRelations] = Math.max(dp[0][currentNode][numRelations],
							dp[0][currentNode][numRelations - allowance]
									+ Math.max(dp[0][childNum][allowance], dp[1][childNum][allowance]));

					dp[1][currentNode][numRelations] = Math.max(dp[1][currentNode][numRelations],
							dp[1][currentNode][numRelations - allowance] + dp[0][childNum][allowance]);

					if (allowance < numRelations) {
						dp[1][currentNode][numRelations] = Math.max(dp[1][currentNode][numRelations],
								dp[1][currentNode][numRelations - allowance - 1] + dp[1][childNum][allowance]);
					}
				}
			}
		}
	}

	public static int calculateRank(int currentNode) {
		int count = 0;
		for (int child : children[currentNode]) {
			count += calculateRank(child) + 1;
		}
		return rank[currentNode] = count;
	}
}
