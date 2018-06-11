import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Cowmooting {

	static int maxC;
	static int nE;
	static int nV;

	static ArrayList<Integer>[] aList;
	static ArrayList<Integer>[] tList;
	static ArrayList<Integer>[] cList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		maxC = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());
		nV = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		cList = new ArrayList[nV];
		tList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			cList[i] = new ArrayList<>();
			tList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int t = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			tList[a].add(t);
			tList[b].add(t);
			cList[a].add(c);
			cList[b].add(c);
		}

		int[][] dp = new int[nV][100 * 100];

		for (int[] a : dp) {
			Arrays.fill(a, Integer.MAX_VALUE / 2);
		}

		dp[0][0] = 0;

		for (int cTime = 0; cTime < 100 * 100; cTime++) {
			for (int cV = 0; cV < nV; cV++) {
				if (dp[cV][cTime] != Integer.MAX_VALUE / 2) {
					for (int aI = 0; aI < aList[cV].size(); aI++) {
						int aV = aList[cV].get(aI);
						int nT = cTime + tList[cV].get(aI);
						int nC = dp[cV][cTime] + cList[cV].get(aI);

						if (nT < 100 * 100 && nC < dp[aV][nT]) {
							dp[aV][nT] = nC;
						}
					}
				}
			}
		}

		int minT = -1;

		for (int cTime = 0; cTime < 100 * 100; cTime++) {
			if(dp[1][cTime] <= maxC) {
				minT = cTime;
				break;
			}
		}
		printer.println(minT);
		printer.close();
	}
}
