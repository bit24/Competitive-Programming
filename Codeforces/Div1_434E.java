import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_434E implements Runnable {

	static int nV;

	static int[] nxt;
	static int[] lSt;

	static int[] dep;
	static int[] hC;

	static int[] nxt2;
	static int[] lSt2;
	static long[] dlt2;

	static long[] fSum;

	static long[] ans;

	public static void main(String[] args) throws IOException {
		new Thread(null, new Div1_434E(), "Main", 1 << 28).start();
	}

	public void run() {
		try {
			execute();
		} catch (IOException e) {}
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());

		nxt = new int[nV];
		lSt = new int[nV];
		Arrays.fill(lSt, -1);

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int root = -1;
		for (int i = 0; i < nV; i++) {
			int pV = Integer.parseInt(inputData.nextToken()) - 1;
			if (pV != -1) {
				nxt[i] = lSt[pV];
				lSt[pV] = i;
			} else {
				root = i;
			}
		}

		dep = new int[nV];
		hC = new int[nV];
		Arrays.fill(hC, -1);

		dep[root] = 1;
		prep(root);

		nxt2 = new int[nV];
		lSt2 = new int[nV];
		dlt2 = new long[nV];
		Arrays.fill(lSt2, -1);

		ArrayList<Query> fQueries = calc(root);
		fSum = new long[nV];

		for (Query cQ : fQueries) {
			fSum[cQ.v] = cQ.pSum;
			calc2(cQ.v);
		}

		ans = new long[nV];

		calcF(root, 0);

		for (int i = 0; i < nV; i++) {
			printer.println(ans[i]);
		}
		printer.close();
	}

	int prep(int cV) {
		int aV = lSt[cV];
		int cHt = 0;
		while (aV != -1) {
			dep[aV] = dep[cV] + 1;
			int aHt = prep(aV);
			if (aHt + 1 > cHt) {
				cHt = aHt + 1;
				hC[cV] = aV;
			}
			aV = nxt[aV];
		}
		return cHt;
	}

	ArrayList<Query> calc(int cV) {
		int cHC = hC[cV];
		if (cHC == -1) {
			ArrayList<Query> qList = new ArrayList<>();
			qList.add(new Query(cV, 1, dep[cV] - 1));
			return qList;
		}

		ArrayList<Query> curL = calc(cHC);

		int aV = lSt[cV];
		while (aV != -1) {
			if (aV != cHC) {
				ArrayList<Query> chldL = calc(aV);

				for (int rH = 1; rH <= chldL.size(); rH++) {
					Query subQ = chldL.get(chldL.size() - rH);
					Query parQ = curL.get(curL.size() - rH);

					parQ.pSum += (long) dep[cV] * subQ.cnt;
					subQ.pSum += (long) dep[cV] * parQ.cnt;
					parQ.cnt += subQ.cnt;

					// subQ is never used again

					dlt2[subQ.v] = subQ.pSum - parQ.pSum;
					nxt2[subQ.v] = lSt2[parQ.v];
					lSt2[parQ.v] = subQ.v;
				}
			}
			aV = nxt[aV];
		}
		curL.add(new Query(cV, 1, dep[cV] - 1));
		return curL;
	}

	void calc2(int cV) {
		int aV = lSt2[cV];
		while (aV != -1) {
			fSum[aV] = fSum[cV] + dlt2[aV];
			calc2(aV);
			aV = nxt2[aV];
		}
	}

	void calcF(int cV, long pAns) {
		ans[cV] = pAns + fSum[cV];
		int aV = lSt[cV];
		while (aV != -1) {
			calcF(aV, ans[cV]);
			aV = nxt[aV];
		}
	}

	class Query {
		int v;
		int cnt;
		long pSum;

		Query(int v, int cntD, long pSum) {
			this.v = v;
			this.cnt = cntD;
			this.pSum = pSum;
		}
	}
}
