import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_504B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long N = Long.parseLong(inputData.nextToken());
		long tar = Long.parseLong(inputData.nextToken());
		long maxF = Math.min(tar - 1, N);
		long minF = Math.max(1, tar - N);

		if (maxF < minF) {
			printer.println(0);
			printer.close();
			return;
		}

		long ans = (maxF - minF + 1) / 2;
		printer.println(ans);
		printer.close();
	}
}
