import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class beehives {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		while (true) {
			String nLine = reader.readLine();
			if (nLine.equals("0.0 0")) {
				break;
			}
			StringTokenizer inputData = new StringTokenizer(nLine);
			double dist = Double.parseDouble(inputData.nextToken());
			int nP = Integer.parseInt(inputData.nextToken());
			double[][] pts = new double[nP][2];
			boolean[] sour = new boolean[nP];

			for (int i = 0; i < nP; i++) {
				inputData = new StringTokenizer(reader.readLine());
				pts[i][0] = Double.parseDouble(inputData.nextToken());
				pts[i][1] = Double.parseDouble(inputData.nextToken());
			}

			for (int i = 0; i < nP; i++) {
				for (int j = 0; j < nP; j++) {
					if (i != j) {
						if ((pts[i][0] - pts[j][0]) * (pts[i][0] - pts[j][0])
								+ (pts[i][1] - pts[j][1]) * (pts[i][1] - pts[j][1]) <= dist * dist) {
							sour[i] = true;
							sour[j] = true;
						}
					}
				}
			}
			int nS = 0;
			for (int i = 0; i < nP; i++) {
				if (sour[i]) {
					nS++;
				}
			}
			printer.println(nS + " sour, " + (nP - nS) + " sweet");
		}
		printer.close();
	}

}
