import java.util.ArrayList;

class BinaryJumpLCA {

	int numV;
	ArrayList<ArrayList<Integer>> children;

	int root;

	int[][] anc;
	int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	void preprocess() {
		anc = new int[numV][21];
		anc[root][0] = root;
		depth = new int[numV];
		fParent(root);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < numV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	void fParent(int cV) {
		for (int child : children.get(cV)) {
			anc[child][0] = cV;
			depth[child] = depth[cV] + 1;
			fParent(child);
		}
	}

	int findLCA(int a, int b) {
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