import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_408F {
	// note: there's a very high constant factor but i'm too lazy to optimize it

	static final long MAX = 1_000_000_000;

	public static void main(String[] args) throws IOException {
		new Div2_408F().execute();
	}

	boolean[] ignore;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numO = Integer.parseInt(inputData.nextToken());

		BoundSegTree bounds = new BoundSegTree(numE);
		MaxSegTree integ = new MaxSegTree(numE);

		ignore = new boolean[numE + 1];

		int[][] ops = new int[numO + 1][4];
		for (int i = 1; i <= numO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				ops[i][0] = 1;
				ops[i][1] = Integer.parseInt(inputData.nextToken());
				ops[i][2] = Integer.parseInt(inputData.nextToken());
				ops[i][3] = Integer.parseInt(inputData.nextToken());
			} else {
				ops[i][0] = 2;
				ops[i][1] = Integer.parseInt(inputData.nextToken());
				ops[i][2] = Integer.parseInt(inputData.nextToken());
			}
		}

		for (int i = 1; i <= numO; i++) {
			if (ops[i][0] == 1) {
				int l = ops[i][1];
				int r = ops[i][2];
				int max = ops[i][3];
				if (integ.query(l, r).it > max) {
					System.out.println("NO");
					return;
				}
				bounds.bound(l, r, max);
			} else {
				int uI = ops[i][1];
				int val = ops[i][2];
				if (!ignore[uI]) {
					bounds.forceLazy(uI);
					ignore[uI] = true;
				}
				integ.set(uI, val);
			}
		}

		for (int i = 1; i <= numE; i++) {
			bounds.forceLazy(i);
		}

		long[] fBounds = bounds.elements;

		MaxSegTree setVals = new MaxSegTree(numE);
		MaxSegTree origVals = new MaxSegTree(numE);

		for (int i = 1; i <= numE; i++) {
			origVals.set(i, fBounds[i]);
		}

		long[] fValues = new long[numE + 1];
		Arrays.fill(fValues, -1);

		for (int i = 1; i <= numO; i++) {
			if (ops[i][0] == 1) {
				int l = ops[i][1];
				int r = ops[i][2];
				int max = ops[i][3];
				if (setVals.query(l, r).it == max) {
					continue;
				}
				ItInd data = origVals.query(l, r);
				if (data.it != max) {
					System.out.println("NO");
					return;
				}
				fValues[data.ind] = max;
			} else {
				int uI = ops[i][1];
				int val = ops[i][2];
				origVals.set(uI, -1);
				setVals.set(uI, val);
			}
		}

		PriorityQueue<ItInd> decQueue = new PriorityQueue<ItInd>(Collections.reverseOrder());

		long fXOR = 0;
		for (int i = 1; i <= numE; i++) {
			if (fValues[i] != -1) {
				fXOR ^= fValues[i];
			} else {
				decQueue.add(new ItInd(fBounds[i], i));
			}
		}

		for (int i = 1; i <= numE; i++) {
			if (fValues[i] == -1) {
				fValues[i] = 0;
			}
		}

		while (!decQueue.isEmpty()) {
			ItInd next = decQueue.remove();
			long item = next.it;
			int ind = next.ind;
			long highest = Long.highestOneBit(item);

			if ((fXOR & highest) != 0) {
				fXOR ^= highest - 1;
				fValues[ind] ^= highest - 1;
				break;
			}

			fXOR ^= highest;
			fValues[ind] ^= highest;

			long remnant = item ^ highest;
			if (remnant != 0) {
				decQueue.add(new ItInd(remnant, ind));
			}
		}

		printer.println("YES");
		for (int i = 1; i <= numE; i++) {
			printer.print(fValues[i] + " ");
		}
		printer.println();
		printer.close();
	}

	class BoundSegTree {
		int numE;
		long[] elements;
		long[] lazy;

		BoundSegTree(int numE) {
			this.numE = numE;
			elements = new long[numE + 1];
			Arrays.fill(elements, MAX);
			lazy = new long[numE * 4];
			Arrays.fill(lazy, MAX);
		}

		void bound(int uL, int uR, long max) {
			if (uL <= uR) {
				bound(1, 1, numE, uL, uR, max);
			}
		}

		private void bound(int nI, int cL, int cR, int uL, int uR, long max) {
			// current node needs to have no lazy before returning because it will be used to compute elements[parent]
			if (lazy[nI] != MAX) {

				if (cL != cR) {
					lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
					lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);
				} else if (!ignore[cL]) {
					elements[cL] = Math.min(elements[cL], lazy[nI]);
				}
				lazy[nI] = MAX;
			}
			if (cR < uL || uR < cL) {
				return;
			}

			if (uL <= cL && cR <= uR) {
				if (cL != cR) {
					lazy[nI * 2] = Math.min(lazy[nI * 2], max);
					lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], max);
				} else if (!ignore[cL]) {
					elements[cL] = Math.min(elements[cL], max);
				}
				return;
			}

			int mid = (cL + cR) / 2;

			bound(nI * 2, cL, mid, uL, uR, max);
			bound(nI * 2 + 1, mid + 1, cR, uL, uR, max);
		}

		void forceLazy(int uI) {
			forceLazy(1, 1, numE, uI);
		}

		private void forceLazy(int nI, int cL, int cR, int uI) {
			if (cL != cR) {
				lazy[nI * 2] = Math.min(lazy[nI * 2], lazy[nI]);
				lazy[nI * 2 + 1] = Math.min(lazy[nI * 2 + 1], lazy[nI]);

				int mid = (cL + cR) / 2;
				if (uI <= mid) {
					forceLazy(nI * 2, cL, mid, uI);
				} else {
					forceLazy(nI * 2 + 1, mid + 1, cR, uI);
				}
			} else if (!ignore[cL]) {
				elements[cL] = Math.min(elements[cL], lazy[nI]);
			}

			lazy[nI] = MAX;
		}
	}

	class MaxSegTree {
		int numE;
		ItInd[] tree;

		MaxSegTree(int numE) {
			this.numE = numE;
			tree = new ItInd[numE * 4];
			Arrays.fill(tree, new ItInd(-1, -1));
		}

		void set(int uI, long val) {
			set(1, 1, numE, uI, val);
		}

		private void set(int nI, int cL, int cR, int uI, long val) {
			if (cL != cR) {
				int mid = (cL + cR) / 2;
				if (uI <= mid) {
					set(nI * 2, cL, mid, uI, val);
				} else {
					set(nI * 2 + 1, mid + 1, cR, uI, val);
				}
				tree[nI] = max(tree[nI * 2], tree[nI * 2 + 1]);
			} else {
				tree[nI] = new ItInd(val, cL);
			}
		}

		ItInd query(int qL, int qR) {
			return query(1, 1, numE, qL, qR);
		}

		private ItInd query(int nI, int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return new ItInd(-1, -1);
			}
			if (qL <= cL && cR <= qR) {
				return tree[nI];
			}
			int mid = (cL + cR) / 2;
			return max(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
		}
	}

	class ItInd implements Comparable<ItInd> {
		long it;
		int ind;

		ItInd(long it, int ind) {
			this.it = it;
			this.ind = ind;
		}

		public int compareTo(ItInd o) {
			return Long.compare(it, o.it);
		}
	}

	static ItInd max(ItInd a, ItInd b) {
		return a.it >= b.it ? a : b;
	}

}
