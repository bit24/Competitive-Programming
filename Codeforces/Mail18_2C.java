import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mail18_2C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		long[] a = new long[3];
		long[] b = new long[3];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 3; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < 3; i++) {
			b[i] = Integer.parseInt(inputData.nextToken());
		}
		long min = Math.min(a[0], b[0]);

		a[0] -= min;
		a[1] -= min;
		b[0] -= min;
		b[1] -= min;

		if (a[0] != 0) {
			long[] t = a;
			a = b;
			b = t;
		}

		long gcd = gcd(a[2], b[2]);
		long shift = b[0] / gcd;
		b[0] -= shift * gcd;
		b[1] -= shift * gcd;
		long ans = Math.min(b[1] - b[0] + 1, a[1] - b[0] + 1);
		if (ans < 0) {
			ans = 0;
		}

		shift = (b[0] - a[0] + gcd - 1) / gcd;
		a[0] += shift * gcd;
		a[1] += shift * gcd;

		min = Math.min(a[0], b[0]);

		a[0] -= min;
		a[1] -= min;
		b[0] -= min;
		b[1] -= min;

		if (a[0] != 0) {
			long[] t = a;
			a = b;
			b = t;
		}

		gcd = gcd(a[2], b[2]);
		shift = b[0] / gcd;
		b[0] -= shift * gcd;
		b[1] -= shift * gcd;
		ans = Math.max(ans, Math.min(b[1] - b[0] + 1, a[1] - b[0] + 1));

		printer.println(ans);
		printer.close();
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
