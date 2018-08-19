import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Edu_49C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		while (nT-- > 0) {
			int nS = Integer.parseInt(reader.readLine());

			Integer[] lens = new Integer[nS];
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int i = 0; i < nS; i++) {
				lens[i] = Integer.parseInt(inputData.nextToken());
			}
			Arrays.sort(lens);

			ArrayList<Integer> comp = new ArrayList<>();

			for (int i = 0; i + 1 < nS; i++) {
				if (lens[i].equals(lens[i + 1])) {
					comp.add(lens[i]);
					i++;
				}
			}

			long num = -1;
			long den = -1;
			int ansI = -1;

			for (int i = 1; i < comp.size(); i++) {
				long a = comp.get(i);
				long b = comp.get(i - 1);
				if (num == -1 || num * b > a * den) {
					num = a;
					den = b;
					ansI = i;
				}
			}
			printer.println(
					comp.get(ansI - 1) + " " + comp.get(ansI - 1) + " " + comp.get(ansI) + " " + comp.get(ansI));
		}
		printer.close();
	}
}