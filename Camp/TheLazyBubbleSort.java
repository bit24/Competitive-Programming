import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class TheLazyBubbleSort {

	public static void main(String[] args) throws IOException {
		new TheLazyBubbleSort().main();
	}

	int N;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());

		Item[] orig = new Item[N];
		for (int i = 0; i < N; i++) {
			orig[i] = new Item(Integer.parseInt(reader.readLine()), i);
		}

		Item[] srtd = Arrays.copyOf(orig, N);
		Arrays.sort(srtd);

		//

		int[] comp = new int[N];
		for (int i = 0; i < N; i++) {
			comp[i] = Arrays.binarySearch(srtd, orig[i]) + 1;
		}

		BIT = new int[N + 1];

		ArrayList<Integer> undec = new ArrayList<Integer>();
		Integer[] end = new Integer[N];

		for (int i = 0; i < N; i++) {
			int numG = i - query(comp[i]);
			update(comp[i], 1);

			if (numG >= K) {
				end[i - K] = comp[i];
			} else {
				undec.add(comp[i]);
			}
		}
		Collections.sort(undec);

		int nxt = 0;

		for (int i = 0; i < N; i++) {
			if (end[i] == null) {
				end[i] = undec.get(nxt++);
			}
		}

		for (int i = 0; i < N; i++) {
			printer.println(srtd[end[i] - 1].v);
		}
		printer.close();
	}

	int[] BIT;

	void update(int ind, int delta) {
		while (ind <= N) {
			BIT[ind] += delta;
			ind += (ind & -ind);
		}
	}

	int query(int ind) {
		int sum = 0;
		while (ind > 0) {
			sum += BIT[ind];
			ind -= (ind & -ind);
		}
		return sum;
	}

	class Item implements Comparable<Item> {
		int v;
		int i;

		Item(int v, int i) {
			this.v = v;
			this.i = i;
		}

		public int compareTo(Item o) {
			if (v != o.v) {
				return Integer.compare(v, o.v);
			}
			return Integer.compare(i, o.i);
		}
	}
}
