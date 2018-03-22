import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SureBet {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nB = Integer.parseInt(reader.readLine());

		double[] bF = new double[nB];
		double[] bA = new double[nB];

		for (int i = 0; i < nB; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			bF[i] = Double.parseDouble(inputData.nextToken());
			bA[i] = Double.parseDouble(inputData.nextToken());
		}
		Arrays.sort(bF);
		Arrays.sort(bA);

		int aInd = nB - 1;

		double fProf = 0;
		double aProf = 0;

		double ans = 0;

		for (int i = nB - 1; i >= 0; i--) {
			fProf += bF[i] - 1;
			aProf--;
			while (aInd >= 0 && Math.min(fProf, aProf) < Math.min(fProf - 1, aProf + bA[aInd] - 1)) {
				fProf--;
				aProf += bA[aInd--] - 1;
			}
			ans = Math.max(ans, Math.min(fProf, aProf));
		}
		printer.printf("%.4f", ans);
		printer.println();
		printer.close();
	}

}
