import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MoovieMooving {

	static int nO;
	static int tD;

	static int[] dur;
	static int[][] sT;
	static int[][] eT;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("movie.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("movie.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nO = Integer.parseInt(inputData.nextToken());
		tD = Integer.parseInt(inputData.nextToken());
		dur = new int[nO];
		sT = new int[nO][];
		eT = new int[nO][];

		for (int i = 0; i < nO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			dur[i] = Integer.parseInt(inputData.nextToken());
			int numScreenings = Integer.parseInt(inputData.nextToken());
			sT[i] = new int[numScreenings];
			eT[i] = new int[numScreenings];

			for (int j = 0; j < numScreenings; j++) {
				sT[i][j] = Integer.parseInt(inputData.nextToken());
				eT[i][j] = sT[i][j] + dur[i];
			}
		}
		reader.close();

		int ans = Integer.MAX_VALUE;

		int[] cD = new int[1 << nO];

		for (int cS = 0; cS < 1 << nO; cS++) {
			if (cD[cS] >= tD) {
				ans = Math.min(ans, Integer.bitCount(cS));
			}

			for (int cO = 0; cO < nO; cO++) {
				if ((cS & (1 << cO)) == 0) {
					int sN = Arrays.binarySearch(sT[cO], cD[cS]);
					if (sN < 0) {
						sN = -sN - 2;
						if (sN < 0) {
							continue;
						}
					}
					int nS = cS ^ (1 << cO);
					cD[nS] = Math.max(cD[nS], eT[cO][sN]);
				}
			}
		}
		printer.println(ans == Integer.MAX_VALUE ? -1 : ans);
		printer.close();
	}

}