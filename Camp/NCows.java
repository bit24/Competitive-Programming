import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NCows {

	static int nR;
	static int nC;

	static int[][] type;

	static int[] iM = { -2, -1, 1, 2, 2, 1, -1, -2 };
	static int[] jM = { 1, 2, 2, 1, -1, -2, -2, -1 };

	static ArrayList<Integer>[] aList;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nR = Integer.parseInt(inputData.nextToken());
		nC = Integer.parseInt(inputData.nextToken());

		type = new int[nR][nC];

		for (int i = 0; i < nR; i++) {
			String line = reader.readLine();

			for (int j = 0; j < nC; j++) {
				char cChar = line.charAt(j);
				if (cChar == '.') {
					type[i][j] = 0;
				}
				if (cChar == 'C') {
					type[i][j] = 1;
				}
				if (cChar == '#') {
					type[i][j] = 2;
				}
			}
		}

		aList = new ArrayList[nR * nC + 2];

		for (int i = 0; i < nR * nC + 2; i++) {
			aList[i] = new ArrayList<>();
		}

		boolean[] active = new boolean[nR * nC];
		boolean[] forced = new boolean[nR * nC];

		for (int i = 0; i < nR; i++) {
			for (int j = 0; j < nC; j++) {
				if (type[i][j] < 2) {
					active[i * nC + j] = true;
					if (type[i][j] == 1) {
						forced[i * nC + j] = true;
					}

					for (int d = 0; d < 8; d++) {
						int nI = i + iM[d];
						int nJ = j + jM[d];

						if (0 <= nI && nI < nR && 0 <= nJ && nJ < nC) {
							if (type[nI][nJ] < 2) {
								aList[i * nC + j].add(nI * nC + nJ);
							}
						}
					}
				}
			}
		}

		boolean[] rmv = new boolean[nR * nC];

		for (int i = 0; i < nR * nC; i++) {
			if (forced[i]) {
				for (int aV : aList[i]) {
					rmv[aV] = true;
				}
			}
		}

		for (int i = 0; i < nR * nC; i++) {
			if (active[i]) {
				if (rmv[i]) {
					aList[i].clear();
				}

				ArrayList<Integer> clean = new ArrayList<>();
				for (int j : aList[i]) {
					if (!rmv[j]) {
						clean.add(j);
					}
				}
				aList[i] = clean;
			}
		}

		for (int i = 0; i < nR * nC; i++) {
			if (rmv[i]) {
				active[i] = false;
			}
		}

		vis = new boolean[nR * nC];

		color = new boolean[nR * nC];

		for (int i = 0; i < nR * nC; i++) {
			if (active[i]) {
				if (!vis[i]) {
					dfs(i, true);
				}
			}
		}

		nV = nR * nC + 2;
		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		nLSt = new int[nV];

		nxtE = new int[20 * nV];
		end = new int[20 * nV];
		cap = new int[20 * nV];
		flow = new int[20 * nV];

		source = nR * nC;
		sink = nR * nC + 1;

		int nAct = 0;
		for (int i = 0; i < nR * nC; i++) {
			if (active[i]) {
				nAct++;

				if (color[i]) {
					addEdge(source, i, 1);
				} else {
					addEdge(i, sink, 1);
				}

				for (int aV : aList[i]) {
					if (color[aV] == color[i]) {
						throw new RuntimeException();
					}
					if (color[i]) {
						addEdge(i, aV, 1);
					}
				}
			}
		}

		long nMatch = cFlow();

		printer.println(nAct - nMatch);
		printer.close();
	}

	static boolean[] vis;

	static boolean[] color;

	static void dfs(int cV, boolean add) {
		vis[cV] = true;

		if (add) {
			color[cV] = add;
		}

		for (int aV : aList[cV]) {
			if (!vis[aV]) {
				dfs(aV, !add);
			}
		}
	}

	static int nV;
	static int nE;

	static int[] lSt;
	static int[] nLSt;

	static int[] nxtE; // double number of input edges
	static int[] end;
	static int[] cap;
	static int[] flow;

	static int source;
	static int sink;

	static int[] level;

	static void addEdge(int a, int b, int c) { // must create a back edge so flow can be undone
		nxtE[nE] = lSt[a];
		lSt[a] = nE;
		end[nE] = b;
		cap[nE++] = c;

		nxtE[nE] = lSt[b];
		lSt[b] = nE;
		end[nE] = a;
		cap[nE++] = 0;
	}

	// constructs layered network
	static boolean bfs() {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(source);

		while (!queue.isEmpty()) {
			int cV = queue.remove();
			for (int eI = lSt[cV]; eI != -1; eI = nxtE[eI]) {
				int aV = end[eI];
				if (cap[eI] == flow[eI]) { // note flows can be negative
					continue;
				}
				if (level[aV] != -1) {
					continue;
				}
				level[aV] = level[cV] + 1;
				queue.add(aV);
			}
		}
		return level[sink] != -1;
	}

	// finds augmenting flow
	static long dfs(int cV, long pushed) {
		if (pushed == 0) {
			return 0;
		}
		if (cV == sink) {
			return pushed;
		}
		for (int eI = nLSt[cV]; eI != -1; nLSt[cV] = eI = nxtE[eI]) {
			int aV = end[eI];
			if (level[aV] != level[cV] + 1 || cap[eI] == flow[eI]) {
				continue;
			}
			long aPushed = dfs(aV, Math.min(pushed, cap[eI] - flow[eI]));
			if (aPushed > 0) {
				flow[eI] += aPushed;
				flow[eI ^ 1] -= aPushed; // inverse edge
				return aPushed;
			}
		}
		return 0;
	}

	static long cFlow() {
		level = new int[nV];
		long tFlow = 0;
		while (true) {
			Arrays.fill(level, -1);
			level[source] = 0;
			if (!bfs()) {
				break;
			}
			System.arraycopy(lSt, 0, nLSt, 0, nV);
			long pushed = dfs(source, Long.MAX_VALUE / 4);
			while (pushed > 0) {
				tFlow += pushed;
				pushed = dfs(source, Long.MAX_VALUE / 4);
			}
		}
		return tFlow;
	}

}
