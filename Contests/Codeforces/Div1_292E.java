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
import java.util.TreeMap;
import java.util.TreeSet;

public class Div1_292E {

	long N;
	long M;

	long gcdNM;

	public static void main(String[] args) throws IOException {
		new Div1_292E().main();
	}

	long nInv;
	long mInv;

	public void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		M = Integer.parseInt(inputData.nextToken());
		gcdNM = gcd(N, M);

		if (gcdNM > 2 * 100_000 + 100) {
			printer.println(-1);
			printer.close();
			return;
		}

		ArrayList<Integer>[][] inits = new ArrayList[(int) gcdNM][2];

		for (int i = 0; i < gcdNM; i++) {
			for (int j = 0; j < 2; j++) {
				inits[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < 2; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int nI = Integer.parseInt(inputData.nextToken());
			for (int j = 0; j < nI; j++) {
				int cur = Integer.parseInt(inputData.nextToken());
				inits[(int) (cur % gcdNM)][i].add(cur);
			}
		}

		for (int i = 0; i < gcdNM; i++) {
			if (inits[i][0].isEmpty() && inits[i][1].isEmpty()) {
				printer.println(-1);
				printer.close();
				return;
			}
		}

		nInv = inv(N / gcdNM, M/gcdNM);
		mInv = inv(M / gcdNM, N/gcdNM);

		long max = 0;
		for (int i = 0; i < gcdNM; i++) {

			max = Math.max(max, process(N, M, mInv, inits[i], 0));
			max = Math.max(max, process(M, N, nInv, inits[i], 1));
		}
		printer.println(max);
		printer.close();
	}

	long process(long size, long skip, long inv, ArrayList<Integer>[] inits, int oSide) {
		long sizePrime = size / gcdNM;

		TreeMap[] eInit = { new TreeMap<Long, Long>(), new TreeMap<Long, Long>() };

		TreeSet<Long> sig = new TreeSet<Long>();

		for (int k = 0; k < 2; k++) {
			ArrayList<Integer> cInits = inits[k];
			TreeMap<Long, Long> cMap = eInit[k];

			for (long cV : cInits) {
				long res = cV % size;
				sig.add(res);
				Long pV = cMap.get(res);
				if (pV == null || pV > cV) {
					cMap.put(res, cV);
				}
			}
		}

		ArrayList<Pt> sigPts = new ArrayList<>();

		long st = sig.first();
		for (long ind : sig) {
			long cnt = ((ind - st) / gcdNM * inv % sizePrime + sizePrime) % sizePrime;
			sigPts.add(new Pt(ind, cnt));
		}
		Collections.sort(sigPts);

		long max = 0;
		for (int i = 0; i < sigPts.size(); i++) {
			int nxt = (i + 1) % sigPts.size();
			long diff = (sigPts.get(nxt).numJ - sigPts.get(i).numJ - 1 + sizePrime) % sizePrime;

			long cInd = sigPts.get(i).ind;

			if (diff > 0) {
				max = Math.max(max, min((Long) eInit[0].get(cInd), (Long) eInit[1].get(cInd)) + diff * skip);
			} else {
				if (eInit[oSide].get(cInd) == null) {
					max = Math.max(max, (Long) eInit[oSide ^ 1].get(cInd));
				}
			}
		}
		return max;
	}

	long min(Long a, Long b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		return a < b ? a : b;
	}

	class Pt implements Comparable<Pt> {
		long ind;
		long numJ;

		Pt(long ind, long numJ) {
			this.ind = ind;
			this.numJ = numJ;
		}

		public int compareTo(Pt o) {
			int res = Long.compare(numJ, o.numJ);
			if (res == 0) {
				return -Long.compare(ind, o.ind);
			}
			return res;
		}
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long inv(long number, long mod) {
		long dend = number, dendA = 1;

		long dsor = mod, dsorA = 0;

		while (dsor != 0) {
			long q = dend / dsor;

			long r = dend % dsor, rA = dendA - q * dsorA;

			dend = dsor;
			dendA = dsorA;

			dsor = r;
			dsorA = rA;
		}
		assert (dend == 1);
		return (dendA % mod + mod) % mod;
	}
}
