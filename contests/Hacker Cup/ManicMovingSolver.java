import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class ManicMovingSolver {

	public static void main(String[] args) throws IOException {
		new ManicMovingSolver().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("manic_moving.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("manic_moving.out")));

		int numT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numV = Integer.parseInt(inputData.nextToken());
			int numE = Integer.parseInt(inputData.nextToken());
			int numO = Integer.parseInt(inputData.nextToken());

			ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i <= numV; i++) {
				aList.add(new ArrayList<Integer>());
			}

			long[][] cost = new long[numV + 1][numV + 1];
			for (long[] e : cost) {
				Arrays.fill(e, Integer.MAX_VALUE);
			}
			for (int cV = 1; cV <= numV; cV++) {
				cost[cV][cV] = 0;
				aList.get(cV).add(cV);
			}

			for (int i = 0; i < numE; i++) {
				inputData = new StringTokenizer(reader.readLine());
				int a = Integer.parseInt(inputData.nextToken());
				int b = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken());

				if (cost[a][b] >= Integer.MAX_VALUE) {
					aList.get(a).add(b);
					aList.get(b).add(a);
					cost[a][b] = c;
					cost[b][a] = c;
				} else if (c < cost[a][b]) {
					cost[a][b] = c;
					cost[b][a] = c;
				}
			}

			int[] start = new int[numO + 1];
			int[] end = new int[numO + 1];
			start[0] = end[0] = 1;

			for (int i = 1; i <= numO; i++) {
				inputData = new StringTokenizer(reader.readLine());
				start[i] = Integer.parseInt(inputData.nextToken());
				end[i] = Integer.parseInt(inputData.nextToken());
			}

			// Floyd Warshall
			for (int inter = 1; inter <= numV; inter++) {
				for (int startV = 1; startV <= numV; startV++) {
					for (int endV = 1; endV <= numV; endV++) {
						long newCost = cost[startV][inter] + cost[inter][endV];
						if (newCost < cost[startV][endV]) {
							cost[startV][endV] = newCost;
						}
					}
				}
			}

			long[][][] dp = new long[numO + 1][3][2];
			for (long[][] i : dp) {
				for (long[] j : i) {
					Arrays.fill(j, Integer.MAX_VALUE);
				}
			}

			PriorityQueue<State> queue = new PriorityQueue<State>();
			queue.add(new State(0, 0, DROPOFF, 0));

			while (!queue.isEmpty()) {
				State cS = queue.remove();
				if (cS.lFinished == numO) {
					break;
				}

				int cVertex = cS.status == PICKUP ? start[cS.lFinished + cS.nCarying] : end[cS.lFinished];
				int nPickup = cS.lFinished + cS.nCarying + 1;

				if (cS.nCarying < 2 && nPickup <= numO) {
					if (cS.cost + cost[cVertex][start[nPickup]] < dp[cS.lFinished][cS.nCarying + 1][PICKUP]) {
						dp[cS.lFinished][cS.nCarying + 1][PICKUP] = cS.cost + cost[cVertex][start[nPickup]];

						State nextState = new State(cS.lFinished, cS.nCarying + 1, PICKUP,
								cS.cost + cost[cVertex][start[nPickup]]);
						queue.add(nextState);
					}
				}

				if (cS.nCarying > 0) {
					if (cS.cost
							+ cost[cVertex][end[cS.lFinished + 1]] < dp[cS.lFinished + 1][cS.nCarying - 1][DROPOFF]) {
						dp[cS.lFinished + 1][cS.nCarying - 1][DROPOFF] = cS.cost + cost[cVertex][end[cS.lFinished + 1]];

						State nextState = new State(cS.lFinished + 1, cS.nCarying - 1, DROPOFF,
								cS.cost + cost[cVertex][end[cS.lFinished + 1]]);
						queue.add(nextState);
					}
				}
			}
			long ans = dp[numO][0][DROPOFF];
			if (ans < Integer.MAX_VALUE) {
				printer.println("Case #" + cT + ": " + ans);
			} else {
				printer.println("Case #" + cT + ": -1");
			}
		}
		reader.close();
		printer.close();
	}

	final int PICKUP = 0;
	final int DROPOFF = 1;

	class State implements Comparable<State> {
		int lFinished;
		int nCarying;
		int status;
		long cost;

		State(int eStart, int eEnd, int status, long cost) {
			this.lFinished = eStart;
			this.nCarying = eEnd;
			this.status = status;
			this.cost = cost;
		}

		public int compareTo(State o) {
			return cost < o.cost ? -1 : (cost == o.cost ? 0 : 1);
		}
	}

}
