import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Edu_48E {

	static long lY;
	static long lS;
	static long lE;
	static long lLen;

	static int nS;

	static long[] segS;
	static long[] segE;

	static long[] preSum;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		lY = Integer.parseInt(inputData.nextToken());
		lS = Integer.parseInt(inputData.nextToken());
		lE = Integer.parseInt(inputData.nextToken());
		lLen = lE - lS;

		nS = Integer.parseInt(reader.readLine());
		segS = new long[nS];
		segE = new long[nS];
		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			segS[i] = Integer.parseInt(inputData.nextToken());
			segE[i] = Integer.parseInt(inputData.nextToken());
		}

		preSum = new long[nS];

		preSum[0] = segE[0] - segS[0];
		for (int i = 1; i < nS; i++) {
			preSum[i] = preSum[i - 1] + segE[i] - segS[i];
		}

		int nQ = Integer.parseInt(reader.readLine());

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			printer.println(query(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken())));
		}
		printer.close();
	}

	static double query(long x, long y) {
		double ratio = ((double) y) / (y - lY);
		double pLS = ratio * (lS - x) + x;
		double pLE = ratio * (lE - x) + x;

		int iL = searchGE(pLS);
		int iR = searchLE(pLE);

		double sum = 0;
		if (iL <= iR) {
			sum += preSum[iR];
			if (iL != 0) {
				sum -= preSum[iL - 1];
			}
		}

		if (iL > 0) {
			if (pLS < segE[iL - 1]) {
				sum += Math.min(pLE, segE[iL - 1]) - pLS;
			}
		}

		if (iR + 1 < nS && iR + 1 != iL - 1) {
			if (segS[iR + 1] < pLE) {
				sum += pLE - Math.max(pLS, segS[iR + 1]);
			}
		}
		return (1 / ratio) * sum;
	}

	static int searchGE(double index) {
		int low = 0;
		int high = nS;
		while (low != high) {
			int mid = (low + high) >> 1;
			if (index <= segS[mid]) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	static int searchLE(double index) {
		int low = -1;
		int high = nS - 1;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (segE[mid] <= index) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}
}