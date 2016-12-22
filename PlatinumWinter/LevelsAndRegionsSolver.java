import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LevelsAndRegionsSolver {

	public static void main(String[] args) throws IOException {
		new LevelsAndRegionsSolver().execute();
	}

	long[] sum;
	double[] reciprocalSum;

	double[] cost;

	double[] pDP;
	double[] cDP;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numD = Integer.parseInt(inputData.nextToken());

		long[] num = new long[numE + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numE; i++) {
			num[i] = Long.parseLong(inputData.nextToken());
		}
		reader.close();

		sum = new long[numE + 1];
		reciprocalSum = new double[numE + 1];

		cost = new double[numE + 1];

		for (int i1 = 1; i1 <= numE; i1++) {
			sum[i1] = sum[i1 - 1] + num[i1];
			reciprocalSum[i1] = reciprocalSum[i1 - 1] + 1.0 / num[i1];
			cost[i1] = cost[i1 - 1] + ((double) sum[i1]) / num[i1];
		}

		pDP = new double[numE + 1];
		cDP = new double[numE + 1];

		for (int i = 1; i <= numE; i++) {
			pDP[i] = cost[i];
		}

		for (int i = 2; i <= numD; i++) {
			updateValues(1, numE, 1, numE);
			double[] temp = pDP;
			pDP = cDP;
			cDP = temp;
		}
		System.out.println(pDP[numE]);
	}

	void updateValues(int rLeft, int rRight, int pMin, int pMax) {
		if (rLeft > rRight) {
			return;
		}

		int cInd = (rLeft + rRight) / 2;

		double min = Double.MAX_VALUE;
		int optPrev = 1;

		int pBound = pMax < (cInd - 1) ? pMax : (cInd - 1);
		for (int prev = pMin; prev <= pBound; prev++) {
			double value = pDP[prev]
					+ ((cost[cInd] - cost[prev]) - sum[prev] * (reciprocalSum[cInd] - reciprocalSum[prev]));
			if (value < min) {
				min = value;
				optPrev = prev;
			}
		}

		cDP[cInd] = min;

		if (rLeft == rRight) {
			return;
		}

		updateValues(rLeft, cInd - 1, pMin, optPrev);
		updateValues(cInd + 1, rRight, optPrev, pMax);
	}

}
