import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_199E {

	public static void main(String[] args) throws IOException {
		new Div2_199E().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());
		int numQ = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numV; i++) {
			aList.add(new TreeSet<Integer>());
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a).add(b);
			aList.get(b).add(a);
		}

		preprocess();
		degree = new int[numV];
		parent = new int[numV];
		decompose(0, -1);
		minDist = new int[numV];
		Arrays.fill(minDist, Integer.MAX_VALUE / 4);

		int operating = 0;

		for (int cV = 0; cV != -1; cV = parent[cV]) {
			int thisDist = findDist(operating, cV);
			if (minDist[cV] > thisDist) {
				minDist[cV] = thisDist;
			}
		}

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().charAt(0) == '1') {
				operating = Integer.parseInt(inputData.nextToken()) - 1;
				// "paint" operation
				for (int cV = operating; cV != -1; cV = parent[cV]) {
					int thisDist = findDist(operating, cV);
					if (minDist[cV] > thisDist) {
						minDist[cV] = thisDist;
					}
				}
			} else {
				operating = Integer.parseInt(inputData.nextToken()) - 1;
				int ans = Integer.MAX_VALUE / 4;
				for (int cV = operating; cV != -1; cV = parent[cV]) {
					int thisDist = findDist(operating, cV) + minDist[cV];
					if (ans > thisDist) {
						ans = thisDist;
					}
				}
				printer.println(ans);
			}
		}
		printer.close();
	}

	int numV;
	ArrayList<TreeSet<Integer>> aList = new ArrayList<TreeSet<Integer>>();

	// in centroid trees, it's important to have parent pointers
	int[] parent;

	// CENTROID DECOMPOSITION STUFF

	// not really a proper degree, more like degree + 1 (includes itself)
	int[] degree;

	int gSize = 0;

	// whenever something has no parent use -1

	// the root can be any arbitrary vertex within that "group"
	void decompose(int root, int ctParent) {
		fDegree(root, -1);
		gSize = degree[root];
		int centroid = fCentroid(root, -1);

		parent[centroid] = ctParent;

		for (int adj : aList.get(centroid)) {
			aList.get(adj).remove(centroid);
			decompose(adj, centroid);
		}
		aList.get(centroid).clear();
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to degree of root
	// returns centroid
	int fCentroid(int c, int p) {
		for (int adj : aList.get(c)) {
			if (adj != p && degree[adj] > gSize / 2) {
				return fCentroid(adj, c);
			}
		}
		return c;
	}

	void fDegree(int c, int p) {
		degree[c] = 1;
		for (int adj : aList.get(c)) {
			if (adj != p) {
				fDegree(adj, c);
				degree[c] += degree[adj];
			}
		}
	}

	// LCA STUFF

	int[][] ancestor;
	int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	void preprocess() {
		ancestor = new int[numV][21];
		ancestor[0][0] = 0;
		fParent(0, -1);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < numV; i++) {
				ancestor[i][k] = ancestor[ancestor[i][k - 1]][k - 1];
			}
		}
		depth = new int[numV];

		fDepth(0, -1);
	}

	void fParent(int c, int p) {
		for (int adj : aList.get(c)) {
			if (adj != p) {
				ancestor[adj][0] = c;
				fParent(adj, c);
			}
		}
	}

	void fDepth(int c, int p) {
		for (int adj : aList.get(c)) {
			if (adj != p) {
				depth[adj] = depth[c] + 1;
				fDepth(adj, c);
			}
		}
	}

	int findLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int difference = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((difference & (1 << i)) != 0) {
				b = ancestor[b][i];
			}
		}

		if (a == b) {
			return a;
		}

		for (int i = 20; i >= 0; i--) {
			if (ancestor[a][i] != ancestor[b][i]) {
				a = ancestor[a][i];
				b = ancestor[b][i];
			}
		}
		return ancestor[a][0];
	}

	int findDist(int a, int b) {
		int lca = findLCA(a, b);
		return depth[a] + depth[b] - 2 * depth[lca];
	}

	int[] minDist;

}
