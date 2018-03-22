import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WCS9_TollCostDigits {

	public static void main(String[] args) throws IOException {
		new WCS9_TollCostDigits().execute();
	}

	boolean[][] possible;

	int numV;
	int numE;
	ArrayList<ArrayList<Edge>> oEdges = new ArrayList<ArrayList<Edge>>();

	boolean[] visited;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());
		numE = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i <= numV; i++) {
			oEdges.add(new ArrayList<Edge>());
		}

		for (int i = 0; i < numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int res = Integer.parseInt(inputData.nextToken()) % 10;
			oEdges.get(a).add(new Edge(b, res));
			oEdges.get(b).add(new Edge(a, (10 - res) % 10));
		}
		reader.close();

		possible = new boolean[numV + 1][10];

		ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

		boolean[] visited = new boolean[numV + 1];
		for (int i = 1; i <= numV; i++) {
			if (!visited[i]) {
				ArrayList<Integer> cComponent = new ArrayList<Integer>();
				findComp(i, visited, cComponent);
				components.add(cComponent);

				findRGroups(i, 0);
			}
		}

		long[] fCount = new long[10];

		for (ArrayList<Integer> cComponent : components) {
			long[] mCount = new long[10];

			ArrayList<ArrayList<Integer>> rGroups = new ArrayList<ArrayList<Integer>>();
			boolean[] rVisited = new boolean[10];

			for (int cV : cComponent) {
				ArrayList<Integer> nrGroup = new ArrayList<Integer>();
				for (int res = 0; res < 10; res++) {
					if (possible[cV][res]) {
						mCount[res]++;
						if (!rVisited[res]) {
							rVisited[res] = true;
							nrGroup.add(res);
						}
					}
				}
				if (!nrGroup.isEmpty()) {
					rGroups.add(nrGroup);
				}
			}

			for (ArrayList<Integer> cGroup : rGroups) {
				boolean[] update = new boolean[10];

				for (int i : cGroup) {
					for (int j : cGroup) {
						update[(i - j + 10) % 10] = true;
						update[(j - i + 10) % 10] = true;
					}
				}
				long numWays = mCount[cGroup.get(0)] * (mCount[cGroup.get(0)] - 1L);

				for (int i = 0; i < 10; i++) {
					if (update[i]) {
						fCount[i] += numWays;
					}
				}
			}

			for (int a = 0; a < rGroups.size(); a++) {
				for (int b = a + 1; b < rGroups.size(); b++) {
					ArrayList<Integer> group1 = rGroups.get(a);
					ArrayList<Integer> group2 = rGroups.get(b);

					boolean[][] update = new boolean[2][10];

					for (int i : group1) {
						for (int j : group2) {
							update[0][(j - i + 10) % 10] = true;
							update[1][(i - j + 10) % 10] = true;
						}
					}
					long numWays = mCount[group1.get(0)] * mCount[group2.get(0)];

					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 10; j++) {
							if (update[i][j]) {
								fCount[j] += numWays;
							}
						}
					}
				}

			}
		}

		for (int i = 0; i < 10; i++) {
			printer.println(fCount[i]);
			if (fCount[i] != fCount[(20 - i) % 10]) {
				System.err.println("Error");
				assert (false);
				throw new RuntimeException();
			}
		}
		printer.close();
	}

	void findComp(int index, boolean[] visited, ArrayList<Integer> cComponent) {
		visited[index] = true;
		cComponent.add(index);
		for (Edge outEdge : oEdges.get(index)) {
			if (!visited[outEdge.end]) {
				findComp(outEdge.end, visited, cComponent);
			}
		}
	}

	void findRGroups(int index, int cRes) {
		possible[index][cRes] = true;

		for (Edge outEdge : oEdges.get(index)) {
			int nRes = (cRes + outEdge.res) % 10;
			if (!possible[outEdge.end][nRes]) {
				findRGroups(outEdge.end, nRes);
			}
		}
	}

	class Edge {
		int end;
		int res;

		Edge(int end, int res) {
			this.end = end;
			this.res = res;
		}
	}

}
