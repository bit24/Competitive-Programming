import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Div1_443B {

	public static void main(String[] args) throws IOException {
		new Div1_443B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		long m = Integer.parseInt(inputData.nextToken());

		Group[] groups = new Group[n];
		int nG = 0;

		inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < n; i++) {
			int cT = Integer.parseInt(inputData.nextToken()) - 1;

			if (nG > 0 && cT == groups[nG - 1].t) {
				groups[nG - 1].c++;
				if (groups[nG - 1].c == k) {
					groups[nG - 1] = null;
					nG--;
				}
			} else {
				groups[nG++] = new Group(cT, 1);
			}
		}

		if (nG == 1) {

			printer.println(groups[0].c * m % k);
			printer.close();
			return;
		}

		int pML = 0;
		while (pML < nG && groups[nG - pML - 1].t == groups[pML].t && groups[nG - pML - 1].c + groups[pML].c == k) {
			pML++;
		}

		if (pML == nG) {
			if ((nG & 1) == 0) {
				printer.println(0);
			} else {
				long ans = 0;
				for (int i = 0; i < nG; i++) {
					ans += groups[i].c;
				}
				ans -= groups[nG / 2].c;
				long prod = groups[nG / 2].c * m % k;
				if (prod == 0) {
					printer.println(0);
					printer.close();
					return;
				}
				ans += prod;
				printer.println(ans);
			}
			printer.close();
			return;
		}

		if (pML * 2 + 1 == nG) {
			long ans = 0;
			for (int i = 0; i < nG; i++) {
				ans += groups[i].c;
			}
			ans -= groups[pML].c;
			long prod = groups[pML].c * m % k;
			if (prod == 0) {
				printer.println(0);
				printer.close();
				return;
			}
			ans += prod;
			printer.println(ans);
			printer.close();
			return;
		}

		Group ext = null;
		if (groups[nG - pML - 1].t == groups[pML].t) {
			ext = new Group(groups[nG - pML - 1].t, (groups[nG - pML - 1].c + groups[pML].c) % k);
			pML++;
		}

		long rep = 0;
		for (int i = pML; i < nG - pML; i++) {
			rep += groups[i].c;
		}
		if (ext != null) {
			rep += ext.c;
		}
		long ans = 0;
		for (int i = 0; i < nG; i++) {
			ans += groups[i].c;
		}
		ans += (m - 1) * rep;
		printer.println(ans);
		printer.close();
	}

	class Group {
		int t;
		int c;

		Group(int t, int c) {
			this.t = t;
			this.c = c;
		}
	}
}
