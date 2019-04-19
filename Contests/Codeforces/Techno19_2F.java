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
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Techno19_2F {

	static int MODE = -1;

	static final Comparator<int[]> COMP_ARR = new Comparator<int[]>() {
		public int compare(int[] a1, int[] a2) {
			if (MODE != -1 && (a1.length == 32 || a2.length == 32)) {
				if (a1.length == 32) {
					if (MODE < a2.length) {
						return -1;
					}
					if (MODE > a2.length) {
						return 1;
					}
				} else {
					if (a1.length < MODE) {
						return -1;
					}
					if (a1.length > MODE) {
						return 1;
					}
				}

				for (int i = 0; i < MODE; i++) {
					if (a1[i] < a2[i]) {
						return -1;
					}
					if (a1[i] > a2[i]) {
						return 1;
					}
				}
				return 0;
			} else {
				if (a1.length < a2.length) {
					return -1;
				}
				if (a1.length > a2.length) {
					return 1;
				}

				for (int i = 0; i < a2.length; i++) {
					if (a1[i] < a2[i]) {
						return -1;
					}
					if (a1[i] > a2[i]) {
						return 1;
					}
				}
				return 0;
			}
		}
	};

	static TreeMap<int[], Integer> index = new TreeMap<>(COMP_ARR);
	static int[][] states;

	static ArrayList<int[]> start = new ArrayList<>();

	static int[] numD;

	static int[] pFact = new int[1_000_001];

	static TreeMap<int[], Integer> divCostInd = new TreeMap<>(COMP_ARR);

	public static void main(String[] args) throws IOException {
		cGen = new int[23];
		numD = new int[6000];
		states = new int[6000][];
		genAll(0, 23, 23, 1);

		aList = new ArrayList[6000];
		for (int i = 0; i < 6000; i++) {
			aList[i] = new ArrayList<>();
		}
		fAList();

		divCost = new int[400][301];
		for (int[] a : divCost) {
			Arrays.fill(a, Integer.MAX_VALUE / 2);
		}
		cost = new int[index.size()];

		Arrays.fill(pFact, 1);
		for (int i = 2; i <= 1_000_000; i++) {
			if (pFact[i] == 1) {
				for (int m = i; m <= 1_000_000; m += i) {
					pFact[m] = i;
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		int nT = Integer.parseInt(reader.readLine());

		while (nT-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());

			int[] aA = toArray(a);
			int[] bA = toArray(b);

			Integer aRes = divCostInd.get(aA);
			if (aRes == null) {
				aRes = divCostInd.size();
				bfs(aA, divCostInd.size());
				divCostInd.put(aA, divCostInd.size());
			}
			Integer bRes = divCostInd.get(bA);
			if (bRes == null) {
				bRes = divCostInd.size();
				bfs(bA, divCostInd.size());
				divCostInd.put(bA, divCostInd.size());
			}

			int minA = Integer.MAX_VALUE / 4;
			for (int nD = 1; nD <= 300; nD++) {
				minA = Math.min(minA, divCost[aRes][nD] + divCost[bRes][nD]);
			}
			printer.println(minA);
		}
		printer.close();
	}

	static int[] toArray(int a) {
		ArrayList<Integer> cnts = new ArrayList<Integer>();
		int last = -1;
		int cCnt = 0;

		while (a > 1) {
			int cPFact = pFact[a];
			if (cPFact == last) {
				cCnt++;
			} else {
				if (cCnt != 0) {
					cnts.add(cCnt);
				}
				last = cPFact;
				cCnt = 1;
			}
			a /= pFact[a];
		}
		if (cCnt != 0) {
			cnts.add(cCnt);
		}
		Collections.sort(cnts, Comparator.reverseOrder());
		int[] res = new int[cnts.size()];
		for (int i = 0; i < cnts.size(); i++) {
			res[i] = cnts.get(i);
		}
		return res;
	}

	static int[] cost;

	static int[][] divCost;

	static void bfs(int[] sState, int searchI) {
		int sInd = index.get(sState);
		ArrayDeque<Integer> queue = new ArrayDeque<>();

		queue.add(index.get(sState));

		Arrays.fill(cost, -1);
		cost[sInd] = 0;

		while (!queue.isEmpty()) {
			int cStInd = queue.remove();
			for (int nStInd : aList[cStInd]) {
				if (cost[nStInd] == -1) {
					cost[nStInd] = cost[cStInd] + 1;
					queue.add(nStInd);
				}
			}
		}

		for (Entry<int[], Integer> cE : index.entrySet()) {
			int cNumD = numD[cE.getValue()];
			if (cNumD <= 300) {
				divCost[searchI][cNumD] = Math.min(divCost[searchI][cNumD], cost[cE.getValue()]);
			}
		}
	}

	static ArrayList<Integer>[] aList;

	static void fAList() {
		int[] cState = new int[32];
		for (Entry<int[], Integer> cE : index.entrySet()) {
			int[] oState = cE.getKey();
			int cStInd = cE.getValue();

			int cLen = states[cStInd].length;
			for (int i = 0; i < cLen; i++) {
				cState[i] = oState[i];
			}
			MODE = cLen;

			for (int i = 0; i < cLen; i++) {
				if (i == 0 || cState[i - 1] > cState[i]) {
					cState[i]++;
					Integer nStInd = index.get(cState);
					cState[i]--;

					if (nStInd == null) {
						continue;
					}
					aList[cStInd].add(nStInd);
				}

				if (i == cLen - 1 && cState[i] == 1) {
					MODE--;
					Integer nStInd = index.get(cState);
					MODE++;
					if (nStInd == null) {
						continue;
					}
					aList[cStInd].add(nStInd);
				} else if (i == cLen - 1 || cState[i] > cState[i + 1]) {
					cState[i]--;
					Integer nStInd = index.get(cState);
					cState[i]++;
					if (nStInd == null) {
						continue;
					}
					aList[cStInd].add(nStInd);
				}
			}
			cState[cLen] = 1;

			MODE++;
			Integer nStInd = index.get(cState);
			MODE--;
			if (nStInd == null) {
				continue;
			}
			aList[cStInd].add(nStInd);
		}
	}

	static int[] cGen;

	static void genAll(int i, int max, int rem, int cProd) {
		numD[index.size()] = cProd;

		int[] cKey = Arrays.copyOf(cGen, i);
		states[index.size()] = cKey;
		index.put(cKey, index.size());

		if (rem > 0) {
			for (int nV = Math.min(max, rem); nV >= 1; nV--) {
				cGen[i] = nV;
				genAll(i + 1, nV, rem - nV, cProd * (nV + 1));
			}
		}
	}
}