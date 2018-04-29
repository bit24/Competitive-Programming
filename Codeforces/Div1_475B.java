import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div1_475B {

	static ArrayList<Integer>[] chldn;

	static boolean[] marked;
	static boolean[] rmvd;

	static ArrayList<Integer> sol = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nV = Integer.parseInt(reader.readLine());
		chldn = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			chldn[i] = new ArrayList<>();
		}

		int root = -1;

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			int par = Integer.parseInt(inputData.nextToken()) - 1;
			if (par == -1) {
				root = i;
			} else {
				chldn[par].add(i);
			}
		}
		marked = new boolean[nV];
		for (int i = 0; i < nV; i++) {
			if (i == root) {
				marked[i] = (chldn[i].size() & 1) == 0;
			} else {
				marked[i] = (chldn[i].size() & 1) == 1;
			}
		}

		rmvd = new boolean[nV];
		dfs(root);

		if (rmvd[root]) {
			printer.println("YES");
			for (int i : sol) {
				printer.println(i + 1);
			}
		} else {
			printer.println("NO");
		}
		printer.close();
	}

	static void dfs(int cV) {
		for (int chld : chldn[cV]) {
			if (!rmvd[chld]) {
				dfs(chld);
				if (rmvd[chld]) {
					marked[cV] = !marked[cV];
				}
			}
		}
		if (marked[cV]) {
			rmvAll(cV);
		}
	}

	static void rmvAll(int cV) {
		sol.add(cV);
		rmvd[cV] = true;
		for (int chld : chldn[cV]) {
			if (!rmvd[chld]) {
				rmvAll(chld);
			}
		}
	}
}
