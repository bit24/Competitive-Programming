import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mail18_2B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());
		int L = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());
		int[] len = new int[N];
		int nSeg = 0;

		for (int i = 0; i < N; i++) {
			len[i] = Integer.parseInt(inputData.nextToken());
			if (len[i] > L) {
				if (i == 0 || len[i - 1] <= L) {
					nSeg++;
				}
			}
		}

		while (Q-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			if (inputData.nextToken().equals("0")) {
				printer.println(nSeg);
			} else {
				int i = Integer.parseInt(inputData.nextToken()) - 1;
				int u = Integer.parseInt(inputData.nextToken());
				if (len[i] <= L) {
					len[i] += u;
					if (len[i] > L) {
						nSeg++;
						if (i != 0 && len[i - 1] > L) {
							nSeg--;
						}
						if (i != N - 1 && len[i + 1] > L) {
							nSeg--;
						}
					}
				}
			}
		}
		printer.close();
	}
}
