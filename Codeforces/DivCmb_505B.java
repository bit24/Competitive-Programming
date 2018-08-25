import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_505B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nP = Integer.parseInt(reader.readLine());
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		long a = Integer.parseInt(inputData.nextToken());
		long b = Integer.parseInt(inputData.nextToken());

		ArrayList<Long> cand = new ArrayList<>();

		for (long i = 2; i * i <= a; i++) {
			if (a % i == 0) {
				cand.add(i);
				a /= i;
				while (a % i == 0) {
					a /= i;
				}
			}
		}
		if (a != 1) {
			cand.add(a);
		}

		for (long i = 2; i * i <= b; i++) {
			if (b % i == 0) {
				cand.add(i);
				b /= i;
				while (b % i == 0) {
					b /= i;
				}
			}
		}
		if (b != 1) {
			cand.add(b);
		}

		boolean[] pos = new boolean[cand.size()];
		Arrays.fill(pos, true);

		for (int i = 1; i < nP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			a = Integer.parseInt(inputData.nextToken());
			b = Integer.parseInt(inputData.nextToken());
			for (int j = 0; j < cand.size(); j++) {
				if (pos[j] && !(a % cand.get(j) == 0 || b % cand.get(j) == 0)) {
					pos[j] = false;
				}
			}
		}

		for (int i = 0; i < cand.size(); i++) {
			if(pos[i]) {
				printer.println(cand.get(i));
				printer.close();
				return;
			}
		}
		printer.println(-1);
		printer.close();
	}
}
