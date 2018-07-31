import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class PlatformParkour {

	static int nH;
	static long[] heights;

	static long[] deltas;

	static long[] dMin;
	static long[] dMax;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("platform.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("platform.out")));

		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			nH = Integer.parseInt(inputData.nextToken());
			int nP = Integer.parseInt(inputData.nextToken());

			heights = new long[nH];

			inputData = new StringTokenizer(reader.readLine());
			heights[0] = Integer.parseInt(inputData.nextToken());
			heights[1] = Integer.parseInt(inputData.nextToken());

			long W = Integer.parseInt(inputData.nextToken());
			long X = Integer.parseInt(inputData.nextToken());
			long Y = Integer.parseInt(inputData.nextToken());
			long Z = Integer.parseInt(inputData.nextToken());

			for (int i = 2; i < nH; i++) {
				heights[i] = (W * heights[i - 2] + X * heights[i - 1] + Y) % Z;
			}

			deltas = new long[nH - 1];
			for (int i = 0; i + 1 < nH; i++) {
				deltas[i] = heights[i + 1] - heights[i];
			}

			dMin = new long[nH - 1];
			dMax = new long[nH - 1];
			Arrays.fill(dMin, Long.MIN_VALUE / 8);
			Arrays.fill(dMax, Long.MAX_VALUE / 8);

			for (int i = 0; i < nP; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int s = Integer.parseInt(inputData.nextToken()) - 1;
				int e = Integer.parseInt(inputData.nextToken()) - 1;
				int up = Integer.parseInt(inputData.nextToken());
				int down = Integer.parseInt(inputData.nextToken());

				if (s < e) {
					assign(s, e - 1, -down, up);
				} else {
					assign(e, s - 1, -up, down);
				}
			}

			double low = 0;
			double high = 1_000_000;

			while (high - low > 0.000_000_01) {
				double mid = (high + low) / 2;
				if (possible(mid)) {
					high = mid;
				} else {
					low = mid;
				}
			}
			printer.print("Case #" + cT + ": ");
			printer.printf("%.7f", ((high + low) / 2));
			printer.println();
		}
		reader.close();
		printer.close();
	}

	static void assign(int l, int r, int min, int max) {
		for (int i = l; i <= r; i++) {
			dMin[i] = Math.max(dMin[i], min);
			dMax[i] = Math.min(dMax[i], max);
		}
	}

	static boolean possible(double t) {
		double pLB = heights[0] - t;
		double pUB = heights[0] + t;

		for (int i = 1; i < nH; i++) {
			double cLB = pLB + dMin[i - 1];
			double cUB = pUB + dMax[i - 1];

			cLB = Math.max(cLB, heights[i] - t);
			cUB = Math.min(cUB, heights[i] + t);

			if (cLB > cUB) {
				return false;
			}

			pLB = cLB;
			pUB = cUB;
		}
		return true;
	}
}