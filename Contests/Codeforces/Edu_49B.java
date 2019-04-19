import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Edu_49B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());

		long sGroup = (N * N + 1) / 2;
		while (Q-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			long x = Integer.parseInt(inputData.nextToken());
			long y = Integer.parseInt(inputData.nextToken());
			long nD = (x - 1) / 2;
			x -= 2 * nD;
			long ans = nD * N;

			if (x == 1) {
				if ((x + y) % 2 == 0) {
					ans += (y + 1) / 2;
				} else {
					ans += sGroup + y / 2;
				}
			} else {
				if ((x + y) % 2 == 0) {
					ans += (N + 1) / 2 + y / 2;
				} else {
					ans += sGroup + N / 2 + (y + 1) / 2;
				}
			}
			printer.println(ans);
		}
		printer.close();
	}
}
