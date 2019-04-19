import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_468C {

	static int[] tree = new int[4 * 100_010];

	static void update(int nI, int cL, int cR, int uI, int uV) {
		if (cL == cR) {
			tree[nI] = Math.max(tree[nI], uV);
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI, uV);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI, uV);
			}
			tree[nI] = Math.max(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	static int query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}
		int mid = (cL + cR) >> 1;
		return Math.max(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nS = Integer.parseInt(inputData.nextToken());
		int N = Integer.parseInt(inputData.nextToken());

		int[] delta = new int[N + 2];

		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			delta[l]++;
			delta[r + 1]--;
		}

		int[] array = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			array[i] = array[i - 1] + delta[i];
		}

		int[] lDecreasing = new int[N + 1];

		for (int i = N; i >= 1; i--) {
			int nLongest = query(1, 0, 100_000, 0, array[i]) + 1;
			lDecreasing[i] = nLongest;
			update(1, 0, 100_000, array[i], nLongest);
		}

		Arrays.fill(tree, 0);

		int ans = 0;
		for (int i = 1; i <= N; i++) {
			int nLongest = query(1, 0, 100_000, 0, array[i]) + 1;
			ans = Math.max(ans, nLongest + lDecreasing[i] - 1);
			update(1, 0, 100_000, array[i], nLongest);
		}
		printer.println(ans);
		printer.close();
	}
}
