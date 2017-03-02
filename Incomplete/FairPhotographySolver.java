import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FairPhotographySolver {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		new FairPhotographySolver().execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	final int numB = 8;
	int numE;
	int numR;

	int[] breed;
	int[] xVal;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("fairphoto.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("fairphoto.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numR = Integer.parseInt(inputData.nextToken());

		KVPair[] input = new KVPair[numE + 1];
		for (int i = 1; i <= numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			input[i] = new KVPair(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()) - 1);
		}
		reader.close();
		Arrays.sort(input, 1, numE + 1);
		breed = new int[numE + 1];
		xVal = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			breed[i] = input[i].v;
			xVal[i] = input[i].k;
		}

		// right exclusive, left inclusive
		int[][] trPos = new int[numE + 1][numB];
		int[][] tlPos = new int[numE + 1][numB];

		// mark as non-existent
		for (int b = 0; b < numB; b++) {
			trPos[numE][b] = numE + 1;
			tlPos[0][b] = 0;
		}

		for (int i = numE - 1; i >= 0; i--) {
			for (int b = 0; b < numB; b++) {
				trPos[i][b] = trPos[i + 1][b];
			}
			trPos[i][breed[i + 1]] = i + 1;
		}
		for (int i = 1; i <= numE; i++) {
			for (int b = 0; b < numB; b++) {
				tlPos[i][b] = tlPos[i - 1][b];
			}
			tlPos[i][breed[i]] = i;
		}

		TreeMap<int[], Integer> states = new TreeMap<int[], Integer>(sortFunc);

		int[] count = new int[numB + 1];


		boolean[] visited = new boolean[numB];
		for (int nIt = 0; nIt < numB; nIt++) {
			int lmI = numE + 1;
			int lmB = -1;
			for (int cB = 0; cB < numB; cB++) {
				if (!visited[cB] && trPos[0][cB] < lmI) {
					lmI = trPos[0][cB];
					lmB = cB;
				}
			}
			if (lmB == -1) {
				break;
			}
			visited[lmB] = true;
			int[] currState = toStateArray(visited, count);
			if (!states.containsKey(currState)) {
				states.put(currState, 0);
			}
		}

		long t1 = System.currentTimeMillis();

		int ans = 0;
		for (int cI = 1; cI <= numE; cI++) {
			count[breed[cI]]++;

			visited = new boolean[numB];
			int numV = 0;

			for (int nIt = 0; nIt < numB; nIt++) {
				int rmI = 0;
				int rmB = -1;
				for (int cB = 0; cB < numB; cB++) {
					if (!visited[cB] && tlPos[cI][cB] > rmI) {
						rmI = tlPos[cI][cB];
						rmB = cB;
					}
				}
				if (rmB == -1) {
					break;
				}
				visited[rmB] = true;
				numV++;
				if (numV >= numR) {
					Integer lI = states.get(toStateArray(visited, count));
					if (lI != null) {
						int length = xVal[cI] - xVal[lI + 1];
						if (ans < length) {
							ans = length;
						}
					}
				}
			}

			visited = new boolean[numB];
			for (int nIt = 0; nIt < numB; nIt++) {
				int lmI = numE + 1;
				int lmB = -1;
				for (int cB = 0; cB < numB; cB++) {
					if (!visited[cB] && trPos[cI][cB] < lmI) {
						lmI = trPos[cI][cB];
						lmB = cB;
					}
				}
				if (lmB == -1) {
					break;
				}
				visited[lmB] = true;

				int[] currState = toStateArray(visited, count);
				if (!states.containsKey(currState)) {
					states.put(currState, cI);
				}
			}
		}
		System.out.println(System.currentTimeMillis() - t1);

		if (ans == 0) {
			printer.println(-1);
		} else {
			printer.println(ans);
		}
		printer.close();
	}
	

	int[] toStateArray(boolean[] used, int[] count) {
		int[] values = new int[numB];
		int reference = -1;
		for (int i = 0; i < numB; i++) {
			if (!used[i]) {
				values[i] = -count[i] - numE - 1;
			} else {
				if (reference == -1) {
					reference = count[i];
				}
				values[i] = count[i] - reference;
			}
		}
		// System.out.println(Arrays.toString(values));
		return values;
	}

	Comparator<int[]> sortFunc = new Comparator<int[]>() {
		public int compare(int[] s1, int[] s2) {
			for (int i = 0; i < numB; i++) {
				if (s1[i] < s2[i]) {
					return -1;
				}
				if (s1[i] > s2[i]) {
					return 1;
				}
			}
			return 0;
		}
	};

	class KVPair implements Comparable<KVPair> {
		int k;
		int v;

		KVPair(int k, int v) {
			this.k = k;
			this.v = v;
		}

		public int compareTo(KVPair o) {
			return Integer.compare(k, o.k);
		}
	}
}
