import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class MoorioKart {

	public static void main(String[] args) throws IOException {
		new MoorioKart().main();
	}

	final long MOD = 1_000_000_007L;

	int nV;
	int nE;
	int lkCst;
	int lnBd;

	ArrayList<Integer>[] aList;
	ArrayList<Integer>[] cList;

	boolean[] vis;

	ArrayList<Integer> eComp;
	ArrayList<ArrayList<Integer>> comps = new ArrayList<>();

	TreeMap<Integer, Group> cPaths;

	ArrayList<TreeMap<Integer, Group>> paths = new ArrayList<>();

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("mooriokart.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("mooriokart.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nE = Integer.parseInt(inputData.nextToken());
		lkCst = Integer.parseInt(inputData.nextToken());
		lnBd = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		cList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			cList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			int c = Integer.parseInt(inputData.nextToken());
			aList[a].add(b);
			aList[b].add(a);
			cList[a].add(c);
			cList[b].add(c);
		}

		vis = new boolean[nV];
		for (int i = 0; i < nV; i++) {
			if (!vis[i]) {
				eComp = new ArrayList<>();
				eComp(i);
				comps.add(eComp);
			}
		}

		lnBd = Math.max(0, lnBd - lkCst * comps.size());

		for (ArrayList<Integer> cComp : comps) {
			cPaths = new TreeMap<>();
			for (int mV : cComp) {
				cntPaths(mV, -1, 0);
			}
			paths.add(cPaths);
		}

		TreeMap<Integer, Group> cAdPaths = new TreeMap<>();
		cAdPaths.put(0, new Group(1, 0));

		TreeMap<Integer, Group> nAdPaths = new TreeMap<>();

		for (TreeMap<Integer, Group> cPath : paths) {
			for (Entry<Integer, Group> cE1 : cAdPaths.entrySet()) {
				for (Entry<Integer, Group> cE2 : cPath.entrySet()) {
					int nLn = Math.min(lnBd, cE1.getKey() + cE2.getKey());
					Group nGroup = merge(cE1.getValue(), cE2.getValue());
					Group adIto = nAdPaths.get(nLn);
					if (adIto == null) {
						nAdPaths.put(nLn, nGroup);
					} else {
						adIto.add(nGroup);
					}
				}
			}
			TreeMap<Integer, Group> temp = cAdPaths;
			cAdPaths = nAdPaths;
			nAdPaths = temp;
			nAdPaths.clear();
		}

		Group calc = cAdPaths.get(lnBd);
		if (calc == null) {
			printer.println(0);
			printer.close();
		}

		long ans = calc.sLn;
		ans = (ans + comps.size() * lkCst % MOD * calc.cnt) % MOD;

		for (int i = comps.size() - 1; i >= 1; i--) {
			ans = ans * i % MOD;
		}

		ans = ans * 500_000_004L % MOD;
		printer.println(ans);
		printer.close();
	}

	void eComp(int cV) {
		vis[cV] = true;
		eComp.add(cV);
		for (int aV : aList[cV]) {
			if (!vis[aV]) {
				eComp(aV);
			}
		}
	}

	void cntPaths(int cV, int pV, int cD) {
		if (pV != -1) {
			if (cD < lnBd) {
				Group cGroup = cPaths.get(cD);
				if (cGroup == null) {
					cPaths.put(cD, new Group(1, cD));
				} else {
					cGroup.cnt = (cGroup.cnt + 1) % MOD;
					cGroup.sLn = (cGroup.sLn + cD) % MOD;
				}
			} else {
				Group cGroup = cPaths.get(lnBd);
				if (cGroup == null) {
					cPaths.put(lnBd, new Group(1, cD));
				} else {
					cGroup.cnt = (cGroup.cnt + 1) % MOD;
					cGroup.sLn = (cGroup.sLn + cD) % MOD;
				}
			}
		}

		for (int aI = 0; aI < aList[cV].size(); aI++) {
			int aV = aList[cV].get(aI);
			int aC = cList[cV].get(aI);
			if (aV != pV) {
				cntPaths(aV, cV, cD + aC);
			}
		}
	}

	class Group {
		long cnt;
		long sLn;

		Group(long cnt, long sLn) {
			this.cnt = cnt % MOD;
			this.sLn = sLn % MOD;
		}

		void add(Group o) {
			cnt = (cnt + o.cnt) % MOD;
			sLn = (sLn + o.sLn) % MOD;
		}
	}

	Group merge(Group a, Group b) {
		return new Group(a.cnt * b.cnt % MOD, (a.cnt * b.sLn + b.cnt * a.sLn) % MOD);
	}

}
