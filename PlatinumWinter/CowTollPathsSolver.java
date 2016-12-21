import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class CowTollPathsSolver {

	static int[] vCost;

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numV = Integer.parseInt(inputData.nextToken());
		int numEdges = Integer.parseInt(inputData.nextToken());
		int numQueries = Integer.parseInt(inputData.nextToken());

		vCost = new int[numV];
		for (int i = 0; i < numV; i++) {
			vCost[i] = Integer.parseInt(reader.readLine());
		}

		int[][] cCost = new int[numV][numV];

		for (int i = 0; i < numV; i++) {
			for (int j = 0; j < numV; j++) {
				cCost[i][j] = Integer.MAX_VALUE / 4;
			}
		}

		int[][] fCost = new int[numV][numV];
		for (int i = 0; i < numV; i++) {
			Arrays.fill(fCost[i], Integer.MAX_VALUE / 4);
		}

		for (int i = 0; i < numV; i++) {
			cCost[i][i] = 0;
			fCost[i][i] = vCost[i];
		}

		for (int i = 0; i < numEdges; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());

			if (c < cCost[a][b]) {
				assert (cCost[a][b] == cCost[b][a]);
				cCost[a][b] = c;
				cCost[b][a] = c;
				fCost[a][b] = c + Math.max(vCost[a], vCost[b]);
				fCost[b][a] = fCost[a][b];
			}
		}

		Integer[] ordering = new Integer[numV];
		for (int i = 0; i < numV; i++) {
			ordering[i] = i;
		}
		Arrays.sort(ordering, new CowTollPathsSolver().new BYCost());

		for (int inter : ordering) {
			for (int start = 0; start < numV; start++) {
				for (int end = 0; end < numV; end++) {

					int newCost = cCost[start][inter] + cCost[inter][end];
					if (newCost < cCost[start][end]) {
						cCost[start][end] = newCost;
						// System.out.println((start + 1) + ", " + (end + 1) + " ->" + newCost);
					}

					if (vCost[start] <= vCost[inter] && vCost[end] <= vCost[inter]) {
						int totalCost = cCost[start][end] + vCost[inter];
						if (totalCost < fCost[start][end]) {
							fCost[start][end] = totalCost;
						}
					}
				}
			}
		}

		for (int i = 0; i < numQueries; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			printer.println(fCost[a][b]);
		}
		printer.close();
	}

	class BYCost implements Comparator<Integer> {
		public int compare(Integer c1, Integer c2) {
			return Integer.compare(vCost[c1], vCost[c2]);
		}
	}
}
