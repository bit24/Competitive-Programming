import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.StringTokenizer;

class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numProblems = Integer.parseInt(reader.readLine());

		while (numProblems-- > 0) {
			int numLCit = Integer.parseInt(reader.readLine());
			String[] lCitP = new String[numLCit];
			int[] lValue = new int[numLCit];
			for (int i = 0; i < numLCit; i++) {
				StringTokenizer inputData = new StringTokenizer(reader.readLine());
				inputData.nextToken();
				lCitP[i] = inputData.nextToken();
				lValue[i] = Integer.parseInt(inputData.nextToken());
			}

			int numRCit = Integer.parseInt(reader.readLine());
			String[] rCitP = new String[numRCit];
			int[] rValue = new int[numRCit];
			for (int i = 0; i < numRCit; i++) {
				StringTokenizer inputData = new StringTokenizer(reader.readLine());
				inputData.nextToken();
				rCitP[i] = inputData.nextToken();
				rValue[i] = Integer.parseInt(inputData.nextToken());
			}

			long[][] dp = new long[numLCit][numRCit];
			int[][] numBridges = new int[numLCit][numRCit];

			for (int l = 0; l < numLCit; l++) {
				for (int r = 0; r < numRCit; r++) {
					long best = 0;
					int bridgeNum = 0;
					if (lCitP[l].equals(rCitP[r])) {
						best = lValue[l] + rValue[r];
						bridgeNum = 1;
						if (l != 0 && r != 0) {
							best += dp[l - 1][r - 1];
							bridgeNum += numBridges[l - 1][r - 1];
						}
					}
					if (l != 0) {
						if (best < dp[l - 1][r]) {
							best = dp[l - 1][r];
							bridgeNum = numBridges[l - 1][r];
						}
						else if(best == dp[l - 1][r]){
							bridgeNum = Math.min(bridgeNum, numBridges[l - 1][r]);
						}
					}
					if (r != 0) {
						if (best < dp[l][r - 1]) {
							best = dp[l][r - 1];
							bridgeNum = numBridges[l][r - 1];
						}
						else if(best == dp[l][r - 1]){
							bridgeNum = Math.min(bridgeNum, numBridges[l][r-1]);	
						}
					}
					dp[l][r] = best;
					numBridges[l][r] = bridgeNum;

				}
			}

			if (numLCit == 0 || numRCit == 0) {
				printer.println(0 + " " + 0);
			} else {
				printer.print(dp[numLCit - 1][numRCit - 1] + " ");
				printer.println(numBridges[numLCit - 1][numRCit - 1]);
			}

		}

		reader.close();
		printer.close();

	}

}
