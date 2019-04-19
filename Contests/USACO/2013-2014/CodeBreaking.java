import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class CodeBreaking {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		new CodeBreaking().execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}
	int nV;

	int[] par;
	ArrayList<StCh>[] stChs;

	TreeMap<Integer, Integer> updateS = new TreeMap<>();
	TreeMap<Integer, ArrayList<Integer>> substates = new TreeMap<>();

	static final int MOD = 1234567;

	int[] segTree;

	TreeMap<Integer, Integer>[] uCnt;
	TreeMap<Integer, Integer>[] aCnt;

	TreeSet<Integer>[] pStates;
	TreeSet<Integer>[] fbdn;

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("code.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("code.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());
		par = new int[nV];
		stChs = new ArrayList[nV];
		uCnt = new TreeMap[nV];
		aCnt = new TreeMap[nV];
		pStates = new TreeSet[nV];
		fbdn = new TreeSet[nV];

		for (int i = 0; i < nV; i++) {
			stChs[i] = new ArrayList<>();
			uCnt[i] = new TreeMap<>();
			aCnt[i] = new TreeMap<>();
			pStates[i] = new TreeSet<>();
			pStates[i].add(0);
			fbdn[i] = new TreeSet<>();
		}

		for (int i = 1; i < nV; i++) {
			int cP = Integer.parseInt(reader.readLine());
			par[i] = cP;
			stChs[par[i]].add(new StCh(0, i));
		}

		for (int i = 0; i < nB; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int oV = Integer.parseInt(inputData.nextToken());
			String val = inputData.nextToken();
			int state = 0;
			for (int j = 0; j < 5; j++) {
				state <<= 4;
				state |= 1 + val.charAt(j) - '0';
			}
			fbdn[oV].add(state);
			// note: these states include pV
			for (int j = 4, cV = oV; j > 0; j--, cV = par[cV]) {
				int cState = ((1 << (4 * j)) - 1) & state;
				stChs[par[cV]].add(new StCh(cState, cV));
				pStates[cV].add(cState);
			}
		}
		reader.close();

		for (int i = 0; i < nV; i++) {
			stChs[i] = nDup(stChs[i]);
		}

		segTree = new int[4 * nV];
		Arrays.fill(segTree, 1);

		for (int cV = nV - 1; cV >= 0; cV--) {
			updateS.clear();
			substates.clear();
			updateS.put(0, 0);
			substates.put(0, new ArrayList<Integer>());
			for (int i = 0; i < stChs[cV].size(); i++) {
				int cS = stChs[cV].get(i).state;
				if (cS == 0 || (i > 0 && stChs[cV].get(i - 1).state == cS)) {
					continue;
				}
				updateS.put(cS, i);
				while (!updateS.containsKey(cS >> 4)) {
					ArrayList<Integer> nList = new ArrayList<>();
					nList.add(cS);
					substates.put(cS >> 4, nList);
					updateS.put(cS >> 4, i);
					cS >>= 4;
				}
				ArrayList<Integer> cList = substates.get(cS >> 4);
				if(cList == null) {
					substates.put(cS >> 4, cList = new ArrayList<>());
				}
				cList.add(cS);
			}
			solveCases(cV, 0);

			for (int pS : pStates[cV]) {
				int mS = pS;
				while (mS > 0 && mS < (1 << 12)) {
					mS <<= 4;
				}
				int sum = 0;
				for (int cC = 1; cC <= 10; cC++) {
					int nS = cC << 16 | mS;
					if (fbdn[cV].contains(nS)) {
						continue;
					}
					for (nS >>= 4; !aCnt[cV].containsKey(nS); nS >>= 4);
					sum = (sum + aCnt[cV].get(nS)) % MOD;
				}
				uCnt[cV].put(pS, sum);
			}
		}

		long tPos = 1;
		for (int i = 0; i < nV; i++) {
			tPos = (tPos * 10L) % MOD;
		}
		printer.println((tPos - uCnt[0].get(0) + MOD) % MOD);
		printer.close();
	}

	void solveCases(int cV, int cS) {
		ArrayList<Integer> cLog = new ArrayList<Integer>();
		if (updateS.containsKey(cS)) {
			for (int cU = updateS.get(cS); cU < stChs[cV].size() && stChs[cV].get(cU).state == cS; cU++) {
				int chldV = stChs[cV].get(cU).chld;
				cLog.add(update(1, 1, nV, chldV, uCnt[chldV].get(cS)));
			}
		}

		aCnt[cV].put(cS, segTree[1]);
		if(substates.containsKey(cS)) {
			for (int sS : substates.get(cS)) {
				solveCases(cV, sS);
			}
		}
		
		if (updateS.containsKey(cS)) {
			for (int cU = updateS.get(cS), i = 0; cU < stChs[cV].size() && stChs[cV].get(cU).state == cS; cU++, i++) {
				int chldV = stChs[cV].get(cU).chld;
				update(1, 1, nV, chldV, cLog.get(i));
			}
		}
	}

	// returns oV
	int update(int nI, int cL, int cR, int uI, int uV) {
		if (cL == cR) {
			int oV = segTree[nI];
			segTree[nI] = uV;
			return oV;
		} else {
			int mid = (cL + cR) / 2;
			int oV;
			if (uI <= mid) {
				oV = update(nI * 2, cL, mid, uI, uV);
			} else {
				oV = update(nI * 2 + 1, mid + 1, cR, uI, uV);
			}
			segTree[nI] = (int) ((long) segTree[nI * 2] * segTree[nI * 2 + 1] % MOD);
			return oV;
		}
	}

	ArrayList<StCh> nDup(ArrayList<StCh> list) {
		if (list.isEmpty()) {
			return new ArrayList<>();
		}
		Collections.sort(list);
		ArrayList<StCh> nDup = new ArrayList<>();
		nDup.add(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			if (!list.get(i).equals(list.get(i - 1))) {
				nDup.add(list.get(i));
			}
		}
		return nDup;
	}

	class StCh implements Comparable<StCh> {
		int state;
		int chld;

		StCh(int state, int chld) {
			this.state = state;
			this.chld = chld;
		}

		public int compareTo(StCh o) {
			return state < o.state ? -1 : state == o.state ? Integer.compare(chld, o.chld) : 1;
		}

		public boolean equals(Object o) {
			return state == ((StCh) o).state && chld == ((StCh) o).chld;
		}
	}

}
