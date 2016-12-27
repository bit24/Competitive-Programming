import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_389F {

	public static void main(String[] args) throws IOException {
		new Div2_389F().execute();
	}

	int numV;
	int numI;

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	boolean[] isI;

	// root at 0
	ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();

	int[] iCount;

	int i_troid;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numV = Integer.parseInt(inputData.nextToken());
		numI = Integer.parseInt(inputData.nextToken()) * 2;

		for (int i = 0; i < numV; i++) {
			aList.add(new ArrayList<Integer>());
			children.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList.get(a).add(b);
			aList.get(b).add(a);
		}

		isI = new boolean[numV];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numI; i++) {
			isI[Integer.parseInt(inputData.nextToken()) - 1] = true;
		}
		reader.close();

		buildChildren(0, new boolean[numV]);

		iCount = new int[numV];
		find_iCount(0);

		i_troid = find_i_troid(0);

		printer.println(1);
		printer.println((i_troid + 1));

		// should sort by max size
		PriorityQueue<State> processingQueue = new PriorityQueue<State>(Collections.reverseOrder());

		boolean[] visited = new boolean[numV];
		visited[i_troid] = true;
		for (int adj : aList.get(i_troid)) {
			ArrayList<Integer> members = new ArrayList<Integer>();
			findLists(adj, visited, members);
			if(members.size() > 0){
				processingQueue.add(new State(members.size(), members));
			}
		}

		if (isI[i_troid]) {
			State stateB = processingQueue.remove();
			int a = i_troid;
			int b = stateB.value.remove(--stateB.key);
			printer.println((a + 1) + " " + (b + 1) + " " + (i_troid + 1));
			if (stateB.key > 0) {
				processingQueue.add(stateB);
			}
		}

		while (!processingQueue.isEmpty()) {
			State stateA = processingQueue.remove();
			State stateB = processingQueue.remove();

			int a = stateA.value.remove(--stateA.key);
			int b = stateB.value.remove(--stateB.key);
			printer.println((a + 1) + " " + (b + 1) + " " + (i_troid + 1));
			if (stateA.key > 0) {
				processingQueue.add(stateA);
			}
			if (stateB.key > 0) {
				processingQueue.add(stateB);
			}
		}
		printer.close();
	}

	class State implements Comparable<State> {
		int key;
		ArrayList<Integer> value;

		public int compareTo(State o) {
			return Integer.compare(key, o.key);
		}

		State(int key, ArrayList<Integer> value) {
			this.key = key;
			this.value = value;
		}
	}

	void findLists(int v, boolean[] visited, ArrayList<Integer> members) {
		visited[v] = true;
		if (isI[v]) {
			members.add(v);
		}

		for (int adj : aList.get(v)) {
			if (!visited[adj]) {
				findLists(adj, visited, members);
			}
		}
	}

	void buildChildren(int v, boolean[] visited) {
		visited[v] = true;
		for (int adj : aList.get(v)) {
			if (!visited[adj]) {
				children.get(v).add(adj);
				buildChildren(adj, visited);
			}
		}
	}

	void find_iCount(int v) {
		int sum = isI[v] ? 1 : 0;
		for (int child : children.get(v)) {
			find_iCount(child);
			sum += iCount[child];
		}
		iCount[v] = sum;
	}

	// like centroid
	int find_i_troid(int v) {
		for (int child : children.get(v)) {
			if (iCount[child] > numI / 2) {
				return find_i_troid(child);
			}
		}
		return v;
	}
}
