import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Div1_485F {

	static final long INF = Long.MAX_VALUE / 2;

	static final int SQRTN = 31622776;

	public static void main(String[] args) throws IOException {
		new Div1_485F().execute();
	}

	boolean[] ans;

	boolean[] prime = new boolean[SQRTN + 1];

	int[] primes = new int[1951957];

	void execute() throws IOException {
		Arrays.fill(prime, true);

		int nP = 0;
		for (int i = 2; i <= SQRTN; i++) {
			if (prime[i]) {
				if ((long) i * i <= SQRTN) {
					for (int m = i * i; m <= SQRTN; m += i) {
						prime[m] = false;
					}
				}
				primes[nP++] = i;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());
		TreeMap<Long, ArrayList<Query>> queries = new TreeMap<>();

		for (int i = 0; i < nT; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			long n = Long.parseLong(inputData.nextToken());
			long k = Long.parseLong(inputData.nextToken());

			ArrayList<Query> cList = queries.get(k);

			if (cList == null) {
				cList = new ArrayList<>();
				queries.put(k, cList);
			}

			cList.add(new Query(n, i));
		}

		ans = new boolean[nT];
		for (Entry<Long, ArrayList<Query>> cE : queries.entrySet()) {
			process(cE.getKey(), cE.getValue());
		}

		for (int i = 0; i < nT; i++) {
			printer.println(ans[i] ? "YES" : "NO");
		}
		printer.close();
	}

	void process(long k, ArrayList<Query> queries) {
		if (k == 1) {
			return;
		}
		long cV = k;
		ArrayList<Long> pFact = new ArrayList<>();

		if (cV % 2 == 0) {
			cV /= 2;
			pFact.add(2L);

			while (cV % 2 == 0) {
				cV /= 2;
			}
		}

		long cP = primes[1];
		for (int i = 1; cP * cP <= cV; i++, cP = primes[i]) {
			if (cV % cP == 0) {
				cV /= cP;
				pFact.add(cP);
				while (cV % cP == 0) {
					cV /= cP;
				}
			}
		}

		if (cV != 1) {
			pFact.add(cV);
		}

		if (pFact.size() == 1) {
			for (Query cQuery : queries) {
				ans[cQuery.i] = cQuery.n % k == 0;
			}
			return;
		}

		int minF = (int) (long) pFact.get(0);

		if (pFact.size() == 2) {
			solve2(pFact.get(0), pFact.get(1), queries);
			return;
		}

		int[] trans = new int[pFact.size()];
		for (int i = 1; i < pFact.size(); i++) {
			trans[i] = (int) (pFact.get(i) % minF);
		}

		long[] mCost = new long[minF];
		Arrays.fill(mCost, INF);
		mCost[0] = 0;

		PriorityQueue<State> sQ = new PriorityQueue<>();
		sQ.add(new State(0, 0));

		while (!sQ.isEmpty()) {
			State cS = sQ.remove();
			if (cS.c > mCost[cS.i]) {
				continue;
			}

			for (int i = 1; i < pFact.size(); i++) {
				int nI = cS.i + trans[i];
				if (nI >= minF) {
					nI -= minF;
				}
				long nC = cS.c + pFact.get(i);

				if (mCost[nI] > nC) {
					mCost[nI] = nC;
					sQ.add(new State(nC, nI));
				}
			}
		}

		for (Query cQuery : queries) {
			ans[cQuery.i] = mCost[(int) (cQuery.n % minF)] <= cQuery.n;
		}
	}

	void solve2(long a, long b, ArrayList<Query> queries) {
		extEuclid(a, b);

		qR %= a;
		if (qR < 0) {
			qR += a;
		}

		for (Query cQuery : queries) {
			long n = cQuery.n;
			if (n % gcdR != 0) {
				continue;
			}

			long nB = (n / gcdR) % a * qR % a;

			ans[cQuery.i] = nB * b <= n;
		}
	}

	long gcdR;
	long pR;
	long qR;

	// equation: p*a + q*b = gcd(a,b);
	void extEuclid(long a, long b) {
		long dend = a; // dividend
		long dsor = b; // divisor
		long dendA = 1;
		long dendB = 0;
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
		// gcd = dend;
		// pRes = dendA;
		// qRes = dendB;
		gcdR = dend;
		pR = dendA;
		qR = dendB;
	}

	class State implements Comparable<State> {
		long c;
		int i;

		State(long c, int i) {
			this.c = c;
			this.i = i;
		}

		public int compareTo(State o) {
			long y = o.c;
			return (c < y) ? -1 : ((c == y) ? 0 : 1);
		}
	}

	class Query {
		long n;
		int i;

		Query(long n, int i) {
			this.n = n;
			this.i = i;
		}
	}
}