import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_459B {

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] wList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		wList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			wList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int u = Integer.parseInt(inputData.nextToken()) - 1;
			int v = Integer.parseInt(inputData.nextToken()) - 1;
			int w = inputData.nextToken().charAt(0) - 'a';
			aList[u].add(v);
			wList[u].add(w);
		}
		dp = new int[nV][nV][27];
		for (int i = 0; i < nV; i++) {
			for (int j = 0; j < nV; j++) {
				Arrays.fill(dp[i][j], -1);
			}
		}

		for (int i = 0; i < nV; i++) {
			for (int j = 0; j < nV; j++) {
				if (calc(i, j, 0)) {
					printer.print('A');
				} else {
					printer.print('B');
				}
			}
			printer.println();
		}
		printer.close();
	}

	static int[][][] dp;

	static boolean calc(int u, int v, int w) {
		if (dp[u][v][w] != -1) {
			return dp[u][v][w] == 1;
		}

		for (int aI = 0; aI < aList[u].size(); aI++) {
			int aV = aList[u].get(aI);
			int eW = wList[u].get(aI);
			if (w <= eW) {
				if (calc(v, aV, eW) == false) {
					dp[u][v][w] = 1;
					return true;
				}
			}
		}
		dp[u][v][w] = 0;
		return false;
	}

}
