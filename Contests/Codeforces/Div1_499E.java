import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div1_499E {

	static int[] mC;
	static int nO;
	static int nC;
	static int nQ;

	static int[][] oCoor;
	static int[][] cCoor;
	static int[][] qCoor;

	static boolean[] ans;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		mC = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
				Integer.parseInt(inputData.nextToken()) };
		nO = Integer.parseInt(inputData.nextToken());
		nC = Integer.parseInt(inputData.nextToken());
		nQ = Integer.parseInt(inputData.nextToken());

		oCoor = new int[nO][3];
		for (int i = 0; i < nO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 3; j++) {
				oCoor[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		cCoor = new int[nC][3];
		for (int i = 0; i < nC; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 3; j++) {
				cCoor[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		qCoor = new int[nQ][4];
		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < 3; j++) {
				qCoor[i][j] = Integer.parseInt(inputData.nextToken());
			}
			qCoor[i][3] = i;
		}
		//////

		int[][] bounds = new int[3][2];
		for (int i = 0; i < 3; i++) {
			bounds[i][0] = Integer.MAX_VALUE;
			bounds[i][1] = Integer.MIN_VALUE;
		}

		for (int i = 0; i < nO; i++) {
			for (int j = 0; j < 3; j++) {
				bounds[j][0] = Math.min(bounds[j][0], oCoor[i][j]);
				bounds[j][1] = Math.max(bounds[j][1], oCoor[i][j]);
			}
		}
		//////

		for (int i = 0; i < nC; i++) {
			int cnt = 0;
			for (int j = 0; j < 3; j++) {
				if (bounds[j][0] <= cCoor[i][j] && cCoor[i][j] <= bounds[j][1]) {
					cnt++;
				}
			}
			if (cnt == 3) {
				printer.println("INCORRECT");
				printer.close();
				return;
			}
		}
		printer.println("CORRECT");
		//////

		ArrayList<int[]>[] pGroups = new ArrayList[8];

		for (int i = 0; i < 8; i++) {
			pGroups[i] = new ArrayList<>();
		}

		// 0 = exclude lower
		// 1 = exclude greater
		for (int i = 0; i < nC; i++) {
			int[] cOrient = new int[3];
			for (int j = 0; j < 3; j++) {
				if (cCoor[i][j] < bounds[j][0]) {
					cOrient[j] = 0;
				} else if (cCoor[i][j] <= bounds[j][1]) {
					cCoor[i][j] = 1;
					cOrient[j] = 1;
				} else {
					cOrient[j] = 1;
				}
			}
			pGroups[cOrient[0] + (cOrient[1] << 1) + (cOrient[2] << 2)].add(cCoor[i]);
		}

		ans = new boolean[nQ];
		Arrays.fill(ans, true);

		for (int i = 0; i < 8; i++) {
			ArrayList<int[]> qCopy = new ArrayList<int[]>();
			for (int j = 0; j < nQ; j++) {
				qCopy.add(Arrays.copyOf(qCoor[j], 4));
			}

			for (int j = 0; j < 3; j++) {
				if ((i & (1 << j)) == 0) {
					for (int[] cCoor : qCopy) {
						cCoor[j] = mC[j] + 1 - cCoor[j];
					}
					for (int[] cCoor : pGroups[i]) {
						cCoor[j] = mC[j] + 1 - cCoor[j];
					}
				}
			}
			process(pGroups[i], qCopy);
		}

		pLoop:
		for (int i = 0; i < nQ; i++) {
			for (int j = 0; j < 3; j++) {
				if (qCoor[i][j] < bounds[j][0] || bounds[j][1] < qCoor[i][j]) {
					printer.println(ans[i] ? "UNKNOWN" : "CLOSED");
					continue pLoop;
				}
			}
			printer.println("OPEN");
		}
		printer.close();
	}

	// closed means that any point with greater than or equal coordinates is excluded
	static void process(ArrayList<int[]> closed, ArrayList<int[]> queries) {
		ArrayList<int[]> merged = closed;
		merged.addAll(queries);
		Collections.sort(merged, byX);
		BIT_init();

		for (int[] cP : merged) {
			// update
			if (cP.length == 3) {
				BIT_update(cP[1], cP[2] - 1);
			}
			// query
			else {
				int maxZ = BIT_query(cP[1]);
				if (cP[2] > maxZ) {
					ans[cP[3]] = false;
				}
			}
		}
	}

	static int[] BIT;

	static void BIT_init() {
		BIT = new int[mC[1] + 1];
		Arrays.fill(BIT, Integer.MAX_VALUE);
	}

	static void BIT_update(int i, int v) {
		while (i < BIT.length) {
			BIT[i] = Math.min(BIT[i], v);
			i += (i & -i);
		}
	}

	static int BIT_query(int i) {
		int min = Integer.MAX_VALUE;
		while (i > 0) {
			min = Math.min(min, BIT[i]);
			i -= (i & -i);
		}
		return min;
	}

	// ties are broken with priority to updates
	static Comparator<int[]> byX = new Comparator<int[]>() {
		public int compare(int[] c1, int[] c2) {
			if (c1[0] < c2[0]) {
				return -1;
			}
			if (c1[0] > c2[0]) {
				return 1;
			}
			return Integer.compare(c1.length, c2.length);
		}
	};
}