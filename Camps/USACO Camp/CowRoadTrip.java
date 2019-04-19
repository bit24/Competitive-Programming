import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class CowRoadTrip {

	public static void main(String[] args) throws IOException {
		new CowRoadTrip().main();
	}

	ArrayList<Edge>[] aList;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());

		aList = new ArrayList[N];

		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(new Edge(b, c));
			aList[b].add(new Edge(a, c));
		}

		for (ArrayList<Edge> cList : aList) {
			Collections.sort(cList);
		}

		nI = new long[N];
		nD = new long[N];

		dfs(0, -1, -1);
		printer.println(ans);
		printer.close();
	}

	long[] nI;
	long[] nD;

	long ans;

	void dfs(int cV, int pV, int pC) {
		for (Edge cEdge : aList[cV]) {
			if (cEdge.end != pV) {
				dfs(cEdge.end, cV, cEdge.cost);
			}
		}

		long hCnt = 0;
		long tCnt = 1;
		for (int i = 0; i < aList[cV].size(); i++) {
			Edge cEdge = aList[cV].get(i);
			if (cEdge.end != pV) {

				if (cEdge.cost < pC) {
					nD[cV] += nD[cEdge.end] + 1;
				}
				if (cEdge.cost > pC) {
					nI[cV] += nI[cEdge.end] + 1;
				}

				ans += nD[cEdge.end] + 1;

				if (i == 0 || (aList[cV].get(i - 1).end == pV ? (i == 1 || aList[cV].get(i - 2).cost < cEdge.cost)
						: aList[cV].get(i - 1).cost < cEdge.cost)) {
					tCnt += hCnt;
					hCnt = 0;
				}
				ans += tCnt * (nI[cEdge.end] + 1);
				hCnt += 1 + nD[cEdge.end];
			}
		}
	}

	class Edge implements Comparable<Edge> {
		int end;
		int cost;

		Edge(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}

		public int compareTo(Edge o) {
			return Integer.compare(cost, o.cost);
		}
	}
}
