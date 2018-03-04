import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_468A {

	static int[] nxt = new int[100_000];
	static int[] lSt = new int[100_000];

	static int[] cnts = new int[100_000];

	public static void main(String[] args) throws IOException {
		Arrays.fill(lSt, -1);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i < nV; i++) {
			int prev = Integer.parseInt(inputData.nextToken()) - 1;
			nxt[i] = lSt[prev];
			lSt[prev] = i;
		}
		dfs(0, 0);
		int ans = 0;
		for (int i = 0; i < 100_000; i++) {
			if ((cnts[i] & 1) == 1) {
				ans++;
			}
		}
		printer.println(ans);
		printer.close();
	}

	static void dfs(int cV, int cDepth) {
		cnts[cDepth]++;
		int aV = lSt[cV];
		while (aV != -1) {
			dfs(aV, cDepth + 1);
			aV = nxt[aV];
		}
	}
}
