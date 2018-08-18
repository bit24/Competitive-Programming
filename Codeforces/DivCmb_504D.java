import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DivCmb_504D {

	public static void main(String[] args) throws IOException {
		new DivCmb_504D().execute();
	}

	static int[] comp;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int N = Integer.parseInt(inputData.nextToken());
		int Q = Integer.parseInt(inputData.nextToken());
		int[] arr = new int[N + 1];
		inputData = new StringTokenizer(reader.readLine());

		boolean cQ = false;
		for (int i = 1; i <= N; i++) {
			arr[i] = Integer.parseInt(inputData.nextToken());
			if (arr[i] == Q) {
				cQ = true;
			}
		}

		int nZ = 0;
		int nZI = -1;
		for (int i = 1; i <= N; i++) {
			if (arr[i] != 0) {
				nZ++;
				nZI = i;
			}
		}

		if (nZ == 0) {
			printer.println("YES");
			for (int i = 1; i <= N; i++) {
				printer.print(Q + " ");
			}
			printer.println();
			printer.close();
			return;
		}

		if (nZ == N) {
			if (!cQ) {
				printer.println("NO");
				printer.close();
				return;
			}
		}

		comp = new int[nZ + 1];
		int nCI = 1;

		for (int i = 1; i <= N; i++) {
			if (arr[i] != 0) {
				comp[nCI++] = arr[i];
			}
		}

		tree = new int[nZ * 4];
		Arrays.fill(tree, Integer.MAX_VALUE);
		init(1, 1, nZ);

		int[] first = new int[Q + 1];
		int[] last = new int[Q + 1];
		Arrays.fill(first, nZ + 1);
		Arrays.fill(last, 0);

		for (int i = nZ; i >= 1; i--) {
			first[comp[i]] = i;
		}
		for (int i = 1; i <= nZ; i++) {
			last[comp[i]] = i;
		}

		for (int i = 1; i <= Q; i++) {
			if (first[i] <= last[i]) {
				if (query(1, 1, nZ, first[i], last[i]) < i) {
					printer.println("NO");
					printer.close();
					return;
				}
			}
		}

		printer.println("YES");

		if (!cQ) {
			for (int i = 1; i <= N; i++) {
				if (arr[i] == 0) {
					arr[i] = Q;
					break;
				}
			}
		}

		for (int i = nZI - 1; i >= 1; i--) {
			if (arr[i] == 0) {
				arr[i] = arr[i + 1];
			}
		}

		for (int i = nZI + 1; i <= N; i++) {
			if (arr[i] == 0) {
				arr[i] = arr[i - 1];
			}
		}

		for (int i = 1; i <= N; i++) {
			printer.print(arr[i] + " ");
		}
		printer.println();
		printer.close();
	}

	int[] tree;

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return Integer.MAX_VALUE;
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}
		int mid = (cL + cR) >> 1;
		return Math.min(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	void init(int nI, int cL, int cR) {
		if (cL == cR) {
			tree[nI] = comp[cL];
		} else {
			int mid = (cL + cR) >> 1;
			init(nI * 2, cL, mid);
			init(nI * 2 + 1, mid + 1, cR);
			tree[nI] = Math.min(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}
}
