import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div2_418B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int len = Integer.parseInt(reader.readLine());

		int[][] seqs = new int[2][len];

		int[][] numCnt = new int[2][len + 1];
		for (int i = 0; i < 2; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < len; j++) {
				numCnt[i][seqs[i][j] = Integer.parseInt(inputData.nextToken())]++;
			}
		}

		int[] conf = new int[len];
		int[] confCnt = new int[len + 1];

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < len; j++) {
				if (numCnt[i][seqs[i][j]] == 1) {
					confCnt[conf[j] = seqs[i][j]]++;
				}
			}
		}

		int[] missing = new int[2];
		int missCnt = 0;

		for (int i = 1; i <= len; i++) {
			if (confCnt[i] == 0) {
				missing[missCnt++] = i;
			}
		}

		for (int i = 0; i < len; i++) {
			if (conf[i] == 0) {
				conf[i] = missing[--missCnt];
			}
			printer.print(conf[i] + " ");
		}

		printer.println();
		printer.close();
	}

}
