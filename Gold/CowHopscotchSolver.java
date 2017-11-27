import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class CowHopscotchSolver {

	public static void main(String[] args) throws IOException {
		new CowHopscotchSolver().execute();
	}

	long MOD = 1000000007L;
	int numR;
	int numC;
	int numT;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("hopscotch.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numR = Integer.parseInt(inputData.nextToken());
		numC = Integer.parseInt(inputData.nextToken());
		numT = Integer.parseInt(inputData.nextToken());

		int[][] types = new int[numR + 1][numC + 1];

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] typeLocs = new ArrayList[numT + 1];
		for (int i = 1; i <= numT; i++) {
			typeLocs[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i <= numR; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 1; j <= numC; j++) {
				typeLocs[types[i][j] = Integer.parseInt(inputData.nextToken())].add(j);
			}
		}
		reader.close();

		CompBIT[] BITs = new CompBIT[numT + 1];

		for (int i = 1; i <= numT; i++) {
			BITs[i] = new CompBIT(typeLocs[i]);
		}

		long[] totalSum = new long[numC + 1];

		totalSum[1] = 1;
		BITs[types[1][1]].update(1, 1);

		long[][] count = new long[numR + 1][numC + 1];
		count[1][1] = 1;

		// the uppermost and leftmost row/column is unreachable except for (1,1)
		for (int i = 2; i <= numR; i++) {
			long rSum = 0;

			for (int j = 2; j <= numC; j++) {
				totalSum[j] = (totalSum[j] + count[i - 1][j]) % MOD;
				
				BITs[types[i - 1][j]].update(j, count[i - 1][j]);

				rSum = (rSum + totalSum[j - 1]) % MOD;
				count[i][j] = (MOD + rSum - BITs[types[i][j]].query(j - 1)) % MOD;
			}
		}

		printer.println(count[numR][numC]);
		printer.close();
	}

	// compressed BIT
	class CompBIT {
		long[] BIT;
		int[] pos;

		// inpElements may contain duplicates, is zero indexed
		CompBIT(ArrayList<Integer> inpPos) {
			if (inpPos.isEmpty()) {
				BIT = new long[1];
				pos = new int[1];
				return;
			}

			Collections.sort(inpPos);

			int numP = 1;
			for (int i = 1; i < inpPos.size(); i++) {
				if (inpPos.get(i) != inpPos.get(i - 1)) {
					numP++;
				}
			}

			pos = new int[numP + 1];
			BIT = new long[numP + 1];

			pos[1] = inpPos.get(0);
			int nextI = 2;
			for (int i = 1; i < inpPos.size(); i++) {
				if (inpPos.get(i) != inpPos.get(i - 1)) {
					pos[nextI++] = inpPos.get(i);
				}
			}
		}

		void update(int value, long delta) {
			updateBIT(binSearch(value), delta);
		}

		long query(int value) {
			return queryBIT(binSearch(value));
		}

		// inlineable
		private int binSearch(int value) {
			int low = 0;
			int high = pos.length - 1;

			while (low != high) {
				int mid = (low + high + 1) >> 1;
				if (pos[mid] <= value) {
					low = mid;
				} else {
					high = mid - 1;
				}
			}
			return low;
		}

		private void updateBIT(int ind, long delta) {
			while (ind < BIT.length) {
				BIT[ind] = (BIT[ind] + delta) % MOD;
				ind += (ind & -ind);
			}
		}

		private long queryBIT(int ind) {
			long sum = 0;
			while (ind > 0) {
				sum = (sum + BIT[ind]) % MOD;
				ind -= (ind & -ind);
			}
			return sum;
		}
	}

}
