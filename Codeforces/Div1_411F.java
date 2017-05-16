import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_411F {

	int numTV;
	int A;
	int B;

	ArrayList<Integer>[] tAList;

	boolean[][] tVAct;
	int[] numM;

	int cTime = 0;
	int[] disc;
	int[] low;

	ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

	ArrayList<ArrayList<Integer>> sCCs = new ArrayList<ArrayList<Integer>>();

	int numC;

	boolean[][] compAct;

	// represents the minimum and the maximum amounts of active members a tournament vertex can have
	int[] min;
	int[] max;

	long MOD = 1_000_000_007L;

	int[][] comb;

	void dfs(int cV) {
		disc[cV] = low[cV] = cTime++;
		stack.push(cV);

		for (int adj : tAList[cV]) {
			if (disc[adj] == -1) {
				dfs(adj);
			}
			if (low[adj] < low[cV]) {
				low[cV] = low[adj];
			}
		}
		if (disc[cV] == low[cV]) {
			ArrayList<Integer> cSCC = new ArrayList<Integer>();
			while (stack.peek() != cV) {
				low[stack.peek()] = Integer.MAX_VALUE;
				cSCC.add(stack.pop());
			}
			low[stack.pop()] = Integer.MAX_VALUE;
			cSCC.add(cV);
			sCCs.add(cSCC);
		}
	}

	void findSCCs() {
		disc = new int[numTV];
		low = new int[numTV];
		Arrays.fill(disc, -1);

		for (int i = 0; i < numTV; i++) {
			if (disc[i] == -1) {
				dfs(i);
			}
		}

		numC = sCCs.size();
	}

	int fGCD(int a, int b) {
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	void condenseComps() {
		compAct = new boolean[numC][];
		for (int cComp = 0; cComp < numC; cComp++) {
			ArrayList<Integer> cMems = sCCs.get(cComp);
			int cGCD = numM[cMems.get(0)];
			for (int j : cMems) {
				cGCD = fGCD(cGCD, numM[j]);
			}
			boolean[] cCompSet = compAct[cComp] = new boolean[cGCD];
			for (int cM : cMems) {
				for (int sM = 0; sM < numM[cM]; sM++) {
					if (tVAct[cM][sM]) {
						cCompSet[sM % cGCD] = true;
					}
				}
			}
		}
	}

	void pushConnections() {
		boolean[] cSet = compAct[numC - 1];

		for (int cComp = numC - 2; cComp >= 0; cComp--) {
			boolean[] pSet = cSet;
			cSet = compAct[cComp];
			int gcd = fGCD(pSet.length, cSet.length);

			boolean[] resSet = new boolean[gcd];

			for (int i = 0; i < pSet.length; i++) {
				if (pSet[i]) {
					resSet[i % gcd] = true;
				}
			}

			for (int cRes = 0; cRes < gcd; cRes++) {
				if(resSet[cRes]){
					for (int cNum = cRes; cNum < cSet.length; cNum += gcd) {
						cSet[cNum] = true;
					}
				}
			}
		}
	}

	void findMaxs() {
		max = new int[numTV];

		for (int cComp = 0; cComp < numC; cComp++) {
			int aCnt = 0;
			for (boolean b : compAct[cComp]) {
				if (b) {
					aCnt++;
				}
			}
			for (int cM : sCCs.get(cComp)) {
				max[cM] = numM[cM] / compAct[cComp].length * aCnt;
			}
		}
	}

	// uses Pascal's identity
	void preComb() {
		comb = new int[numTV + 1][];
		comb[0] = new int[] { 1 };
		for (int i = 1; i <= numTV; i++) {
			int[] pRow = comb[i - 1];
			int[] cRow = comb[i] = new int[i + 1];
			cRow[0] = 1;
			for (int j = 1; j < i; j++) {
				int val = pRow[j] + pRow[j - 1];
				cRow[j] = val < 1_000_000_007 ? val : val - 1_000_000_007;
			}
			cRow[i] = 1;
		}
	}

	long findAns() {
		long ans = 0;

		for (int sLws = 0; sLws < numTV; sLws++) {
			int sGrt = 0;
			int pGrt = 0;
			for (int oth = 0; oth < numTV; oth++) {
				if (oth == sLws) {
					continue;
				}
				if (min[oth] > max[sLws]) {
					if (++sGrt >= A) {
						continue;
					}
				}

				// secondary condition in case of equality to prevent overcounting
				else if (max[oth] > max[sLws] || (max[oth] == max[sLws] && sLws < oth)) {
					pGrt++;
				}
			}

			// condition: B - 1 - sGrt <= numT
			int s = B - 1 - sGrt;
			for (int numT = s > 0 ? s : 0; numT < B && numT <= pGrt && numT + sGrt < A; numT++) {
				ans = (ans + ((long) comb[pGrt][numT]) * comb[sGrt][B - 1 - numT]) % MOD;
			}
		}
		return ans;
	}

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numTV = Integer.parseInt(inputData.nextToken());
		A = Integer.parseInt(inputData.nextToken());
		B = Integer.parseInt(inputData.nextToken());

		tAList = new ArrayList[numTV];

		for (int i = 0; i < numTV; i++) {
			tAList[i] = new ArrayList<Integer>();
			String nLine = reader.readLine();
			for (int j = 0; j < numTV; j++) {
				if (nLine.charAt(j) == '1') {
					tAList[i].add(j);
				}
			}
		}

		tVAct = new boolean[numTV][];
		numM = new int[numTV];
		min = new int[numTV];

		for (int i = 0; i < numTV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cNumM = numM[i] = Integer.parseInt(inputData.nextToken());
			boolean[] cVAct = tVAct[i] = new boolean[cNumM];

			String nLine = inputData.nextToken();
			for (int j = 0; j < cNumM; j++) {
				if (cVAct[j] = nLine.charAt(j) == '1') {
					min[i]++;
				}
			}
		}
		reader.close();

		findSCCs();
		condenseComps();
		pushConnections();
		findMaxs();
		preComb();

		long ans = findAns();
		System.out.println(ans);
	}

	public static void main(String[] args) throws IOException {
		new Div1_411F().execute();
	}

}
