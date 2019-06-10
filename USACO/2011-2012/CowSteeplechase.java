import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CowSteeplechase {

	static ArrayList<Integer>[] aList;
	static int[][] res;

	static int src;
	static int snk;

	static int tFlow;

	static int nV;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("steeple.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("steeple.out")));
		int nI = Integer.parseInt(reader.readLine());

		boolean[] vert = new boolean[nI];
		int[] maj = new int[nI];
		int[] min1 = new int[nI];
		int[] min2 = new int[nI];

		for (int i = 0; i < nI; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int x1 = Integer.parseInt(inputData.nextToken());
			int y1 = Integer.parseInt(inputData.nextToken());
			int x2 = Integer.parseInt(inputData.nextToken());
			int y2 = Integer.parseInt(inputData.nextToken());
			if (x1 == x2) {
				vert[i] = true;
				maj[i] = x1;
				min1[i] = Math.min(y1, y2);
				min2[i] = Math.max(y1, y2);
			} else {
				vert[i] = false;
				maj[i] = y1;
				min1[i] = Math.min(x1, x2);
				min2[i] = Math.max(x1, x2);
			}
		}
		reader.close();

		src = nI;
		snk = nI + 1;
		nV = nI + 2;

		aList = new ArrayList[nV];
		for (int i = 0; i < nV; i++) {
			aList[i] = new ArrayList<Integer>();
		}

		res = new int[nV][nV];

		for (int i = 0; i < nI; i++) {
			if (vert[i]) {
				aList[src].add(i);
				aList[i].add(src);
				res[src][i] = 1;
			} else {
				aList[i].add(snk);
				aList[snk].add(i);
				res[i][snk] = 1;
			}
		}

		for (int i = 0; i < nI; i++) {
			for (int j = i + 1; j < nI; j++) {
				if (vert[i] != vert[j] && min1[i] <= maj[j] && maj[j] <= min2[i] && min1[j] <= maj[i]
						&& maj[i] <= min2[j]) {
					aList[i].add(j);
					aList[j].add(i);
					if(vert[i]) {
						res[i][j] = 1;
					}
					else {
						res[j][i] = 1;
					}
				}
			}
		}

		computeMFRes();
		printer.println(nI - tFlow);
		printer.close();
	}

	static int[] FPTS() {
		int[] prev = new int[nV];
		Arrays.fill(prev, -2);
		prev[src] = -1;

		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(src);

		while (!queue.isEmpty()) {
			int cV = queue.remove();
			if (cV == snk) {
				return prev;
			}

			for (int adj : aList[cV]) {
				if (prev[adj] == -2 && res[cV][adj] > 0) {
					prev[adj] = cV;
					queue.add(adj);
				}
			}
		}

		return null;
	}

	static void computeMFRes() {
		while (true) {
			int[] prev = FPTS();
			if (prev == null) {
				break;
			}

			int cV = snk;
			int bNeck = Integer.MAX_VALUE;
			while (cV != src) {
				int pV = prev[cV];
				bNeck = Math.min(bNeck, res[pV][cV]);
				cV = pV;
			}
			
			cV = snk;
			while (cV != src) {
				int pV = prev[cV];
				res[pV][cV] -= bNeck;
				res[cV][pV] += bNeck;
				cV = pV;
			}
			tFlow += bNeck;
		}
	}

}
