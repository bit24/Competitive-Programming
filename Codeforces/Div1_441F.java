import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_441F {

	public static void main(String[] args) throws IOException {
		new Div1_441F().execute();
	}

	int[] par;
	boolean[] avail;

	int fPar(int i) {
		if (par[i] == i) {
			return i;
		}
		return par[i] = fPar(par[i]);
	}

	boolean merge(int a, int b) {
		int aP = fPar(a);
		int bP = fPar(b);
		if (aP == bP) {
			if (!avail[aP]) {
				return false;
			}
			avail[aP] = false;
			return true;
		}
		if (!avail[aP] && !avail[bP]) {
			return false;
		}
		par[aP] = bP;
		avail[bP] = avail[aP] && avail[bP];
		return true;
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int m = Integer.parseInt(inputData.nextToken());

		Pcs[] pcss = new Pcs[m];

		for (int i = 0; i < m; i++) {
			inputData = new StringTokenizer(reader.readLine());
			pcss[i] = new Pcs(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1,
					Integer.parseInt(inputData.nextToken()));
		}

		Arrays.sort(pcss);

		par = new int[n];
		avail = new boolean[n];

		for (int i = 0; i < n; i++) {
			par[i] = i;
			avail[i] = true;
		}

		int ans = 0;
		for (int i = m - 1; i >= 0; i--) {
			if (merge(pcss[i].a, pcss[i].b)) {
				ans += pcss[i].w;
			}
		}
		printer.println(ans);
		printer.close();
	}

	class Pcs implements Comparable<Pcs> {
		int a;
		int b;
		int w;

		Pcs(int u, int v, int w) {
			this.a = u;
			this.b = v;
			this.w = w;
		}

		public int compareTo(Pcs o) {
			return w < o.w ? -1 : w == o.w ? 0 : 1;
		}
	}

}
