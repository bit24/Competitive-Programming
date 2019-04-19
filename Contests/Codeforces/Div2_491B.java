import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_491B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nE = Integer.parseInt(reader.readLine());
		Integer[] e = new Integer[nE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}
		Arrays.sort(e);

		for (int i = 0; i < nE; i++) {
			double sum = 0;
			sum += i * 5;
			for (int j = i; j < nE; j++) {
				sum += e[j];
			}

			double avg = Math.round(sum / nE);
			if (avg == 5) {
				printer.println(i);
				printer.close();
			}
		}
		printer.println(nE);
		printer.close();
	}
}
