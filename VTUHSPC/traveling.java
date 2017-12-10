import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class traveling {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nU = Integer.parseInt(inputData.nextToken());
		int nD = Integer.parseInt(inputData.nextToken());
		int[] uC = new int[nU];
		int[] uE = new int[nU];

		double tE = 0;
		for (int i = 0; i < nU; i++) {
			inputData = new StringTokenizer(reader.readLine());
			tE += uE[i] = Integer.parseInt(inputData.nextToken());
			uC[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] dC = new int[nD];
		int[] dE = new int[nD];
		
		for (int i = 0; i < nD; i++) {
			inputData = new StringTokenizer(reader.readLine());
			dE[i] = Integer.parseInt(inputData.nextToken());
			dC[i] = Integer.parseInt(inputData.nextToken());
		}

		double low = 0;
		double high = 1e10;

		while (high - low > 1e-6) {
			double mid = (high + low) / 2;
			double rT = mid;

			double uCE = 0;
			for (int i = 0; i < nU; i++) {
				if (rT >= uC[i]) {
					rT -= uC[i];
					uCE += uE[i];
				} else {
					uCE += (double) uE[i] / uC[i] * rT;
					break;
				}
			}

			rT = mid;
			double dCE = tE;
			for (int i = 0; i < nD; i++) {
				if (rT >= dC[i]) {
					rT -= dC[i];
					dCE -= dE[i];
				} else {
					dCE -= (double) dE[i] / dC[i] * rT;
					break;
				}
			}
			if (uCE < dCE) {
				low = mid;
			} else {
				high = mid;
			}
		}

		System.out.printf("%.6f", ((high + low) / 2));
		System.out.println();
	}

}
