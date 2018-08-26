import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class Edu_49F {
	static int nC;
	static int[][] choices;

	static int nV;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nC = Integer.parseInt(reader.readLine());
		choices = new int[nC][2];

		for (int i = 0; i < nC; i++) {
			String nLine = reader.readLine();
			int num0 = 0;
			int j;
			for (j = 0; j < nLine.length() && nLine.charAt(j) != ' '; j++) {
				num0 *= 10;
				num0 += nLine.charAt(j) - '0';
			}

			int num1 = 0;
			j++;
			for (; j < nLine.length(); j++) {
				num1 *= 10;
				num1 += nLine.charAt(j) - '0';
			}

			choices[i][0] = num0;
			choices[i][1] = num1;
		}

		long[] b = new long[nC * 2];
		for (int i = 0; i < nC; i++) {
			b[i << 1] = (long) choices[i][0] << 32 | (i << 1);
			b[(i << 1) + 1] = (long) choices[i][1] << 32 | ((i << 1) + 1);
		}
		Arrays.sort(b);
		int[] rMap = new int[nC * 2];

		int p = 0;
		rMap[0] = (int) (b[0] >> 32);
		for (int i = 0; i < nC * 2; i++) {
			if (i > 0 && (b[i] ^ b[i - 1]) >> 32 != 0) {
				p++;
				rMap[p] = (int) (b[i] >> 32);
			}
			choices[(int) b[i] >> 1][(int) (b[i] & 1)] = p;
		}

		nV = p + 1;

		anc = new int[nV];
		full = new boolean[nV];
		rank = new int[nV];

		int low = 0;
		int high = nV;

		while (low != high) {
			int mid = (low + high) >> 1;
			if (possible(mid)) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		if (low == nV) {
			printer.println(-1);
		} else {
			printer.println(rMap[low]);
		}
		printer.close();
	}

	static boolean possible(int last) {
		if (last == nV) {
			return true;
		}
		Arrays.fill(anc, -1);
		Arrays.fill(full, false);
		Arrays.fill(rank, 0);

		for (int i = 0; i < nC; i++) {
			if (choices[i][0] > last) {
				return false;
			}

			if (choices[i][1] <= last) {
				if (!merge(choices[i][0], choices[i][1])) {
					return false;
				}
			} else {
				if (!merge(choices[i][0], choices[i][0])) {
					return false;
				}
			}
		}
		return true;
	}

	static int[] stack = new int[1000001];

	static int[] anc;
	static boolean[] full;
	static int[] rank;

	static boolean merge(int a, int b) {
		int aRoot = fRoot(a);
		int bRoot = fRoot(b);

		if (aRoot == bRoot) {
			if (full[aRoot]) {
				return false;
			}
			full[aRoot] = true;
			return true;
		}

		if (full[aRoot] && full[bRoot]) {
			return false;
		}

		if (rank[a] < rank[b]) {
			anc[aRoot] = bRoot;
			full[bRoot] = full[aRoot] || full[bRoot];
		} else {
			if (rank[a] == rank[b]) {
				anc[aRoot] = bRoot;
				rank[bRoot]++;
				full[bRoot] = full[aRoot] || full[bRoot];
			} else {
				anc[bRoot] = aRoot;
				full[aRoot] = full[aRoot] || full[bRoot];
			}
		}
		return true;
	}

	static int fRoot(int cur) {
		int sSize = 0;
		while (anc[cur] != -1) {
			stack[sSize++] = cur;
			cur = anc[cur];
		}

		for (int i = 0; i < sSize; i++) {
			anc[stack[i]] = cur;
		}
		return cur;
	}
}