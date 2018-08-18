import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class DivCmb_504E {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

	static int N;

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(reader.readLine());
		int cI = 1;
		int cJ = 1;

		ArrayList<Character> p1 = new ArrayList<>();

		while (cI + cJ < N + 1) {
			if (cI + 1 <= N && query1(cI + 1, cJ)) {
				p1.add('D');
				cI++;
			} else {
				p1.add('R');
				cJ++;
			}
		}

		ArrayList<Character> p2 = new ArrayList<>();

		cI = N;
		cJ = N;

		while (cI + cJ > N + 1) {
			if (cJ - 1 >= 1 && query2(cI, cJ - 1)) {
				p2.add('R');
				cJ--;
			} else {
				p2.add('D');
				cI--;
			}
		}
		Collections.reverse(p2);

		printer.print("! ");
		for (Character c : p1) {
			printer.print(c);
		}
		for (Character c : p2) {
			printer.print(c);
		}
		printer.println();
		printer.close();
	}

	static boolean query1(int i, int j) throws IOException {
		printer.println("? " + i + " " + j + " " + N + " " + N);
		printer.flush();
		return reader.readLine().equals("YES");
	}

	static boolean query2(int i, int j) throws IOException {
		printer.println("? " + "1 1 " + i + " " + j);
		printer.flush();
		return reader.readLine().equals("YES");
	}
}
