import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RunningAwayFromTheBarn {

	static ArrayList<Integer>[] aList;

	static int[][] anc;
	static long[][] jCost;

	static int[] preRem;

	static int[] ans;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("runaway.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("runaway.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		long mD = Long.parseLong(inputData.nextToken());

		aList = new ArrayList[nV];

		anc = new int[nV][18];
		jCost = new long[nV][18];

		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
			Arrays.fill(anc[i], -1);
		}

		for (int i = 1; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int p = Integer.parseInt(inputData.nextToken()) - 1;
			long c = Long.parseLong(inputData.nextToken());
			anc[i][0] = p;
			jCost[i][0] = c;
			aList[p].add(i);
		}
		reader.close();

		for (int jL = 0; jL < 17; jL++) {
			for (int cV = 0; cV < nV; cV++) {
				int jmp = anc[cV][jL];
				if (jmp != -1) {
					int dJmp = anc[jmp][jL];
					if (dJmp != -1) {
						anc[cV][jL + 1] = dJmp;
						jCost[cV][jL + 1] = jCost[cV][jL] + jCost[jmp][jL];
					}
				}
			}
		}
		preRem = new int[nV];

		for (int sV = 0; sV < nV; sV++) {
			long cSum = 0;
			int cV = sV;
			for (int jL = 17; jL >= 0; jL--) {
				if (anc[cV][jL] != -1 && cSum + jCost[cV][jL] <= mD) {
					cSum += jCost[cV][jL];
					cV = anc[cV][jL];
				}
			}
			preRem[cV]++;
		}

		ans = new int[nV];
		calcVal(0);
		for (int i = 0; i < nV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static int calcVal(int cV) {
		int cnt = 1;

		for (int adj : aList[cV]) {
			cnt += calcVal(adj);
		}
		ans[cV] = cnt;
		return cnt - preRem[cV];
	}

}
