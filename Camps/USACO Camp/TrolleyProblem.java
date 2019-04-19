import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class TrolleyProblem {

	public static void main(String[] args) throws IOException {
		new TrolleyProblem().main();
	}

	static int nV;
	static int nE;
	static int K;

	static int[] d;
	static int[] dC;

	static ArrayList<Integer>[] eList;
	static ArrayList<Integer>[] cList;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		d = new int[nV];
		dC = new int[nV];

		eList = new ArrayList[nV];
		cList = new ArrayList[nV];

		for (int i = 0; i < nV; i++) {
			eList[i] = new ArrayList<Integer>();
			cList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			d[a] = b;
			dC[a] = c;
		}

		for (int i = 0; i < nE - nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			eList[a].add(b);
			cList[a].add(c);
		}

		long[][] iCost = new long[nV][K + 1];
		for (long[] a : iCost) {
			Arrays.fill(a, Long.MAX_VALUE / 8);
		}
		iCost[0][K] = 0;

		PriorityQueue<State> queue = new PriorityQueue<>();
		queue.add(new State(0, K, 0));

		while (!queue.isEmpty()) {
			State cState = queue.remove();
			if (iCost[cState.cV][cState.nS] < cState.cost) {
				continue;
			}

			int nV = d[cState.cV];
			long nCost = cState.cost + dC[cState.cV];

			if (iCost[nV][cState.nS] > nCost) {
				iCost[nV][cState.nS] = nCost;
				queue.add(new State(nV, cState.nS, nCost));
			}

			if (cState.nS > 0) {
				for (int i = 0; i < eList[cState.cV].size(); i++) {
					nV = eList[cState.cV].get(i);
					nCost = cState.cost + cList[cState.cV].get(i);

					if (iCost[nV][cState.nS - 1] > nCost) {
						iCost[nV][cState.nS - 1] = nCost;
						queue.add(new State(nV, cState.nS - 1, nCost));
					}
				}
			}
		}

		long ans = Long.MAX_VALUE / 8;

		long[][] sCost = new long[nV][K + 1];

		for (int eV = 0; eV < nV; eV++) {
			queue.clear();
			for (long[] a : sCost) {
				Arrays.fill(a, Long.MAX_VALUE / 8);
			}

			for (int i = 0; i <= K; i++) {
				sCost[eV][i] = iCost[eV][i];
				queue.add(new State(eV, i, sCost[eV][i]));
			}

			while (!queue.isEmpty()) {
				State cState = queue.remove();
				if (cState.cost > ans) {
					break;
				}

				if (sCost[cState.cV][cState.nS] < cState.cost) {
					continue;
				}

				int nV = d[cState.cV];
				long nCost = cState.cost + dC[cState.cV];

				if (nV == eV) {
					ans = Math.min(ans, nCost);
				}

				if (sCost[nV][cState.nS] > nCost) {
					sCost[nV][cState.nS] = nCost;
					queue.add(new State(nV, cState.nS, nCost));
				}

				if (cState.nS > 0) {
					for (int i = 0; i < eList[cState.cV].size(); i++) {
						nV = eList[cState.cV].get(i);
						nCost = cState.cost + cList[cState.cV].get(i);

						if (nV == eV) {
							ans = Math.min(ans, nCost);
						}

						if (sCost[nV][cState.nS - 1] > nCost) {
							sCost[nV][cState.nS - 1] = nCost;
							queue.add(new State(nV, cState.nS - 1, nCost));
						}
					}
				}
			}
		}
		printer.println(ans);
		printer.close();
	}

	class State implements Comparable<State> {
		int cV;
		int nS;
		long cost;

		State(int cV, int nS, long cost) {
			this.cV = cV;
			this.nS = nS;
			this.cost = cost;
		}

		public int compareTo(State o) {
			return Long.compare(cost, o.cost);
		}
	}
}
