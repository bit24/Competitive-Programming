import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_410E {

	int numE;
	int[] code;
	int[] pre;

	class Pair {
		int ind;
		int pre;

		Pair(int ind, int pre) {
			this.ind = ind;
			this.pre = pre;
		}
	}

	Pair[] tree;
	boolean[] visited;
	ArrayDeque<Integer> ordering = new ArrayDeque<Integer>();

	Pair max(Pair a, Pair b) {
		return a.pre > b.pre ? a : b;
	}

	void init(int nI, int l, int r) {
		if (l == r) {
			tree[nI] = new Pair(l, pre[l]);
		} else {
			int m = (l + r) / 2;
			init(nI * 2, l, m);
			init(nI * 2 + 1, m + 1, r);

			tree[nI] = max(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	void rem(int nI, int cL, int cR, int rI) {
		if (cL == cR) {
			tree[nI].pre = 0;
		} else {
			int m = (cL + cR) / 2;

			if (rI <= m) {
				rem(nI * 2, cL, m, rI);
			} else {
				rem(nI * 2 + 1, m + 1, cR, rI);
			}

			tree[nI] = max(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	Pair query(int nI, int cL, int cR, int qL, int qR) {
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}
		if (qR < cL || cR < qL) {
			return new Pair(0, 0);
		}
		int m = (cL + cR) / 2;

		return max(query(nI * 2, cL, m, qL, qR), query(nI * 2 + 1, m + 1, cR, qL, qR));
	}

	void addSub(int cV) {
		visited[cV] = true;

		rem(1, 1, numE, cV);

		if (pre[cV] <= numE && !visited[pre[cV]]) {
			addSub(pre[cV]);
		}

		Pair next = query(1, 1, numE, 1, code[cV] - 1);
		while (next.pre > cV) {
			addSub(next.ind);
			next = query(1, 1, numE, 1, code[cV] - 1);
		}
		ordering.addLast(cV);
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		numE = Integer.parseInt(reader.readLine());
		code = new int[numE + 1];
		pre = new int[numE + 1];
		tree = new Pair[4 * numE];
		visited = new boolean[numE + 1];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		reader.close();

		Arrays.fill(pre, numE + 1);
		for (int i = 1; i <= numE; i++) {
			code[i] = Integer.parseInt(inputData.nextToken());
			if (code[i] != -1) {
				pre[code[i]] = i;
			} else {
				code[i] = numE + 1;
			}
		}

		init(1, 1, numE);

		for (int i = 1; i <= numE; i++) {
			if (!visited[i]) {
				addSub(i);
			}
		}

		int next = 1;
		int[] value = new int[numE + 1];

		for (Integer item : ordering) {
			value[item] = next++;
		}
		
		for (int i = 1; i <= numE; i++) {
			printer.print(value[i] + " ");
		}

		printer.println();
		printer.close();
	}

	public static void main(String[] args) throws IOException {
		new Div2_410E().execute();
	}

}
