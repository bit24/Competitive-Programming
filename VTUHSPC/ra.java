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

public class ra {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nP = Integer.parseInt(inputData.nextToken());
		int nT = Integer.parseInt(inputData.nextToken());

		String[] names = new String[nP];

		nV = nP + nT + 2;
		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		source = nP + nT;
		sink = nP + nT + 1;

		for (int i = 0; i < nP; i++) {
			aList[source].add(i);
		}

		for (int i = 0; i < nT; i++) {
			aList[i + nP].add(sink);
		}

		for (int i = 0; i < nP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			names[i] = inputData.nextToken();
			int nA = Integer.parseInt(inputData.nextToken());
			for (int j = 0; j < nA; j++) {
				aList[i].add(nP + Integer.parseInt(inputData.nextToken()) - 1);
			}
		}

		cleanAList();

		int low = 1;
		int high = 100;

		while (low < high) {
			int mid = (low + high) / 2;
			res = new int[nV][nV];
			for (int i = 0; i < nP; i++) {
				res[source][i] = mid;
			}
			for (int i = 0; i < nT; i++) {
				res[nP + i][sink] = 2;
			}
			for (int i = 0; i < nP; i++) {
				for (int a : aList[i]) {
					res[i][a] = 1;
				}
			}
			computeMFRes();
			if (tFlow == 2 * nT) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		int fCost = low;
		res = new int[nV][nV];
		for (int i = 0; i < nP; i++) {
			res[source][i] = fCost;
		}
		for (int i = 0; i < nT; i++) {
			res[nP + i][sink] = 2;
		}
		for (int i = 0; i < nP; i++) {
			for (int a : aList[i]) {
				res[i][a] = 1;
			}
		}
		computeMFRes();
		System.out.println(fCost);

		for (int i = 0; i < nT; i++) {
			printer.print("Day " + (i + 1) + ":");
			for (int j = 0; j < nP; j++) {
				if (res[i + nP][j] == 1) {
					printer.print(" " + names[j]);
				}
			}
			printer.println();
		}
		printer.close();
	}

	static int nV;
	static ArrayList<Integer>[] aList;
	static int[][] res; // residual

	static int source;
	static int sink;

	static int tFlow;

	static void cleanAList() {
		boolean[][] connect = new boolean[nV][nV];
		for (int i = 0; i < nV; i++) {
			for (int j : aList[i]) {
				connect[i][j] = connect[j][i] = true;
			}
		}

		for (int i = 0; i < nV; i++) {
			aList[i].clear();
			for (int j = 0; j < nV; j++) {
				if (connect[i][j]) {
					aList[i].add(j);
				}
			}
		}
	}

	static int[] FPTS() {
		int[] prev = new int[nV];
		Arrays.fill(prev, -2);
		prev[source] = -1;

		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(source);

		while (!queue.isEmpty()) {
			int cV = queue.remove();
			if (cV == sink) {
				return prev;
			}

			for (int adj : aList[cV]) {
				if (prev[adj] == -2 && res[cV][adj] > 0) {
					prev[adj] = cV;
					queue.add(adj);
				}
			}
		}

		return null;
	}

	static void computeMFRes() {
		tFlow = 0;
		while (true) {
			int[] prev = FPTS();
			if (prev == null) {
				break;
			}

			int cV = sink;
			int bNeck = Integer.MAX_VALUE;
			while (cV != source) {
				int pV = prev[cV];
				bNeck = Math.min(bNeck, res[pV][cV]);
				cV = pV;
			}

			cV = sink;
			while (cV != source) {
				int pV = prev[cV];
				res[pV][cV] -= bNeck;
				res[cV][pV] += bNeck;
				cV = pV;
			}
			tFlow += bNeck;
		}
	}

}
