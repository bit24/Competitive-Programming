import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TR_13A {

	public static void main(String[] args) throws IOException {
		new TR_13A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int left = Integer.parseInt(reader.readLine());
		int count = 1;

		ArrayList<Integer> num = new ArrayList<Integer>();
		while (left >= count + count + 1) {
			num.add(count);
			left -= count;
			count++;
		}
		if (left != 0) {
			num.add(left);
		}

		printer.println(num.size());
		for (int i : num) {
			printer.print(i + " ");
		}
		printer.close();

	}

}
