import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div1_483E {

	static int nV;
	static ArrayList<Integer>[] chldn;

	static int nR;
	static ArrayList<Integer>[] routes;

	static int nQ;

	static ArrayList<Integer>[] queries;
	static int[][] qInf;

	static boolean[] min1;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		nV = Integer.parseInt(reader.readLine());

		chldn = new ArrayList[nV];
		routes = new ArrayList[nV];
		queries = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			chldn[i] = new ArrayList<>();
			routes[i] = new ArrayList<>();
			queries[i] = new ArrayList<>();
		}

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		for (int i = 1; i < nV; i++) {
			chldn[Integer.parseInt(inputData.nextToken()) - 1].add(i);
		}

		prepLCA();

		nR = Integer.parseInt(reader.readLine());

		for (int i = 0; i < nR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			routes[a].add(b);
			routes[b].add(a);
		}

		prepReach();

		nQ = Integer.parseInt(reader.readLine());
		qInf = new int[nQ][3];
		
		min1 = new boolean[nQ];

		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int lca = fLCA(a, b);

			int cost = 0;

			for (int k = 16; k >= 0; k--) {
				if (h[reach[a][k]] > h[lca]) {
					a = reach[a][k];
					cost += 1 << k;
				}
			}

			for (int k = 16; k >= 0; k--) {
				if (h[reach[b][k]] > h[lca]) {
					b = reach[b][k];
					cost += 1 << k;
				}
			}

			if (h[reach[a][0]] <= h[lca] && h[reach[b][0]] <= h[lca]) {
				
				if (lca == a || lca == b) {
					qInf[i][2] = cost;
					min1[i] = true;
					continue;
				}
				
				qInf[i][0] = a;
				qInf[i][1] = b;
				qInf[i][2] = cost;
				queries[a].add(i);
				queries[b].add(i);
			} else {
				qInf[i][2] = -1;
			}
		}

		first = new int[nV];
		last = new int[nV];
		BIT = new int[2 * nV + 1];

		eulerDFS(0);


		queryDFS(0);

		for (int i = 0; i < nQ; i++) {
			if (qInf[i][2] == -1) {
				printer.println(-1);
				continue;
			}
			if (min1[i]) {
				printer.println(qInf[i][2] + 1);
			} else {
				printer.println(qInf[i][2] + 2);
			}
		}
		printer.close();
	}

	static void queryDFS(int cV) {
		int[] stores = new int[queries[cV].size()];
		for (int qI = 0; qI < queries[cV].size(); qI++) {
			int cQ = queries[cV].get(qI);
			int eV;
			if (qInf[cQ][0] == cV) {
				eV = qInf[cQ][1];
			} else {
				assert (qInf[cQ][1] == cV);
				eV = qInf[cQ][0];
			}
			stores[qI] = BIT_query(last[eV]) - BIT_query(first[eV] - 1);
		}
		for (int aV : routes[cV]) {
			BIT_add(first[aV], 1);
		}

		for (int aV : chldn[cV]) {
			queryDFS(aV);
		}

		for (int qI = 0; qI < queries[cV].size(); qI++) {
			int cQ = queries[cV].get(qI);
			int eV;
			if (qInf[cQ][0] == cV) {
				eV = qInf[cQ][1];
			} else {
				assert (qInf[cQ][1] == cV);
				eV = qInf[cQ][0];
			}
			if (stores[qI] < BIT_query(last[eV]) - BIT_query(first[eV] - 1)) {
				min1[cQ] = true;
			}
		}
	}

	static int nN = 1;
	static int[] first;
	static int[] last;

	static void eulerDFS(int cV) {
		first[cV] = nN++;
		for (int aV : chldn[cV]) {
			eulerDFS(aV);
		}
		last[cV] = nN++;
	}

	static int[] BIT;

	static void BIT_add(int i, int d) {
		while (i <= 2 * nV) {
			BIT[i] += d;
			i += (i & -i);
		}
	}

	static int BIT_query(int i) {
		int sum = 0;
		while (i > 0) {
			sum += BIT[i];
			i -= (i & -i);
		}
		return sum;
	}

	static int[] h;
	static int[][] anc;

	static void lcaDFS(int cV) {
		for (int aV : chldn[cV]) {
			h[aV] = h[cV] + 1;
			anc[aV][0] = cV;
			lcaDFS(aV);
		}
	}

	static void prepLCA() {
		h = new int[nV];
		anc = new int[nV][17];
		lcaDFS(0);

		for (int k = 1; k < 17; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static int fLCA(int a, int b) {
		if (h[a] < h[b]) {
			int t = a;
			a = b;
			b = t;
		}
		int d = h[a] - h[b];
		for (int i = 0; i < 17; i++) {
			if ((d & (1 << i)) != 0) {
				a = anc[a][i];
			}
		}
		if (a == b) {
			return a;
		}
		for (int i = 16; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}

	static int[][] reach;

	static void prepReach() {
		reach = new int[nV][17];
		for (int i = 0; i < nV; i++) {
			reach[i][0] = i;
		}

		for (int i = 0; i < nV; i++) {
			for (int j : routes[i]) {
				int lca = fLCA(i, j);
				if (h[lca] < h[reach[i][0]]) {
					reach[i][0] = lca;
				}
			}
		}

		reachDFS(0);

		for (int k = 1; k < 17; k++) {
			for (int i = 0; i < nV; i++) {
				reach[i][k] = reach[reach[i][k - 1]][k - 1];
			}
		}
	}

	static void reachDFS(int cV) {
		for (int aV : chldn[cV]) {
			reachDFS(aV);
			if (h[reach[aV][0]] < h[reach[cV][0]]) {
				reach[cV][0] = reach[aV][0];
			}
		}
	}

}
