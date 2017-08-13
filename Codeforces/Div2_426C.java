import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_426C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numT = Integer.parseInt(inputData.nextToken());

		while (numT-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			long product = ((long) (a)) * b;
			int cbrt = cbrt(product);
			if (cbrt == -1 || a % cbrt != 0 || b % cbrt != 0) {
				printer.println("No");
				continue;
			}
			printer.println("Yes");
		}
		printer.close();
	}

	static int cbrt(long n) {
		int l = 0;
		int h = 1_000_000;

		while (l < h) {
			int m = (l + h) / 2;

			long cube = ((long) m) * m * m;
			if (cube > n) {
				h = m - 1;
			} else if (cube < n) {
				l = m + 1;
			} else if (cube == n) {
				return m;
			}
		}
		if (l == h && ((long) (l)) * l * l == n) {
			return l;
		}
		return -1;
	}

}
