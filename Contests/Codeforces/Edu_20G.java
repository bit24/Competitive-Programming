import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

// offline solution 
public class Edu_20G {

	int numE;
	int numR;

	int[] base;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numR = Integer.parseInt(inputData.nextToken());

		base = new int[numE + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numE; i++) {
			base[i] = Integer.parseInt(inputData.nextToken());
		}

		int numQ = Integer.parseInt(reader.readLine());
		int[] act = new int[numQ + 1];
		int[] qL = new int[numQ + 1];
		int[] qR = new int[numQ + 1];
		int[] qV = new int[numQ + 1];

		ArrayList<Integer> posList = new ArrayList<Integer>();

		for (int i = 1; i <= numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			act[i] = Integer.parseInt(inputData.nextToken());
			qL[i] = Integer.parseInt(inputData.nextToken());
			qR[i] = Integer.parseInt(inputData.nextToken());
			if (act[i] == 1) {
				qV[i] = Integer.parseInt(inputData.nextToken());
			}
			posList.add(qL[i]);
			posList.add(qR[i]);
		}

		Collections.sort(posList);

		ArrayList<Integer> sigList = new ArrayList<Integer>();
		for (int i = 0; i < posList.size(); i++) {
			int cPos = posList.get(i);
			while (i + 1 < posList.size() && posList.get(i + 1) == cPos) {
				i++;
			}
			sigList.add(cPos);
			if (i + 1 < posList.size() && cPos + 1 < posList.get(i + 1)) {
				sigList.add(cPos + 1);
			}
		}

		SegTree baseTree = new SegTree(base);
		int masterSize = sigList.size();

		int[] masterInit = new int[masterSize + 1];

		for (int i = 0; i + 1 < masterSize; i++) {
			int cLeft = sigList.get(i);
			int cRight = sigList.get(i + 1) - 1;

			if (cRight - cLeft + 1 >= numE) {
				masterInit[i + 1] = baseTree.query(1, 1, numE, 1, numE);
			} else {
				cLeft %= numE;
				if (cLeft == 0) {
					cLeft = numE;
				}
				cRight %= numE;
				if (cRight == 0) {
					cRight = numE;
				}
				if (cLeft <= cRight) {
					masterInit[i + 1] = baseTree.query(1, 1, numE, cLeft, cRight);
				} else {
					masterInit[i + 1] = min(baseTree.query(1, 1, numE, cLeft, numE),
							baseTree.query(1, 1, numE, 1, cRight));
				}
			}
		}
		int fIndex = sigList.get(masterSize - 1);
		fIndex %= numE;
		if (fIndex == 0) {
			fIndex = numE;
		}
		masterInit[masterSize] = base[fIndex];

		SegTree masterTree = new SegTree(masterInit);

		for (int i = 1; i <= numQ; i++) {
			int pLeft = 1 + Collections.binarySearch(sigList, qL[i]);
			int pRight = 1 + Collections.binarySearch(sigList, qR[i]);
			if (act[i] == 1) {
				masterTree.update(1, 1, masterSize, pLeft, pRight, qV[i]);
			} else {
				printer.println(masterTree.query(1, 1, masterSize, pLeft, pRight));
			}
		}
		printer.close();
	}

	int min(int a, int b) {
		return a < b ? a : b;
	}

	class SegTree {
		int numE;
		int[] lazy;
		int[] tree;

		SegTree(int numE) {
			this.numE = numE;
			lazy = new int[4 * numE];
			tree = new int[4 * numE];
			Arrays.fill(tree, Integer.MAX_VALUE);
		}

		SegTree(int[] elements) {
			numE = elements.length - 1;
			lazy = new int[4 * numE];
			tree = new int[4 * numE];
			Arrays.fill(tree, Integer.MAX_VALUE);
			init(1, 1, numE, elements);
		}

		void init(int nI, int cL, int cR, int[] elements) {
			if (cL == cR) {
				tree[nI] = elements[cL];
			} else {
				int mid = (cL + cR) / 2;
				init(nI * 2, cL, mid, elements);
				init(nI * 2 + 1, mid + 1, cR, elements);
				tree[nI] = min(tree[nI * 2], tree[nI * 2 + 1]);
			}
		}

		void update(int nI, int cL, int cR, int uL, int uR, int delta) {
			if (lazy[nI] != 0) {
				tree[nI] = lazy[nI];
				if (cL != cR) {
					lazy[nI * 2] = lazy[nI];
					lazy[nI * 2 + 1] = lazy[nI];
				}
				lazy[nI] = 0;
			}
			if (uR < cL || cR < uL) {
				return;
			}
			if (uL <= cL && cR <= uR) {
				tree[nI] = delta;
				if (cL != cR) {
					lazy[nI * 2] = delta;
					lazy[nI * 2 + 1] = delta;
				}
				return;
			}
			int mid = (cL + cR) / 2;
			update(nI * 2, cL, mid, uL, uR, delta);
			update(nI * 2 + 1, mid + 1, cR, uL, uR, delta);
			tree[nI] = min(tree[nI * 2], tree[nI * 2 + 1]);
		}

		int query(int nI, int cL, int cR, int qL, int qR) {
			if (cR < qL || qR < cL) {
				return Integer.MAX_VALUE;
			}

			if (lazy[nI] != 0) {
				tree[nI] = lazy[nI];
				if (cL != cR) {
					// internal node
					lazy[nI * 2] = lazy[nI];
					lazy[nI * 2 + 1] = lazy[nI];
				}
				lazy[nI] = 0;
			}

			if (qL <= cL && cR <= qR) {
				return tree[nI];
			}

			int mid = (cL + cR) / 2;
			return min(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
		}
	}

	public static void main(String[] args) throws IOException {
		new Edu_20G().execute();
	}

}
