import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class H_19F {

	public static void main(String[] args) throws IOException {
		new H_19F().main();
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());
		BitSet[] dSet = new BitSet[7001];

		for (int i = 1; i <= 7000; i++) {
			dSet[i] = new BitSet(7001);
		}

		for (int i = 1; i <= 7000; i++) {
			for (int m = i; m <= 7000; m += i) {
				dSet[m].set(i);
			}
		}

		BitSet[] eSet = new BitSet[7001];

		for (int i = 1; i <= 7000; i++) {
			eSet[i] = new BitSet(7001);
		}

		for (int i = 7000; i >= 1; i--) {
			eSet[i].set(i);
			for (int m = i + i; m <= 7000; m += i) {
				eSet[i].sxor(eSet[i], eSet[m]);
			}
		}

		BitSet[] sets = new BitSet[N];
		for (int i = 0; i < N; i++) {
			sets[i] = new BitSet(7001);
		}

		ArrayList<Integer> ans = new ArrayList<>();

		while (Q-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int t = Integer.parseInt(inputData.nextToken());
			int x = Integer.parseInt(inputData.nextToken()) - 1;

			if (t == 1 || t == 4) {
				int v = Integer.parseInt(inputData.nextToken());
				if (t == 1) {
					sets[x].copy(dSet[v]);
				} else {
					ans.add((int) sets[x].andpar(eSet[v]));
				}
			} else {
				int y = Integer.parseInt(inputData.nextToken()) - 1;
				int z = Integer.parseInt(inputData.nextToken()) - 1;
				if (t == 2) {
					sets[x].sxor(sets[y], sets[z]);
				} else {
					sets[x].sand(sets[y], sets[z]);
				}
			}
		}

		StringBuilder str = new StringBuilder();
		for (int i : ans) {
			str.append(i);
		}
		printer.println(str.toString());
		printer.close();
	}

	class BitSet {
		static final int MMASK = (1 << 6) - 1; // &MMASK == %64

		int nE;
		int lE;
		long[] a;

		BitSet(int size) {
			nE = (size + 63) >> 6;
			lE = size & MMASK;
			a = new long[nE];
		}

		void set(int ind) {
			a[ind >> 6] |= 1L << (ind & MMASK);
		}

		boolean isSet(int ind) {
			return (a[ind >> 6] & (1L << (ind & MMASK))) != 0;
		}

		void clear() {
			for (int i = 0; i < nE; i++) {
				a[i] = 0;
			}
		}

		void sxor(BitSet s1, BitSet s2) {
			for (int i = 0; i < nE; i++) {
				a[i] = s1.a[i] ^ s2.a[i];
			}
		}

		void sand(BitSet s1, BitSet s2) {
			for (int i = 0; i < nE; i++) {
				a[i] = s1.a[i] & s2.a[i];
			}
		}

		long andpar(BitSet o) {
			long par = 0;
			for (int i = 0; i < nE; i++) {
				long c = a[i] & o.a[i];
				c ^= c >> 1;
				c ^= c >> 2;
				c = (c & 0x1111111111111111L) * 0x1111111111111111L;
				par ^= (c >> 60) & 1;
			}
			return par;
		}

		void copy(BitSet o) {
			System.arraycopy(o.a, 0, a, 0, nE);
		}
	}
}
