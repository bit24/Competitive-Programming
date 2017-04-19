import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class AlphabetCakeSolver {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("alphabet.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("alphabet.out")));

		int numT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numR = Integer.parseInt(inputData.nextToken());
			int numC = Integer.parseInt(inputData.nextToken());

			char[][] items = new char[numR][numC];
			for (int i = 0; i < numR; i++) {
				String nLine = reader.readLine();
				for (int j = 0; j < numC; j++) {
					items[i][j] = nLine.charAt(j);
				}
			}

			int i;
			for (i = 0; i < numR; i++) {
				char toAssign = '?';
				for (int j = 0; j < numC; j++) {
					if (items[i][j] != '?') {
						toAssign = items[i][j];
						break;
					}
				}
				if (toAssign != '?') {
					for (int j = 0; j < numC; j++) {
						if (items[i][j] == '?' || items[i][j] == toAssign) {
							items[i][j] = toAssign;
						} else {
							toAssign = items[i][j];
						}
					}

					for (int k = i - 1; k >= 0; k--) {
						for (int j = 0; j < numC; j++) {
							items[k][j] = items[k + 1][j];
						}
					}
					i++;
					break;
				}
			}

			for (i = 0; i < numR; i++) {
				char toAssign = '?';
				for (int j = 0; j < numC; j++) {
					if (items[i][j] != '?') {
						toAssign = items[i][j];
						break;
					}
				}
				if (toAssign != '?') {
					for (int j = 0; j < numC; j++) {
						if (items[i][j] == '?' || items[i][j] == toAssign) {
							items[i][j] = toAssign;
						} else {
							toAssign = items[i][j];
						}
					}
				} else {
					for (int j = 0; j < numC; j++) {
						items[i][j] = items[i - 1][j];
					}
				}
			}

			printer.println("Case #" + cT + ":");
			for (i = 0; i < numR; i++) {
				for (int j = 0; j < numC; j++) {
					printer.print(items[i][j]);
				}
				printer.println();
			}
		}
		reader.close();
		printer.close();

	}

}
