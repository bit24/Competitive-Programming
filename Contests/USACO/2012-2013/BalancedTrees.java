import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BalancedTrees {

	static ArrayList<Integer>[] chldn;

	static int[] mark;

	static int[][] longC;

	static int ans = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("btree.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("btree.out")));
		int nV = Integer.parseInt(reader.readLine());

		chldn = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			chldn[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < nV; i++) {
			chldn[Integer.parseInt(reader.readLine()) - 1].add(i);
		}
		mark = new int[nV];
		for (int i = 0; i < nV; i++) {
			mark[i] = reader.readLine().charAt(0) == '(' ? 0 : 1;
		}
		reader.close();

		longC = new int[nV][];
		hChain(0);
		pChain(0, new int[] { 0, 0 });
		printer.println(ans);
		printer.close();
	}

	static void hChain(int cV) {
		int[] max = new int[2];
		for (int chV : chldn[cV]) {
			hChain(chV);
			max = max(max, longC[chV]);
		}
		longC[cV] = apply(mark[cV], max);
	}

	static void pChain(int cV, int[] fTop) {
		fTop = max(fTop, new int[] { 0, 0 });
		ans = Math.max(ans, Math.max(Math.min(fTop[0], longC[cV][1]), Math.min(fTop[1], longC[cV][0])));

		fTop = apply(mark[cV], fTop);

		ArrayList<Integer> cChldn = chldn[cV];
		int nChldn = cChldn.size();
		if (nChldn != 0) {
			int[][] preM = new int[nChldn][2];
			preM[0] = copy(longC[cChldn.get(0)]);

			for (int i = 1; i < nChldn; i++) {
				preM[i] = max(preM[i - 1], longC[cChldn.get(i)]);
			}

			int[][] sufM = new int[nChldn][2];
			sufM[nChldn - 1] = copy(longC[cChldn.get(nChldn - 1)]);
			for (int i = nChldn - 2; i >= 0; i--) {
				sufM[i] = max(sufM[i + 1], longC[cChldn.get(i)]);
			}

			for (int i = 0; i < nChldn; i++) {
				int[] cMax = copy(fTop);
				if (i - 1 >= 0) {
					cMax = max(cMax, apply(mark[cV], preM[i - 1]));
				}
				if (i + 1 < nChldn) {
					cMax = max(cMax, apply(mark[cV], sufM[i + 1]));
				}
				pChain(cChldn.get(i), cMax);
			}
		}
	}

	static int[] copy(int[] orig) {
		return new int[] { orig[0], orig[1] };
	}

	static int[] max(int[] a, int[] b) {
		return new int[] { Math.max(a[0], b[0]), Math.max(a[1], b[1]) };
	}

	static int[] apply(int dBit, int[] orig) {
		return dBit == 0 ? (new int[] { orig[0] + 1, orig[1] - 1 }) : (new int[] { orig[0] - 1, orig[1] + 1 });
	}

}
