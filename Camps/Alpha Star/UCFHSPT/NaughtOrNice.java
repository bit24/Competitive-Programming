import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class NaughtOrNice {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int nH = Integer.parseInt(inputData.nextToken());
			int nU = Integer.parseInt(inputData.nextToken());

			int[][] dSum = new int[11][nH + 1];

			for (int i = 1; i <= nU; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int s = Integer.parseInt(inputData.nextToken());
				int rep = Integer.parseInt(inputData.nextToken());
				int n = Integer.parseInt(inputData.nextToken());
				dSum[rep][s]++;
				int eI = s + rep * n;
				if (eI <= nH) {
					dSum[rep][eI]--;
				}
			}

			int maxI = -1;
			int maxS = 0;

			int[][] sums = new int[11][nH + 1];

			for (int i = 1; i <= nH; i++) {
				int tSum = 0;
				for (int j = 1; j <= 10; j++) {
					sums[j][i] += dSum[j][i];
					int pI = i - j;
					if (1 <= pI) {
						sums[j][i] += sums[j][i - j];
					}
					tSum += sums[j][i];
				}
				if (tSum > maxS) {
					maxS = tSum;
					maxI = i;
				}
			}
			printer.println("House " + maxI + " should get the biggest and best gift next Christmas.");
		}
		printer.close();
	}
}
