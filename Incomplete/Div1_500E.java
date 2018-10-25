import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_500E {

	static int N;
	static int B;

	static int[] a;
	static int[] srt;

	static ArrayList<Integer> order = new ArrayList<>();

	static HashMap<Integer, Integer> map = new HashMap<>();

	static boolean[] visited;

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		B = Integer.parseInt(inputData.nextToken());
		a = new int[N];

		inputData = new StringTokenizer(reader.readLine());

		TreeSet<Integer> unique = new TreeSet<>();

		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
			unique.add(a[i]);
		}

		int cnt = 0;
		for (Integer i : unique) {
			map.put(i, cnt++);
		}

		for (int i = 0; i < N; i++) {
			a[i] = map.get(a[i]);
		}

		srt = Arrays.copyOf(a, N);
		Arrays.sort(srt);

		int nDis = 0;
		for (int i = 0; i < N; i++) {
			if (a[i] != srt[i]) {
				nDis++;
			}
		}
		if (nDis > B) {
			printer.println(-1);
			printer.close();
			return;
		}

		int nV = N + cnt;
		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N; i++) {
			if (a[i] != srt[i]) {
				aList[N + a[i]].add(i);
				aList[i].add(N + srt[i]);
			}
		}

		visited = new boolean[nV];

		ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			if (a[i] != srt[i] && !visited[i]) {
				dfs(i);
				cycles.add(order);
				order = new ArrayList<>();
			}
		}

		if (cycles.size() == 100) {
			int[] compNum = new int[N];

			int cComp = 1;
			for (ArrayList<Integer> cCycle : cycles) {
				for (int i = 0; i < cCycle.size() - 1; i++) {
					if (cCycle.get(i) < N) {
						compNum[cCycle.get(i)] = cComp;
					}
				}
				cComp++;
			}
			boolean eSpan = false;

			for (int i = 0; i < N; i++) {
				if (a[i] != srt[i]) {
					if (compNum[a[i]] != compNum[srt[i]]) {
						printer.println(compNum[a[i]] + " " + compNum[srt[i]]);
						eSpan = true;
					}
				}
			}

			printer.println(cComp - 1);
			printer.println(eSpan);
			printer.println(nDis);
			printer.close();
			return;
		}

		printer.println(cycles.size());
		for (ArrayList<Integer> cCycle : cycles) {
			printer.println(cCycle.size() / 2);
			for (int i = 0; i < cCycle.size() - 1; i++) {
				if (cCycle.get(i) < N) {
					printer.print(cCycle.get(i) + 1 + " ");
				}
			}
			printer.println();
		}
		printer.close();
	}

	static void dfs(int i) {
		visited[i] = true;

		while (!aList[i].isEmpty()) {
			int lInd = aList[i].size() - 1;
			int nxt = aList[i].get(lInd);
			aList[i].remove(lInd);
			dfs(nxt);
		}
		order.add(i);
	}

	static void ass(boolean inp) {// assertions may not be enabled
		if (!inp) {
			throw new RuntimeException();
		}
	}
}
