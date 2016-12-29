import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class TR_13B {

	public static void main(String[] args) throws IOException {
		new TR_13B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		String base = "0000";
		printer.println(base);
		printer.flush();

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int baseRight = Integer.parseInt(inputData.nextToken());

		if (baseRight == 4) {
			return;
		}

		int[] values = new int[4];

		outerLoop: for (int i = 0; i < 4; i++) {
			StringBuilder str = new StringBuilder();

			for (int j = 1; j < 10; j++) {
				for (int k = 0; k < i; k++) {
					str.append(values[k]);
				}
				str.append(j);
				for (int k = i + 1; k < 4; k++) {
					str.append("0");
				}
				printer.println(str.toString());
				printer.flush();
				str = new StringBuilder();
				inputData = new StringTokenizer(reader.readLine());
				int numRight = Integer.parseInt(inputData.nextToken());
				if (numRight == 4) {
					return;
				}

				if (numRight == baseRight + 1) {
					values[i] = j;
					baseRight = numRight;
					continue outerLoop;
				}
			}
			values[i] = 0;
		}

	}

}
