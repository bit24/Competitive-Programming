import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SolutionGoGopher {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		tLoop:
		for (int cT = 1; cT <= nT; cT++) {
			int req = Integer.parseInt(reader.readLine());

			boolean[][] filled = new boolean[3][3];

			for (int cRI = 2; cRI <= 14; cRI += 3) {
				for (int cCI = 2; cCI <= 14; cCI += 3) {
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							filled[i][j] = false;
						}
					}
					int nFill = 0;
					while (nFill != 9) {
						printer.println(cRI + " " + cCI);
						printer.flush();
						String inpLine = reader.readLine();
						if (inpLine.equals("0 0")) {
							continue tLoop;
						}
						StringTokenizer inputData = new StringTokenizer(inpLine);
						int mRI = Integer.parseInt(inputData.nextToken()) - (cRI - 1);
						int mCI = Integer.parseInt(inputData.nextToken()) - (cCI - 1);
						if(!filled[mRI][mCI]) {
							filled[mRI][mCI] = true;
							nFill++;
						}
					}
				}
			}
		}
	}
}
