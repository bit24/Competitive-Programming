import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_405D {

	public static void main(String[] args) throws IOException {
		new Div2_405D().execute();
	}

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());
		jL = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[numV];
		children = new ArrayList[numV];

		for (int i = 0; i < numV; i++) {
			aList[i] = new ArrayList<Integer>();
			children[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		visited = new boolean[numV];
		root(0);

		num = new int[numV][jL + 1];
		depth = new int[numV];
		stSize = new int[numV];
		computeAns(0);

		// upon supplementation, pSum should be divisible by jL
		assert (pSum % jL == 0);
		printer.println(pSum / jL);
		printer.close();
	}

	int numV;
	ArrayList<Integer>[] aList;
	boolean[] visited;

	void root(int cV) {
		visited[cV] = true;
		for (int adj : aList[cV]) {
			if (!visited[adj]) {
				children[cV].add(adj);
				root(adj);
			}
		}
	}

	int jL;

	ArrayList<Integer>[] children;

	int[][] num;

	int[] depth;
	int[] stSize;

	void computeAns(int cV) {
		stSize[cV] = 1;
		for (int child : children[cV]) {
			depth[child] = depth[cV] + 1;
			computeAns(child);
			stSize[cV] += stSize[child];
		}
		processV(cV);
	}

	// part 1: we count the sum of the distances between all the vertices
	// part 2: for every pair of vertices whose distance isn't divisible by jL, we're going to supplement it until it
	// does so we can basically "round up"
	// answer is just previous sum / jL

	// note: clever use of depth modding allows us to use a simple transition of count from a child to cV

	long pSum;

	void processV(int cV) {
		num[cV][depth[cV] % jL] = 1;

		for (int child : children[cV]) {
			for (int sMod = 0; sMod < jL; sMod++) {
				for (int eMod = 0; eMod < jL; eMod++) {
					// cV is LCA, we're finding their distance modulo jL to calculate supplements
					int dist = subMod_jL(sMod + eMod, depth[cV] * 2);

					// if jL doesn't divide into distance, we're going to supplement it until it does
					int supp = subMod_jL(jL, dist);

					// every pair of vertices with these sMod and eMod values is going to need the supplement
					pSum += ((long) supp) * num[cV][sMod] * num[child][eMod];
				}
			}
			// now add all the vertices in child to num[cV]
			for (int i = 0; i < jL; i++) {
				num[cV][i] += num[child][i];
			}
		}
		// as per part 1 in outline, we count the number of vertices that uses edge from cV to parent(cV)
		pSum += ((long) stSize[cV]) * (numV - stSize[cV]);
	}

	int subMod_jL(int a, int b) {
		return ((a - b % jL) + jL) % jL;
	}
}
