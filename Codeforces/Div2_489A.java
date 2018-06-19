import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div2_489A {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		TreeSet<Integer> e = new TreeSet<>();
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < n; i++) {
			e.add(Integer.parseInt(inputData.nextToken()));
		}
		if (e.contains(0)) {
			printer.println(e.size() - 1);
		}
		else {
			printer.println(e.size());
		}
		printer.close();
	}
}