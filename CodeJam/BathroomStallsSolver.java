import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BathroomStallsSolver {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("stalls.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("stalls.out")));

		int nT = Integer.parseInt(reader.readLine());

		tLoop:
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			long numS = Long.parseLong(inputData.nextToken());
			long numP = Long.parseLong(inputData.nextToken());
			getNum(numS);

			count = new long[indices.length];

			count[indices.length - 1] = 1;
			for (int i = indices.length - 1; i > 0; i--) {
				long cL = indices[i] - 1;
				if ((cL & 1) == 0L) {
					count[binSearch(cL / 2)] += 2 * count[i];
				} else {
					count[binSearch(cL / 2)] += count[i];
					count[binSearch((cL + 1) / 2)] += count[i];
				}
			}

			for (int i = indices.length - 1; i >= 0; i--) {
				if (numP > count[i]) {
					numP -= count[i];
				} else {
					printer.println("Case #" + cT + ": " + indices[i] / 2 + " " + (indices[i] - 1) / 2);
					continue tLoop;
				}
			}
		}
		reader.close();
		printer.close();
	}

	static long[] count;

	static long[] indices;

	static int binSearch(long index) {
		int low = 0;
		int high = indices.length - 1;
		while (low != high) {
			int mid = (low + high) / 2;
			if (indices[mid] < index) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}

	static void getNum(long seed) {
		TreeSet<Long> reachable = new TreeSet<Long>();
		reachable.add(seed);

		ArrayList<Long> list = new ArrayList<Long>();

		while (true) {
			long current = reachable.last();
			if (current < 1) {
				break;
			}
			reachable.remove(current);
			list.add(current);
			current--;
			reachable.add(current / 2);
			if ((current & 1) == 1L) {
				reachable.add((current + 1) / 2);
			}
		}

		indices = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			indices[i] = list.get(list.size() - 1 - i);
		}
	}

}
