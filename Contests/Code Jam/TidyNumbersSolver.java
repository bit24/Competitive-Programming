import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TidyNumbersSolver {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("tidy.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("tidy.out")));

		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			String text = reader.readLine();
			int nL = text.length();
			int[] num = new int[nL];
			for (int i = 0; i < nL; i++) {
				num[i] = Character.getNumericValue(text.charAt(i));
			}

			boolean updated = true;

			while (updated) {
				updated = false;
				for (int i = 1; i < nL; i++) {
					if (num[i - 1] > num[i]) {
						updated = true;
						num[i - 1]--;
						for (int j = i; j < nL; j++) {
							num[j] = 9;
						}
					}
				}
			}

			int i = 0;
			while (num[i] == 0) {
				i++;
			}

			printer.print("Case #" + cT + ": ");
			for (; i < nL; i++) {
				printer.print(num[i]);
			}
			printer.println();
		}
		reader.close();
		printer.close();
	}

}
