import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_503E {

	static int nV;

	static int[] lSt;
	static int[] ePt;
	static int[] nxt;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		lSt = new int[nV];
		Arrays.fill(lSt, -1);
		ePt = new int[nE];
		nxt = new int[nE];

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			nxt[i] = lSt[a];
			ePt[i] = b;
			lSt[a] = i;
		}

		rmvd = new boolean[nV];
		marked = new boolean[nV];
		adjM = new boolean[nV];

		solve();

		int cnt = 0;
		for (int i = 0; i < nV; i++) {
			if (marked[i]) {
				cnt++;
			}
		}
		printer.println(cnt);
		for (int i = 0; i < nV; i++) {
			if (marked[i]) {
				printer.print(i + 1 + " ");
			}
		}
		printer.println();
		printer.close();
	}

	static boolean[] rmvd;
	static boolean[] marked;
	static boolean[] adjM;

	static int nNRmvd = 0;

	static void solve() {
		while (nNRmvd < nV && rmvd[nNRmvd]) {
			nNRmvd++;
		}
		if (nNRmvd == nV) {
			return;
		}

		int cV = nNRmvd;
		rmvd[cV] = true;

		int aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			rmvd[aV] = true;
			aI = nxt[aI];
		}

		solve();

		aI = lSt[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			rmvd[aV] = false;
			aI = nxt[aI];
		}

		if (!adjM[cV]) {
			marked[cV] = true;

			aI = lSt[cV];
			while (aI != -1) {
				int aV = ePt[aI];
				adjM[aV] = true;
				aI = nxt[aI];
			}
		}
	}

}
