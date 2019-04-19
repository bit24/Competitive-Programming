import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div1_339D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nV = Integer.parseInt(reader.readLine());
		aList = new ArrayList[nV];
		cChList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
			cChList[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV - 1; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
		}

		preprocess();

		int nQ = Integer.parseInt(reader.readLine());

		inQList = new boolean[nV];

		qLoop:
		for (int cQ = 1; cQ <= nQ; cQ++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int K = Integer.parseInt(inputData.nextToken());
			qList.clear();

			for (int i = 0; i < K; i++) {
				qList.add(Integer.parseInt(inputData.nextToken()) - 1);
			}

			for (int i : qList) {
				inQList[i] = true;
			}

			for (int i : qList) {
				if (i != 0 && inQList[anc[i][0]]) {
					for (int j : qList) {
						inQList[j] = false;
					}
					printer.println(-1);
					continue qLoop;
				}
			}

			Collections.sort(qList, BY_ORDER);

			ArrayList<Integer> lcas = new ArrayList<Integer>();

			for (int i = 0; i + 1 < qList.size(); i++) {
				lcas.add(fLCA(qList.get(i), qList.get(i + 1)));
			}

			qList.addAll(lcas);
			Collections.sort(qList, BY_ORDER);
			qList = rDup(qList);

			ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

			int root = -1;

			for (int i : qList) {
				if (stack.isEmpty()) {
					assert (root == -1);
					root = i;
					stack.push(i);
					continue;
				}

				while (!isAnc(stack.peek(), i)) {
					stack.pop();
				}

				cChList[stack.peek()].add(i);
				stack.push(i);
			}

			cnt = 0;
			dfs(root);
			printer.println(cnt);

			for (int i : qList) {
				inQList[i] = false;
				cChList[i].clear();
			}
		}
		printer.close();
	}

	static int cnt;

	static boolean dfs(int cV) {
		if (inQList[cV]) {
			for (int chV : cChList[cV]) {
				if (dfs(chV)) {
					cnt++;
				}
			}
			return true;
		} else {
			int cCnt = 0;
			for (int chV : cChList[cV]) {
				if (dfs(chV)) {
					cCnt++;
				}
			}
			if (cCnt >= 2) {
				cnt++;
				return false;
			}
			return cCnt == 1;
		}
	}

	static ArrayList<Integer> qList = new ArrayList<>();
	static boolean[] inQList;

	static ArrayList<Integer>[] cChList;

	static int nV;
	static ArrayList<Integer>[] aList;

	static int root;

	static int[][] anc;
	static int[] depth;
	static int[] order;
	static int cT = 0;

	// good for depth of up to 1_048_576 = 2^20

	static void preprocess() {
		anc = new int[nV][21];
		anc[root][0] = root;
		depth = new int[nV];
		order = new int[nV];
		fParent(root);

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	static void fParent(int cV) {
		order[cV] = cT++;
		for (int aV : aList[cV]) {
			if (aV != anc[cV][0]) {
				anc[aV][0] = cV;
				depth[aV] = depth[cV] + 1;
				fParent(aV);
			}
		}
	}

	static int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		b = jump(b, depth[b] - depth[a]);

		if (a == b) {
			return a;
		}
		for (int i = 20; i >= 0; i--) {
			if (anc[a][i] != anc[b][i]) {
				a = anc[a][i];
				b = anc[b][i];
			}
		}
		return anc[a][0];
	}

	static int jump(int cV, int d) {
		for (int i = 0; i <= 20; i++) {
			if ((d & (1 << i)) != 0) {
				cV = anc[cV][i];
			}
		}
		return cV;
	}

	static boolean isAnc(int a, int b) {
		return jump(b, depth[b] - depth[a]) == a;
	}

	static final Comparator<Integer> BY_ORDER = new Comparator<Integer>() {
		public int compare(Integer v1, Integer v2) {
			return Integer.compare(order[v1], order[v2]);
		}
	};

	static ArrayList<Integer> rDup(ArrayList<Integer> inp) {
		ArrayList<Integer> ans = new ArrayList<>();
		for (int i = 0; i < inp.size(); i++) {
			if (i == 0 || !inp.get(i).equals(inp.get(i - 1))) {
				ans.add(inp.get(i));
			}
		}
		return ans;
	}
}
