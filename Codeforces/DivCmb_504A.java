import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DivCmb_504A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int aLen = Integer.parseInt(inputData.nextToken());
		int bLen = Integer.parseInt(inputData.nextToken());
		String a = reader.readLine();
		String b = reader.readLine();
		int sLoc = a.indexOf('*');

		if (sLoc == -1) {
			printer.println(a.equals(b) ? "YES" : "NO");
			printer.close();
			return;
		}

		if (aLen - 1 > bLen) {
			printer.println("NO");
			printer.close();
			return;
		}

		if (!a.substring(0, sLoc).equals(b.substring(0, sLoc))) {
			printer.println("NO");
			printer.close();
			return;
		}

		if (!a.substring(sLoc + 1, aLen).equals(b.substring(bLen - (aLen - sLoc - 1), bLen))) {
			printer.println("NO");
			printer.close();
			return;
		}

		printer.println("YES");
		printer.close();
	}
}
