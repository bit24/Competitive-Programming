import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_415D {

	static BufferedReader reader;
	static PrintWriter printer;

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());

		int pos1 = binSearch(1, numE);
		if (pos1 == 1) {
			printer.println("2 " + pos1 + " " + binSearch(2, numE));
			printer.close();
			return;
		}

		int pos2 = binSearch(1, pos1 - 1);

		if (pos2 + 1 == pos1) {
			printer.println("1 " + pos2 + " " + pos1);
			printer.flush();
			if (reader.readLine().charAt(0) != 'T') {
				pos2 = binSearch(pos1 + 1, numE);
			}
		}

		printer.println("2 " + pos1 + " " + pos2);
		printer.close();
	}

	static int binSearch(int left, int right) throws IOException {
		// left and right implies that there must be at least 1 selected in the range

		while (right - left > 2) {
			int spacing = (right - left + 3) / 4;

			printer.println("1 " + (left + spacing) + " " + (right - spacing));
			printer.flush();

			if (reader.readLine().charAt(0) == 'T') {
				right = (left + right) / 2;
			} else {
				left = (left + right) / 2 + 1;
			}
		}
		
		while(right != left){
			printer.println("1 " + left + " " + right);
			printer.flush();
			if (reader.readLine().charAt(0) == 'T') {
				right--;
			} else {
				left++;
			}
		}

		assert (left == right);
		return left;
	}

}
