import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div1_526C {

	static int nV;

	static ArrayList<Integer>[] chldn;

	static int root;

	static int[][] anc;
	static int[] depth;

	static int[] num;

	static int[] nLoc;

	static int[][] tree;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		chldn = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			chldn[i] = new ArrayList<>();
		}

		anc = new int[nV][21];
		depth = new int[nV];

		num = new int[nV];
		nLoc = new int[nV];
		tree = new int[nV * 4][2];
		for (int[] a : tree) {
			a[0] = a[1] = -1;
		}

		root = 0;

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			num[i] = Integer.parseInt(inputData.nextToken());
			nLoc[num[i]] = i;
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i < nV; i++) {
			anc[i][0] = Integer.parseInt(inputData.nextToken()) - 1;
			chldn[anc[i][0]].add(i);
		}

		preprocess();

		build(1, 0, nV - 1);

		int nQ = Integer.parseInt(reader.readLine());

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("1")) {
				int a = Integer.parseInt(inputData.nextToken()) - 1;
				int b = Integer.parseInt(inputData.nextToken()) - 1;

				int temp = num[a];
				num[a] = num[b];
				num[b] = temp;

				nLoc[num[a]] = a;
				nLoc[num[b]] = b;

				update(1, 0, nV - 1, num[a]);
				update(1, 0, nV - 1, num[b]);
			} else {
				printer.println(query(1, 0, nV - 1, nLoc[0], nLoc[0]) + 1);
			}
		}
		printer.close();
	}

	static void build(int nI, int cL, int cR) {
		if (cL == cR) {
			tree[nI][0] = nLoc[cL];
			tree[nI][1] = nLoc[cL];
		} else {
			int mid = (cL + cR) >> 1;
			build(nI * 2, cL, mid);
			build(nI * 2 + 1, mid + 1, cR);
			if (tree[nI * 2][0] != -1 && tree[nI * 2 + 1][0] != -1) {
				merge(tree[nI * 2][0], tree[nI * 2][1], tree[nI * 2 + 1][0], tree[nI * 2 + 1][1]);
				tree[nI][0] = mResp[0];
				tree[nI][1] = mResp[1];
			}
		}
	}

	static int query(int nI, int cL, int cR, int e1, int e2) {
		if (cL == cR) {
			merge(e1, e2, nLoc[cL], nLoc[cL]);
			if (mResp[0] != -1) {
				return cL;
			} else {
				return cL - 1;
			}
		}
		int mid = (cL + cR) >> 1;

		merge(tree[nI * 2][0], tree[nI * 2][1], e1, e2);
		if (mResp[0] != -1) {
			return query(nI * 2 + 1, mid + 1, cR, mResp[0], mResp[1]);
		}
		return query(nI * 2, cL, mid, e1, e2);
	}

	static void update(int nI, int cL, int cR, int uI) {
		if (cL == cR) {
			tree[nI][0] = nLoc[cL];
			tree[nI][1] = nLoc[cL];
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI);
			}
			merge(tree[nI * 2][0], tree[nI * 2][1], tree[nI * 2 + 1][0], tree[nI * 2 + 1][1]);
			tree[nI][0] = mResp[0];
			tree[nI][1] = mResp[1];
		}
	}

	static int[] mResp = new int[2];

	static void merge1(int... a) {
		for (int i = 0; i < 3; i++) {
			if (a[i] == -1) {
				mResp[0] = mResp[1] = -1;
				return;
			}
		}

		if (onPath(a[0], a[1], a[2])) {
			mResp[0] = a[0];
			mResp[1] = a[1];
			return;
		}
		if (onPath(a[0], a[2], a[1])) {
			mResp[0] = a[0];
			mResp[1] = a[2];
			return;
		}
		if (onPath(a[1], a[2], a[0])) {
			mResp[0] = a[1];
			mResp[1] = a[2];
			return;
		}
		mResp[0] = mResp[1] = -1;
	}

	static void merge(int... a) {
		merge1(a[0], a[1], a[2]);
		merge1(mResp[0], mResp[1], a[3]);
	}

	static boolean onPath(int a, int b, int c) {
		if (a == c || b == c) {
			return true;
		}

		if (depth[a] > depth[c]) {
			a = jump(a, depth[a] - depth[c] - 1);
		}
		if (depth[b] > depth[c]) {
			b = jump(b, depth[b] - depth[c] - 1);
		}
		if (a == b) {
			return false;
		}

		if (anc[a][0] == c || anc[b][0] == c) {
			return true;
		}

		return false;
	}

	// good for depth of up to 1_048_576 = 2^20

	static void preprocess() {
		anc[root][0] = root;
		fParent(root);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static void fParent(int cV) {
		for (int aV : chldn[cV]) {
			anc[aV][0] = cV;
			depth[aV] = depth[cV] + 1;
			fParent(aV);
		}
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		b = jump(b, depth[b] - depth[a]);
		if (a == b) {
			return a;
		}
		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}

	static int jump(int cV, int d) {
		for (int i = 0; i <= 20; i++) {
			if ((d & (1 << i)) != 0) {
				cV = anc[cV][i];
			}
		}
		return cV;
	}

	static Comparator<Integer> BY_DEPTH = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return -Integer.compare(depth[o1], depth[o2]); // greatest depth first
		}
	};
}