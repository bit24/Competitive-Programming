import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lyft_18FB {

	static ArrayList<Integer>[] aList = new ArrayList[1000];
	static boolean[] set1 = new boolean[1000];
	static boolean[] set2 = new boolean[1000];
	static boolean[] vis = new boolean[1000];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());

		for (int i = 0; i < 1000; i++) {
			aList[i] = new ArrayList<>();
		}

		tLoop:
		for (int cT = 0; cT < nT; cT++) {
			for (int i = 0; i < 1000; i++) {
				aList[i].clear();
			}
			Arrays.fill(set1, false);
			Arrays.fill(set2, false);
			Arrays.fill(vis, false);

			int nV = Integer.parseInt(reader.readLine());
			for (int i = 0; i < nV - 1; i++) {
				StringTokenizer inputData = new StringTokenizer(reader.readLine());
				int a = Integer.parseInt(inputData.nextToken()) - 1;
				int b = Integer.parseInt(inputData.nextToken()) - 1;
				aList[a].add(b);
				aList[b].add(a);
			}

			int k = Integer.parseInt(reader.readLine());
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int i = 0; i < k; i++) {
				set1[Integer.parseInt(inputData.nextToken()) - 1] = true;
			}

			k = Integer.parseInt(reader.readLine());
			inputData = new StringTokenizer(reader.readLine());
			int any = -1;
			for (int i = 0; i < k; i++) {
				int cur = Integer.parseInt(inputData.nextToken()) - 1;
				set2[cur] = true;
				any = cur;
			}

			printer.println("B " + (any + 1));
			printer.flush();
			int match = Integer.parseInt(reader.readLine()) - 1;
			if (set1[match]) {
				printer.println("C " + (match + 1));
				printer.flush();
				continue tLoop;
			}

			ans = -1;
			dfs(match);

			printer.println("A " + (ans + 1));
			printer.flush();
			int rep = Integer.parseInt(reader.readLine()) - 1;

			if (set2[rep]) {
				printer.println("C " + (ans + 1));
				printer.flush();
			} else {
				printer.println("C -1");
				printer.flush();
			}
		}
	}

	static int ans = -1;

	static void dfs(int cV) {
		if (ans != -1) {
			return;
		}

		if (set1[cV]) {
			ans = cV;
			return;
		}

		vis[cV] = true;

		for (int aV : aList[cV]) {
			if (!vis[aV]) {
				dfs(aV);
			}
		}
	}

}
