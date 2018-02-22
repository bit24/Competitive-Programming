import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Div1_431C {

	int nE;

	ArrayList<Integer>[] members = new ArrayList[400_000];

	long[][] tree = new long[400_000][];

	void aMember(int nI, int cL, int cR, int uL, int uR) {
		if (uR < cL || cR < uL) {
			return;
		}

		members[nI].add(uL);
		if (uL <= cL && cR <= uR) {
			return;
		}

		if (cL != cR) {
			int mid = (cL + cR) >> 1;
			aMember(nI * 2, cL, mid, uL, uR);
			aMember(nI * 2 + 1, mid + 1, cR, uL, uR);
		}
	}

	ArrayList<Integer> clean(ArrayList<Integer> inp) {
		Collections.sort(inp);
		ArrayList<Integer> ret = new ArrayList<>();
		ret.add(inp.get(0));

		for (int i = 1; i < inp.size(); i++) {
			if (ret.get(ret.size() - 1) != inp.get(i)) {
				ret.add(inp.get(i));
			}
		}
		return ret;
	}

	void init(int nI, int cL, int cR) {
		members[nI].add(0);
		members[nI].add(1);
		members[nI] = clean(members[nI]);
		tree[nI] = new long[members[nI].size()];
		if (cL != cR) {
			int mid = (cL + cR) >> 1;
			init(nI * 2, cL, mid);
			init(nI * 2 + 1, mid + 1, cR);
		}
	}

	int binSearch(ArrayList<Integer> list, int item) {
		int low = 1;
		int high = list.size() - 1;
		while (low != high) {
			int mid = (low + high + 1) >> 1;
			if (list.get(mid) <= item) {
				low = mid;
			} else {
				high = mid - 1;
			}
		}
		return low;
	}

	long query2(int nI, int cL, int cR, int qL) {
		int mI = binSearch(members[nI], qL);
		long sum = 0;
		while (mI < tree[nI].length) {
			sum += tree[nI][mI];
			mI += (mI & -mI);
		}
		return sum;
	}

	long query1(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			return query2(nI, 1, nE, qL);
		}

		int mid = (cL + cR) >> 1;
		return query1(nI * 2, cL, mid, qL, qR) + query1(nI * 2 + 1, mid + 1, cR, qL, qR);
	}

	long query0(int qL, int qR) {
		return query1(1, 1, nE, qL, qR);
	}

	void upd2(int nI, int cL, int cR, int uI, int uV) {
		int mI = binSearch(members[nI], uI);
		while (mI > 0) {
			tree[nI][mI] += uV;
			mI -= (mI & -mI);
		}
	}

	void upd1(int nI, int cL, int cR, int uI1, int uI2, int uV) {
		upd2(nI, 1, nE, uI2, uV);
		if (cL != cR) {
			int mid = (cL + cR) / 2;
			if (uI1 <= mid) {
				upd1(nI * 2, cL, mid, uI1, uI2, uV);
			} else {
				upd1(nI * 2 + 1, mid + 1, cR, uI1, uI2, uV);
			}
		}
	}

	void upd0(int uI1, int uI2, int uV) {
		upd1(1, 1, nE, uI1, uI2, uV);
	}

	void execute() throws IOException {
		for (int i = 0; i < members.length; i++) {
			members[i] = new ArrayList<>();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		TreeSet<Integer>[] occur = new TreeSet[nE + 1];
		for (int i = 1; i <= nE; i++) {
			occur[i] = new TreeSet<>();
		}

		int[] shape = new int[nE + 1];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nE; i++) {
			shape[i] = Integer.parseInt(inputData.nextToken());
			occur[shape[i]].add(i);
		}

		int[][] queries = new int[nQ + 1][3];
		for (int i = 1; i <= nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			queries[i] = new int[] { Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()) };
		}

		for (int i = 1; i <= nQ; i++) {
			if (queries[i][0] == 2) {
				aMember(1, 1, nE, queries[i][1], queries[i][2]);
			}
		}

		init(1, 1, nE);

		for (int i = 1; i <= nE; i++) {
			Integer prev = occur[shape[i]].lower(i);
			if (prev != null) {
				upd0(i, prev, i - prev);
			}
		}

		for (int cQ = 1; cQ <= nQ; cQ++) {
			if (queries[cQ][0] == 1) {
				int ind = queries[cQ][1];
				int nS = queries[cQ][2];
				int oS = shape[ind];
				Integer oP = occur[oS].lower(ind);
				Integer nP = occur[nS].lower(ind);

				Integer oA = occur[oS].higher(ind);
				Integer nA = occur[nS].higher(ind);

				if (oP != null) {
					upd0(ind, oP, -(ind - oP));
				}
				if (nP != null) {
					upd0(ind, nP, ind - nP);
				}

				if (oA != null) {
					upd0(oA, ind, -(oA - ind));
					if (oP != null) {
						upd0(oA, oP, oA - oP);
					}
				}

				if (nA != null) {
					if (nP != null) {
						upd0(nA, nP, -(nA - nP));
					}
					upd0(nA, ind, nA - ind);
				}
				shape[ind] = nS;
				occur[oS].remove(ind);
				occur[nS].add(ind);
			} else {
				printer.println(query0(queries[cQ][1], queries[cQ][2]));
			}
		}
		printer.close();
	}

	public static void main(String[] args) throws IOException {
		new Div1_431C().execute();
	}
}
