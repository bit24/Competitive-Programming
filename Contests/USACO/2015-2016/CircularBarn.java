import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CircularBarn {

	public static void main(String[] args) throws IOException {
		new CircularBarn().execute();
	}

	int numE;
	int numSelect;

	long[] elements;
	long[][] minCost;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("cbarn.in"));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numSelect = Integer.parseInt(inputData.nextToken());
		elements = new long[numE];
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		long solution = Long.MAX_VALUE;

		for (int startLocation = 0; startLocation < numE; startLocation++) {
			long[] shifted = new long[numE];
			System.arraycopy(elements, startLocation, shifted, 0, numE - startLocation);
			System.arraycopy(elements, 0, shifted, numE - startLocation, startLocation);

			long result = computeMin(shifted);
			if (result < solution) {
				solution = result;
			}
		}
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));
		printer.println(solution);
		printer.close();
	}

	long computeMin(long[] elements) {
		long[] suffixSum = new long[numE];
		suffixSum[numE - 1] = elements[numE - 1];
		for (int i = numE - 2; i >= 0; i--) {
			suffixSum[i] = elements[i] + suffixSum[i + 1];
		}

		minCost = new long[numSelect][numE];

		for (int i = 0; i < numSelect; i++) {
			for (int j = 0; j < numE; j++) {
				minCost[i][j] = Long.MAX_VALUE / 8;
			}
		}

		long baseCost = 0;
		for (int i = 0; i < numE; i++) {
			baseCost += i * elements[i];
		}

		minCost[0][0] = baseCost;

		for (int i = 1; i < numSelect; i++) {

			hull = new Line[numE];
			hSize = 0;

			// let dp[i][j] = min cost for the WHOLE if i accesses are used and j is the location of the last access

			add(new Line(0, minCost[i - 1][0]));
			for (int j = 1; j < numE; j++) {
				// dp[i][j] = min{dp[i-1][k] - (j-k) * suffixSum[j]} for all k < j
				// = min{dp[i-1][k] + k * suffixSum[j]} - j*suffixSum[j]

				long outClause = -j * suffixSum[j];

				long minClause = query(suffixSum[j]);

				minCost[i][j] = minClause + outClause;

				add(new Line(j, minCost[i - 1][j]));
			}
		}
		long finalMin = Long.MAX_VALUE;
		for (int i = 0; i < numE; i++) {
			if (minCost[numSelect - 1][i] < finalMin) {
				finalMin = minCost[numSelect - 1][i];
			}
		}
		return finalMin;
	}

	Line[] hull;
	int hSize;

	// reverse hull

	boolean okay() {
		if (hSize <= 2) {
			return true;
		}
		return xIntersection(hull[hSize - 3], hull[hSize - 2]) > xIntersection(hull[hSize - 3], hull[hSize - 1]);
	}

	void add(Line newLine) {
		assert (hSize == 0 || newLine.slope > hull[hSize - 1].slope);
		hull[hSize++] = newLine;

		while (!okay()) {
			hull[hSize - 2] = hull[hSize - 1];
			hSize--;
		}
	}

	double xIntersection(Line a, Line b) {
		return ((double) (b.yIntercept - a.yIntercept)) / (a.slope - b.slope);
	}

	long query(long xVal) {
		int low = 0;
		int high = hSize - 1;

		// low and high inclusive
		while (low != high) {
			int mid = (low + high) / 2;
			double xLeft = mid != hSize - 1 ? xIntersection(hull[mid], hull[mid + 1]) : Double.NEGATIVE_INFINITY;
			double xRight = mid != 0 ? xIntersection(hull[mid], hull[mid - 1]) : Double.POSITIVE_INFINITY;

			if (xVal < xLeft) {
				low = mid + 1;
			} else if (xVal <= xRight) {
				Line minLine = hull[mid];
				return minLine.slope * xVal + minLine.yIntercept;
			} else {
				high = mid - 1;
			}
		}
		assert (low == high);

		Line minLine = hull[low];
		return minLine.slope * xVal + minLine.yIntercept;
	}

	class Line {
		long slope;
		long yIntercept;

		Line(long slope, long yIntercept) {
			this.slope = slope;
			this.yIntercept = yIntercept;
		}
	}
}
