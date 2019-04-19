import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Edu_49A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		tLoop:
		while (nT-- > 0) {
			int len = Integer.parseInt(reader.readLine());
			String str = reader.readLine();
			for (int i = 0; i < len / 2; i++) {
				if (!pos(str.charAt(i), str.charAt(len - 1 - i))) {
					printer.println("NO");
					continue tLoop;
				}
			}
			printer.println("YES");
		}
		printer.close();
	}

	static boolean pos(char c1, char c2) {
		int dist = Math.abs(c1 - c2);
		if (dist == 0 || dist == 2) {
			return true;
		}
		return false;
	}
}
