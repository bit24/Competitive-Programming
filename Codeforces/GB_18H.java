import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class GB_18H {

	public static void main(String[] args) throws IOException {
		new GB_18H().main();
	}

	boolean[] prime = new boolean[200_006];

	ArrayList<Integer> primes = new ArrayList<Integer>();

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int F = Integer.parseInt(inputData.nextToken());

		Arrays.fill(prime, true);
		for (int i = 2; i <= 200_005; i++) {
			if (prime[i]) {
				for (int m = i + i; m <= 200_005; m += i) {
					prime[m] = false;
				}
				primes.add(i);
			}
		}

		ArrayList<Integer> trans = new ArrayList<>(primes);

		for (int i = 0; i < primes.size(); i++) {
			for (int j = i; j < primes.size(); j++) {
				if ((long) primes.get(i) * primes.get(j) <= 200_005) {
					trans.add(primes.get(i) * primes.get(j));
				} else {
					break;
				}
			}
		}
		Collections.sort(trans);
		trans.remove(new Integer(F));

		BitSet transS = new BitSet(200_006);
		for (int a : trans) {
			transS.set(a);
		}

		BitSet[] nxS = new BitSet[101];
		for (int i = 0; i <= 100; i++) {
			nxS[i] = new BitSet(200_006);
		}

		int[] g = new int[200_006];
		for (int i = 0; i <= 200_005; i++) {
			int mex = 0;
			while (nxS[mex].isSet(i)) {
				mex++;
			}
			g[i] = mex;
			nxS[mex].ore(transS);
			transS.shiftL(1);
		}

		int xor = 0;
		for (int i = 0; i < N; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			xor ^= g[b - a - 1];
			xor ^= g[c - b - 1];
		}
		if (xor == 0) {
			printer.println("Bob");
			printer.println("Alice");
		}
		else {
			printer.println("Alice");
			printer.println("Bob");
		}
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

		void shiftL(int k) { // k < 64, can be extended by shifting k/64 first and then k%64
			long cMask = (1 << k) - 1;

			a[nE - 1] <<= k;

			for (int i = nE - 2; i >= 0; i--) {
				a[i + 1] |= (a[i] >>> (64 - k)) & cMask;
				a[i] <<= k;
			}

			a[nE - 1] &= (1 << lE) - 1;
		}

		void ore(BitSet o) {
			for (int i = 0; i < nE; i++) {
				a[i] |= o.a[i];
			}
		}
	}
}
