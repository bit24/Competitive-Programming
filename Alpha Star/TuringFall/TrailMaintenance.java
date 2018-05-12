import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class TrailMaintenance {

	static int[] anc;

	public static void main(String[] args) throws IOException {
		new TrailMaintenance().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nU = Integer.parseInt(inputData.nextToken());

		anc = new int[nV];

		TreeSet<Edge> edges = new TreeSet<>();

		uLoop:
		for (int i = 0; i < nU; i++) {
			inputData = new StringTokenizer(reader.readLine());
			edges.add(new Edge(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()), i));

			if (edges.size() < nV - 1) {
				printer.println(-1);
				continue;
			}

			for (int j = 0; j < nV; j++) {
				anc[j] = j;
			}

			int ans = 0;
			for (Edge cEdge : edges) {
				int aFirst = fFirst(cEdge.a);
				int bFirst = fFirst(cEdge.b);
				if (aFirst != bFirst) {
					anc[aFirst] = bFirst;
					ans += cEdge.c;
				}
			}

			int first = fFirst(0);
			for (int j = 0; j < nV; j++) {
				if (fFirst(j) != first) {
					printer.println(-1);
					continue uLoop;
				}
			}
			printer.println(ans);
		}
		printer.close();
	}

	static int fFirst(int i) {
		if (anc[i] == i) {
			return i;
		}
		return anc[i] = fFirst(anc[i]);
	}

	class Edge implements Comparable<Edge> {
		int a;
		int b;
		int c;
		int i;

		Edge(int a, int b, int c, int i) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.i = i;
		}

		public int compareTo(Edge o) {
			return c != o.c ? Integer.compare(c, o.c) : Integer.compare(i, o.i);
		}
	}
}
