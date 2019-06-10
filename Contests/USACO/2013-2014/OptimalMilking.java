import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class OptimalMilking {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("optmilk.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("optmilk.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		tree = new int[numE * 4][4];
		int numD = Integer.parseInt(inputData.nextToken());
		int[] initData = new int[numE + 1];

		for (int i = 1; i <= numE; i++) {
			initData[i] = Integer.parseInt(reader.readLine());
		}
		buildTree(initData, 1, 1, numE);

		long ans = 0;
		while (numD-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int ind = Integer.parseInt(inputData.nextToken());
			int nVal = Integer.parseInt(inputData.nextToken());
			update(ind, nVal);
			ans += query(1, numE)[nTaken];
		}
		reader.close();
		printer.println(ans);
		printer.close();
	}

	static void buildTree(int[] initData, int nI, int cL, int cR) {
		if (cL == cR) {
			tree[nI][nTaken] = initData[cL];
			return;
		}

		int mid = (cL + cR) / 2;
		buildTree(initData, nI * 2, cL, mid);
		buildTree(initData, nI * 2 + 1, mid + 1, cR);
		tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
	}

	static final int bTaken = 0;
	static final int lTaken = 1;
	static final int rTaken = 2;
	static final int nTaken = 3;

	static int numE;
	static int[][] tree;

	static int[] query(int qL, int qR) {
		if (qL > qR) {
			return new int[4];
		}
		return query(1, 1, numE, qL, qR);
	}

	static int[] query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return new int[4];
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int midPt = (cL + cR) / 2;

		return merge(query(nI * 2, cL, midPt, qL, qR), query(nI * 2 + 1, midPt + 1, cR, qL, qR));
	}

	static void update(int uI, int nVal) {
		update(1, 1, numE, uI, nVal);
	}

	static void update(int nI, int cL, int cR, int uI, int nVal) {
		if (cL == cR) {
			tree[nI][nTaken] = nVal;
			return;
		}

		int mid = (cL + cR) / 2;

		if (uI <= mid) {
			update(nI * 2, cL, mid, uI, nVal);
		} else {
			update(nI * 2 + 1, mid + 1, cR, uI, nVal);
		}

		tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
	}

	static int[] merge(int[] lState, int[] rState) {
		int[] ans = new int[4];
		ans[bTaken] = Math.max(lState[lTaken] + rState[bTaken], lState[bTaken] + rState[rTaken]);
		ans[lTaken] = Math.max(lState[lTaken] + rState[lTaken], lState[bTaken] + rState[nTaken]);
		ans[rTaken] = Math.max(lState[nTaken] + rState[bTaken], lState[rTaken] + rState[rTaken]);
		ans[nTaken] = Math.max(lState[nTaken] + rState[lTaken], lState[rTaken] + rState[nTaken]);
		return ans;
	}

}
