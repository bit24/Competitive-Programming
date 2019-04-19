import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class EmptyStalls {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("empty.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("empty.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nS = Integer.parseInt(inputData.nextToken());

		int[] nW = new int[nS];
		Arrays.fill(nW, -1);

		int nK = Integer.parseInt(inputData.nextToken());
		while (nK-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int X = Integer.parseInt(inputData.nextToken());
			int Y = Integer.parseInt(inputData.nextToken());
			long A = Integer.parseInt(inputData.nextToken());
			long B = Integer.parseInt(inputData.nextToken());

			for (int i = 1; i <= Y; i++) {
				int pos = (int) ((A * i + B) % nS);
				nW[pos] += X;
			}
		}
		reader.close();

		for (int i = 0; i < nS - 1; i++) {
			if (nW[i] > 0) {
				nW[i + 1] += nW[i];
				nW[i] = 0;
			}
		}
		if (nW[nS - 1] > 0) {
			nW[0] += nW[nS - 1];
			nW[nS - 1] = 0;
		}
		for (int i = 0; i < nS - 1; i++) {
			if (nW[i] > 0) {
				nW[i + 1] += nW[i];
				nW[i] = 0;
			}
		}
		int empty = -1;
		for (int i = 0; i < nS; i++) {
			if (nW[i] == -1) {
				empty = i;
				break;
			}
		}

		printer.println(empty);
		printer.close();
	}
}
