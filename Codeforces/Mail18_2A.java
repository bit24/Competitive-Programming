import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mail18_2A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int s = Integer.parseInt(inputData.nextToken()) - 1;

		int[] a = new int[N];
		int[] b = new int[N];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			a[i] = Integer.parseInt(inputData.nextToken());
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < N; i++) {
			b[i] = Integer.parseInt(inputData.nextToken());
		}

		if (a[0] == 0) {
			printer.println("NO");
			printer.close();
			return;
		}
		if (a[s] == 1) {
			printer.println("YES");
			printer.close();
			return;
		}

		for (int i = s + 1; i < N; i++) {
			if (a[i] == 1 && b[i] == 1 && b[s] == 1) {
				printer.println("YES");
				printer.close();
				return;
			}
		}
		printer.println("NO");
		printer.close();
	}
}
