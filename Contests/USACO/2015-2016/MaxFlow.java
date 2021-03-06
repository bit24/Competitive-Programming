import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MaxFlow {

	public static void main(String[] args) throws IOException {
		new MaxFlow().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("maxflow.in"));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVertices = Integer.parseInt(inputData.nextToken());
		int numUpdates = Integer.parseInt(inputData.nextToken());
		adjacents.add(null);
		children.add(null);
		parent = new int[numVertices + 1];
		for (int i = 1; i <= numVertices; i++) {
			children.add(new ArrayList<Integer>());
			adjacents.add(new ArrayList<Integer>());
		}

		for (int i = 1; i <= numVertices - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int v1 = Integer.parseInt(inputData.nextToken());
			int v2 = Integer.parseInt(inputData.nextToken());
			adjacents.get(v1).add(v2);
			adjacents.get(v2).add(v1);
		}

		computeJumpAncestors();
		deltaCount = new int[numVertices + 1];

		for (int i = 1; i <= numUpdates; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int ep1 = Integer.parseInt(inputData.nextToken());
			int ep2 = Integer.parseInt(inputData.nextToken());
			int LCA = findLCA(ep1, ep2);
			deltaCount[ep1]++;
			deltaCount[ep2]++;
			deltaCount[LCA]--;
			if (LCA != root) {
				deltaCount[parent[LCA]]--;
			}
		}

		reader.close();
		
		findCount();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("maxflow.out")));
		printer.println(max);
		printer.close();
	}

	int max = 0;

	void findCount() {
		int[] count = new int[numVertices + 1];
		Arrays.fill(count, -1);
		ArrayDeque<Integer> exploreStack = new ArrayDeque<Integer>();
		exploreStack.add(root);
		while (!exploreStack.isEmpty()) {
			int cV = exploreStack.peekLast();

			boolean computable = true;
			for (int child : children.get(cV)) {
				if (count[child] == -1) {
					exploreStack.add(child);
					computable = false;
				}
			}

			if (computable) {
				int sum = deltaCount[cV];
				for (int child : children.get(cV)) {
					sum += count[child];
				}
				count[cV] = sum;
				if(max < sum){
					max = sum;
				}
				exploreStack.removeLast();
			}
		}
	}

	ArrayList<ArrayList<Integer>> adjacents = new ArrayList<ArrayList<Integer>>();

	int[] deltaCount;
	int root = 1;
	int numVertices;
	int[][] jAncestors;
	int[] depth;
	ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();;
	int[] parent;

	void BFSQueue() {
		Arrays.fill(depth, -1);
		ArrayDeque<Integer> exploreQueue = new ArrayDeque<Integer>();
		exploreQueue.add(root);
		depth[root] = 0;

		while (!exploreQueue.isEmpty()) {
			int cVertex = exploreQueue.poll();
			for (int adjacentV : adjacents.get(cVertex)) {
				if (depth[adjacentV] == -1) {
					children.get(cVertex).add(adjacentV);
					parent[adjacentV] = cVertex;
					depth[adjacentV] = depth[cVertex] + 1;
					exploreQueue.add(adjacentV);
				}
			}
		}
	}

	void computeJumpAncestors() {
		depth = new int[numVertices + 1];
		BFSQueue();
		jAncestors = new int[numVertices + 1][18];
		for (int i = 1; i <= numVertices; i++) {
			jAncestors[i][0] = parent[i];
		}

		for (int jInd = 0; jInd < 17; jInd++) {
			for (int cV = 1; cV <= numVertices; cV++) {
				jAncestors[cV][jInd + 1] = jAncestors[jAncestors[cV][jInd]][jInd];
			}
		}
	}

	int findLCA(int v1, int v2) {
		if (depth[v2] < depth[v1]) {
			return findLCA(v2, v1);
		}
		assert (depth[v1] <= depth[v2]);

		int diff = depth[v2] - depth[v1];

		// jumps up in powers of 2
		// time complexity O(log n)
		for (int i = 0; i <= 17; i++) {
			if ((diff & (1 << i)) != 0) {
				v2 = jAncestors[v2][i];
			}
		}

		if (v1 == v2) {
			return v1;
		}

		// jumps up in powers of 2
		// time complexity 0(log n)
		for (int jIndex = 17; jIndex >= 0; jIndex--) {
			if (jAncestors[v1][jIndex] != jAncestors[v2][jIndex]) {
				// always under jumps
				v1 = jAncestors[v1][jIndex];
				v2 = jAncestors[v2][jIndex];
			}
		}

		// complete last jump to compensate for under jumping
		assert (jAncestors[v1][0] == jAncestors[v2][0]);
		return jAncestors[v1][0];
	}

}
