import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_453B {

	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		n = Integer.parseInt(reader.readLine());
		int[] prev = new int[n + 1];
		int[] cur = new int[n + 1];
		prev[0] = 1;
		cur[1] = 1;

		for (int i = 1; i < n; i++) {
			int[] nxt = add(prev, lShift(cur));
			prev = cur;
			cur = nxt;
		}
		printer.println(n);
		for (int i = 0; i <= n; i++) {
			printer.print(cur[i] + " ");
		}
		printer.println();
		printer.println(n - 1);
		for (int i = 0; i <= n - 1; i++) {
			printer.print(prev[i] + " ");
		}
		printer.println();
		printer.close();
	}

	static int[] add(int[] a, int[] b) {
		int[] ret = new int[n + 1];
		for (int i = 0; i <= n; i++) {
			ret[i] = (a[i] + b[i]) % 2;
		}
		return ret;
	}

	static int[] lShift(int[] items) {
		int[] ret = new int[n + 1];
		for (int i = n; i >= 1; i--) {
			ret[i] = items[i - 1];
		}
		return ret;
	}

}
