import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BuildingATallBarn {

	static int nJ;
	static long[] jobs;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tallbarn.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tallbarn.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nJ = Integer.parseInt(inputData.nextToken());
		long nC = Long.parseLong(inputData.nextToken()) - nJ;
		jobs = new long[nJ];

		for (int i = 0; i < nJ; i++) {
			jobs[i] = Long.parseLong(reader.readLine());
		}
		reader.close();

		double low = 0;
		double high = 1e12;
		while (high - low > 1e-8) {
			double mid = (low + high) / 2;
			if (cNC(mid) > nC) {
				low = mid;
			} else {
				high = mid;
			}
		}

		double fSelect = (low + high) / 2;

		double ans = 0;
		long tA = 0;
		for (int i = 0; i < nJ; i++) {
			long nA = (long) ((-1 + Math.sqrt(1 + 4 * jobs[i] / fSelect)) / 2);
			ans += (double) jobs[i] / (nA + 1);
			tA += nA;
		}
		ans -= (nC - tA) * fSelect;
		printer.println(Math.round(ans));
		printer.close();
	}

	// W/C - W/(C+1) = W/(C*(C+1))
	// W/(C*(C+1)) >= a
	// (C*(C+1)) <= W/a
	// C^2+C-W/a <= 0;
	static long cNC(double mBen) {
		long sum = 0;
		for (int i = 0; i < nJ; i++) {
			sum += (long) ((-1 + Math.sqrt(1 + 4 * jobs[i] / mBen)) / 2);
		}
		// System.out.println(sum);
		return sum;
	}

}
