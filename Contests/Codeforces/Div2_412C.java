import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_412C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int numT = Integer.parseInt(reader.readLine());

		for (int i = 0; i < numT; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			long x = Long.parseLong(inputData.nextToken());
			long y = Long.parseLong(inputData.nextToken());
			long p = Long.parseLong(inputData.nextToken());
			long q = Long.parseLong(inputData.nextToken());

			if (q == p) {
				printer.println(x == y ? 0 : -1);
				continue;
			}

			if (p == 0) {
				printer.println(x == 0 ? 0 : -1);
				continue;
			}

			long b1 = x == 0 ? 0 : (x - 1) / p + 1;
			long b2 = y == 0 ? 0 : (y - 1) / q + 1;
			long b3 = x == y ? 0 : (y - x - 1) / (q - p) + 1;
			long m = Math.max(b1, Math.max(b2, b3));
			printer.println(m * q - y);
		}
		printer.close();
	}

}
