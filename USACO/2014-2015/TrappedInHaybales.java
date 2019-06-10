import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class TrappedInHaybales {

	public static void main(String[] args) throws IOException {
		new TrappedInHaybales().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("trapped.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("trapped.out")));
		int nB = Integer.parseInt(reader.readLine());
		Pair[] bales = new Pair[nB];
		for (int i = 0; i < nB; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int sz = Integer.parseInt(inputData.nextToken());
			int pos = Integer.parseInt(inputData.nextToken());
			bales[i] = new Pair(pos, sz);
		}
		reader.close();
		Arrays.sort(bales, 0, nB);
		int[] dist = new int[nB];

		for (int i = 0; i < nB; i++) {
			bales[i].i = i;
			if (i + 1 < nB) {
				dist[i] = bales[i + 1].pos - bales[i].pos;
			}
		}

		TreeSet<Pair> pSet = new TreeSet<>();

		Arrays.sort(bales, bySz);

		int[] dA = new int[nB];
		for (int i = nB - 1; i >= 0; i--) {
			Pair lwr = pSet.lower(bales[i]);
			if (lwr != null && bales[i].pos - lwr.pos <= bales[i].sz) {
				dA[lwr.i]++;
				dA[bales[i].i]--;
			}
			Pair hgr = pSet.higher(bales[i]);
			if (hgr != null && hgr.pos - bales[i].pos <= bales[i].sz) {
				dA[bales[i].i]++;
				dA[hgr.i]--;
			}
			pSet.add(bales[i]);
		}

		int ans = 0;
		int sum = 0;
		for (int i = 0; i < nB - 1; i++) {
			sum += dA[i];
			if (sum > 0) {
				ans += dist[i];
			}
		}
		printer.println(ans);
		printer.close();
	}

	Comparator<Pair> bySz = new Comparator<Pair>() {
		public int compare(Pair o1, Pair o2) {
			return o1.sz < o2.sz ? -1 : o1.sz == o2.sz ? Integer.compare(o1.pos, o2.pos) : 1;
		}
	};

	class Pair implements Comparable<Pair> {
		int pos;
		int sz;
		int i;

		Pair(int a, int b) {
			this.pos = a;
			this.sz = b;
		}

		public int compareTo(Pair o) {
			return (pos < o.pos) ? -1 : ((pos == o.pos) ? ((sz < o.sz) ? -1 : ((sz == o.sz) ? 0 : 1)) : 1);
		}
	}

}
