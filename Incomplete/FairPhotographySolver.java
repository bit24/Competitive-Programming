import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FairPhotographySolver {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		new FairPhotographySolver().execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	int numB = 8;
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

		int[][] trPos = new int[numE + 2][numB];
		int[][] tlPos = new int[numE + 1][numB];

		// mark as impossible
		for (int b = 0; b < numB; b++) {
			trPos[numE + 1][b] = numE + 1;
			tlPos[0][b] = 0;
		}

		for (int i = numE; i >= 1; i--) {
			for (int b = 0; b < numB; b++) {
				trPos[i][b] = trPos[i + 1][b];
			}
			trPos[i][breed[i]] = i;
		}
		for (int i = 1; i <= numE; i++) {
			for (int b = 0; b < numB; b++) {
				tlPos[i][b] = tlPos[i - 1][b];
			}
			tlPos[i][breed[i]] = i;
		}

		TreeMap<State, Integer> states = new TreeMap<State, Integer>();

		int[] count = new int[numB + 1];
		for (int r = 1; r <= numE; r++) {
			count[breed[r]]++;

			KVPair[] order = new KVPair[numB];
			for (int b = 0; b < numB; b++) {
				order[b] = new KVPair(tlPos[r][b], b);
			}
			Arrays.sort(order, Collections.reverseOrder());

			int bSet = 0;
			for (int i = 0; i < numB && 1 <= order[i].k; i++) {
				bSet |= (1 << order[i].v);
				if (i >= numR - 1) {
					states.put(new State(bSet, count), r);
				}
			}
		}

		int ans = 0;
		count = new int[numB + 1];
		for (int l = 1; l <= numE; l++) {
			count[breed[l]]++;

			KVPair[] order = new KVPair[numB];
			for (int b = 0; b < numB; b++) {
				order[b] = new KVPair(trPos[l][b], b);
			}
			Arrays.sort(order);

			int bSet = 0;
			for (int i = 0; i < numB && order[i].k <= numE; i++) {
				bSet |= (1 << order[i].v);
				if (i >= numR - 1) {
					Integer r = states.get(new State(bSet, count));
					if (r != null) {
						int length = xVal[r] - xVal[l + 1];
						if (ans < length) {
							ans = length;
						}
					}
				}
			}
		}
		if(ans == 0){
			printer.println(-1);
		}
		else{
			printer.println(ans);
		}
		printer.close();
	}

	// represents a prefix
	// dependent on set being determined
	// if two states are equal, then there is a valid photo
	class State implements Comparable<State> {
		int bSet;
		int[] values = new int[numB];

		State(int bSet, int[] count) {
			this.bSet = bSet;
			int reference = -1;
			for (int i = 0; i < numB; i++) {
				if ((bSet & (1 << i)) == 0) {
					values[i] = count[i];
				} else {
					if (reference == -1) {
						reference = count[i];
					}
					values[i] = count[i] - reference;
				}
			}
		}

		public int compareTo(State o) {
			if (bSet < o.bSet) {
				return -1;
			}
			if (bSet > o.bSet) {
				return 1;
			}
			for (int i = 0; i < numB; i++) {
				if (values[i] < o.values[i]) {
					return -1;
				}
				if (values[i] > o.values[i]) {
					return 1;
				}
			}
			return 0;
		}
	}

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
