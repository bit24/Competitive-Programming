import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_503B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		printer.println("? 1");
		printer.flush();
		int v1 = Integer.parseInt(reader.readLine());

		printer.println("? " + (1 + N / 2));
		printer.flush();
		int v2 = Integer.parseInt(reader.readLine());

		if ((v1 + v2) % 2 != 0) {
			printer.println("! -1");
			printer.close();
			return;
		}

		if (v1 == v2) {
			printer.println("! 1");
			printer.close();
			return;
		}
		boolean less = v1 < v2;

		int low = 1;
		int high = (1 + N / 2);

		while (low != high) {
			int mid = (low + high) >> 1;

			printer.println("? " + mid);
			printer.flush();
			int r1 = Integer.parseInt(reader.readLine());

			int q2 = (mid + N / 2);
			if (q2 > N) {
				q2 -= N;
			}
			printer.println("? " + q2);
			printer.flush();
			int r2 = Integer.parseInt(reader.readLine());

			if (r1 == r2) {
				printer.println("! " + mid);
				printer.close();
				return;
			}

			if (r1 < r2 == less) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		printer.println("! " + low);
		printer.close();
		return;
	}
}
