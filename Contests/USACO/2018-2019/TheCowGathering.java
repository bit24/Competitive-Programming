import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TheCowGathering {

	static int N;
	static int M;

	static ArrayList<Integer>[] aList;

	static ArrayList<Integer>[] aftList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("gathering.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("gathering.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[N];
		aftList = new ArrayList[N];

		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
			aftList[i] = new ArrayList<>();
		}

		int[] cnt = new int[N];

		for (int i = 0; i < N - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
			cnt[a]++;
			cnt[b]++;
		}

		for (int i = 0; i < M; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aftList[a].add(b);
			cnt[b]++;
		}

		/*
		if (N == 5 && M == 1) {
			if (aList[0].get(0) == 1 && aList[1].get(1) == 2 && aList[2].get(1) == 3 && aList[3].get(1) == 4) {
				if (aftList[1].get(0) == 3) {
					printer.println(0);
					printer.println(0);
					printer.println(1);
					printer.println(1);
					printer.println(1);
					printer.close();
					return;
				}
			}
		}*/

		ArrayDeque<Integer> pQueue = new ArrayDeque<Integer>();
		boolean[] queued = new boolean[N];

		for (int i = 0; i < N; i++) {
			if (cnt[i] <= 1) {
				queued[i] = true;
				pQueue.add(i);
			}
		}

		while (!pQueue.isEmpty()) {
			int cur = pQueue.remove();
			for (int adj : aList[cur]) {
				cnt[adj]--;
				if (!queued[adj] && cnt[adj] <= 1) {
					queued[adj] = true;
					pQueue.add(adj);
				}
			}
			for (int aft : aftList[cur]) {
				cnt[aft]--;
				if (!queued[aft] && cnt[aft] <= 1) {
					queued[aft] = true;
					pQueue.add(aft);
				}
			}
		}

		boolean pAll = true;
		for (int i = 0; i < N; i++) {
			if (!queued[i]) {
				pAll = false;
				break;
			}
		}

		if (!pAll) {
			for (int i = 0; i < N; i++) {
				printer.println(0);
			}
			printer.close();
			return;
		}

		// above code is tested to work

		depth = new int[N];
		loc = new int[N];
		end = new int[N];

		anc = new int[N][21];

		dfs(0, -1);
		preprocess();

		int[] dAr = new int[N + 1];
		int global = 0;

		for (int cV = 0; cV < N; cV++) {
			for (int aft : aftList[cV]) {
				if (loc[cV] <= loc[aft] && loc[aft] <= end[cV]) {
					int subHead = jumpUp(aft, depth[aft] - depth[cV] - 1);
					dAr[loc[subHead]]--;
					dAr[end[subHead] + 1]++;
					global++;
				} else {
					dAr[loc[cV]]++;
					dAr[end[cV] + 1]--;
				}
			}
		}

		int[] marks = new int[N];
		marks[0] = dAr[0];

		for (int i = 1; i < N; i++) {
			marks[i] = marks[i - 1] + dAr[i];
		}
		for (int i = 0; i < N; i++) {
			marks[i] += global;
		}
		
		for (int i = 0; i < N; i++) {
			if (marks[loc[i]] > 0) {
				printer.println(0);
			} else {
				printer.println(1);
			}
			if (marks[i] < 0) {
				throw new RuntimeException();
			}
		}
		printer.close();
	}

	static int[] depth;

	static int lI;

	static int[] loc;
	static int[] end;

	static int[][] anc;

	static void dfs(int c, int p) {
		loc[c] = lI++;
		for (int a : aList[c]) {
			if (a != p) {
				depth[a] = depth[c] + 1;
				anc[a][0] = c;
				dfs(a, c);
			}
		}
		end[c] = lI - 1;
	}

	static void preprocess() {
		anc[0][0] = 0;

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < N; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static int jumpUp(int c, int jL) {
		for (int i = 0; i <= 20; i++) {
			if ((jL & (1 << i)) != 0) {
				c = anc[c][i];
			}
		}
		return c;
	}
}
