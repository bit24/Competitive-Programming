import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_420D {

	public static void main(String[] args) throws IOException {
		new Div2_420D().execute();
	}

	@SuppressWarnings("unchecked")
	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numR = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken());
		int numM = Integer.parseInt(inputData.nextToken());

		ArrayList<Pair>[] rItems = new ArrayList[numR + 2];
		ArrayList<Pair>[] cItems = new ArrayList[numC + 2];

		for (int i = 0; i <= numR + 1; i++) {
			rItems[i] = new ArrayList<Pair>();
		}
		for (int i = 0; i <= numC + 1; i++) {
			cItems[i] = new ArrayList<Pair>();
		}

		Pair start = null;
		for (int i = 0; i < numM; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int r = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			Pair cPair = new Pair(r, c, i);
			if (r == 1 && c == 1) {
				start = cPair;
			}
			rItems[r].add(cPair);
			cItems[c].add(cPair);
		}

		int[] cost = new int[numM];
		Arrays.fill(cost, Integer.MAX_VALUE / 8);
		cost[start.id] = 0;

		boolean[] proc = new boolean[numM];

		ArrayDeque<Pair> bfsQueue = new ArrayDeque<Pair>();
		bfsQueue.add(start);

		while (!bfsQueue.isEmpty()) {
			Pair cur = bfsQueue.remove();
			int cCost = cost[cur.id];

			if (cur.r == numR && cur.c == numC) {
				System.out.println(cCost);
				return;
			}
			if (proc[cur.id]) {
				continue;
			}
			proc[cur.id] = true;

			for (int rDel = -2; rDel <= 2; rDel++) {
				if (cur.r + rDel < 1 || cur.r + rDel > numR) {
					continue;
				}
				for (Pair rPeer : rItems[cur.r + rDel]) {
					if (Math.abs(rPeer.r - cur.r) + Math.abs(rPeer.c - cur.c) == 1) {
						if (cost[rPeer.id] > cCost) {
							cost[rPeer.id] = cCost;
							bfsQueue.addFirst(rPeer);
						}
					} else {
						if (cost[rPeer.id] > cCost + 1) {
							cost[rPeer.id] = cCost + 1;
							bfsQueue.addLast(rPeer);
						}
					}
				}
			}

			for (int cDel = -2; cDel <= 2; cDel++) {
				if (cur.c + cDel < 1 || cur.c + cDel > numC) {
					continue;
				}
				for (Pair cPeer : cItems[cur.c + cDel]) {
					if (Math.abs(cPeer.r - cur.r) + Math.abs(cPeer.c - cur.c) == 1) {
						if (cost[cPeer.id] > cCost) {
							cost[cPeer.id] = cCost;
							bfsQueue.addFirst(cPeer);
						}
					} else {
						if (cost[cPeer.id] > cCost + 1) {
							cost[cPeer.id] = cCost + 1;
							bfsQueue.addLast(cPeer);
						}
					}
				}
			}
		}

		int ans = Integer.MAX_VALUE / 8;

		for (Pair cPair : rItems[numR]) {
			if (cost[cPair.id] + 1 < ans) {
				ans = cost[cPair.id] + 1;
			}
		}
		for (Pair cPair : rItems[numR - 1]) {
			if (cost[cPair.id] + 1 < ans) {
				ans = cost[cPair.id] + 1;
			}
		}
		for (Pair cPair : cItems[numC]) {
			if (cost[cPair.id] + 1 < ans) {
				ans = cost[cPair.id] + 1;
			}
		}
		for (Pair cPair : cItems[numC - 1]) {
			if (cost[cPair.id] + 1 < ans) {
				ans = cost[cPair.id] + 1;
			}
		}
		if (ans < Integer.MAX_VALUE / 8) {
			System.out.println(ans);
			return;
		}

		System.out.println(-1);
	}

	class Pair {
		int r;
		int c;
		int id;

		Pair(int r, int c, int id) {
			this.r = r;
			this.c = c;
			this.id = id;
		}
	}
}
