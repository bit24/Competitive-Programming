import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class GB_2016E {

	public static void main(String[] args) throws IOException {
		new GB_2016E().execute();
	}

	char[] elements;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());

		elements = (" " + reader.readLine()).toCharArray();
		tree = new int[numE * 4][][];
		build(1, 1, numE);

		int numQ = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int left = Integer.parseInt(inputData.nextToken());
			int right = Integer.parseInt(inputData.nextToken());
			int[][] data = query(1, 1, numE, left, right);
			if (data[0][4] == Integer.MAX_VALUE / 4) {
				printer.println(-1);
			} else {
				printer.println(data[0][4]);
			}
		}

		printer.close();
	}

	int[][][] tree;

	char[] good = "2017".toCharArray();

	int[][] query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return null;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) / 2;
		int[][] a = query(nI * 2, cL, mid, qL, qR);
		int[][] b = query(nI * 2 + 1, mid + 1, cR, qL, qR);

		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		int[][] rCost = new int[5][5];

		for (int left = 0; left <= 4; left++) {
			for (int right = 0; right <= 4; right++) {
				rCost[left][right] = Integer.MAX_VALUE / 4;
			}
		}

		for (int left = 0; left <= 4; left++) {
			for (int right = left; right <= 4; right++) {
				for (int split = left; split <= right; split++) {
					int newCost = a[left][split] + b[split][right];
					if (newCost < rCost[left][right]) {
						rCost[left][right] = newCost;
					}
				}
			}
		}

		return rCost;
	}

	void build(int nI, int l, int r) {
		if (l == r) {
			char value = elements[l];
			int[][] cost = new int[5][5];

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 4; j++) {
					if (i != j) {
						cost[i][j] = Integer.MAX_VALUE / 4;
					}
				}
			}

			for (int i = 0; i < 4; i++) {
				if (value == good[i]) {
					cost[i][i + 1] = 0;
					cost[i][i] = 1;
					break;
				}
			}
			if (value == '6') {
				cost[3][3] = 1;
				cost[4][4] = 1;
			}
			tree[nI] = cost;
		} else {
			int mid = (l + r) / 2;
			build(nI * 2, l, mid);
			build(nI * 2 + 1, mid + 1, r);

			int[][] a = tree[nI * 2];
			int[][] b = tree[nI * 2 + 1];

			int[][] rCost = new int[5][5];

			for (int left = 0; left <= 4; left++) {
				for (int right = 0; right <= 4; right++) {
					rCost[left][right] = Integer.MAX_VALUE / 4;
				}
			}

			for (int left = 0; left <= 4; left++) {
				for (int right = left; right <= 4; right++) {
					for (int split = left; split <= right; split++) {
						int newCost = a[left][split] + b[split][right];
						if (newCost < rCost[left][right]) {
							rCost[left][right] = newCost;
						}
					}
				}
			}
			tree[nI] = rCost;
		}
	}
}