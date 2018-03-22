import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class TheLazyCow {

	public static void main(String[] args) throws IOException {
		new TheLazyCow().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("lazy.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("lazy.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numP = Integer.parseInt(inputData.nextToken());
		int sLen = Integer.parseInt(inputData.nextToken()) * 2;

		Point[] ptsByX = new Point[numP];
		for (int i = 0; i < numP; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int mult = Integer.parseInt(inputData.nextToken());
			int oX = Integer.parseInt(inputData.nextToken());
			int oY = Integer.parseInt(inputData.nextToken());
			int nX = oX - oY;
			int nY = oX + oY + 1;
			ptsByX[i] = new Point(nX, nY, mult);
		}
		reader.close();
		Arrays.sort(ptsByX, byX);

		tree = new int[numE * 4];
		lazy = new int[numE * 4];
		int lPI = 0;

		int ans = 0;
		for (int nPI = 0; nPI < numP; nPI++) {
			Point nP = ptsByX[nPI];
			update(nP.y - sLen, nP.y, nP.c);

			while (ptsByX[lPI].x + sLen < nP.x) {
				Point rP = ptsByX[lPI];
				update(rP.y - sLen, rP.y, -rP.c);
				lPI++;
			}
			ans = Math.max(ans, query(1, numE));
		}
		printer.println(ans);
		printer.close();
	}

	Comparator<Point> byX = new Comparator<Point>() {
		public int compare(Point o1, Point o2) {
			return o1.x < o2.x ? -1 : o1.x == o2.x ? Integer.compare(o1.y, o2.y) : 1;
		}
	};

	class Point {
		int x;
		int y;
		int c;

		Point(int x, int y, int c) {
			this.x = x;
			this.y = y;
			this.c = c;
		}
	}

	int numE = 2_000_001;
	int[] tree;
	int[] lazy;

	int query(int qL, int qR) {
		qL = Math.max(1, qL);
		qR = Math.min(qR, numE);
		if (qL > qR) {
			return 0;
		}
		return query(1, 1, numE, qL, qR);
	}

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (cR < qL || qR < cL) {
			return 0;
		}

		if (lazy[nI] != 0) {
			tree[nI] += lazy[nI];
			if (cL != cR) {
				// internal node
				lazy[nI * 2] += lazy[nI];
				lazy[nI * 2 + 1] += lazy[nI];
			}
			lazy[nI] = 0;
		}

		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) / 2;

		return Math.max(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	void update(int uL, int uR, int delta) {
		uL = Math.max(1, uL);
		uR = Math.min(uR, numE);
		if (uL <= uR) {
			update(1, 1, numE, uL, uR, delta);
		}
	}

	void update(int nI, int cL, int cR, int uL, int uR, int delta) {
		// current node needs to have no lazy before returning because it will be used to compute tree[parent]
		if (lazy[nI] != 0) {
			tree[nI] += lazy[nI];

			if (cL != cR) {
				lazy[nI * 2] += lazy[nI];
				lazy[nI * 2 + 1] += lazy[nI];
			}
			lazy[nI] = 0;
		}
		if (cR < uL || uR < cL) {
			return;
		}

		if (uL <= cL && cR <= uR) {
			tree[nI] += delta;
			if (cL != cR) {
				lazy[nI * 2] += delta;
				lazy[nI * 2 + 1] += delta;
			}
			return;
		}

		int mid = (cL + cR) / 2;

		update(nI * 2, cL, mid, uL, uR, delta);
		update(nI * 2 + 1, mid + 1, cR, uL, uR, delta);

		tree[nI] = Math.max(tree[nI * 2], tree[nI * 2 + 1]);
	}

}
