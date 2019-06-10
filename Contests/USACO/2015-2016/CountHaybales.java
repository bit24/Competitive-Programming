import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CountHaybales {

	public static void main(String[] args) throws IOException {
		new CountHaybales().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("haybales.in"));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numElements = Integer.parseInt(inputData.nextToken());
		int numRequests = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int[] elements = new int[numElements];

		for (int i = 0; i < numElements; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		buildTree(elements);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));

		for (int i = 0; i < numRequests; i++) {
			inputData = new StringTokenizer(reader.readLine());
			char operation = inputData.nextToken().charAt(0);
			if (operation == 'M') {
				printer.println(queryM(Integer.parseInt(inputData.nextToken()) - 1,
						Integer.parseInt(inputData.nextToken()) - 1));
			}
			if (operation == 'P') {
				update(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1,
						Long.parseLong(inputData.nextToken()));
			}
			if (operation == 'S') {
				printer.println(queryS(Integer.parseInt(inputData.nextToken()) - 1,
						Integer.parseInt(inputData.nextToken()) - 1));
			}
		}
		reader.close();
		printer.close();
	}

	long[] treeM;
	long[] treeS;
	long[] lazy;
	int numElements;

	void buildTree(int[] initialData) {
		numElements = initialData.length - 1;
		treeM = new long[initialData.length * 4];
		treeS = new long[initialData.length * 4];
		lazy = new long[initialData.length * 4];
		buildTree(initialData, 0, 0, numElements);
	}

	void buildTree(int[] initialData, int nodeInd, int rL, int rR) {
		if (rL == rR) {
			// leaf node
			treeM[nodeInd] = initialData[rL];
			treeS[nodeInd] = initialData[rL];
		} else {
			// internal node
			int midPt = rL + (rR - rL) / 2;
			buildTree(initialData, nodeInd * 2 + 1, rL, midPt);
			buildTree(initialData, nodeInd * 2 + 2, midPt + 1, rR);
			treeM[nodeInd] = min(treeM[nodeInd * 2 + 1], treeM[nodeInd * 2 + 2]);
			treeS[nodeInd] = treeS[nodeInd * 2 + 1] + treeS[nodeInd * 2 + 2];
		}
	}

	long queryM(int qL, int qR) {
		return queryM(0, 0, numElements, qL, qR);
	}

	long queryM(int nodeInd, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return Integer.MAX_VALUE;
		}

		if (lazy[nodeInd] != 0) {
			treeM[nodeInd] += lazy[nodeInd];
			treeS[nodeInd] += lazy[nodeInd] * (cR - cL + 1);
			if (cL != cR) {
				// internal node
				lazy[nodeInd * 2 + 1] += lazy[nodeInd];
				lazy[nodeInd * 2 + 2] += lazy[nodeInd];
			}
			lazy[nodeInd] = 0;
		}

		if (qL <= cL && cR <= qR) {
			return treeM[nodeInd];
		}

		int midPt = cL + (cR - cL) / 2;

		long leftQ = queryM(nodeInd * 2 + 1, cL, midPt, qL, qR);
		long rightQ = queryM(nodeInd * 2 + 2, midPt + 1, cR, qL, qR);

		return min(leftQ, rightQ);
	}

	long queryS(int qL, int qR) {
		return queryS(0, 0, numElements, qL, qR);
	}

	long queryS(int nodeInd, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (lazy[nodeInd] != 0) {
			treeM[nodeInd] += lazy[nodeInd];
			treeS[nodeInd] += lazy[nodeInd] * (cR - cL + 1);
			if (cL != cR) {
				// internal node
				lazy[nodeInd * 2 + 1] += lazy[nodeInd];
				lazy[nodeInd * 2 + 2] += lazy[nodeInd];
			}
			lazy[nodeInd] = 0;
		}

		if (qL <= cL && cR <= qR) {
			return treeS[nodeInd];
		}

		int midPt = cL + (cR - cL) / 2;

		long leftQ = queryS(nodeInd * 2 + 1, cL, midPt, qL, qR);
		long rightQ = queryS(nodeInd * 2 + 2, midPt + 1, cR, qL, qR);

		return leftQ + rightQ;
	}

	void update(int uL, int uR, long delta) {
		update(0, 0, numElements, uL, uR, delta);
	}

	void update(int nodeInd, int cL, int cR, int uL, int uR, long delta) {
		if (lazy[nodeInd] != 0) {
			treeM[nodeInd] += lazy[nodeInd];
			treeS[nodeInd] += (cR - cL + 1) * lazy[nodeInd];

			if (cL != cR) {
				lazy[nodeInd * 2 + 1] += lazy[nodeInd];
				lazy[nodeInd * 2 + 2] += lazy[nodeInd];
			}

			lazy[nodeInd] = 0;
		}

		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			treeM[nodeInd] += delta;
			treeS[nodeInd] += (cR - cL + 1) * delta;
			if (cL != cR) {
				lazy[nodeInd * 2 + 1] += delta;
				lazy[nodeInd * 2 + 2] += delta;
			}
			return;
		}

		int midPt = cL + (cR - cL) / 2;

		update(nodeInd * 2 + 1, cL, midPt, uL, uR, delta);
		update(nodeInd * 2 + 2, midPt + 1, cR, uL, uR, delta);

		treeM[nodeInd] = min(treeM[nodeInd * 2 + 1], treeM[nodeInd * 2 + 2]);
		treeS[nodeInd] = treeS[nodeInd * 2 + 1] + treeS[nodeInd * 2 + 2];
	}

	long min(long a, long b) {
		return a < b ? a : b;
	}

}
