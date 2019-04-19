import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div2_411B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());

		for (int i = 0; i < len / 4; i++) {
			printer.print("aabb");
		}

		if (len % 4 != 0) {
			printer.print("aabb".substring(0, len % 4));
		}
		printer.println();
		printer.close();
	}

}
