import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main {

	static int[][] tree;
	static int numE;
	static int numQ;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numE = Integer.parseInt(reader.readLine());
		int[] inputData = new int[numE];
		StringTokenizer inputLine = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			inputData[i] = Integer.parseInt(inputLine.nextToken());
		}
		tree = new int[numE * 4][];

		build(0, 0, numE - 1, inputData);
		numQ = Integer.parseInt(reader.readLine());

		PrintWriter printer = new PrintWriter(new BufferedOutputStream(System.out));
		int prevAns = 0;
		for (int i = 0; i < numQ; i++) {
			inputLine = new StringTokenizer(reader.readLine());
			int v1 = (Integer.parseInt(inputLine.nextToken()) ^ prevAns) - 1;
			int v2 = (Integer.parseInt(inputLine.nextToken()) ^ prevAns) - 1;
			int v3 = Integer.parseInt(inputLine.nextToken()) ^ prevAns;
			prevAns = query(0, 0, numE - 1, v1, v2, v3);
			printer.println(prevAns);
		}

		reader.close();
		printer.close();
	}

	public static void build(int ind, int l, int r, int[] input) {
		if (l == r) {
			tree[ind] = new int[] { input[l] };
		} else {
			int mid = l + (r - l) / 2;
			build(ind * 2 + 1, l, mid, input);
			build(ind * 2 + 2, mid + 1, r, input);
			tree[ind] = merge(tree[ind * 2 + 1], tree[ind * 2 + 2]);
		}
	}

	public static int query(int ind, int cL, int cR, int qL, int qR, int value) {
		if (cR < qL || qR < cL) {
			return 0;
		}
		if (qL <= cL && cR <= qR) {
			int v = (tree[ind].length - bSearch(tree[ind], value));
			// System.out.println(v);
			return (v);
		}
		int mid = cL + (cR - cL) / 2;
		return query(ind * 2 + 1, cL, mid, qL, qR, value) + query(ind * 2 + 2, mid + 1, cR, qL, qR, value);
	}

	public static int bSearch(int[] elements, int higher) {
		int l = 0;
		int h = elements.length;

		while (l < h) {
			int mid = (l + h) / 2;
			if (elements[mid] <= higher) {
				l = mid + 1;
			} else {
				h = mid;
			}
		}
		return l;
	}

	public static int[] merge(int[] a, int[] b) {
		int aInd = 0;
		int bInd = 0;

		int[] out = new int[a.length + b.length];
		while (true) {
			if (a[aInd] < b[bInd]) {
				out[aInd + bInd] = a[aInd];
				aInd++;
			} else {
				out[aInd + bInd] = b[bInd];
				bInd++;
			}
			if (aInd == a.length) {
				System.arraycopy(b, bInd, out, aInd + bInd, b.length - bInd);
				break;
			}
			if (bInd == b.length) {
				System.arraycopy(a, aInd, out, aInd + bInd, a.length - aInd);
				break;
			}
		}
		return out;
	}
}
