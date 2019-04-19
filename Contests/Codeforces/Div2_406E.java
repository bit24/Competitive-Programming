import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_406E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numI = Integer.parseInt(reader.readLine());
		int[] items = new int[numI + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] last = new int[numI + 1];
		Arrays.fill(last, -1);
		int[] next = new int[numI + 1];

		for (int i = numI; i >= 1; i--) {
			int cItem = items[i];
			next[i] = last[cItem];
			last[cItem] = i;
		}

		tree = new int[numI * 4];
		for (int i = 1; i <= numI; i++) {
			if (last[i] != -1) {
				update(1, 1, numI, last[i], 1);
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] queries = new ArrayList[numI + 1];
		for (int i = 1; i <= numI; i++) {
			queries[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i <= numI; i++) {
			queries[1].add(i);
		}

		int[] cCost = new int[numI + 1];

		for (int cInd = 1; cInd <= numI; cInd++) {
			for (int cQ : queries[cInd]) {
				int lInd = query(1, 1, numI, cQ);
				cCost[cQ]++;
				if (lInd < numI) {
					queries[lInd + 1].add(cQ);
				}
			}
			update(1, 1, numI, cInd, -1);
			if (next[cInd] != -1) {
				update(1, 1, numI, next[cInd], 1);
			}
		}

		for (int i = 1; i <= numI; i++) {
			printer.print(cCost[i] + " ");
		}
		printer.println();
		printer.close();
	}

	static int[] tree;

	static void update(int nI, int cL, int cR, int uI, int del) {
		if (cL == cR) {
			tree[nI] += del;
		} else {
			int mid = (cL + cR) / 2;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI, del);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI, del);
			}
			tree[nI] = tree[nI * 2] + tree[nI * 2 + 1];
		}
	}

	static int query(int nI, int cL, int cR, int qAmt) {
		if (cL == cR) {
			if (qAmt >= tree[nI]) {
				return cL;
			} else {
				return cL - 1;
			}
		} else {
			int mid = (cL + cR) / 2;
			if (tree[nI * 2] > qAmt) {
				return query(nI * 2, cL, mid, qAmt);
			} else {
				return query(nI * 2 + 1, mid + 1, cR, qAmt - tree[nI * 2]);
			}
		}
	}

}
