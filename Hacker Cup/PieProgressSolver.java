import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PieProgressSolver {

	public static void main(String[] args) throws IOException {
		new PieProgressSolver().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("pie_progress.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("pie_progess.out")));

		int numT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numD = Integer.parseInt(inputData.nextToken());
			int numP = Integer.parseInt(inputData.nextToken());

			long[][] cost = new long[numD][numP];

			for (int i = 0; i < numD; i++) {
				inputData = new StringTokenizer(reader.readLine());
				for (int j = 0; j < numP; j++) {
					cost[i][j] = Long.parseLong(inputData.nextToken());
				}
				Arrays.sort(cost[i]);
			}

			int[] numB = new int[numD];
			PriorityQueue<KVPair> availible = new PriorityQueue<KVPair>();

			long tCost = 0;
			for (int i = 0; i < numD; i++) {
				availible.add(new KVPair(cost[i][0] + 1L, i));

				KVPair purchase = availible.remove();
				tCost += purchase.key;

				int cBought = ++numB[purchase.value];

				if (cBought < numP) {
					assert ((cBought + 1) * (cBought + 1) - cBought * cBought == 2 * cBought + 1);
					availible.add(new KVPair(cost[purchase.value][cBought] + 2L * cBought + 1L, purchase.value));
				}
			}
			printer.println("Case #" + cT + ": " + tCost);
		}
		reader.close();
		printer.close();
	}

	class KVPair implements Comparable<KVPair> {
		long key;
		int value;

		KVPair(long key, int value) {
			this.key = key;
			this.value = value;
		}

		public int compareTo(KVPair o) {
			return (key < o.key) ? -1 : ((key == o.key) ? 0 : 1);
		}
	}
}
