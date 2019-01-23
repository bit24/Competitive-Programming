import java.util.ArrayList;

class BinaryJumpLCA {

	static int nV;
	static ArrayList<Integer>[] chldn;

	static int root;

	static int[][] anc;
	static int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	static void preprocess() {
		anc = new int[nV][21];
		anc[root][0] = root;
		depth = new int[nV];
		fParent(root);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static void fParent(int cV) {
		for (int child : chldn[cV]) {
			anc[child][0] = cV;
			depth[child] = depth[cV] + 1;
			fParent(child);
		}
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}

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
}
