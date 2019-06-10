import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PalindromicPaths {

	static int len;
	static char[][] ele;
	static final int MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		new PalindromicPaths().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("palpath.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("palpath.out")));
		len = Integer.parseInt(reader.readLine());

		ele = new char[len * 2 - 1][len];

		for (int cR = 0; cR < len; cR++) {
			String inputLine = reader.readLine();
			for (int cC = 0; cC < len; cC++) {
				int cD = cR + cC;

				if (cD < len) {
					ele[cD][cC] = inputLine.charAt(cC);
				} else {
					ele[cD][(len - 1) - cR] = inputLine.charAt(cC);
				}
			}
		}
		reader.close();

		int[][] cStates = new int[len][len];

		for (int i = 0; i < len; i++) {
			cStates[i][i] = 1;
		}

		for (int dS = len; dS >= 2; dS--) {
			int[][] nStates = new int[len][len];
			int nLD = dS - 2;
			int nRD = (len - 1) + (len - dS) + 1;

			for (int lP = 0; lP < dS; lP++) {
				for (int rP = 0; rP < dS; rP++) {
					int cNW = (cStates[lP][rP] %= MOD);
					if (cNW == 0) {
						continue;
					}

					if (lP - 1 >= 0) {
						if (rP - 1 >= 0 && ele[nLD][lP
								- 1] == ele[nRD][rP - 1]) {
							nStates[lP - 1][rP - 1] += cNW;
							
							if(nStates[lP - 1][rP - 1] >= MOD){
								nStates[lP - 1][rP - 1] %= MOD;
							}
						}
						if (rP < dS - 1 && ele[nLD][lP
								- 1] == ele[nRD][rP]) {
							nStates[lP - 1][rP] += cNW;
							
							if(nStates[lP - 1][rP] >= MOD){
								nStates[lP - 1][rP] %= MOD;
							}
						}
					}
					if (lP < dS - 1) {
						if (rP - 1 >= 0
								&& ele[nLD][lP] == ele[nRD][rP
										- 1]) {
							nStates[lP][rP - 1] += cNW;
							
							if(nStates[lP][rP - 1] >= MOD){
								nStates[lP][rP - 1] %= MOD;
							}
						}
						if (rP < dS - 1
								&& ele[nLD][lP] == ele[nRD][rP]) {
							nStates[lP][rP] += cNW;
							
							if(nStates[lP][rP] >= MOD){
								nStates[lP][rP] %= MOD;
							}
						}
					}
				}
			}
			cStates = nStates;
		}
		printer.println(cStates[0][0] % MOD);
		printer.close();
	}
}