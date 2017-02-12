import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class H_2015F {

	int numV;
	ArrayList<TreeSet<Edge>> aList = new ArrayList<TreeSet<Edge>>();

	class Edge implements Comparable<Edge> {
		int e;
		long c;

		Edge(int e, long c) {
			this.e = e;
			this.c = c;
		}

		public int compareTo(Edge o) {
			return (e < o.e) ? -1 : ((e == o.e) ? 0 : 1);
		}
	}

	ArrayList<ArrayList<Integer>> ctAncs = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Long>> ctDists = new ArrayList<ArrayList<Long>>();

	int[] degree;

	int gSize = 0;

	// whenever something has no ctParent use -1

	void decompose(int root, int cctParent) {
		fDegree(root, -1);
		gSize = degree[root];
		int centroid = fCentroid(root, -1);

		f_ctData(centroid, -1, centroid, 0L);

		for (Edge edge : aList.get(centroid)) {
			int adj = edge.e;
			aList.get(adj).remove(new Edge(centroid, 0));
			decompose(adj, centroid);
		}
		aList.get(centroid).clear();
	}

	void fDegree(int c, int p) {
		degree[c] = 1;
		for (Edge edge : aList.get(c)) {
			int adj = edge.e;
			if (adj != p) {
				fDegree(adj, c);
				degree[c] += degree[adj];
			}
		}
	}

	int fCentroid(int c, int p) {
		for (Edge edge : aList.get(c)) {
			int adj = edge.e;
			if (adj != p && degree[adj] > gSize / 2) {
				return fCentroid(adj, c);
			}
		}
		return c;
	}

	void f_ctData(int c, int p, int cent, long d) {
		ctAncs.get(c).add(cent);
		ctDists.get(c).add(d);
		for (Edge edge : aList.get(c)) {
			int adj = edge.e;
			if (adj != p) {
				f_ctData(adj, c, cent, d + edge.c);
			}
		}
	}

	ArrayList<ArrayList<Long>> cDists = new ArrayList<ArrayList<Long>>();
	ArrayList<ArrayList<Long>> pDists = new ArrayList<ArrayList<Long>>();

	void cData() {
		for (int i = 0; i < numV; i++) {
			cDists.add(new ArrayList<Long>());
			pDists.add(new ArrayList<Long>());
		}

		for (int c = 0; c < numV; c++) {
			for (int ancI = ctAncs.get(c).size() - 1; ancI >= 0; ancI--) {
				cDists.get(ctAncs.get(c).get(ancI)).add(ctDists.get(c).get(ancI));
				if (ancI - 1 >= 0) {
					pDists.get(ctAncs.get(c).get(ancI)).add(ctDists.get(c).get(ancI - 1));
				}
			}
		}
		for (int c = 0; c < numV; c++) {
			Collections.sort(cDists.get(c));
			Collections.sort(pDists.get(c));
		}
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());

		int numQ = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numV; i++) {
			aList.add(new TreeSet<Edge>());
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			long c = Long.parseLong(inputData.nextToken());
			assert (1L <= c);
			aList.get(a).add(new Edge(b, c));
			aList.get(b).add(new Edge(a, c));
		}

		degree = new int[numV];
		for (int i = 0; i < numV; i++) {
			ctAncs.add(new ArrayList<Integer>());
			ctDists.add(new ArrayList<Long>());
		}
		decompose(0, -1);
		cData();

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int qV = Integer.parseInt(inputData.nextToken()) - 1;
			long qR = Long.parseLong(inputData.nextToken());

			long ans = countLE(cDists.get(qV), qR);
			for (int ancI = ctAncs.get(qV).size() - 1; ancI - 1 >= 0; ancI--) {
				int cV = ctAncs.get(qV).get(ancI);
				int cP = ctAncs.get(qV).get(ancI - 1);

				long rem = qR - ctDists.get(qV).get(ancI - 1);
				// do not count those that are reachable from children
				long add = countLE(cDists.get(cP), rem);
				long sub = countLE(pDists.get(cV), rem);
				ans += add - sub;
			}
			printer.println(ans);
		}
		printer.close();
	}

	long countLE(ArrayList<Long> elements, long v) {
		int high = elements.size() - 1;
		int low = -1;

		while (low != high) {
			int mid = (low + high + 1) / 2;
			if (elements.get(mid) <= v) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low + 1;
	}

	public static void main(String[] args) throws IOException {
		new H_2015F().execute();
	}
}