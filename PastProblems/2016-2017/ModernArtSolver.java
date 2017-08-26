import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class ModernArtSolver {

	public static void main(String[] args) throws IOException {
		new ModernArtSolver().execute();
	}

	int length;

	int[][] grid;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("art.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("art.out")));

		length = Integer.parseInt(reader.readLine());
		grid = new int[length + 1][length + 1];

		int[] left = new int[length * length + 1];
		int[] right = new int[length * length + 1];
		int[] top = new int[length * length + 1];
		int[] bottom = new int[length * length + 1];

		Arrays.fill(left, Integer.MAX_VALUE);
		Arrays.fill(right, Integer.MIN_VALUE);
		Arrays.fill(top, Integer.MAX_VALUE);
		Arrays.fill(bottom, Integer.MIN_VALUE);

		boolean[] seen = new boolean[length * length + 1];
		for (int cR = 1; cR <= length; cR++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int cC = 1; cC <= length; cC++) {
				int cColor = grid[cR][cC] = Integer.parseInt(inputData.nextToken());
				if (cColor != 0) {
					seen[cColor] = true;

					if (cC < left[cColor]) {
						left[cColor] = cC;
					}
					if (right[cColor] < cC) {
						right[cColor] = cC;
					}
					if (cR < top[cColor]) {
						top[cColor] = cR;
					}
					if (bottom[cColor] < cR) {
						bottom[cColor] = cR;
					}
				}
			}
		}
		reader.close();

		int colorCount = 0;
		for (int i = 1; i <= length * length; i++) {
			if (seen[i]) {
				colorCount++;
			}
		}

		if (colorCount == 1) {
			printer.println(length * length - 1);
			printer.close();
		}

		ArrayList<KVPair> addQueue = new ArrayList<KVPair>();
		ArrayList<KVPair> remQueue = new ArrayList<KVPair>();

		for (int cColor = 1; cColor <= length * length; cColor++) {
			if (left[cColor] != Integer.MAX_VALUE) {
				addQueue.add(new KVPair(left[cColor], cColor));
				remQueue.add(new KVPair(right[cColor], cColor));
			}
		}

		Collections.sort(addQueue);
		Collections.sort(remQueue);

		boolean[] possible = new boolean[length * length + 1];
		Arrays.fill(possible, true);

		BIT = new int[length * length + 1];

		int nAdd = 0;
		int nRem = 0;
		for (int cC = 1; cC <= length; cC++) {
			while (nAdd < addQueue.size() && addQueue.get(nAdd).k == cC) {
				int cColor = addQueue.get(nAdd).v;
				updateRange(top[cColor], bottom[cColor], 1);
				nAdd++;
			}

			for (int cR = 1; cR <= length; cR++) {
				if (query(cR) > 1) {
					possible[grid[cR][cC]] = false;
				}
			}

			while (nRem < remQueue.size() && remQueue.get(nRem).k == cC) {
				int cColor = remQueue.get(nRem).v;
				updateRange(top[cColor], bottom[cColor], -1);
				nRem++;
			}
		}

		int count = 0;
		for (int i = 1; i <= length * length; i++) {
			if (possible[i]) {
				count++;
			}
		}
		printer.println(count);
		printer.close();
	}

	int[] BIT;

	void updateRange(int left, int right, int delta) {
		update(left, delta);
		update(right + 1, -delta);
	}

	void update(int ind, int delta) {
		while (ind < BIT.length) {
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
