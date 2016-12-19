import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

public class StreetRace {

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	static ArrayList<ArrayList<Integer>> mList = new ArrayList<ArrayList<Integer>>();

	static int[] disc;
	static int[] low;
	static int[] parent;

	static int currentTime = 0;

	static LinkedHashSet<Integer> AP = new LinkedHashSet<Integer>();

	static LinkedHashSet<Integer> APb = new LinkedHashSet<Integer>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String nextLine = reader.readLine();
		int currentVertex = 0;
		while (!nextLine.equals("-1")) {
			aList.add(new ArrayList<Integer>());
			StringTokenizer inputData = new StringTokenizer(nextLine);

			int nextInt = Integer.parseInt(inputData.nextToken());
			while (nextInt != -2) {
				aList.get(currentVertex).add(nextInt);
				nextInt = Integer.parseInt(inputData.nextToken());
			}
			nextLine = reader.readLine();
			currentVertex++;
		}

		for (int i = 0; i < aList.size(); i++) {
			mList.add(new ArrayList<Integer>());
		}

		for (int start = 0; start < aList.size(); start++) {
			for (int end : aList.get(start)) {
				mList.get(start).add(end);
				mList.get(end).add(start);
			}
		}
		
		findBCCSA();
		findBCCSM();

		System.out.print(AP.size());

		for (int i : AP) {
			System.out.print(" " + i);
		}
		System.out.println();

		System.out.print(APb.size());

		for (int i : APb) {
			System.out.print(" " + i);
		}
		System.out.println();

	}

	public static void findBCCSA() {
		low = new int[aList.size()];
		disc = new int[aList.size()];
		parent = new int[aList.size()];

		// mark "not visited"
		Arrays.fill(disc, -1);
		Arrays.fill(low, -1);
		Arrays.fill(parent, -1);

		BCCDFSA(0);
	}

	public static void findBCCSM() {
		low = new int[aList.size()];
		disc = new int[aList.size()];
		parent = new int[aList.size()];
		currentTime = 0;

		// mark "not visited"
		Arrays.fill(disc, -1);
		Arrays.fill(low, -1);
		Arrays.fill(parent, -1);

		BCCDFSM(0);
	}

	static void BCCDFSA(int currentNode) {
		disc[currentNode] = currentTime;
		low[currentNode] = currentTime++;
		int childrenCount = 0;

		for (int neighbor : aList.get(currentNode)) {
			if (disc[neighbor] == -1) {
				parent[neighbor] = currentNode;
				childrenCount++;
				BCCDFSA(neighbor);

				// update low values
				if (low[neighbor] < low[currentNode]) {
					low[currentNode] = low[neighbor];
				}

				if ((disc[currentNode] != 0 && (low[neighbor] >= disc[currentNode]))
						|| ((disc[currentNode] == 0 && (childrenCount > 1)))) {

					AP.add(currentNode);
				}
			} else if (neighbor != parent[currentNode] && disc[neighbor] < disc[currentNode]) {
				if (low[currentNode] > disc[neighbor]) {
					low[currentNode] = disc[neighbor];
				}
			}
		}
	}

	static void BCCDFSM(int currentNode) {
		disc[currentNode] = currentTime;
		low[currentNode] = currentTime++;
		int childrenCount = 0;

		for (int neighbor : aList.get(currentNode)) {
			if (disc[neighbor] == -1) {
				parent[neighbor] = currentNode;
				childrenCount++;
				BCCDFSM(neighbor);

				// update low values
				if (low[neighbor] < low[currentNode]) {
					low[currentNode] = low[neighbor];
				}

				if ((disc[currentNode] != 0 && (low[neighbor] >= disc[currentNode]))
						|| ((disc[currentNode] == 0 && (childrenCount > 1)))) {

					APb.add(currentNode);
				}
			} else if (neighbor != parent[currentNode] && disc[neighbor] < disc[currentNode]) {
				if (low[currentNode] > disc[neighbor]) {
					low[currentNode] = disc[neighbor];
				}
			}
		}

	}

}
