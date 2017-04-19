import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class RatatouilleSolver {

	public static void main(String[] args) throws IOException {
		new RatatouilleSolver().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("ratatouille.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("ratatouille.out")));

		int nT = Integer.parseInt(reader.readLine());

		tLoop:
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numE = Integer.parseInt(inputData.nextToken());
			int numP = Integer.parseInt(inputData.nextToken());

			int[] req = new int[numE];

			inputData = new StringTokenizer(reader.readLine());
			for (int i = 0; i < numE; i++) {
				req[i] = Integer.parseInt(inputData.nextToken());
			}

			@SuppressWarnings("unchecked")
			ArrayList<Range>[] servs = new ArrayList[numE];

			for (int i = 0; i < numE; i++) {
				servs[i] = new ArrayList<Range>();
				inputData = new StringTokenizer(reader.readLine());
				for (int j = 0; j < numP; j++) {
					int nxt = Integer.parseInt(inputData.nextToken());

					Range pos = new Range(findLow(req[i], nxt), findHigh(req[i], nxt));

					if (pos.low != -1) {
						servs[i].add(pos);
					}
				}
				Collections.sort(servs[i]);
			}

			int[] next = new int[numE];

			for (int i = 0; i < numE; i++) {
				if (next[i] == servs[i].size()) {
					printer.println("Case #" + cT + ": 0");
					continue tLoop;
				}
			}

			int ans = 0;

			oLoop:
			while (true) {
				int max = servs[0].get(next[0]).low;
				for (int i = 1; i < numE; i++) {
					if (servs[i].get(next[i]).low > max) {
						max = servs[i].get(next[i]).low;
					}
				}
				while (true) {
					boolean nUpdate = false;
					for (int i = 0; i < numE; i++) {
						while (servs[i].get(next[i]).high < max) {
							next[i]++;
							if (next[i] == servs[i].size()) {
								break oLoop;
							}
							if (servs[i].get(next[i]).low > max) {
								max = servs[i].get(next[i]).low;
								nUpdate = true;
							}
						}
					}
					if (!nUpdate) {
						break;
					}
				}

				ans++;
				for (int i = 0; i < numE; i++) {
					next[i]++;
					if (next[i] == servs[i].size()) {
						break oLoop;
					}
				}
			}
			printer.println("Case #" + cT + ": " + ans);
		}
		reader.close();
		printer.close();
	}

	int findHigh(int req, int item) {
		int low = 1;
		int high = 1_000_000;

		while (low != high) {
			int mid = (low + high + 1) / 2;
			if ((0.9 * mid) * req <= item) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}

		if (0.9 * low * req <= item && item <= 1.1 * low * req) {
			return low;
		} else {
			return -1;
		}
	}

	int findLow(int req, int item) {
		int low = 1;
		int high = 1_000_000;

		while (low != high) {
			int mid = (low + high) / 2;
			if (item <= (1.1 * mid) * req) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		if (0.9 * low * req <= item && item <= 1.1 * low * req) {
			return low;
		} else {
			return -1;
		}
	}

	class Range implements Comparable<Range> {
		int low;
		int high;

		Range(int l, int h) {
			low = l;
			high = h;
		}

		public int compareTo(Range o) {
			if (low != o.low) {
				return low < o.low ? -1 : 1;
			}
			return Integer.compare(high, o.high);
		}
	}

}
