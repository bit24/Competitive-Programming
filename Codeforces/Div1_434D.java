import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_434D {

	static int[] nxt;
	static int[] end;
	static int[] lSt;
	static boolean[] used;

	static ArrayList<String> ans = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());

		nxt = new int[nE * 2];
		end = new int[nE * 2];
		used = new boolean[nE * 2];
		lSt = new int[nV];
		Arrays.fill(lSt, -1);

		int cE = 0;
		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			nxt[cE] = lSt[a];
			end[cE] = b;
			lSt[a] = cE;
			cE++;

			nxt[cE] = lSt[b];
			end[cE] = a;
			lSt[b] = cE;
			cE++;
		}

		for (int i = 0; i < nV; i++) {
			dfs(i);
		}
		printer.println(ans.size());
		for (String cur : ans) {
			printer.println(cur);
		}
		printer.close();
	}

	static int dfs(int cV) {
		int eI = lSt[cV];
		int pFree = -1;
		while (eI != -1) {
			if (!used[eI]) {
				used[eI] = true;
				used[eI ^ 1] = true;
				int aV = end[eI];
				int ret = dfs(aV);
				if (ret != -1) {
					ans.add((ret + 1) + " " + (aV + 1) + " " + (cV + 1));
				} else {
					if (pFree != -1) {
						ans.add((pFree + 1) + " " + (cV + 1) + " " + (aV + 1));
						pFree = -1;
					} else {
						pFree = aV;
					}
				}
			}
			eI = nxt[eI];
		}
		return pFree;
	}

}
