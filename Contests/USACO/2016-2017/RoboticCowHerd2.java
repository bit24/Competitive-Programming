import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class RoboticCowHerd2 {

	static long[][] cost;

	public static void main(String[] args) throws IOException {
		new RoboticCowHerd2().execute();
	}

	static int nL;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("roboherd.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("roboherd.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nL = Integer.parseInt(inputData.nextToken());
		int nR = Integer.parseInt(inputData.nextToken());

		cost = new long[nL + 1][];
		long bCost = 0;
		for (int i = 0; i < nL; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int nI = Integer.parseInt(inputData.nextToken());
			cost[i] = new long[nI];
			for (int j = 0; j < nI; j++) {
				cost[i][j] = Integer.parseInt(inputData.nextToken());
			}
			Arrays.sort(cost[i]);
			for (int j = nI - 1; j > 0; j--) {
				cost[i][j] -= cost[i][j - 1];
			}
			bCost += cost[i][0];
		}
		reader.close();

		cost[nL] = new long[] { Integer.MAX_VALUE };

		long tCost = bCost;
		nR--;

		PriorityQueue<State> sQueue = new PriorityQueue<State>();
		sQueue.add(new State(init(0, nL - 1), bCost));

		while (nR > 0) {
			nR--;
			State cur = sQueue.remove();
			Node cCfg = cur.cfg;
			tCost += cur.fCost();
			Upg minU = cCfg.minU;

			if (minU.ind + 1 < cost[minU.loc].length) {
				Node nCfg = update(cCfg, 0, nL - 1, minU.loc, minU.ind + 1);
				sQueue.add(new State(nCfg, cur.fCost()));
			} else {
				Node nCfg = update(cCfg, 0, nL - 1, minU.loc, -1);
				sQueue.add(new State(nCfg, cur.fCost()));
			}
			Node nCfg = update(cCfg, 0, nL - 1, minU.loc, -1);
			if (nCfg.minU.loc != nL) {
				sQueue.add(new State(nCfg, cur.pCost));
			}
		}
		printer.println(tCost);
		printer.close();
	}

	Node init(int cL, int cR) {
		Node cN = new Node();
		if (cL == cR) {
			if (cost[cL].length == 1) {
				cN.minU = new Upg(nL, 0);
			} else {
				cN.minU = new Upg(cL, 1);
			}
		} else {
			int mid = (cL + cR) / 2;
			cN.left = init(cL, mid);
			cN.right = init(mid + 1, cR);
			cN.minU = min(cN.left.minU, cN.right.minU);
		}
		return cN;
	}

	Node update(Node cN, int cL, int cR, int uI, int uV) {
		Node ret = new Node();
		if (cL == cR) {
			if (uV == -1) {
				ret.minU = new Upg(nL, 0);
			} else {
				ret.minU = new Upg(uI, uV);
			}
		} else {
			int mid = (cL + cR) / 2;
			if (uI <= mid) {
				ret.left = update(cN.left, cL, mid, uI, uV);
				ret.right = cN.right;
			} else {
				ret.left = cN.left;
				ret.right = update(cN.right, mid + 1, cR, uI, uV);
			}
			ret.minU = min(ret.left.minU, ret.right.minU);
		}
		return ret;
	}

	class State implements Comparable<State> {
		Node cfg;
		long pCost;

		long fCost() {
			Upg minU = cfg.minU;
			return pCost + cost[minU.loc][minU.ind];
		}

		public int compareTo(State o) {
			return Long.compare(fCost(), o.fCost());
		}

		State(Node cfg, long pCost) {
			this.cfg = cfg;
			this.pCost = pCost;
		}
	}

	class Node {
		Node left;
		Node right;
		Upg minU;
	}

	class Upg {
		int loc;
		int ind;

		Upg(int loc, int ind) {
			this.loc = loc;
			this.ind = ind;
		}
	}

	Upg min(Upg a, Upg b) {
		return cost[a.loc][a.ind] < cost[b.loc][b.ind] ? a : b;
	}

}
