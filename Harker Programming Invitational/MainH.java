import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MainH {

	public static void main(String[] args) throws IOException {
		new MainH().main();
	}

	static int nV;
	static int nE;

	static Edge[] edges;

	static int[] par;
	static long tDist;

	static void merge(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);

		if (aParent == bParent) {
			throw new RuntimeException();
		}

		par[aParent] = bParent;
	}

	static boolean inSameSet(int a, int b) {
		int aParent = findParent(a);
		int bParent = findParent(b);
		return aParent == bParent;
	}

	static int findParent(int x) {
		if (par[x] == x) {
			return x;
		} else {
			return par[x] = findParent(par[x]);
		}
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());

		edges = new Edge[nE];
		par = new int[nV];

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			if (a == -1 || b == -1) {
				throw new RuntimeException();
			}
			int n = Integer.parseInt(inputData.nextToken());
			edges[i] = new Edge(a, b, n);
		}
		Arrays.sort(edges);

		for (int i = 0; i < nV; i++) {
			par[i] = i;
		}

		int nM = 0;

		int last = -1;
		for (Edge edge : edges) {
			if (!inSameSet(edge.e1, edge.e2)) {
				merge(edge.e1, edge.e2);
				tDist += edge.c;
				nM++;
				last = edge.c;
			}
		}

		if (nM == nV - 1) {
			printer.println(tDist - last);
		} else if (nM == nV - 2) {
			printer.println(tDist);
		} else {
			printer.println(-1);
		}
		printer.close();
	}

	class Edge implements Comparable<Edge> {
		int e1;
		int e2;
		int c;

		public int compareTo(Edge o) {
			return c - o.c;
		}

		Edge(int e1, int e2, int c) {
			this.e1 = e1;
			this.e2 = e2;
			this.c = c;
		}
	}
}