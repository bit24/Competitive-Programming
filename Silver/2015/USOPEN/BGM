import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class BGM {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("bgm.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bgm.out")));

		int numAssignments = Integer.parseInt(reader.readLine());

		int[][] amount = new int[26][7];

		for (int i = 0; i < numAssignments; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			amount[inputData.nextToken().charAt(0) - 'A'][(Integer.parseInt(inputData.nextToken()) % 7 + 7) % 7]++;
		}
		reader.close();

		long numWays = 0;
		for (int b = 0; b < 7; b++) {
			for (int e = 0; e < 7; e++) {
				for (int s = 0; s < 7; s++) {
					for (int i = 0; i < 7; i++) {
						for (int g = 0; g < 7; g++) {
							for (int o = 0; o < 7; o++) {
								for (int m = 0; m < 7; m++) {
									if ((b + e + s + s + i + e) * (g + o + e + s) * (m + o + o) % 7 == 0) {
										numWays += ((long) amount['B' - 'A'][b]) * amount['E' - 'A'][e]
												* amount['S' - 'A'][s] * amount['I' - 'A'][i]
												* amount['G' - 'A'][g] * amount['O' - 'A'][o] * amount['M' - 'A'][m];
									}
								}
							}
						}
					}
				}
			}

		}
		printer.println(numWays);
		printer.close();
	}

}
