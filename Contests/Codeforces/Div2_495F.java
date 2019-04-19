import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_495F {

	public static void main(String[] args) throws IOException {
		new Div2_495F().execute();
	}

	int nE;
	int nQ;
	int bound;

	int[] e;

	Data[] tree;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		nQ = Integer.parseInt(inputData.nextToken());
		bound = Integer.parseInt(inputData.nextToken());

		e = new int[nE + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		tree = new Data[nE * 4];
		init(1, 1, nE);

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int t = Integer.parseInt(inputData.nextToken());
			int a = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			if (t == 1) {
				update(1, 1, nE, a, b);
			} else {
				Data res = query(1, 1, nE, a, b);
				printer.println(res.tCnt);
			}
		}
		printer.close();
	}

	void init(int nI, int cL, int cR) {
		if (cL == cR) {
			Data nData = new Data();
			nData.states = new int[][] { { e[cL] }, { e[cR] } };
			nData.sCnt = new int[][] { { 1 }, { 1 } };
			nData.tCnt = e[cL] >= bound ? 1 : 0;
			tree[nI] = nData;
		} else {
			int mid = (cL + cR) >> 1;
			init(nI * 2, cL, mid);
			init(nI * 2 + 1, mid + 1, cR);
			tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	void update(int nI, int cL, int cR, int uI, int uV) {
		if (cL == cR) {
			Data nData = new Data();
			nData.states = new int[][] { { uV }, { uV } };
			nData.sCnt = new int[][] { { 1 }, { 1 } };
			nData.tCnt = uV >= bound ? 1 : 0;
			tree[nI] = nData;
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI, uV);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI, uV);
			}
			tree[nI] = merge(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	Data query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return new Data();
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}
		int mid = (cL + cR) >> 1;
		return merge(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	Data[] chldn = new Data[2];

	ArrayList<Integer> nStates = new ArrayList<>();
	ArrayList<Integer> nSCnt = new ArrayList<>();

	Data merge(Data l, Data r) {
		if (l.states == null) {
			return r;
		}
		if (r.states == null) {
			return l;
		}

		chldn[0] = l;
		chldn[1] = r;

		Data ret = new Data();
		ret.states = new int[2][];
		ret.sCnt = new int[2][];

		for (int d = 0; d < 2; d++) {
			nStates.clear();
			nSCnt.clear();
			Data ch0 = chldn[d];
			Data ch1 = chldn[d ^ 1];

			for (int i = 0; i < ch0.states[d].length - 1; i++) {
				nStates.add(ch0.states[d][i]);
				nSCnt.add(ch0.sCnt[d][i]);
			}

			int cState = ch0.states[d][ch0.states[d].length - 1];
			int cSCnt = ch0.sCnt[d][ch0.states[d].length - 1];

			int i = 0;
			for (; i < ch1.states[d].length && ((ch1.states[d][i] | cState) == cState); i++) {
				cSCnt += ch1.sCnt[d][i];
			}
			nStates.add(cState);
			nSCnt.add(cSCnt);

			while (i < ch1.states[d].length) {
				cState = cState | ch1.states[d][i];
				cSCnt = ch1.sCnt[d][i];

				i++;
				for (; i < ch1.states[d].length && ((ch1.states[d][i] | cState) == cState); i++) {
					cSCnt += ch1.sCnt[d][i];
				}
				nStates.add(cState);
				nSCnt.add(cSCnt);
			}

			ret.states[d] = new int[nStates.size()];
			for (int j = 0; j < nStates.size(); j++) {
				ret.states[d][j] = nStates.get(j);
			}

			ret.sCnt[d] = new int[nStates.size()];
			for (int j = 0; j < nStates.size(); j++) {
				ret.sCnt[d][j] = nSCnt.get(j);
			}
		}

		long tSum = l.tCnt + r.tCnt;

		int[] lStates = l.states[1];
		int[] lSCnt = l.sCnt[1];

		int[] rStates = r.states[0];
		int[] rSCnt = r.sCnt[0];

		long sSum = 0;
		int j = lStates.length - 1;

		for (int i = 0; i < rStates.length; i++) {
			while (j >= 0 && (lStates[j] | rStates[i]) >= bound) {
				sSum += lSCnt[j];
				j--;
			}
			tSum += rSCnt[i] * sSum;
		}
		ret.tCnt = tSum;
		return ret;
	}

	class Data {
		int[][] states;
		int[][] sCnt;
		long tCnt;
	}

}
