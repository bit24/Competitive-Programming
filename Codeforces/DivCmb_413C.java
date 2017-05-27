import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class DivCmb_413C {

	static int[] cost;

	public static void main(String[] args) throws IOException {
		new DivCmb_413C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numO = Integer.parseInt(inputData.nextToken());

		int[] amt = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()) };

		int[] ben = new int[numO];
		cost = new int[numO];
		int[] type = new int[numO];

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] cSrtd = new ArrayList[2];
		cSrtd[0] = new ArrayList<Integer>();
		cSrtd[1] = new ArrayList<Integer>();

		int[] maxes = new int[] { -1, -1 };

		for (int i = 0; i < numO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			ben[i] = Integer.parseInt(inputData.nextToken());
			cost[i] = Integer.parseInt(inputData.nextToken());
			type[i] = inputData.nextToken().charAt(0) == 'C' ? 0 : 1;
			cSrtd[type[i]].add(i);
			if (cost[i] <= amt[type[i]]) {
				maxes[type[i]] = Math.max(maxes[type[i]], ben[i]);
			}
		}
		reader.close();

		int maxTBen = 0;

		if (maxes[0] != -1 && maxes[1] != -1) {
			maxTBen = maxes[0] + maxes[1];
		}

		Collections.sort(cSrtd[0], byCost);
		Collections.sort(cSrtd[1], byCost);

		for (int i = 0; i < 2; i++) {

			MultiSet<Integer> reachable = new MultiSet<Integer>();

			int nEval = 0;

			for (int j = cSrtd[i].size() - 1; j >= 0; j--) {
				int cBen = ben[cSrtd[i].get(j)];
				int cCost = cost[cSrtd[i].get(j)];

				while (nEval < cSrtd[i].size() && cost[cSrtd[i].get(nEval)] + cCost <= amt[i]) {
					reachable.add(ben[cSrtd[i].get(nEval)]);
					nEval++;
				}

				if (j < nEval) {
					reachable.remove(cBen);
					if (reachable.size() > 0) {
						maxTBen = Math.max(maxTBen, reachable.max() + cBen);
					}
					reachable.add(cBen);
				} else {
					if (reachable.size() > 0) {
						maxTBen = Math.max(maxTBen, reachable.max() + cBen);
					}
				}
			}
		}
		System.out.println(maxTBen);
	}

	static Comparator<Integer> byCost = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return Integer.compare(cost[o1], cost[o2]);
		}
	};

	class MultiSet<T> {
		TreeMap<T, Integer> internal = new TreeMap<T, Integer>();

		void add(T addend) {
			Integer cnt = internal.get(addend);
			if (cnt == null) {
				internal.put(addend, 1);
			} else {
				internal.put(addend, cnt + 1);
			}
		}

		void remove(T subtrahend) {
			Integer cnt = internal.get(subtrahend);
			if (cnt != null) {
				if (cnt == 1) {
					internal.remove(subtrahend);
				} else {
					internal.put(subtrahend, cnt - 1);
				}
			}
		}

		T max() {
			return internal.lastKey();
		}

		T min() {
			return internal.firstKey();
		}

		int size() {
			return internal.size();
		}
	}

}
