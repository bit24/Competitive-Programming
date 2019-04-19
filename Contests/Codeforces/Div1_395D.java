import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Div1_395D {

	public static void main(String[] args) throws IOException {
		POW = new long[500_000];
		POW[0] = 1;
		for (int i = 1; i < 500_000; i++) {
			POW[i] = POW[i - 1] * BASE % MOD;
		}
		new Div1_395D().main();
	}

	ArrayList<Edge> aList[];
	ArrayList<Edge> rList[];

	int[] numI;
	boolean[] inQ;

	ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

	HashMap<Long, Long> cnts = new HashMap<Long, Long>();

	int maxV = -1;
	long maxC = -1;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;

			Edge p = new Edge(a, b);
			Edge q = new Edge(b, a);
			p.opp = q;
			q.opp = p;

			aList[a].add(p);
			aList[b].add(q);
		}

		numI = new int[nV];
		inQ = new boolean[nV];

		for (int i = 0; i < nV; i++) {
			if (aList[i].size() == 1) {
				inQ[i] = true;
			}
		}

		for (int i = 0; i < nV; i++) {
			if (aList[i].size() == 1) {
				inQ[i] = false;
				calculate(i);
			}
		}

		while (!queue.isEmpty()) {
			int cur = queue.remove();
			inQ[cur] = false;
			calculate(cur);
		}

		dfs1(0, -1);
		dfs2(0, -1);

		printer.println(maxV + 1);
		printer.close();
	}

	void dfs2(int cV, int pV) {
		if (cnts.size() > maxC) {
			maxC = cnts.size();
			maxV = cV;
		}

		for (Edge aE : aList[cV]) {
			if (aE.e == pV) {
				continue;
			}
			Long aKey = aE.hash;
			Long prev = cnts.get(aKey);
			cnts.put(aKey, prev == null ? 1 : prev + 1);

			Long rKey = aE.opp.hash;
			prev = cnts.get(rKey);
			if (prev == 1) {
				cnts.remove(rKey);
			} else {
				cnts.put(rKey, prev - 1);
			}

			dfs2(aE.e, cV);

			// undo
			aKey = aE.opp.hash;
			prev = cnts.get(aKey);
			cnts.put(aKey, prev == null ? 1 : prev + 1);

			rKey = aE.hash;
			prev = cnts.get(rKey);
			if (prev == 1) {
				cnts.remove(rKey);
			} else {
				cnts.put(rKey, prev - 1);
			}
		}
	}

	void dfs1(int cV, int pV) {
		for (Edge aE : aList[cV]) {
			if (aE.e == pV) {
				continue;
			}
			Long key = aE.opp.hash;
			Long prev = cnts.get(key);
			cnts.put(key, prev == null ? 1 : prev + 1);

			dfs1(aE.e, cV);
		}
	}

	void calculate(int cV) {
		Collections.sort(aList[cV], BY_OUT_HASH);
		if (numI[cV] == aList[cV].size() - 1) {
			long hash = '(';
			int len = 1;

			for (int i = 1; i < aList[cV].size(); i++) {
				hash = (hash * POW[aList[cV].get(i).opp.len] + aList[cV].get(i).opp.hash) % MOD;
				len += aList[cV].get(i).opp.len;
			}
			hash = (hash * BASE + ')') % MOD;
			len++;

			aList[cV].get(0).hash = hash;
			aList[cV].get(0).len = len;

			if (++numI[aList[cV].get(0).e] >= aList[aList[cV].get(0).e].size() - 1 && !inQ[aList[cV].get(0).e]) {
				queue.add(aList[cV].get(0).e);
				inQ[aList[cV].get(0).e] = true;
			}
		} else {
			long[] sufH = new long[aList[cV].size() + 1];
			sufH[aList[cV].size()] = ')';
			int[] sufL = new int[aList[cV].size() + 1];
			sufL[aList[cV].size()] = 1;

			for (int i = aList[cV].size() - 1; i >= 0; i--) {
				sufH[i] = (aList[cV].get(i).opp.hash * POW[sufL[i + 1]] + sufH[i + 1]) % MOD;
				sufL[i] = aList[cV].get(i).opp.len + sufL[i + 1];
			}

			long hash = '(';
			int len = 1;

			for (int i = 0; i < aList[cV].size(); i++) {
				if (aList[cV].get(i).hash == -1) {
					aList[cV].get(i).hash = (hash * POW[sufL[i + 1]] + sufH[i + 1]) % MOD;
					aList[cV].get(i).len = len + sufL[i + 1];

					if (++numI[aList[cV].get(i).e] >= aList[aList[cV].get(i).e].size() - 1
							&& !inQ[aList[cV].get(i).e]) {
						queue.add(aList[cV].get(i).e);
						inQ[aList[cV].get(i).e] = true;
					}
				}

				hash = (hash * POW[aList[cV].get(i).opp.len] + aList[cV].get(i).opp.hash) % MOD;
				len += aList[cV].get(i).opp.len;
			}
		}
	}

	class Edge {
		int s;
		int e;
		Edge opp;
		long hash = -1;
		int len;

		Edge(int s, int e) {
			this.s = s;
			this.e = e;
		}
	}

	final Comparator<Edge> BY_OUT_HASH = new Comparator<Edge>() {
		public int compare(Edge e1, Edge e2) {
			return Long.compare(e1.opp.hash, e2.opp.hash);
		}
	};

	static final long MOD = 1_000_000_000 + 123;
	static final long BASE = 34447759;
	static long[] POW;

}
