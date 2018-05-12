import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Farmer {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nG = Integer.parseInt(inputData.nextToken());
		int nC = Integer.parseInt(inputData.nextToken());
		int nL = Integer.parseInt(inputData.nextToken());
		int[] cCnt = new int[300];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nC; i++) {
			cCnt[Integer.parseInt(inputData.nextToken())]++;
		}
		int[] oCnt = Arrays.copyOf(cCnt, 300);

		int[] reverse = new int[300];
		for (int i = 1; i < 150; i++) {
			int nM = (cCnt[i] - 1) / 2;
			reverse[i * 2] += nM;
			cCnt[i * 2] += nM;
			cCnt[i] -= 2 * nM;
		}

		int[] prev = new int[nG + 1 + 200 + 300];
		Arrays.fill(prev, -1);
		prev[0] = 0;

		for (int i = 1; i < 300; i++) {
			for (int j = 0; j < cCnt[i]; j++) {
				for (int k = nG; k >= 0; k--) {
					if (prev[k + i] == -1 && prev[k] != -1) {
						prev[k + i] = k;
					}
				}
			}
		}

		int max = nG;
		while (prev[max] == -1) {
			max--;
		}

		int[] pCnt = new int[300];
		for (int cur = max; cur != 0; cur = prev[cur]) {
			pCnt[cur - prev[cur]]++;
		}

		for (int i = 299; i >= 1; i--) {
			int tBreak = Math.min(pCnt[i], reverse[i]);
			pCnt[i] -= tBreak;
			pCnt[i / 2] += tBreak * 2;
		}

		int ans = max;
		int req = nG - max;

		ArrayList<Integer> lines = new ArrayList<Integer>();
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nL; i++) {
			lines.add(Integer.parseInt(inputData.nextToken()));
		}

		for (int i = 1; i < 300; i++) {
			for (int j = 0; j < oCnt[i] - pCnt[i]; j++) {
				lines.add(i);
			}
		}

		Collections.sort(lines);
		for (int i = lines.size() - 1; i >= 0; i--) {
			int delta = Math.min(req, lines.get(i));
			if (delta != 0) {
				req -= delta;
				ans += delta - 1;
			}
		}
		printer.println(ans);
		printer.close();
	}
}
