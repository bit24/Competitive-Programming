import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class EasyVectorSolver {

	public static void main(String[] args) throws IOException {
		new EasyVectorSolver().execute();
	}

	long[] elements;
	long[] prefixSum;

	int[] evenKeys;
	int[] oddKeys;

	TreeSet<Integer> queryKeySet = new TreeSet<Integer>();
	TreeMap<Integer, Long> queryResults = new TreeMap<Integer, Long>();

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numE = Integer.parseInt(inputData.nextToken());
		assert (numE >= 2);
		int numQ = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		elements = new long[numE];
		for (int i = 0; i < numE; i++) {
			elements[i] = Long.parseLong(inputData.nextToken());
		}

		prefixSum = new long[numE];
		prefixSum[0] = elements[0];
		for (int i = 1; i < numE; i++) {
			prefixSum[i] = prefixSum[i - 1] + elements[i];
		}

		int[] queries = new int[numQ];
		for (int i = 0; i < numQ; i++) {
			queries[i] = Integer.parseInt(reader.readLine());
			queryKeySet.add(queries[i]);
		}
		reader.close();

		Integer[] queryKeys = queryKeySet.toArray(new Integer[0]);
		int numEven = 0;
		for (long i : queryKeys) {
			if ((i & 1) == 0) {
				numEven++;
			}
		}

		evenKeys = new int[numEven];
		oddKeys = new int[queryKeys.length - numEven];

		numEven = 0;
		int numOdd = 0;
		for (int i : queryKeys) {
			if ((i & 1) == 0) {
				evenKeys[numEven++] = i;
			} else {
				oddKeys[numOdd++] = i;
			}
		}

		// Arrays.sort(queryKeys);

		findValues(evenKeys, 0, evenKeys.length - 1, 0, numE - 1);
		findValues(oddKeys, 0, oddKeys.length - 1, 0, numE - 1);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		for (int i = 0; i < numQ; i++) {
			long value = queryResults.get(queries[i]);
			printer.println(value);
		}
		printer.close();
	}

	void findValues(int[] keys, int lInd, int rInd, int sMin, int sMax) {
		if (lInd > rInd) {
			return;
		}

		if (lInd == rInd) {
			int numMoves = keys[lInd];
			long max = 0;

			long right = (sMax <= numMoves) ? sMax : numMoves;
			for (int cS = sMin; cS <= right; cS++) {
				long sum = prefixSum[cS];
				long numRemaining = numMoves - cS;
				if (cS > 0) {
					// move back and forth
					sum += (numRemaining >> 1) * (elements[cS - 1] + elements[cS]);
					if ((numRemaining & 1) == 1) {
						sum += elements[cS - 1];
					}

					// sum += (numRemaining + 1) / 2 * elements[cS - 1];
					// sum += numRemaining / 2 * elements[cS];
				}
				if (max < sum) {
					max = sum;
				}
			}
			queryResults.put(numMoves, max);
			return;
		}

		int mid = (lInd + rInd) / 2;

		int numMoves = keys[mid];
		long max = 0;
		int optS = sMin;

		long right = (sMax <= numMoves) ? sMax : numMoves;
		for (int cS = sMin; cS <= right; cS++) {
			long sum = prefixSum[cS];
			long numRemaining = numMoves - cS;
			if (cS > 0) {
				// move back and forth
				sum += (numRemaining >> 1) * (elements[cS - 1] + elements[cS]);
				if ((numRemaining & 1) == 1) {
					sum += elements[cS - 1];
				}

				// sum += ((numRemaining + 1) / 2) * elements[cS - 1];
				// sum += (numRemaining / 2) * elements[cS];
			}
			if (max < sum) {
				max = sum;
				optS = cS;
			}
		}

		queryResults.put(numMoves, max);

		findValues(keys, lInd, mid - 1, sMin, optS);
		findValues(keys, mid + 1, rInd, optS, sMax);
	}
}
