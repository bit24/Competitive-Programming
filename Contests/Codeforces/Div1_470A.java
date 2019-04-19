import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Div1_470A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		boolean[] prime = new boolean[1_000_001];
		Arrays.fill(prime, true);
		prime[1] = false;
		for (int i = 2; i <= 1_000_000; i++) {
			if (prime[i] == true) {
				for (int mult = i + i; mult <= 1_000_000; mult += i) {
					prime[mult] = false;
				}
			}
		}

		boolean[] step1 = new boolean[1_000_001];

		for (int cP = 2; cP <= 1_000_000; cP++) {
			if (prime[cP] && N % cP == 0) {
				int sub = N - cP;
				for (int j = sub + 1; j <= N; j++) {
					step1[j] = true;
				}
			}
		}

		int min = Integer.MAX_VALUE;
		for (int cP = 2; cP <= 1_000_000; cP++) {
			if (prime[cP]) {
				for (int mult = cP + cP; mult <= 1_000_000; mult += cP) {
					if (step1[mult]) {
						min = Math.min(min, mult - cP + 1);
					}
				}
			}
		}
		printer.println(min);
		printer.close();
	}
}
