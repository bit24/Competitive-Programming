import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_491C {

	static long n;
	static long h;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		n = Long.parseLong(reader.readLine());
		h = (n + 1) / 2;

		long low = 1;
		long high = 1_000_000_000_000_000_000L;

		while (low != high) {
			long mid = (low + high) >> 1;
			if (acceptable(mid)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		printer.println(low);
		printer.close();
	}

	static boolean acceptable(long k) {
		long cur = n;
		long cnt = 0;
		while (cur != 0) {
			long delta = Math.min(cur, k);
			cur -= delta;
			cnt += delta;
			long delta2 = cur / 10;
			cur -= delta2;
		}
		return cnt >= h;
	}
}
