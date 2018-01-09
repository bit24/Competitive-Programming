import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_446C {

	public static void main(String[] args) throws IOException {
		new Div1_446C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		Edge[] edges = new Edge[nE];
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			edges[i] = new Edge(Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()), i);
		}

		Edge[] sEdges = Arrays.copyOf(edges, nE);
		Arrays.sort(sEdges);
		int orgC = -1;
		int asdC = -1;
		for (int i = 0; i < nE; i++) {
			if (sEdges[i].c == orgC) {
				sEdges[i].c = asdC;
			} else {
				orgC = sEdges[i].c;
				sEdges[i].c = ++asdC;
			}
		}

		int nC = asdC + 1;

		int nQ = Integer.parseInt(reader.readLine());
		boolean[] pos = new boolean[nQ];
		Arrays.fill(pos, true);

		ArrayList<Subquery>[] subqueries = new ArrayList[nC];

		for (int i = 0; i < nC; i++) {
			subqueries[i] = new ArrayList<>();
		}

		ArrayList<Edge>[] eByC = new ArrayList[nC];

		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int qNE = Integer.parseInt(inputData.nextToken());
			ArrayList<Integer> costs = new ArrayList<>();

			for (int j = 0; j < qNE; j++) {
				Edge cE = edges[Integer.parseInt(inputData.nextToken()) - 1];
				if (eByC[cE.c] == null) {
					eByC[cE.c] = new ArrayList<>();
					costs.add(cE.c);
				}
				eByC[cE.c].add(cE);
			}

			for (int cC : costs) {
				subqueries[cC].add(new Subquery(eByC[cC], i));
				eByC[cC] = null;
			}
		}

		DSet mst = new DSet(nV);
		DSet cSet = new DSet(nV);

		int nEI = 0;

		for (int cC = 0; cC < nC; cC++) {
			sqLoop:
			for (Subquery cSq : subqueries[cC]) {
				ArrayList<Integer> mod = new ArrayList<Integer>();
				for (Edge cE : cSq.edges) {
					int r1 = mst.fRoot(cE.v1);
					int r2 = mst.fRoot(cE.v2);
					mod.add(r1);
					mod.add(r2);
					if (r1 == r2) {
						pos[cSq.qI] = false;
						cSet.reset(mod);
						continue sqLoop;
					}

					if (!cSet.merge(r1, r2)) {
						pos[cSq.qI] = false;
						cSet.reset(mod);
						continue sqLoop;
					}
				}
				cSet.reset(mod);
			}
			while (nEI < nE && sEdges[nEI].c == cC) {
				mst.merge(sEdges[nEI].v1, sEdges[nEI].v2);
				nEI++;
			}
		}
		for(int i = 0; i < nQ; i++) {
			if(pos[i]) {
				printer.println("YES");
			}
			else {
				printer.println("NO");
			}
		}
		printer.close();
	}

	class Edge implements Comparable<Edge> {
		int v1;
		int v2;
		int c;
		int i;

		Edge(int v1, int v2, int c, int i) {
			this.v1 = v1;
			this.v2 = v2;
			this.c = c;
			this.i = i;
		}

		public int compareTo(Edge o) {
			return Integer.compare(c, o.c);
		}
	}

	class Subquery {
		ArrayList<Edge> edges;
		int qI;

		Subquery(ArrayList<Edge> edges, int qI) {
			this.edges = edges;
			this.qI = qI;
		}
	}

	class DSet {
		int[] par;

		DSet(int nE) {
			par = new int[nE];
			for (int i = 0; i < nE; i++) {
				par[i] = i;
			}
		}

		int fRoot(int x) {
			if (par[x] == x) {
				return x;
			}
			return par[x] = fRoot(par[x]);
		}

		boolean merge(int a, int b) {
			int aRoot = fRoot(a);
			int bRoot = fRoot(b);
			if (aRoot != bRoot) {
				par[aRoot] = bRoot;
				return true;
			} else {
				return false;
			}
		}

		void reset(ArrayList<Integer> indices) {
			for (int i : indices) {
				par[i] = i;
			}
		}
	}
}
