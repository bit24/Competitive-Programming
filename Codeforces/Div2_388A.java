import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Div2_388A {

	public static void main(String[] args) throws IOException {
		new Div2_388A().execute();
	}

	ArrayList<Integer> primes = new ArrayList<Integer>();

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int num = Integer.parseInt(reader.readLine());
		reader.close();

		if ((num & 1) == 0) {
			int num2 = num / 2;
			printer.println(num2);

			for (int i = 0; i < num2; i++) {
				printer.print(2 + " ");
			}
		} else {
			int num2 = num / 2 - 1;
			printer.println(num2 + 1);
			for (int i = 0; i < num2; i++) {
				printer.print(2 + " ");
			}
			printer.print(3);
		}
		printer.close();
	}

}
