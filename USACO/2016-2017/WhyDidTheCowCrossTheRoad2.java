import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WhyDidTheCowCrossTheRoad2 {

	int numE;

	public static void main(String[] args) throws IOException {
		new WhyDidTheCowCrossTheRoad2().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("nocross.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("nocross.out")));
		numE = Integer.parseInt(reader.readLine());
		int[] aEl = new int[numE + 1];

		int[] bLoc = new int[numE + 1];

		for (int i = 1; i <= numE; i++) {
			int item = Integer.parseInt(reader.readLine());
			aEl[i] = item;
		}
		for (int i = 1; i <= numE; i++) {
			int item = Integer.parseInt(reader.readLine());
			bLoc[item] = i;
		}
		reader.close();

		tree = new long[(numE + 1) * 4];
		// int[] debug = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			int cE = aEl[i];
			long[] qResults = new long[9];
			int qInd = 0;
			for (int pair = Math.max(1, cE - 4); pair <= Math.min(numE, cE + 4); pair++) {
				int bInd = bLoc[pair];
				qResults[qInd++] = query(1, bInd - 1);
			}
			qInd = 0;
			for (int pair = Math.max(1, cE - 4); pair <= Math.min(numE, cE + 4); pair++) {
				int bInd = bLoc[pair];
				long pBest = qResults[qInd++];
				set(bInd, pBest + 1);
				// debug[bInd] = pBest + 1;
			}
		}
		long ans = query(1, numE);
		printer.println(ans);
		printer.close();
	}

	long[] tree;

	long query(int qL, int qR) {
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, numE, qL, qR);
	}

	long query(int nodeInd, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nodeInd];
		}

		int midPt = cL + (cR - cL) / 2;

		long leftQ = query(nodeInd * 2, cL, midPt, qL, qR);
		long rightQ = query(nodeInd * 2 + 1, midPt + 1, cR, qL, qR);

		return Math.max(leftQ, rightQ);
	}

	void set(int uI, long nV) {
		update(1, 1, numE, uI, nV);
	}

	void update(int nodeInd, int cL, int cR, int uI, long nV) {
		assert (cL <= uI);
		assert (uI <= cR);
		if (cL == cR) {
			tree[nodeInd] = nV;
			return;
		}

		int midPt = cL + (cR - cL) / 2;

		if (uI <= midPt) {
			update(nodeInd * 2, cL, midPt, uI, nV);
		} else {
			update(nodeInd * 2 + 1, midPt + 1, cR, uI, nV);
		}

		tree[nodeInd] = Math.max(tree[nodeInd * 2], tree[nodeInd * 2 + 1]);
	}

}
