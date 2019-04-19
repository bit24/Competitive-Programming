import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class RealityBreakdown {

	public static void main(String[] args) throws IOException {
		new RealityBreakdown().main();
	}

	long BASE = 582931;
	final long MOD = 1_000_000_123;
	long[] POW = new long[50_001];

	int N;
	int nQ;
	int L;

	void main() throws IOException {
		POW[0] = 1;
		for (int i = 1; i <= 50_000; i++) {
			POW[i] = POW[i - 1] * BASE % MOD;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		N = Integer.parseInt(inputData.nextToken());
		nQ = Integer.parseInt(inputData.nextToken());

		let = new char[N];
		int[] sCnt = new int[26];
		for (int i = 0; i < N; i++) {
			let[i] = reader.readLine().charAt(0);
			sCnt[let[i] - 'a']++;
		}

		aList = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		queries = new String[nQ];
		preH = new long[nQ][];
		for (int i = 0; i < nQ; i++) {
			String cS = reader.readLine();
			queries[i] = cS;
			preH[i] = new long[cS.length() + 1];
			for (int j = 1; j <= cS.length(); j++) {
				preH[i][j] = (preH[i][j - 1] * BASE + cS.charAt(j - 1)) % MOD;
			}
			qI.put(new Hash(preH[i][cS.length()], cS.length()), i);
		}

		ans = new long[nQ];

		vCount = new int[N];
		removed = new boolean[N];
		decompose(0);

		for (int i = 0; i < nQ; i++) {
			if (queries[i].length() == 1) {
				// ass(ans[i] == 0);
				ans[i] = sCnt[queries[i].charAt(0) - 'a'];
			}
			printer.println(ans[i]);
		}
		printer.close();
	}

	void ass(boolean b) {
		if (!b) {
			throw new RuntimeException();
		}
	}

	long[] ans;

	TreeMap<Hash, Integer> qI = new TreeMap<>();

	char[] let;

	String[] queries;
	long[][] preH;

	void process(MSet pS, MSet sS, char add, long m) {
		if (pS.map.size() * sS.map.size() <= L) {
			for (Entry<Hash, Integer> p : pS.map.entrySet()) {
				for (Entry<Hash, Integer> s : sS.map.entrySet()) {
					Hash cHash = comb(p.getKey(), s.getKey(), add);
					Integer ind = qI.get(cHash);
					if (ind != null) {
						ans[ind] += m * p.getValue() * s.getValue();
					}
				}
			}
		} else {
			for (int qI = 0; qI < nQ; qI++) {
				String cQ = queries[qI];

				for (int bk = 1; bk <= cQ.length(); bk++) {
					if (cQ.charAt(bk - 1) == add) {
						Hash p = new Hash(preH[qI][bk - 1], bk - 1);
						Hash s = new Hash((preH[qI][cQ.length()] + (MOD - POW[cQ.length() - bk]) * preH[qI][bk]) % MOD,
								cQ.length() - bk);
						ans[qI] += m * pS.query(p) * sS.query(s);
					}
				}
			}
		}
	}

	Hash comb(Hash p, Hash s, char add) {
		return new Hash((p.h * POW[s.l + 1] + add * POW[s.l] + s.h) % MOD, p.l + s.l + 1);
	}

	MSet nP;
	MSet nS;

	void dfs(int cV, int pV, Hash pPH, Hash pSH) {
		Hash nPH = new Hash((POW[pPH.l] * let[cV] + pPH.h) % MOD, pPH.l + 1);
		Hash nSH = new Hash((pSH.h * BASE + let[cV]) % MOD, pSH.l + 1);

		nP.add(nPH);
		nS.add(nSH);

		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				dfs(aV, cV, nPH, nSH);
			}
		}
	}

	ArrayList<Integer>[] aList;

	int[] vCount;

	int gSize = 0;

	boolean[] removed;

	// whenever something has no par use -1

	// prerequisite: initialize vCount and par
	// the root can be any arbitrary vertex within that "group"
	void decompose(int root) {
		fCount(root, -1);
		gSize = vCount[root];
		int centroid = fCentroid(root, -1);

		removed[centroid] = true;

		MSet tPSet = new MSet();
		MSet tSSet = new MSet();

		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				nP = new MSet();
				nS = new MSet();
				dfs(aV, centroid, new Hash(0, 0), new Hash(0, 0));
				process(nP, nS, let[centroid], -1);
				tPSet = merge(tPSet, nP);
				tSSet = merge(tSSet, nS);
			}
		}

		process(tPSet, tSSet, let[centroid], 1);

		for (Entry<Hash, Integer> p : tPSet.map.entrySet()) {
			Hash oHash = p.getKey();
			Hash cHash = comb(oHash, new Hash(0, 0), let[centroid]);
			Integer ind = qI.get(cHash);
			if (ind != null) {
				ans[ind] += p.getValue();
			}
		}

		for (Entry<Hash, Integer> s : tSSet.map.entrySet()) {
			Hash oHash = s.getKey();
			Hash cHash = comb(new Hash(0, 0), oHash, let[centroid]);
			Integer ind = qI.get(cHash);
			if (ind != null) {
				ans[ind] += s.getValue();
			}
		}

		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				decompose(aV);
			}
		}
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to vCount of root
	// returns centroid
	int fCentroid(int cV, int pV) {
		dLoop:
		while (true) {
			for (int aV : aList[cV]) {
				if (!removed[aV] && aV != pV && vCount[aV] > gSize / 2) {
					pV = cV;
					cV = aV;
					continue dLoop;
				}
			}
			return cV;
		}
	}

	void fCount(int cV, int pV) {
		vCount[cV] = 1;
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCount[cV] += vCount[aV];
			}
		}
	}

	class MSet {
		TreeMap<Hash, Integer> map = new TreeMap<>();

		void add(Hash i) {
			Integer cnt = map.get(i);
			if (cnt == null) {
				map.put(i, 1);
			} else {
				map.put(i, cnt + 1);
			}
		}

		void add(Hash i, int d) {
			Integer cnt = map.get(i);
			if (cnt == null) {
				map.put(i, d);
			} else {
				map.put(i, cnt + d);
			}
		}

		int query(Hash i) {
			Integer cnt = map.get(i);
			if (cnt == null) {
				return 0;
			} else {
				return cnt;
			}
		}
	}

	MSet merge(MSet a, MSet b) {
		if (a.map.size() < b.map.size()) {
			for (Entry<Hash, Integer> c : a.map.entrySet()) {
				b.add(c.getKey(), c.getValue());
			}
			return b;
		} else {
			for (Entry<Hash, Integer> c : b.map.entrySet()) {
				a.add(c.getKey(), c.getValue());
			}
			return a;
		}
	}

	class Hash implements Comparable<Hash> {
		long h;
		int l;

		Hash(long h, int l) {
			this.h = h;
			this.l = l;
		}

		public int compareTo(Hash o) {
			if (l != o.l) {
				return l < o.l ? -1 : 1;
			}
			return Long.compare(h, o.h);
		}
	}
}