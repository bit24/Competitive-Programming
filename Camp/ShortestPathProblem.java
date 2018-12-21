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

public class ShortestPathProblem {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		int nExt = nE - (nV - 1);
		int[][] eEdges = new int[nExt][2];
		int cExt = 0;

		uPar = new int[nV];
		for (int i = 0; i < nV; i++) {
			uPar[i] = i;
		}

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			if (a == b) {
				throw new RuntimeException();
			}

			int aP = fPar(a);
			int bP = fPar(b);
			if (aP == bP) {
				eEdges[cExt][0] = a;
				eEdges[cExt++][1] = b;
			} else {
				aList[a].add(b);
				aList[b].add(a);
				uPar[aP] = bP;
			}
		}

		if (cExt != nExt) {
			throw new RuntimeException();
		}

		preprocess();

		int[][] dists = new int[nExt][nV];

		for (int[] a : dists) {
			Arrays.fill(a, Integer.MAX_VALUE / 2);
		}

		for (int i = nExt - 1; i >= 0; i--) {
			aList[eEdges[i][0]].add(eEdges[i][1]);
			aList[eEdges[i][1]].add(eEdges[i][0]);
			bfs(eEdges[i][0], dists[i]);
		}

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int dist = depth[a] + depth[b] - 2 * depth[fLCA(a, b)];

			for (int i = 0; i < nExt; i++) {
				dist = Math.min(dist, dists[i][a] + dists[i][b]);
			}
			printer.println(dist);
		}
		printer.close();
	}

	static void bfs(int cV, int[] dist) {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		dist[cV] = 0;
		queue.add(cV);

		while (!queue.isEmpty()) {
			cV = queue.remove();
			for (int aV : aList[cV]) {
				if (dist[cV] + 1 < dist[aV]) {
					dist[aV] = dist[cV] + 1;
					queue.add(aV);
				}
			}
		}
	}

	static int[] uPar;

	static int fPar(int i) {
		return i == uPar[i] ? i : (uPar[i] = fPar(uPar[i]));
	}

	static int nV;
	static ArrayList<Integer>[] aList;

	static int[][] anc;
	static int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	static void preprocess() {
		anc = new int[nV][21];
		anc[0][0] = 0;
		depth = new int[nV];
		dfs(0, -1);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static void dfs(int cV, int pV) {
		for (int aV : aList[cV]) {
			if (aV != pV) {
				anc[aV][0] = cV;
				depth[aV] = depth[cV] + 1;
				dfs(aV, cV);
			}
		}
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}

		if (a == b) {
			return a;
		}
		
		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}
}