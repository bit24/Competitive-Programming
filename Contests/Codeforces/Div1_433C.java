import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_433C {

	public static void main(String[] args) throws IOException {
		new Div1_433C().execute();
	}

	int n;
	int[] trees;

	int[] lC = new int[10_000_000];
	int[] rC = new int[10_000_000];
	int[] val = new int[10_000_000];
	int nN;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		n = Integer.parseInt(inputData.nextToken());
		int q = Integer.parseInt(inputData.nextToken());

		trees = new int[n + 1];
		trees[0] = init(1, n);

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= n; i++) {
			trees[i] = update(trees[i - 1], 1, n, Integer.parseInt(inputData.nextToken()));
		}

		for (int i = 1; i <= q; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int b = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			int t = Integer.parseInt(inputData.nextToken());

			long s1 = query(1, l - 1, 1, n);
			long s2 = query(r + 1, n, 1, n);
			long s3 = query(1, n, 1, b - 1);
			long s4 = query(1, n, t + 1, n);

			long a1 = query(1, l - 1, 1, b - 1);
			long a2 = query(r + 1, n, 1, b - 1);
			long a3 = query(r + 1, n, t + 1, n);
			long a4 = query(1, l - 1, t + 1, n);

			long sum = (long) n * (n - 1) - s1 * (s1 - 1) - s2 * (s2 - 1) - s3 * (s3 - 1) - s4 * (s4 - 1)
					+ a1 * (a1 - 1) + a2 * (a2 - 1) + a3 * (a3 - 1) + a4 * (a4 - 1);
			sum /= 2;
			printer.println(sum);
		}
		printer.close();
	}

	int init(int cL, int cR) {
		int ret = nN++;
		if (cL != cR) {
			int mid = (cL + cR) >> 1;
			lC[ret] = init(cL, mid);
			rC[ret] = init(mid + 1, cR);
		}
		return ret;
	}

	int update(int cNode, int cL, int cR, int uI) {
		int ret = nN++;
		if (cL == cR) {
			val[ret] = 1;
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				lC[ret] = update(lC[cNode], cL, mid, uI);
				rC[ret] = rC[cNode];
			} else {
				lC[ret] = lC[cNode];
				rC[ret] = update(rC[cNode], mid + 1, cR, uI);
			}
			val[ret] = val[lC[ret]] + val[rC[ret]];
		}
		return ret;
	}

	int query(int l, int r, int b, int t) {
		return query(trees[r], 1, n, b, t) - query(trees[l - 1], 1, n, b, t);
	}

	int query(int cNode, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			return val[cNode];
		}
		int mid = (cL + cR) >> 1;
		return query(lC[cNode], cL, mid, qL, qR) + query(rC[cNode], mid + 1, cR, qL, qR);
	}

}
