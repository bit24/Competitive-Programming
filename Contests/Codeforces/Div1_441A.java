import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Div1_441A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());

		ArrayList<Integer> vals = new ArrayList<Integer>();
		for (int i = Math.max(n - 81, 1); i < n; i++) {
			int sum = i;
			int copy = i;
			while (copy > 0) {
				sum += copy % 10;
				copy /= 10;
			}
			if(sum == n) {
				vals.add(i);
			}
		}
		printer.println(vals.size());
		for(int i : vals) {
			printer.println(i);
		}
		printer.close();
	}

}
