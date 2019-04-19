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

public class Div2_409E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nFbdn = Integer.parseInt(inputData.nextToken());
		int mod = Integer.parseInt(inputData.nextToken());
		boolean[] fbdn = new boolean[mod];
		if (nFbdn > 0) {
			inputData = new StringTokenizer(reader.readLine());
			while (nFbdn-- > 0) {
				fbdn[Integer.parseInt(inputData.nextToken())] = true;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] members = new ArrayList[mod];

		for (int i = 0; i < mod; i++) {
			members[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < mod; i++) {
			if (!fbdn[i]) {
				members[gcd(i, mod)].add(i);
			}
		}
		if (!fbdn[0]) {
			members[0].add(0);
		}

		int[] pEndCnt = new int[mod];
		for (int cEnd = 0; cEnd < mod; cEnd++) {
			if (members[cEnd].isEmpty()) {
				pEndCnt[cEnd] = -1;
			} else {
				pEndCnt[cEnd] = members[cEnd].size();
			}
		}

		int[] prev = new int[mod];
		Arrays.fill(prev, -1);

		for (int cEnd = 1; cEnd < mod; cEnd++) {
			if (pEndCnt[cEnd] != -1) {
				int cCnt = pEndCnt[cEnd];
				if (pEndCnt[0] != -1 && cCnt + members[0].size() > pEndCnt[0]) {
					pEndCnt[0] = cCnt + members[0].size();
					prev[0] = cEnd;
				}
				for (int nEnd = 2 * cEnd; nEnd < mod; nEnd += cEnd) {
					if (pEndCnt[nEnd] != -1 && cCnt + members[nEnd].size() > pEndCnt[nEnd]) {
						pEndCnt[nEnd] = cCnt + members[nEnd].size();
						prev[nEnd] = cEnd;
					}
				}
			}
		}

		int mV = 0;
		int mCnt = 0;
		for (int i = 0; i < mod; i++) {
			if (pEndCnt[i] > mCnt) {
				mCnt = pEndCnt[i];
				mV = i;
			}
		}

		printer.println(mCnt);
		int cV = mV;

		ArrayList<Integer> seq = new ArrayList<Integer>();
		while (cV != -1) {
			for (int mInt : members[cV]) {
				seq.add(mInt);
			}

			cV = prev[cV];
		}
		Collections.reverse(seq);
		
		if (!seq.isEmpty()) {
			printer.print(seq.get(0) + " ");
			for (int i = 0; i + 1 < seq.size(); i++) {
				printer.print(div(seq.get(i + 1), seq.get(i), mod) + " ");
			}
		}
		printer.println();
		printer.close();
	}

	static int gcd(int a, int b) {
		if (a > b) {
			return gcd(b, a);
		}
		return a == 0 ? b : gcd(a, b % a);
	}

	static int div(int num, int den, int mod) {
		// "a" = den
		// "b" = mod

		long dend = den;
		long dendA = 1;
		long dendB = 0;

		long dsor = mod;
		long dsorA = 0;
		long dsorB = 1;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor;
			long rA = dendA - q * dsorA;
			long rB = dendB - q * dsorB;

			dend = dsor;
			dendA = dsorA;
			dendB = dsorB;

			dsor = r;
			dsorA = rA;
			dsorB = rB;
		}
		long gcd = dend;
		long pRes = dendA;
		return (int) ((num / gcd * pRes % mod + mod) % mod);
	}

}
