import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_491A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int A = Integer.parseInt(inputData.nextToken());
		int B = Integer.parseInt(inputData.nextToken());
		int C = Integer.parseInt(inputData.nextToken());
		int N = Integer.parseInt(inputData.nextToken());

		if (C > A || C > B) {
			printer.println(-1);
			printer.close();
		}

		int celeb = A + B - C;

		if (celeb + 1 > N) {
			printer.println(-1);
			printer.close();
		}
		printer.println(N - celeb);
		printer.close();
	}
}
