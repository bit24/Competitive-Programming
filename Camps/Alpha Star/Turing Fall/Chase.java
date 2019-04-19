import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Chase {

	static int nV;
	static int nD;

	static long[] nP;
	static long[] aP;

	static ArrayList<Integer>[] aList;

	static long ans = 0;

	static int cnt = 0;

	static long t1;
	static long t2;

	static ArrayDeque<long[][][]> rQueue = new ArrayDeque<long[][][]>();

	public static void main(String[] args) throws IOException {
		new Chase().execute();
	}

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		// BufferedReader reader = new BufferedReader(new FileReader("chase.in"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		nD = Integer.parseInt(inputData.nextToken());
		if (nD == 0) {
			System.out.println(0);
			return;
		}
		nP = new long[nV];
		aP = new long[nV];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nV; i++) {
			nP[i] = Integer.parseInt(inputData.nextToken());
		}

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < nV - 1; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
			aP[a] += nP[b];
			aP[b] += nP[a];
		}

		calc(0, -1);
		System.out.println(ans);
	}

	Ret calc(int cV, int pV) {
		long[][][] cMUp;
		long[][][] cMDn;
		if (!rQueue.isEmpty()) {
			cMUp = rQueue.remove();
		} else {
			cMUp = new long[nD + 1][2][2];
		}
		if (!rQueue.isEmpty()) {
			cMDn = rQueue.remove();
		} else {
			cMDn = new long[nD + 1][2][2];
		}
		for (int i = 0; i <= nD; i++) {
			cMUp[i][0][0] = 0;
			cMUp[i][0][1] = -1;
			cMUp[i][1][0] = 0;
			cMUp[i][1][1] = -2;

			cMDn[i][0][0] = 0;
			cMDn[i][0][1] = -1;
			cMDn[i][1][0] = 0;
			cMDn[i][1][1] = -2;
		}
		cMUp[1][0][0] = aP[cV];
		cMUp[1][1][0] = aP[cV];

		for (int aV : aList[cV]) {
			if (aV == pV) {
				continue;
			}
			Ret aRets = calc(aV, cV);

			long[] aMUp = aRets.a;
			long[] aMDn = aRets.b;

			for (int aD = 0; aD < nD; aD++) {
				cMUp[aD] = max(cMUp[aD], new long[] { aMUp[aD], aV });
				cMUp[aD + 1] = max(cMUp[aD + 1], new long[] { aMUp[aD] + aP[cV] - nP[aV], aV });
				cMDn[aD] = max(cMDn[aD], new long[] { aMDn[aD], aV });
				cMDn[aD + 1] = max(cMDn[aD + 1], new long[] { aMDn[aD] + aP[aV] - nP[cV], aV });
			}
			int aD = nD;
			cMUp[aD] = max(cMUp[aD], new long[] { aMUp[aD], aV });
			cMDn[aD] = max(cMDn[aD], new long[] { aMDn[aD], aV });
		}

		for (int cD = 1; cD <= nD; cD++) {
			cMUp[cD] = max(cMUp[cD], cMUp[cD - 1]);
			cMDn[cD] = max(cMDn[cD], cMDn[cD - 1]);
		}
		for (int cD = 0; cD <= nD; cD++) {
			if (cMUp[cD][0][1] != cMDn[nD - cD][0][1]) {
				ans = Math.max(ans, cMUp[cD][0][0] + cMDn[nD - cD][0][0]);
			} else {
				if (cMUp[cD][1][1] != cMDn[nD - cD][0][1]) {
					ans = Math.max(ans, cMUp[cD][1][0] + cMDn[nD - cD][0][0]);
				}
				if (cMUp[cD][0][1] != cMDn[nD - cD][1][1]) {
					ans = Math.max(ans, cMUp[cD][0][0] + cMDn[nD - cD][1][0]);
				}
			}
		}
		long[] impA = new long[nD + 1];
		long[] impB = new long[nD + 1];
		for (int i = 0; i <= nD; i++) {
			impA[i] = cMUp[i][0][0];
			impB[i] = cMDn[i][0][0];
		}
		if (rQueue.size() < 1000) {
			rQueue.add(cMUp);
			rQueue.add(cMDn);
		}
		return new Ret(impA, impB);
	}

	static long[][] max(long[][] oVals, long[] nVals) {
		if (oVals[0][1] == nVals[1]) {
			if (oVals[0][0] >= nVals[0]) {
				return oVals;
			} else {
				return new long[][] { nVals, oVals[1] };
			}
		} else if (oVals[0][0] >= nVals[0]) {
			if (oVals[1][0] >= nVals[0]) {
				return oVals;
			} else {
				return new long[][] { oVals[0], nVals };
			}
		} else {
			return new long[][] { nVals, oVals[0] };
		}
	}

	static long[][] max(long[][] oVals, long[][] nVals) {
		return max(max(oVals, nVals[0]), nVals[1]);
	}

	class Ret {
		long[] a;
		long[] b;

		Ret(long[] a, long[] b) {
			this.a = a;
			this.b = b;
		}
	}

}
