import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class H_19B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int N = Integer.parseInt(reader.readLine());
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(reader.readLine());
		}

		for (int i = 0; i < (1 << N); i++) {
			int sum = 0;
			for (int j = 0; j < N; j++) {
				if ((i & (1 << j)) != 0) {
					sum += a[j];
				} else {
					sum -= a[j];
				}
			}
			if (sum % 360 == 0) {
				printer.println("YES");
				printer.close();
				return;
			}
		}
		printer.println("NO");
		printer.close();
	}
}
