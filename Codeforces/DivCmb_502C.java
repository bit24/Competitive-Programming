import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DivCmb_502C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		int sqrt = (int) Math.sqrt(n);

		ArrayList<Integer> build = new ArrayList<Integer>();

		int rem = n % sqrt;
		for (int i = rem - 1; i >= 0; i--) {
			build.add(n - i);
		}

		for (int i = n / sqrt - 1; i >= 0; i--) {
			for (int j = 0; j < sqrt; j++) {
				build.add(i * sqrt + j + 1);
			}
		}

		for (int i : build) {
			printer.print(i + " ");
		}
		printer.println();
		printer.close();
	}
}
