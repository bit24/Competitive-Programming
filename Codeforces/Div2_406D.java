import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_406D {

	static int numP;

	public static void main(String[] args) throws IOException {
		new Div2_406D().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numP = Integer.parseInt(inputData.nextToken());
		int numI = Integer.parseInt(inputData.nextToken());
		int st = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i <= 8 * numP; i++) {
			aList.add(new ArrayList<Integer>());
			eCostList.add(new ArrayList<Integer>());
		}
		uTree = new int[4 * numP];
		lTree = new int[4 * numP];
		nFree = numP + 1;
		buildUDTree(1, 1, numP);
		
		while (numI-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			String nxt = inputData.nextToken();
			if (nxt.equals("1")) {
				int a = Integer.parseInt(inputData.nextToken());
				int b = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken());
				aList.get(a).add(b);
				eCostList.get(a).add(c);
			} else if (nxt.equals("2")) {
				int a = Integer.parseInt(inputData.nextToken());
				int l = Integer.parseInt(inputData.nextToken());
				int r = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken());
				addUE(1, 1, numP, l, r, a, c);
			} else {
				int a = Integer.parseInt(inputData.nextToken());
				int l = Integer.parseInt(inputData.nextToken());
				int r = Integer.parseInt(inputData.nextToken());
				int c = Integer.parseInt(inputData.nextToken());
				addLE(1, 1, numP, l, r, a, c);
			}
		}

		dist = new long[numP * 8];

		Arrays.fill(dist, Long.MAX_VALUE / 8);
		dist[st] = 0;

		PriorityQueue<State> sQueue = new PriorityQueue<State>();
		sQueue.add(new State(st, 0));

		while (!sQueue.isEmpty()) {
			State cState = sQueue.remove();
			if (dist[cState.pos] < cState.cost) {
				continue;
			}

			for (int aI = 0; aI < aList.get(cState.pos).size(); aI++) {
				int cAdj = aList.get(cState.pos).get(aI);
				long tCost = cState.cost + eCostList.get(cState.pos).get(aI);

				if (tCost < dist[cAdj]) {
					dist[cAdj] = tCost;
					sQueue.add(new State(cAdj, tCost));
				}
			}
		}

		for (int i = 1; i <= numP; i++) {
			if (dist[i] < Long.MAX_VALUE / 8) {
				printer.print(dist[i] + " ");
			} else {
				printer.print("-1 ");
			}
		}
		printer.println();
		printer.close();
	}

	class State implements Comparable<State> {
		int pos;
		long cost;

		State(int pos, long cost) {
			this.pos = pos;
			this.cost = cost;
		}

		public int compareTo(State o) {
			long y = o.cost;
			return (cost < y) ? -1 : ((cost == y) ? 0 : 1);
		}
	}

	static long[] dist;

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();;
	static ArrayList<ArrayList<Integer>> eCostList = new ArrayList<ArrayList<Integer>>();;

	static int[] uTree;
	static int[] lTree;
	static int nFree;

	static void buildUDTree(int nI, int cL, int cR) {
		if (cL == cR) {
			uTree[nI] = cL;
			lTree[nI] = cL;
		} else {
			uTree[nI] = nFree++;
			lTree[nI] = nFree++;
			int mid = (cL + cR) / 2;
			buildUDTree(nI * 2, cL, mid);
			buildUDTree(nI * 2 + 1, mid + 1, cR);

			aList.get(uTree[nI]).add(uTree[nI * 2]);
			aList.get(uTree[nI]).add(uTree[nI * 2 + 1]);
			eCostList.get(uTree[nI]).add(0);
			eCostList.get(uTree[nI]).add(0);

			aList.get(lTree[nI * 2]).add(lTree[nI]);
			aList.get(lTree[nI * 2 + 1]).add(lTree[nI]);
			eCostList.get(lTree[nI * 2]).add(0);
			eCostList.get(lTree[nI * 2 + 1]).add(0);
		}
	}

	static void addUE(int nI, int cL, int cR, int uL, int uR, int from, int cost) {
		if (uR < cL || cR < uL) {
			return;
		}
		if (uL <= cL && cR <= uR) {
			aList.get(from).add(uTree[nI]);
			eCostList.get(from).add(cost);
			return;
		}
		int mid = (cL + cR) / 2;
		addUE(nI * 2, cL, mid, uL, uR, from, cost);
		addUE(nI * 2 + 1, mid + 1, cR, uL, uR, from, cost);
	}

	static void addLE(int nI, int cL, int cR, int uL, int uR, int to, int cost) {
		if (uR < cL || cR < uL) {
			return;
		}
		if (uL <= cL && cR <= uR) {
			aList.get(lTree[nI]).add(to);
			eCostList.get(lTree[nI]).add(cost);
			return;
		}
		int mid = (cL + cR) / 2;
		addLE(nI * 2, cL, mid, uL, uR, to, cost);
		addLE(nI * 2 + 1, mid + 1, cR, uL, uR, to, cost);
	}

}
