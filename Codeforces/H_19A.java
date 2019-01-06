import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class H_19A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		String t = reader.readLine();
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < 5; i++) {
			String cur = inputData.nextToken();
			if (t.charAt(0) == cur.charAt(0) || t.charAt(1) == cur.charAt(1)) {
				printer.println("YES");
				printer.close();
				return;
			}
		}
		printer.println("NO");
		printer.close();
	}
}
