import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class SearchingForSoulmates {

	public static void main(String[] args) throws IOException {
		new SearchingForSoulmates().main();
	}

	int N;

	Pt[] pts;

	void main() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		N = Integer.parseInt(reader.readLine());

		pts = new Pt[N];
		for (int i = 0; i < N; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), i);
		}

		Arrays.sort(pts);
		for (int i = 0; i < N; i++) {
			if (i != 0 && pts[i].compareTo(pts[i - 1]) == 0) {
				throw new RuntimeException();
			}
		}

		TreeSet<Integer> pY = new TreeSet<>();
		for (Pt p : pts) {
			pY.add(p.y);
		}

		TreeMap<Integer, Integer> cMap = new TreeMap<>();
		int yE = 1;
		for (int k : pY) {
			cMap.put(k, yE++);
		}
		for (int i = 0; i < N; i++) {
			pts[i].y = cMap.get(pts[i].y);
		}

		int[] left = new int[N];
		int[] top = new int[N];
		int[] bot = new int[N];
		Arrays.fill(left, -1);
		Arrays.fill(top, -1);
		Arrays.fill(bot, -1);

		int[] last = new int[yE];
		Arrays.fill(last, -1);

		Arrays.sort(pts);

		for (Pt p : pts) {
			if (last[p.y] != -1) {
				left[p.i] = last[p.y];
			}
			last[p.y] = p.x;
		}

		TreeMap<Integer, Integer> lMap = new TreeMap<>();
		Arrays.sort(pts, BY_Y);

		for (Pt p : pts) {
			Integer lastY = lMap.get(p.x);
			if (lastY != null) {
				bot[p.i] = lastY;
			}
			lMap.put(p.x, p.y);
		}

		lMap.clear();
		for (int i = N - 1; i >= 0; i--) {
			Pt p = pts[i];
			Integer lastY = lMap.get(p.x);
			if (lastY != null) {
				top[p.i] = lastY;
			}
			lMap.put(p.x, p.y);
		}

		Arrays.sort(pts);
		tree = new int[4 * yE];
		Arrays.fill(tree, -1);
		boolean[] matched = new boolean[N];

		for (int i = 0; i < N; i++) {
			int fRight = query(1, 1, yE - 1, bot[pts[i].i] != -1 ? bot[pts[i].i] + 1 : 1,
					top[pts[i].i] != -1 ? top[pts[i].i] - 1 : yE - 1);
			if (left[pts[i].i] == -1 ? fRight != -1 : left[pts[i].i] < fRight) {
				matched[pts[i].i] = true;
			}
			update(1, 1, yE - 1, pts[i].y, pts[i].x);
		}

		for (int i = 0; i < N; i++) {
			pts[i].x = 1_000_000_001 - pts[i].x;
		}

		Arrays.sort(pts);

		Arrays.fill(last, -1);
		Arrays.fill(left, -1);
		for (Pt p : pts) {
			if (last[p.y] != -1) {
				left[p.i] = last[p.y];
			}
			last[p.y] = p.x;
		}

		Arrays.fill(tree, -1);
		for (int i = 0; i < N; i++) {
			int fRight = query(1, 1, yE - 1, bot[pts[i].i] != -1 ? bot[pts[i].i] + 1 : 1,
					top[pts[i].i] != -1 ? top[pts[i].i] - 1 : yE - 1);
			if (left[pts[i].i] == -1 ? fRight != -1 : left[pts[i].i] < fRight) {
				matched[pts[i].i] = true;
			}
			update(1, 1, yE - 1, pts[i].y, pts[i].x);
		}

		int ans = 0;
		for (int i = 0; i < N; i++) {
			if (matched[i]) {
				ans++;
			}
		}
		printer.println(ans);
		printer.close();
	}

	int[] tree;

	void update(int nI, int cL, int cR, int uI, int uV) {
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

	int query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return -1;
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}

		int mid = (cL + cR) >> 1;
		return Math.max(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	final Comparator<Pt> BY_Y = new Comparator<Pt>() {
		public int compare(Pt p1, Pt p2) {
			if (p1.y != p2.y) {
				return Integer.compare(p1.y, p2.y);
			} else {
				return Integer.compare(p1.x, p2.x);
			}
		}
	};

	class Pt implements Comparable<Pt> {
		int x;
		int y;
		int i;

		Pt(int x, int y, int i) {
			this.x = x;
			this.y = y;
			this.i = i;
		}

		@Override
		public int compareTo(Pt o) {
			if (x != o.x) {
				return Integer.compare(x, o.x);
			} else {
				return Integer.compare(y, o.y);
			}
		}
	}
}
