import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Interception {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("interception.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("interception.out")));
		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			int deg = Integer.parseInt(reader.readLine());
			for (int i = 0; i < deg + 1; i++) {
				reader.readLine();
			}
			if ((deg & 1) == 0) {
				printer.println("Case #" + cT + ": 0");
			} else {
				printer.println("Case #" + cT + ": 1");
				printer.println("0.0");
			}
		}
		reader.close();
		printer.close();
	}
}
