import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MixedUpCows {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numCows = Integer.parseInt(inputData.nextToken());
		long difference = Integer.parseInt(inputData.nextToken());
		
		long[] value = new long[numCows];
		for(int i = 0; i < numCows; i++){
			value[i] = Integer.parseInt(reader.readLine());
		}
		
		
		int end = 1 << numCows;
		
		long[][] dp = new long[end][numCows];
		
		for(int i = 0; i < numCows; i++){
			dp[1 << i][i] = 1;
		}
		
		for(int set = 3; set < end; set++){
			for(int endCow = 0; endCow < numCows; endCow++){
				if((set & (1 << endCow)) == 0){
					continue;
				}
				for(int previous = 0; previous < numCows; previous++){
					if((set & (1 << previous)) != 0 && Math.abs(value[endCow]-value[previous]) > difference){
						dp[set][endCow] += dp[set ^ (1 << endCow)][previous];
					}
				}
			}
		}
		
		long ans = 0;
		for(int i = 0; i < numCows; i++){
			ans += dp[end-1][i];
		}
		System.out.println(ans);
	}

}
