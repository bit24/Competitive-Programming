import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_449B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken());
		inputData.nextToken();
		int mN = Integer.parseInt(inputData.nextToken());
		int mid = mN / 2;
		int[] e = new int[nE];

		int uL = 0;
		int uR = nE - 1;

		while (uL <= uR) {
			int cE = Integer.parseInt(reader.readLine());
			if (cE <= mid) {
				int i = 0;
				while (i < uL && e[i] <= cE) {
					i++;
				}
				e[i] = cE;
				printer.println(i + 1);
				uL = Math.max(uL, i + 1);
			} else {
				int i = nE - 1;
				while (uR < i && cE <= e[i]) {
					i--;
				}
				e[i] = cE;
				printer.println(i + 1);
				uR = Math.min(uR, i - 1);
			}
			printer.flush();
		}

		printer.flush();
		printer.close();
	}

}
