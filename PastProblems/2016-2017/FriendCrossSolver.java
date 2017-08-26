import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FriendCrossSolver {

	public static void main(String[] args) throws IOException {
		new FriendCrossSolver().execute();
	}

	int numE;
	int K;

	int[] aInd;
	int[] bInd;
	int[] part;

	int[] aLoc;
	int[] bLoc;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("friendcross.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("friendcross.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());
		aInd = new int[numE + 1];
		aLoc = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			aInd[i] = Integer.parseInt(reader.readLine());
			aLoc[aInd[i]] = i;
		}

		bInd = new int[numE + 1];
		bLoc = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			bInd[i] = Integer.parseInt(reader.readLine());
			bLoc[bInd[i]] = i;
		}
		reader.close();

		part = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			part[aLoc[i]] = bLoc[i];
		}

		preIdentify();

		long cnt = 0;
		for (int cT = 1; cT <= numE; cT++) {
			int inRange = cT - K - 1;
			if (inRange >= 1) {
				queryTree.update(aLoc[inRange], bLoc[inRange]);
			}

			int c_aPos = aLoc[cT];
			int c_bPos = bLoc[cT];

			cnt += queryTree.query(1, c_aPos - 1, c_bPos + 1, numE);
			cnt += queryTree.query(c_aPos + 1, numE, 1, c_bPos - 1);
		}
		
		printer.println(cnt);
		printer.close();
	}

	SegTree queryTree;

	// identifies values
	void preIdentify() {
		queryTree = new SegTree(part);
	}

	class TreeNode {
		// items is one-indexed
		int[] items;

		int[] BIT;

		int findLE(int item) {
			int l = 0;
			int h = items.length - 1;

			while (l != h) {
				int mid = (l + h + 1) / 2;
				if (items[mid] <= item) {
					l = mid;
				} else {
					h = mid - 1;
				}
			}
			return l;
		}

		void addItem(int item) {
			int ind = findLE(item);
			assert (items[ind] == item);
			updateInd(ind);
		}

		void updateInd(int ind) {
			while (ind < BIT.length) {
				BIT[ind]++;
				ind += (ind & -ind);
			}
		}

		// find number of elements in range [lItem, rItem]
		int query(int lItem, int rItem) {
			if (lItem > rItem) {
				return 0;
			}
			// we're finding the index of the item less than lItem to exclude
			int lInd = findLE(lItem - 1);
			int rInd = findLE(rItem);
			return queryInd(rInd) - queryInd(lInd);
		}

		int queryInd(int ind) {
			int sum = 0;
			while (ind > 0) {
				sum += BIT[ind];
				ind -= (ind & -ind);
			}
			return sum;
		}

		TreeNode(int v) {
			items = new int[] { 0, v };
			BIT = new int[2];
		}

		TreeNode() {}

		TreeNode(TreeNode original) {
			items = Arrays.copyOf(original.items, original.items.length);
			BIT = new int[items.length];
		}
	}

	TreeNode mergeItems(TreeNode a, TreeNode b) {
		if (a == null) {
			assert (false);
			return new TreeNode(b);
		}
		if (b == null) {
			assert (false);
			return new TreeNode(a);
		}

		int[] aItems = a.items, bItems = b.items;
		int[] rItems = new int[aItems.length + bItems.length - 1];

		int aI = 1, bI = 1;

		while (aI < aItems.length || bI < bItems.length) {
			if (bI == bItems.length || (aI < aItems.length && aItems[aI] < bItems[bI])) {
				rItems[aI + bI - 1] = aItems[aI];
				aI++;
			} else {
				rItems[aI + bI - 1] = bItems[bI];
				bI++;
			}
		}
		TreeNode rNode = new TreeNode();
		rNode.items = rItems;
		rNode.BIT = new int[rItems.length];
		return rNode;
	}

	class SegTree {

		int numE;
		TreeNode[] tree;

		SegTree(int[] initialData) {
			numE = initialData.length - 1;
			tree = new TreeNode[initialData.length * 4];
			buildTree(initialData, 1, 1, numE);
		}

		void buildTree(int[] data, int nI, int rL, int rR) {
			if (rL == rR) {
				tree[nI] = new TreeNode(data[rL]);
			} else {
				// internal node
				int midPt = rL + (rR - rL) / 2;
				buildTree(data, nI * 2, rL, midPt);
				buildTree(data, nI * 2 + 1, midPt + 1, rR);
				tree[nI] = mergeItems(tree[nI * 2], tree[nI * 2 + 1]);
			}
		}

		int query(int qL1, int qR1, int qL2, int qR2) {
			if (qL1 > qR1 || qL2 > qR2) {
				return 0;
			}
			return query(1, 1, numE, qL1, qR1, qL2, qR2);
		}

		int query(int nI, int cL, int cR, int qL1, int qR1, int qL2, int qR2) {
			if (cR < qL1 || qR1 < cL) {
				return 0;
			}

			if (qL1 <= cL && cR <= qR1) {
				return tree[nI].query(qL2, qR2);
			}

			int midPt = cL + (cR - cL) / 2;

			int leftQ = query(nI * 2, cL, midPt, qL1, qR1, qL2, qR2);
			int rightQ = query(nI * 2 + 1, midPt + 1, cR, qL1, qR1, qL2, qR2);

			return leftQ + rightQ;
		}

		void update(int uI1, int uI2) {
			update(1, 1, numE, uI1, uI2);
		}

		void update(int nI, int cL, int cR, int uI1, int uI2) {
			if (cL == cR) {
				tree[nI].addItem(uI2);
				return;
			}

			int midPt = cL + (cR - cL) / 2;

			if (uI1 <= midPt) {
				update(nI * 2, cL, midPt, uI1, uI2);
			} else {
				update(nI * 2 + 1, midPt + 1, cR, uI1, uI2);
			}

			tree[nI].addItem(uI2);
		}
	}

}
