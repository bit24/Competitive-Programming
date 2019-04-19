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
import java.util.StringTokenizer;

public class Div1_310E {

	public static void main(String[] args) throws IOException {
		new Div1_310E().main();
	}

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nV = Integer.parseInt(inputData.nextToken());
		int nE = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<>();
		}

		dsAnc = new int[nV];
		Arrays.fill(dsAnc, -1);

		for (int i = 0; i < nE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			aList[a].add(b);
			aList[b].add(a);
			int aR = fRoot(a);
			int bR = fRoot(b);
			if (aR != bR) {
				dsAnc[aR] = bR;
			}
		}
		
		id = new int[nV];
		findTECCs();

		aList2 = new ArrayList[nV2];
		for (int i = 0; i < nV2; i++) {
			aList2[i] = new ArrayList<>();
		}

		for (int i = 0; i < nV; i++) {
			int idI = id[i];
			for (int j : aList[i]) {
				int idJ = id[j];
				if (idI != idJ) {
					aList2[idI].add(idJ);
				}
			}
		}

		for (int i = 0; i < nV2; i++) {
			aList2[i] = rDup(aList2[i]);
		}

		preprocess();

		up = new int[nV2];
		down = new int[nV2];
		for (int i = 0; i < nV2; i++) {
			up[i] = down[i] = depth[i];
		}

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			if (fRoot(a) != fRoot(b)) {
				printer.println("No");
				printer.close();
				return;
			}
			a = id[a];
			b = id[b];
			if (a == b) {
				continue;
			}
			int lca = fLCA(a, b);

			up[a] = Math.min(up[a], depth[lca]);
			down[b] = Math.min(down[b], depth[lca]);
		}

		for (int i : roots) {
			dfs(i);
		}

		if (isPos) {
			printer.println("Yes");
		} else {
			printer.println("No");
		}
		printer.close();
	}

	boolean isPos = true;

	void dfs(int cV) {
		for (int child : aList2[cV]) {
			if (child == anc[cV][0]) {
				continue;
			}
			dfs(child);
		}

		if (anc[cV][0] != cV) {
			int p = anc[cV][0];
			up[p] = Math.min(up[p], up[cV]);
			down[p] = Math.min(down[p], down[cV]);
		}

		if (down[cV] < depth[cV] && up[cV] < depth[cV]) {
			isPos = false;
		}
	}

	int[] up;
	int[] down;

	ArrayList<Integer> roots = new ArrayList<>();

	int[][] anc;
	int[] depth;

	// good for depth of up to 1_048_576 = 2^20

	void preprocess() {
		anc = new int[nV2][21];
		depth = new int[nV2];
		Arrays.fill(depth, -1);

		for (int i = 0; i < nV2; i++) {
			if (depth[i] == -1) {
				roots.add(i);
				anc[i][0] = i;
				fParent(i);
			}
		}

		for (int k = 1; k <= 20; k++) {
			for (int i = 0; i < nV2; i++) {
				anc[i][k] = anc[anc[i][k - 1]][k - 1];
			}
		}
	}

	void fParent(int cV) {
		for (int child : aList2[cV]) {
			if (child == anc[cV][0]) {
				continue;
			}
			anc[child][0] = cV;
			depth[child] = depth[cV] + 1;
			fParent(child);
		}
	}

	int fLCA(int a, int b) {
		if (depth[a] > depth[b]) {
			int temp = b;
			b = a;
			a = temp;
		}
		int dif = depth[b] - depth[a];

		for (int i = 0; i <= 20; i++) {
			if ((dif & (1 << i)) != 0) {
				b = anc[b][i];
			}
		}

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

	int[] dsAnc;

	int fRoot(int i) {
		return dsAnc[i] == -1 ? i : (dsAnc[i] = fRoot(dsAnc[i]));
	}

	ArrayList<Integer> rDup(ArrayList<Integer> inp) {
		Collections.sort(inp);
		ArrayList<Integer> ans = new ArrayList<>();
		for (int i = 0; i < inp.size(); i++) {
			if (i == 0 || !inp.get(i).equals(inp.get(i - 1))) {
				ans.add(inp.get(i));
			}
		}
		return ans;
	}

	int[] id;

	int nV2;
	ArrayList<Integer>[] aList2;

	int nV;
	ArrayList<Integer>[] aList;

	int cCnt = 0;
	int[] disc; // cCnt at time of visitation
	int[] low; // vertex with the least disc reachable using at most one back edge

	ArrayDeque<Integer> stack = new ArrayDeque<>();

	void findTECCs() {
		disc = new int[nV];
		low = new int[nV];

		Arrays.fill(disc, -1); // mark "not visited"

		for (int i = 0; i < nV; i++) {
			if (disc[i] == -1) {
				dfs(i, -1);
			}
		}
	}

	void dfs(int cV, int pV) {
		disc[cV] = low[cV] = cCnt++;
		stack.push(cV);

		int nCPar = 0;

		for (int aV : aList[cV]) {
			if (disc[aV] == -1) { // tree edge
				dfs(aV, cV);
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}
			} else if (aV == pV) {
				nCPar++;
			} else if (disc[aV] < low[cV]) { // back edge or forward edge
				low[cV] = disc[aV];
			}
		}

		if (nCPar > 1 && disc[pV] < low[cV]) {
			low[cV] = disc[pV];
		}

		if (low[cV] == disc[cV]) {
			while (stack.peek() != cV) {
				id[stack.pop()] = nV2;
			}
			id[stack.pop()] = nV2++;
		}
	}
}
