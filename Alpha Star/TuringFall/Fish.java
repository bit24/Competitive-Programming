import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Fish {

	static int n;
	static long[] pos = new long[100_001];
	static long[] amt = new long[100_001];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		n = Integer.parseInt(reader.readLine());

		for (int i = 0; i < n; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pos[i] = Long.parseLong(inputData.nextToken());
			amt[i] = Long.parseLong(inputData.nextToken());
		}
		pos[n] = 1_000_000_000_001L;

		long low = 0;
		long high = 1_000_000_000_000L;
		while (low != high) {
			long mid = (low + high + 1) >> 1;
			if (pos(mid)) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		printer.println(low);
		printer.close();
	}

	static boolean pos(long req) {
		// System.out.println(req);
		long ext = 0;

		for (int i = 0; i < n; i++) {
			long rem = amt[i] + ext - req;
			if (rem < 0) {
				ext = rem - (pos[i + 1] - pos[i]);
			} else {
				ext = Math.max(0, rem - (pos[i + 1] - pos[i]));
			}
		}
		return ext >= 0;
	}
}
