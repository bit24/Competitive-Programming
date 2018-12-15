import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class LinkCowTree {

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[N + 1];
		for (int i = 0; i <= N; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());

			if (a > b) {
				aList[b].add(a);
			} else {
				aList[a].add(b);
			}
		}

		for (ArrayList<Integer> cL : aList) {
			Collections.sort(cL);
		}

		ArrayList<Integer>[] qRight = new ArrayList[N + 1];
		ArrayList<Integer>[] qInd = new ArrayList[N + 1];

		for (int i = 0; i <= N; i++) {
			qRight[i] = new ArrayList<>();
			qInd[i] = new ArrayList<>();
		}

		for (int i = 0; i < Q; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			qRight[l].add(r);
			qInd[l].add(i);
		}

		BIT = new int[N + 2];
		int[] ans = new int[Q];

		for (int i = N; i >= 1; i--) {

			for (int j = 0; j < aList[i].size(); j++) {
				int uL = j == 0 ? i : aList[i].get(j - 1);
				int uR = aList[i].get(j) - 1;
				update(uR + 1, -(1 - j));
				update(uL, 1 - j);
			}

			int uL = aList[i].isEmpty() ? i : aList[i].get(aList[i].size() - 1);
			int uR = N;
			update(uR + 1, -(1 - aList[i].size()));
			update(uL, 1 - aList[i].size());

			for (int j = 0; j < qRight[i].size(); j++) {
				ans[qInd[i].get(j)] = query(qRight[i].get(j));
			}
		}

		for (int i = 0; i < Q; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	static int[] BIT;

	static void update(int i, int d) {
		while (i < BIT.length) {
			BIT[i] += d;
			i += (i & -i);
		}
	}

	static int query(int i) {
		int sum = 0;
		while (i > 0) {
			sum += BIT[i];
			i -= (i & -i);
		}
		return sum;
	}

}
