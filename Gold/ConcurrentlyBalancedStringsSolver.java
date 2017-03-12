import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class ConcurrentlyBalancedStringsSolver {

	public static void main(String[] args) throws IOException {
		new ConcurrentlyBalancedStringsSolver().execute();
	}

	int numS;
	int numE;

	int[][] strings;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("cbs.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cbs.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numS = Integer.parseInt(inputData.nextToken());
		numE = Integer.parseInt(inputData.nextToken());
		offset = numE + 1;

		strings = new int[numS + 1][numE + 1];

		for (int i = 1; i <= numS; i++) {
			String inputLine = reader.readLine();
			for (int j = 1; j <= numE; j++) {
				if (inputLine.charAt(j - 1) == '(') {
					strings[i][j] = 1;
				} else {
					strings[i][j] = -1;
				}
			}
		}
		reader.close();

		TreeMap<int[], ArrayList<Integer>> values = new TreeMap<int[], ArrayList<Integer>>(KeySetComp);

		int[] preCount = new int[numS + 1];
		for (int i = 1; i <= numE; i++) {
			for (int j = 1; j <= numS; j++) {
				preCount[j] += strings[j][i];
			}
			ArrayList<Integer> list = values.get(preCount);
			if (list == null) {
				list = new ArrayList<Integer>();
				int[] copy = new int[numS + 1];
				for (int j = 1; j <= numS; j++) {
					copy[j] = preCount[j];
				}
				values.put(copy, list);
			}
			list.add(i);
		}

		/*
		 * for (Entry<int[], ArrayList<Integer>> entry : values.entrySet()) {
		 * System.out.println(Arrays.toString(entry.getKey()) + " = " + entry.getValue()); }
		 */

		CheckStruct[] checkSts = new CheckStruct[numS + 1];

		for (int i = 1; i <= numS; i++) {
			checkSts[i] = new CheckStruct(strings[i]);
		}

		long ans = 0;

		preCount = new int[numS + 1];
		for (int i = 1; i <= numE; i++) {
			for (int j = 1; j <= numS; j++) {
				preCount[j] += strings[j][i - 1];
			}
			// prefixCount doesn't include things in index i
			ArrayList<Integer> matches = values.get(preCount);
			if (matches == null) {
				for (int j = 1; j <= numS; j++) {
					checkSts[j].remove(preCount[j] + strings[j][i]);
				}
				continue;
			}

			// out of bounds
			int oB = numE + 1;
			for (int j = 1; j <= numS; j++) {
				int newR = checkSts[j].query(preCount[j] - 1);
				if (newR < oB) {
					oB = newR;
				}
			}
			assert (i <= oB);
			ans += binCount(matches, oB - 1) - binCount(matches, i);

			for (int j = 1; j <= numS; j++) {
				checkSts[j].remove(preCount[j] + strings[j][i]);
			}
		}
		printer.println(ans);
		printer.close();
	}

	int binCount(ArrayList<Integer> elements, int max) {
		int low = -1;
		int high = elements.size() - 1;

		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (elements.get(mid) <= max) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low + 1;
	}

	int offset;

	class CheckStruct {

		@SuppressWarnings("unchecked")
		CheckStruct(int[] string) {
			base = new ArrayDeque[numE * 2 + 3];
			for (int i = 0; i < base.length; i++) {
				base[i] = new ArrayDeque<Integer>();
			}

			int preCount = 0;
			for (int i = 1; i <= numE; i++) {
				preCount += string[i];
				base[offset + preCount].addLast(i);
			}
			tree = new int[10 * numE + 4];
			// Arrays.fill(tree, Integer.MAX_VALUE);
			buildTree(1, -numE, numE);
		}

		void buildTree(int tI, int cL, int cR) {
			if (cL == cR) {
				if (!base[offset + cL].isEmpty()) {
					tree[tI] = base[offset + cL].peekFirst();
				} else {
					tree[tI] = Integer.MAX_VALUE;
				}
			} else {
				int mid = (cL + cR) >> 1;
				buildTree(tI * 2, cL, mid);
				buildTree(tI * 2 + 1, mid + 1, cR);
				int lC = tree[tI * 2];
				int rC = tree[tI * 2 + 1];
				tree[tI] = lC < rC ? lC : rC;
			}
		}

		ArrayDeque<Integer>[] base;
		int[] tree;

		int query(int qR) {
			return query(1, -numE, numE, -numE, qR);
		}

		int query(int tI, int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return Integer.MAX_VALUE;
			}
			if (qL <= cL && cR <= qR) {
				return tree[tI];
			}
			int mid = (cL + cR) >> 1;
			int lC = query(tI * 2, cL, mid, qL, qR);
			int rC = query(tI * 2 + 1, mid + 1, cR, qL, qR);
			return lC < rC ? lC : rC;
		}

		void remove(int uI) {
			remove(1, -numE, numE, uI);
		}

		void remove(int tI, int cL, int cR, int uI) {
			if (cL == cR) {
				base[offset + cL].removeFirst();
				if (!base[offset + cL].isEmpty()) {
					tree[tI] = base[offset + cL].peekFirst();
				} else {
					tree[tI] = Integer.MAX_VALUE;
				}
			} else {
				int mid = (cL + cR) >> 1;
				if (uI <= mid) {
					remove(tI * 2, cL, mid, uI);
				} else {
					remove(tI * 2 + 1, mid + 1, cR, uI);
				}
				int lC = tree[tI * 2];
				int rC = tree[tI * 2 + 1];
				tree[tI] = lC < rC ? lC : rC;
			}
		}
	}

	Comparator<int[]> KeySetComp = new Comparator<int[]>() {
		public int compare(int[] a1, int[] a2) {
			for (int i = 1; i <= numS; i++) {
				if (a1[i] < a2[i]) {
					return -1;
				}
				if (a1[i] > a2[i]) {
					return 1;
				}
			}
			return 0;
		}
	};

}
