import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class EthanFindsTheShortestPath {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("spath.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("spath.out")));
		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int nV = Integer.parseInt(inputData.nextToken());
			int K = Integer.parseInt(inputData.nextToken());

			if (K <= 3 || nV == 2) {
				printer.println("Case #" + cT + ": 0");
				printer.println(1);
				printer.println("1 " + nV + " 1");
				continue;
			}

			if (nV <= K) {
				int sum = 0;
				int nxt = K - nV + 1;
				for (int i = nV; i > 1; i--) {
					sum += nxt;
					nxt++;
				}
				sum -= K;
				printer.println("Case #" + cT + ": " + sum);
				printer.println(nV);

				nxt = K - nV + 1;
				for (int i = nV; i > 1; i--) {
					printer.println(i + " " + (i - 1) + " " + nxt);
					nxt++;
				}
				printer.println("1 " + nV + " " + K);
			} else {
				int sum = 0;
				for (int i = 1; i <= K - 1; i++) {
					sum += i;
				}
				sum -= K;
				printer.println("Case #" + cT + ": " + sum);
				printer.println(K);

				printer.println((K - 1) + " " + nV + " 1");
				int nxt = 2;
				for (int i = K - 1; i > 1; i--) {
					printer.println(i + " " + (i - 1) + " " + nxt);
					nxt++;
				}
				printer.println("1 " + nV + " " + K);
			}
		}
		reader.close();
		printer.close();
	}
}
