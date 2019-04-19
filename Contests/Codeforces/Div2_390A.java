import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_390A {

	public static void main(String[] args) throws IOException {
		new Div2_390A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int numE = Integer.parseInt(reader.readLine());
		int[] elements = new int[numE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		int count = 0;
		ArrayList<Integer> end = new ArrayList<Integer>();

		for (int right = 0; right < numE; right++) {
			if (elements[right] != 0) {
				count++;
				end.add(right);
			}
		}
		if (count == 0) {
			System.out.println("NO");
			return;
		}
		if (count == 1) {
			System.out.println("YES");
			System.out.println(1);
			System.out.println(1 + " " + numE);
			return;
		}

		System.out.println("YES");
		System.out.println(count);
		for (int i = 0; i < end.size() - 1; i++) {
			if (i != 0) {
				printer.println((end.get(i - 1) + 2) + " " + (end.get(i) + 1));
			} else {
				printer.println(1 + " " + (end.get(i) + 1));
			}
		}
		printer.println((end.get(end.size() - 2) + 2) + " " + numE);
		printer.close();
	}

}
