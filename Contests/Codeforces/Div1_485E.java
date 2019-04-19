import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Div1_485E {

	public static void main(String[] args) throws IOException {
		new Div1_485E().execute();
	}

	static final long MOD = 1_000_000_007;

	int[] pow2;

	int K;
	int nH;
	int nT;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		K = Integer.parseInt(inputData.nextToken());
		int nIH = Integer.parseInt(inputData.nextToken());
		int nIT = Integer.parseInt(inputData.nextToken());

		Seg[] iH = new Seg[nIH];
		for (int i = 0; i < nIH; i++) {
			inputData = new StringTokenizer(reader.readLine());
			iH[i] = new Seg(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}

		Seg[] iT = new Seg[nIT];
		for (int i = 0; i < nIT; i++) {
			inputData = new StringTokenizer(reader.readLine());
			iT[i] = new Seg(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
		}

		Arrays.sort(iH);
		Arrays.sort(iT);

		Seg[] heads = new Seg[nIH];
		Seg[] tails = new Seg[nIT];

		for (int i = 0; i < nIH; i++) {
			if (nH == 0 || heads[nH - 1].l < iH[i].l) {
				heads[nH++] = iH[i];
			}
		}

		for (int i = 0; i < nIT; i++) {
			if (nT == 0 || tails[nT - 1].l < iT[i].l) {
				tails[nT++] = iT[i];
			}
		}

		ArrayList<Integer> iSig = new ArrayList<>();
		for (int i = 0; i < nH; i++) {
			iSig.add(heads[i].r);
			iSig.add(heads[i].l - 1);
		}
		for (int i = 0; i < nT; i++) {
			iSig.add(tails[i].r);
			iSig.add(tails[i].l - 1);
		}
		iSig.add(0);
		iSig.add(K);
		Collections.sort(iSig);

		int[] sig = new int[iSig.size()];
		int nS = 0;
		for (int i = 0; i < iSig.size(); i++) {
			if (nS == 0 || sig[nS - 1] != iSig.get(i)) {
				sig[nS++] = iSig.get(i);
			}
		}

		ArrayDeque<Long> tCnt = new ArrayDeque<>();
		ArrayDeque<Integer> tInd = new ArrayDeque<>();
		long tSum = 0;

		ArrayDeque<Long> hCnt = new ArrayDeque<>();
		ArrayDeque<Integer> hInd = new ArrayDeque<>();
		long hSum = 0;

		long mSum = 1;

		int cH = 0;
		int cT = 0;

		for (int i = 1; i < nS; i++) {
			int cR = sig[i];

			int cHR = -1; // streaks cannot be up to cR
			int cTR = -1;

			if (cH < nH && heads[cH].r == cR) {
				cHR = heads[cH].l;
				cH++;
			}

			if (cT < nT && tails[cT].r == cR) {
				cTR = tails[cT].l;
				cT++;
			}
			/////////////////////

			long nMSum = 0;

			int len = sig[i] - sig[i - 1];
			if (len >= 2) {
				nMSum = (pow2(len) - 2) * (mSum + hSum + tSum) % MOD;
			}

			/////////////////////

			// new all heads block
			hInd.add(sig[i - 1] + 1);

			long nSum = (tSum + mSum) % MOD;

			hCnt.add(nSum);

			long nHSum = (hSum + nSum) % MOD;

			///////////////////////
			// new all tails block
			tInd.add(sig[i - 1] + 1);

			nSum = (hSum + mSum) % MOD;

			tCnt.add(nSum);

			long nTSum = (tSum + nSum) % MOD;

			if (cHR != -1) { // hR => bound on length of tail streak
				while (!tInd.isEmpty() && tInd.peek() <= cHR) {
					tInd.remove();
					nTSum = (nTSum - tCnt.remove() + MOD) % MOD;
				}
			}
			if (cTR != -1) { // tR => bound on length of head streak
				while (!hInd.isEmpty() && hInd.peek() <= cTR) {
					hInd.remove();
					nHSum = (nHSum - hCnt.remove() + MOD) % MOD;
				}
			}

			mSum = nMSum;
			hSum = nHSum;
			tSum = nTSum;
		}
		long ans = (mSum + tSum + hSum) % MOD;
		printer.println(ans);
		printer.close();
	}

	long pow2(int pow) {
		long cur = 1;
		long base = 2;
		while (pow > 0) {
			if ((pow & 1) == 1) {
				cur = cur * base % MOD;
			}
			pow >>= 1;
			base = base * base % MOD;
		}
		return cur;
	}

	class Seg implements Comparable<Seg> {
		int l;
		int r;

		Seg(int l, int r) {
			this.l = l;
			this.r = r;
		}

		public int compareTo(Seg o) {
			if(r == o.r) {
				return -Integer.compare(l, o.l);
			}
			return Integer.compare(r, o.r);
		}
	}
}
