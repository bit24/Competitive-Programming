import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Slingshot {

	public static void main(String[] args) throws IOException {
		new Slingshot().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("slingshot.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("slingshot.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nS = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		Point[] updates = new Point[nS];
		for (int i = 0; i < nS; i++) {
			inputData = new StringTokenizer(reader.readLine());
			updates[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()),
					Integer.parseInt(inputData.nextToken()), -1);
		}

		Point[] queries = new Point[nQ];
		for (int i = 0; i < nQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			queries[i] = new Point(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()), -1,
					i);
		}
		reader.close();

		sigY = new ArrayList<>();

		for (int i = 0; i < nS; i++) {
			sigY.add(updates[i].y);
		}
		for (int i = 0; i < nQ; i++) {
			sigY.add(queries[i].y);
		}

		Collections.sort(sigY);
		ArrayList<Long> nDup = new ArrayList<>();
		nDup.add(sigY.get(0));
		for (int i = 1; i < sigY.size(); i++) {
			if (sigY.get(i - 1) != sigY.get(i)) {
				nDup.add(sigY.get(i));
			}
		}
		sigY = nDup;
		minY = sigY.get(0);
		maxY = sigY.get(sigY.size() - 1);

		Arrays.sort(updates);
		Arrays.sort(queries);

		ans = new long[nQ];
		for (int i = 0; i < nQ; i++) {
			ans[queries[i].qI] = Math.abs(queries[i].x - queries[i].y);
		}

		for (Point cQ : queries) {
			cQ.cost = cQ.x + cQ.y;
		}
		for (Point cU : updates) {
			cU.cost -= cU.x + cU.y;
		}
		sweep(queries, updates, true, true);

		for (Point cQ : queries) {
			cQ.cost = cQ.x - cQ.y;
		}
		for (Point cU : updates) {
			cU.cost += 2 * cU.y;
		}
		sweep(queries, updates, true, false);

		for (Point cQ : queries) {
			cQ.cost = -cQ.x + cQ.y;
		}
		for (Point cU : updates) {
			cU.cost += 2L * cU.x - 2 * cU.y;
		}
		sweep(queries, updates, false, true);

		for (Point cQ : queries) {
			cQ.cost = -cQ.x - cQ.y;
		}
		for (Point cU : updates) {
			cU.cost += 2 * cU.y;
		}
		sweep(queries, updates, false, false);
		for (long cAns : ans) {
			printer.println(cAns);
		}
		printer.close();
	}

	long[] ans;

	ArrayList<Long> sigY;
	long minY;
	long maxY;

	int binSearch(long item) {
		int low = 0;
		int high = sigY.size() - 1;
		while (low != high) {
			int mid = (low + high) >> 1;
			if (sigY.get(mid) < item) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		assert (sigY.get(low) == item);
		return low;
	}

	long[] tree = new long[4 * 200_000];

	long query(long qL, long qR) {
		int cQL = binSearch(qL);
		int cQR = binSearch(qR);
		return query(1, 0, sigY.size() - 1, cQL, cQR);
	}

	long query(int nI, int cL, int cR, int qL, int qR) {
		if (qR < cL || cR < qL) {
			return Long.MAX_VALUE / 4;
		}
		if (qL <= cL && cR <= qR) {
			return tree[nI];
		}
		int mid = (cL + cR) >> 1;
		return Math.min(query(nI * 2, cL, mid, qL, qR), query(nI * 2 + 1, mid + 1, cR, qL, qR));
	}

	void update(long uI, long uV) {
		int cUI = binSearch(uI);
		update(1, 0, sigY.size() - 1, cUI, uV);
	}

	void update(int nI, int cL, int cR, int uI, long uV) {
		if (cL == cR) {
			tree[nI] = Math.min(tree[nI], uV);
		} else {
			int mid = (cL + cR) >> 1;
			if (uI <= mid) {
				update(nI * 2, cL, mid, uI, uV);
			} else {
				update(nI * 2 + 1, mid + 1, cR, uI, uV);
			}
			tree[nI] = Math.min(tree[nI * 2], tree[nI * 2 + 1]);
		}
	}

	void sweep(Point[] queries, Point[] updates, boolean LR, boolean QL) {
		Arrays.fill(tree, Long.MAX_VALUE / 4);

		if (LR) {
			int nUI = 0;
			for (int cQI = 0; cQI < queries.length; cQI++) {
				Point cQ = queries[cQI];
				while (nUI < updates.length && updates[nUI].x <= cQ.x) {
					update(updates[nUI].y, updates[nUI].cost);
					nUI++;
				}
				ans[cQ.qI] = Math.min(ans[cQ.qI], cQ.cost + (QL ? query(minY, cQ.y) : query(cQ.y, maxY)));
			}
		} else {
			int nUI = updates.length - 1;
			for (int cQI = queries.length - 1; cQI >= 0; cQI--) {
				Point cQ = queries[cQI];
				while (0 <= nUI && updates[nUI].x >= cQ.x) {
					update(updates[nUI].y, updates[nUI].cost);
					nUI--;
				}
				ans[cQ.qI] = Math.min(ans[cQ.qI], cQ.cost + (QL ? query(minY, cQ.y) : query(cQ.y, maxY)));
			}
		}
	}

	class Point implements Comparable<Point> {
		long x;
		long y;
		long cost;
		int qI;

		Point(int x, int y, int cost, int qI) {
			this.x = x;
			this.y = y;
			this.cost = cost;
			this.qI = qI;
		}

		public int compareTo(Point o) {
			return Long.compare(x, o.x);
		}
	}
}
