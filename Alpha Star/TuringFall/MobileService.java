import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MobileService {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nR = Integer.parseInt(inputData.nextToken());

		int[][] dist = new int[nV][nV];
		for (int i = 0; i < nV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < nV; j++) {
				dist[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}

		int[] req = new int[nR];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nR; i++) {
			req[i] = Integer.parseInt(inputData.nextToken()) - 1;
		}

		int[][] oCost = new int[nV][nV];

		for (int[] a : oCost) {
			Arrays.fill(a, Integer.MAX_VALUE);
		}

		int[][] nCost = new int[nV][nV];

		for (int[] a : nCost) {
			Arrays.fill(a, Integer.MAX_VALUE);
		}

		if (req[0] != 0 && req[0] != 1) {
			nCost[0][1] = dist[2][req[0]];
		}
		if (req[0] != 0 && req[0] != 2) {
			nCost[0][2] = dist[1][req[0]];
		}
		if (req[0] != 1 && req[0] != 2) {
			nCost[1][2] = dist[0][req[0]];
		}

		for (int i = 0; i + 1 < nR; i++) {
			int[][] temp = oCost;
			oCost = nCost;
			nCost = temp;
			for (int[] a : nCost) {
				Arrays.fill(a, Integer.MAX_VALUE);
			}

			for (int v1 = 0; v1 < nV; v1++) {
				for (int v2 = v1 + 1; v2 < nV; v2++) {
					if (oCost[v1][v2] == Integer.MAX_VALUE) {
						continue;
					}
					if (req[i + 1] != v1 && req[i + 1] != v2) {
						nCost[v1][v2] = Math.min(nCost[v1][v2], oCost[v1][v2] + dist[req[i]][req[i + 1]]);
					}
					if (req[i + 1] != req[i] && req[i + 1] != v2) {
						if (req[i] < v2) {
							nCost[req[i]][v2] = Math.min(nCost[req[i]][v2], oCost[v1][v2] + dist[v1][req[i + 1]]);
						} else {
							nCost[v2][req[i]] = Math.min(nCost[v2][req[i]], oCost[v1][v2] + dist[v1][req[i + 1]]);
						}
					}
					if (req[i + 1] != req[i] && req[i + 1] != v1) {
						if (req[i] < v1) {
							nCost[req[i]][v1] = Math.min(nCost[req[i]][v1], oCost[v1][v2] + dist[v2][req[i + 1]]);
						} else {
							nCost[v1][req[i]] = Math.min(nCost[v1][req[i]], oCost[v1][v2] + dist[v2][req[i + 1]]);
						}
					}
				}
			}
		}

		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < nV; i++) {
			for (int j = i + 1; j < nV; j++) {
				ans = Math.min(ans, nCost[i][j]);
			}
		}
		printer.println(ans);
		printer.close();
	}

}
