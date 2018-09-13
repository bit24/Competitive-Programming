import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_493D {

	static final int MOD = 998244353;

	static int K;

	static long[][] res1;
	static long[][] res2;

	static long[][] comb = new long[76][76];

	public static void main(String[] args) throws IOException {
		comb[0][0] = 1;
		for (int i = 1; i <= 75; i++) {
			comb[i][0] = 1;
			for (int j = 1; j <= 75; j++) {
				comb[i][j] = (comb[i - 1][j] + comb[i - 1][j - 1]) % MOD;
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N1 = Integer.parseInt(inputData.nextToken());
		int N2 = Integer.parseInt(inputData.nextToken());
		K = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[N1];
		for (int i = 0; i < N1; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N1 - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		process(N1);
		long[] cycle1 = cycles;

		aList = new ArrayList[N2];
		for (int i = 0; i < N2; i++) {
			aList[i] = new ArrayList<>();
		}

		for (int i = 0; i < N2 - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		process(N2);
		long[] cycle2 = cycles;

		long ans = 0;

		for (int k = 0; k <= K; k += 2) {
			ans = (ans + (long) cycle1[k] * cycle2[K - k] % MOD * comb[K][k]) % MOD;
		}

		printer.println(ans);
		printer.close();

	}

	static void process(int nV) {
		cycles = new long[K + 1];
		vCount = new int[nV];
		removed = new boolean[nV];
		cntU = new int[nV][K + 1];
		cntR = new int[nV][K + 1];

		decompose(0);
	}

	static long[] cycles;

	static ArrayList<Integer>[] aList;

	static int[] vCount;

	static int gSize = 0;

	static boolean[] removed;

	static ArrayList<Integer> members = new ArrayList<>();
	static int[][] cntU;
	static int[][] cntR;

	// whenever something has no par use -1

	// prerequisite: initialize vCount and par
	// the root can be any arbitrary vertex within that "group"
	static void decompose(int root) {
		fCount(root, -1);
		gSize = vCount[root];
		int centroid = fCentroid(root, -1);

		members.clear();
		fMembers(centroid, -1);

		cntU[centroid][0] = 1;
		for (int i = 0; i < K; i++) {
			for (int cV : members) {
				for (int aV : aList[cV]) {
					if (!removed[aV]) {
						cntU[aV][i + 1] = (cntU[aV][i + 1] + cntU[cV][i]) % MOD;
					}
				}
			}
		}

		removed[centroid] = true;

		cntR[centroid][0] = 1;
		for (int i = 0; i < K; i++) {
			for (int cV : members) {
				for (int aV : aList[cV]) {
					if (!removed[aV]) {
						cntR[aV][i + 1] = (cntR[aV][i + 1] + cntR[cV][i]) % MOD;
					}
				}
			}
		}

		for (int cV : members) {
			for (int k = 0; k <= K; k++) {
				for (int i = 0; i <= k; i++) {
					cycles[k] = (cycles[k] + (long) cntR[cV][i] * cntU[cV][k - i]) % MOD;
				}
			}
		}

		for (int aV : aList[centroid]) {
			if (!removed[aV]) {
				decompose(aV);
			}
		}
	}

	static void fMembers(int cV, int pV) {
		members.add(cV);
		Arrays.fill(cntU[cV], 0);
		Arrays.fill(cntR[cV], 0);
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fMembers(aV, cV);
			}
		}
	}

	// prerequisite: fDegree has to be run on the "group" and gSize has to be set to vCount of root
	// returns centroid
	static int fCentroid(int cV, int pV) {
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

	static void fCount(int cV, int pV) {
		vCount[cV] = 1;
		for (int aV : aList[cV]) {
			if (aV != pV && !removed[aV]) {
				fCount(aV, cV);
				vCount[cV] += vCount[aV];
			}
		}
	}
}