import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class HappyTrails {

	public static void main(String[] args) throws IOException {
		new HappyTrails().main();
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());
		K--;

		edges = new Edge[nE];
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			edges[i] = new Edge(Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()));
		}
		// long sTime = System.currentTimeMillis();
		Arrays.sort(edges);

		p = new int[nV];
		r = new int[nV];

		Space sSpace = fMST(new int[nE]);
		queue.add(sSpace);

		while (K-- > 0) {
			if (queue.isEmpty()) {
				printer.println(-1);
				printer.close();
				return;
			}
			Space cSpace = queue.remove();
			fract(cSpace);
		}

		if (queue.isEmpty()) {
			printer.println(-1);
			printer.close();
			return;
		}
		printer.println(queue.remove().mCost);
		// printer.println(System.currentTimeMillis() - sTime);
		printer.close();
	}

	int nV;
	int nE;

	Edge[] edges;

	class Edge implements Comparable<Edge> {
		int a;
		int b;
		int c;

		Edge(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		public int compareTo(Edge o) {
			return Integer.compare(c, o.c);
		}
	}

	class Space implements Comparable<Space> {
		int[] eState; // 0 = undecided but not-in-tree, -1 = forbidden, 1 = required, 2 = in-tree
		int mCost;

		Space(int[] eState, int mCost) {
			this.eState = eState;
			this.mCost = mCost;
		}

		public int compareTo(Space o) {
			return Integer.compare(mCost, o.mCost);
		}
	}

	PriorityQueue<Space> queue = new PriorityQueue<Space>();

	void fract(Space cS) {
		for (int i = 0; i < nE; i++) {
			if (cS.eState[i] == 2) {
				int[] cEState = Arrays.copyOf(cS.eState, nE);
				cEState[i] = -1;
				Space sSpace = fMST(cEState);
				if (sSpace != null) {
					queue.add(sSpace);
				}
				cS.eState[i] = 1; // we can modify it now as we aren't really using it
			}
		}
	}

	int[] p;
	int[] r;

	Space fMST(int[] eState) {
		Arrays.fill(p, -1);
		Arrays.fill(r, 0);
		int eCnt = 0;
		int mCost = 0;

		for (int i = 0; i < nE; i++) {
			if (eState[i] == 1) {
				merge(edges[i].a, edges[i].b);
				mCost += edges[i].c;
				eCnt++;
			}
		}

		for (int i = 0; i < nE; i++) {
			if (eState[i] != -1) {
				if (merge(edges[i].a, edges[i].b)) {
					eState[i] = 2;
					mCost += edges[i].c;
					eCnt++;
				}
			}
		}

		if (eCnt < nV - 1) {
			return null;
		}
		return new Space(eState, mCost);
	}

	boolean merge(int i, int j) {
		int iR = fR(i);
		int jR = fR(j);
		if (iR == jR) {
			return false;
		}
		if (r[iR] < r[jR]) {
			p[iR] = jR;
		} else if (r[iR] > r[jR]) {
			p[jR] = iR;
		} else {
			p[iR] = jR;
			r[jR]++;
		}
		return true;
	}

	int fR(int i) {
		if (p[i] == -1) {
			return i;
		} else {
			return p[i] = fR(p[i]);
		}
	}
}
