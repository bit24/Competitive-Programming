import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_446A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int[] vals = new int[nV];
		int n1 = 0;
		for (int i = 0; i < nV; i++) {
			vals[i] = Integer.parseInt(inputData.nextToken());
			if (vals[i] == 1) {
				n1++;
			}
		}
		int tGCD = vals[0];
		for (int i = 1; i < nV; i++) {
			tGCD = gcd(tGCD, vals[i]);
		}
		if (tGCD != 1) {
			printer.println(-1);
			printer.close();
			return;
		}

		int cGCD = vals[0];
		int oLap = Integer.MAX_VALUE;
		if (n1 > 0) {
			oLap = 0;
		} else {
			for (int i = 0; i < nV; i++) {
				cGCD = gcd(cGCD, vals[i]);
				if (cGCD == 1 && i != 0) {
					int lS = i;
					int lSGCD = vals[i];
					while (lS - 1 >= 0 && gcd(lSGCD, vals[lS - 1]) != 1) {
						lSGCD = gcd(lSGCD, vals[lS - 1]);
						lS--;
					}
					oLap = Math.min(oLap, i - lS);
					cGCD = vals[i];
				}
			}
		}
		printer.println(nV - n1 + oLap);
		printer.close();
	}

	static int gcd(int a, int b) {
		if (a < b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		while (b != 0) {
			int rem = a % b;
			a = b;
			b = rem;
		}
		return a;
	}

}
