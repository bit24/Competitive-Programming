import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_453A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int h = Integer.parseInt(reader.readLine());
		int[] cnt = new int[h + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i <= h; i++) {
			cnt[i] = Integer.parseInt(inputData.nextToken());
		}

		int rInd = -1;
		for (int i = 0; i < h; i++) {
			if (cnt[i] > 1 && cnt[i + 1] > 1) {
				rInd = i;
			}
		}
		if (rInd == -1) {
			printer.println("perfect");
		} else {
			printer.println("ambiguous");
			int prev = 0;
			int nxt = 1;
			for (int i = 0; i <= h; i++) {
				int temp = nxt;
				for (int j = 0; j < cnt[i]; j++) {
					printer.print(prev + " ");
					nxt++;
				}
				prev = temp;
			}
			printer.println();
			prev = 0;
			nxt = 1;
			for (int i = 0; i <= rInd; i++) {
				int temp = nxt;
				for (int j = 0; j < cnt[i]; j++) {
					printer.print(prev + " ");
					nxt++;
				}
				prev = temp;
			}
			int temp = nxt;
			printer.print(prev + " ");
			nxt++;

			prev++;
			for (int j = 1; j < cnt[rInd + 1]; j++) {
				printer.print(prev + " ");
				nxt++;
			}
			prev = temp;
			for (int i = rInd + 2; i <= h; i++) {
				temp = nxt;
				for (int j = 0; j < cnt[i]; j++) {
					printer.print(prev + " ");
					nxt++;
				}
				prev = temp;
			}
			printer.println();
		}
		printer.close();
	}

}
