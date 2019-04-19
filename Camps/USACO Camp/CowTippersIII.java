import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CowTippersIII {

	public static void main(String[] args) throws IOException {
		new CowTippersIII().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		Pair[] e = new Pair[n + 1];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= n; i++) {
			int v = Integer.parseInt(inputData.nextToken());
			e[i] = new Pair(v, i);
		}
		Arrays.sort(e, 1, n + 1);

		Tree front = new Tree();
		front.build(1, 1, n);
		Tree back = new Tree();
		back.build(1, 1, n);

		long ans = 0;
		for (int i = 1; i <= n; i++) {
			int cInd = e[i].i;
			long fComp = -front.query(1, 1, n, cInd, cInd, cInd % 2);
			long bComp = -back.query(1, 1, n, cInd, cInd, cInd % 2);

			long cnt1 = fComp * front.query(1, 1, n, 1, cInd, 0) * bComp * back.query(1, 1, n, cInd, n, 1);
			long cnt2 = fComp * front.query(1, 1, n, 1, cInd, 1) * bComp * back.query(1, 1, n, cInd, n, 0);

			ans = (ans + -(cnt1 + cnt2) * e[i].v) % 1_000_000_007;

			front.flip(1, 1, n, 1, cInd);
			back.flip(1, 1, n, cInd, n);
		}
		printer.println((ans + 1_000_000_007) % 1_000_000_007);
		printer.close();
	}

	class Pair implements Comparable<Pair> {
		int v;
		int i;

		Pair(int v, int i) {
			this.v = v;
			this.i = i;
		}

		public int compareTo(Pair o) {
			return Integer.compare(v, o.v);
		}
	}

	class Tree {

		int[][] tree = new int[400_000][2];
		int[] lazy = new int[400_000];

		void build(int nI, int l, int r) {
			if (l == r) {
				tree[nI][(l % 2)] = 1;
			} else {
				int mid = (l + r) >> 1;
				build(nI * 2, l, mid);
				build(nI * 2 + 1, mid + 1, r);

				for (int i = 0; i < 2; i++) {
					tree[nI][i] = tree[nI * 2][i] + tree[nI * 2 + 1][i];
				}
			}
		}

		int query(int nI, int cL, int cR, int qL, int qR, int t) {
			push(nI, cL, cR);
			if (qR < cL || cR < qL) {
				return 0;
			}
			if (qL <= cL && cR <= qR) {
				return tree[nI][t];
			}
			int mid = (cL + cR) >> 1;
			return query(nI * 2, cL, mid, qL, qR, t) + query(nI * 2 + 1, mid + 1, cR, qL, qR, t);
		}

		void flip(int nI, int cL, int cR, int uL, int uR) {
			push(nI, cL, cR);
			if (uR < cL || cR < uL) {
				return;
			}
			if (uL <= cL && cR <= uR) {
				lazy[nI] ^= 1;
				push(nI, cL, cR);
				return;
			}
			int mid = (cL + cR) >> 1;
			flip(nI * 2, cL, mid, uL, uR);
			flip(nI * 2 + 1, mid + 1, cR, uL, uR);
			for (int i = 0; i < 2; i++) {
				tree[nI][i] = tree[nI * 2][i] + tree[nI * 2 + 1][i];
			}
		}

		void push(int nI, int cL, int cR) {
			if (lazy[nI] != 0) {
				if (cL != cR) {
					lazy[nI * 2] ^= 1;
					lazy[nI * 2 + 1] ^= 1;
				}
				for (int i = 0; i < 2; i++) {
					tree[nI][i] *= -1;
				}
				lazy[nI] = 0;
			}
		}
	}
}
