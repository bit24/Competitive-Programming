import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div2_388D {

	public static void main(String[] args) throws IOException {
		new Div2_388D().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());

		@SuppressWarnings("unchecked")
		ArrayList<Long>[] bids = new ArrayList[numE];
		long[] maxBid = new long[numE];

		for (int i = 0; i < numE; i++) {
			bids[i] = new ArrayList<Long>();
		}

		TreeMap<Long, Integer> bidValues = new TreeMap<Long, Integer>();

		for (int i = 0; i < numE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int person = Integer.parseInt(inputData.nextToken()) - 1;
			long amount = Long.parseLong(inputData.nextToken());
			if (bids[person].size() != 0) {
				bidValues.remove(maxBid[person]);
			}
			bidValues.put(amount, person);
			bids[person].add(amount);
			maxBid[person] = amount;
		}

		int numQ = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numQ; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numR = Integer.parseInt(inputData.nextToken());

			int[] removed = new int[numR];
			for (int j = 0; j < numR; j++) {
				int person = Integer.parseInt(inputData.nextToken()) - 1;
				removed[j] = person;
				bidValues.remove(maxBid[person]);
			}

			Entry<Long, Integer> max = bidValues.lastEntry();

			if (max != null) {
				Long toBeat = bidValues.lowerKey((max.getKey()));
				if (toBeat == null) {
					toBeat = 0L;
				}

				int mPerson = max.getValue();
				long greater = greater(bids[mPerson], toBeat);
				printer.println((mPerson + 1) + " " + greater);
			} else {
				printer.println("0 0");
			}

			for (int person : removed) {
				if (bids[person].size() > 0) {
					bidValues.put(maxBid[person], person);
				}
			}
		}
		printer.close();
	}

	long greater(ArrayList<Long> data, long value) {
		int low = 0;
		int high = data.size() - 1;

		while (low != high) {
			int mid = (low + high) / 2;
			if (value < data.get(mid)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		assert (low == high);
		return data.get(low);
	}

}
