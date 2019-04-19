import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_426D {

	static int len;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		len = Integer.parseInt(inputData.nextToken());
		int numG = Integer.parseInt(inputData.nextToken());

		if (numG > len) {
			System.out.println(0);
			return;
		}

		int[] type = new int[len + 1];
		inputData = new StringTokenizer(reader.readLine());

		int[] last = new int[len + 1];

		int[] prev = new int[len + 1];
		for (int i = 1; i <= len; i++) {
			int cT = Integer.parseInt(inputData.nextToken());
			type[i] = cT;
			prev[i] = last[cT];
			last[cT] = i;
		}

		cTree = new int[4 * len];
		cLazy = new int[4 * len];

		int[] nVal = new int[len + 2];

		for (int i = 1; i <= numG; i++) {
			for (int cI = 1; cI <= len; cI++) {
				update(prev[cI] + 1, cI, 1);
				nVal[cI] = query(1, cI);
			}
			shift(nVal);
			Arrays.fill(cLazy, 0);
			buildTree(nVal);
		}
		System.out.println(nVal[len + 1]);
	}

	static void shift(int[] array) {
		for (int i = len; i >= 0; i--) {
			array[i + 1] = array[i];
		}
	}

	static void buildTree(int[] init) {
		buildTree(init, 1, 1, len);
	}

	static void buildTree(int[] init, int nI, int rL, int rR) {
		if (rL == rR) {
			// leaf node
			cTree[nI] = init[rL];
		} else {
			// internal node
			int midPt = rL + (rR - rL) / 2;
			buildTree(init, nI * 2, rL, midPt);
			buildTree(init, nI * 2 + 1, midPt + 1, rR);
			cTree[nI] = Math.max(cTree[nI * 2], cTree[nI * 2 + 1]);
		}
	}

	static int[] cLazy;
	static int[] cTree;

	static int query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, len, qL, qR);
	}

	static int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (cLazy[nI] != 0) {
			cTree[nI] += cLazy[nI];

			if (cL != cR) {
				cLazy[nI * 2] += cLazy[nI];
				cLazy[nI * 2 + 1] += cLazy[nI];
			}
			cLazy[nI] = 0;
		}

		if (qL <= cL && cR <= qR) {
			return cTree[nI];
		}

		int midPt = cL + (cR - cL) / 2;

		int leftQ = query(nI * 2, cL, midPt, qL, qR);
		int rightQ = query(nI * 2 + 1, midPt + 1, cR, qL, qR);

		return Math.max(leftQ, rightQ);
	}

	static void update(int uL, int uR, int delta) {
		if (uL <= uR) {
			update(1, 1, len, uL, uR, delta);
		}
	}

	static void update(int nI, int cL, int cR, int uL, int uR, int delta) {
		// current node needs to have no cLazy before returning because it will be used to compute tree[parent]
		if (cLazy[nI] != 0) {
			cTree[nI] += cLazy[nI];

			if (cL != cR) {
				cLazy[nI * 2] += cLazy[nI];
				cLazy[nI * 2 + 1] += cLazy[nI];
			}
			cLazy[nI] = 0;
		}
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			cTree[nI] += delta;

			if (cL != cR) {
				cLazy[nI * 2] += delta;
				cLazy[nI * 2 + 1] += delta;
			}
			return;
		}

		int midPt = cL + (cR - cL) / 2;

		update(nI * 2, cL, midPt, uL, uR, delta);
		update(nI * 2 + 1, midPt + 1, cR, uL, uR, delta);

		cTree[nI] = Math.max(cTree[nI * 2], cTree[nI * 2 + 1]);
	}

}
