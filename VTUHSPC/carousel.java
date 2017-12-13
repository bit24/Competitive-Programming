import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class carousel {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		while (true) {
			String nLine = reader.readLine();
			if (nLine.equals("0 0")) {
				break;
			}
			StringTokenizer inputData = new StringTokenizer(nLine);
			int nO = Integer.parseInt(inputData.nextToken());
			int mT = Integer.parseInt(inputData.nextToken());

			int bT = -1;
			int bC = -1;
			for (int i = 0; i < nO; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int nT = Integer.parseInt(inputData.nextToken());
				int cC = Integer.parseInt(inputData.nextToken());
				if (nT > mT) {
					continue;
				}
				if (bT == -1 || nT * bC > bT * cC || (nT * bC == bT * cC && nT > bT)) {
					bT = nT;
					bC = cC;
				}
			}
			if (bT == -1) {
				printer.println("No suitable tickets offered");
			} else {
				printer.println("Buy " + bT + " tickets for $" + bC);
			}
		}
		printer.close();
	}
}
