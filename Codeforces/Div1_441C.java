import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div1_441C {

	static int[] ePt;
	static int[] sInd;
	static int[] nxt;

	static int nE = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nW = Integer.parseInt(inputData.nextToken());
		int nL = Integer.parseInt(inputData.nextToken());

		int[][] words = new int[nW][];
		for (int i = 0; i < nW; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int cL = Integer.parseInt(inputData.nextToken());
			words[i] = new int[cL];
			for (int j = 0; j < cL; j++) {
				words[i][j] = Integer.parseInt(inputData.nextToken()) - 1;
			}
		}

		ePt = new int[200_000];
		sInd = new int[200_000];
		Arrays.fill(sInd, -1);
		nxt = new int[200_000];
		Arrays.fill(nxt, -1);

		for (int i = 0; i + 1 < nW; i++) {
			boolean diff = false;
			for (int j = 0; j < Math.min(words[i].length, words[i + 1].length); j++) {
				if (words[i][j] > words[i + 1][j]) {
					addEdge(words[i][j], 100_000 + words[i][j]);

					addEdge(100_000 + words[i + 1][j], words[i + 1][j]);
					diff = true;
					break;
				} else if (words[i][j] < words[i + 1][j]) {
					addEdge(100_000 + words[i + 1][j], 100_000 + words[i][j]);

					addEdge(words[i][j], words[i + 1][j]);
					diff = true;
					break;
				}
			}
			if (!diff && words[i].length > words[i + 1].length) {
				System.out.println("No");
				return;
			}
		}

		findSCCs();

		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		printer.println("Yes");
		int cnt = 0;
		for (int i = 0; i < nL; i++) {
			if (state[i] == 1) {
				cnt++;
			}
		}
		printer.println(cnt);
		for (int i = 0; i < nL; i++) {
			if (state[i] == 1) {
				printer.print(i + 1 + " ");
			}
		}
		printer.println();
		printer.close();
	}

	static void addEdge(int sV, int eV) {
		ePt[nE] = eV;
		nxt[nE] = sInd[sV];
		sInd[sV] = nE++;
	}

	static int cCnt = 0;
	static int[] disc; // cCnt at time of visitation
	static int[] low; // vertex with the least disc reachable using at most one back edge

	static ArrayDeque<Integer> stack = new ArrayDeque<>();

	static int[] cID;

	static int[] state;

	static void findSCCs() {
		disc = new int[200_000];
		Arrays.fill(disc, -1);
		low = new int[200_000];
		cID = new int[200_000];
		Arrays.fill(cID, -1);
		state = new int[100_000];
		Arrays.fill(state, -1);

		for (int i = 0; i < 200_000; i++) {
			if (disc[i] == -1) {
				dfs(i);
			}
		}
	}

	static void dfs(int cV) {
		disc[cV] = low[cV] = cCnt++;
		stack.push(cV);

		int aI = sInd[cV];
		while (aI != -1) {
			int aV = ePt[aI];
			if (disc[aV] == -1) { // tree edge
				dfs(aV);
				if (low[aV] < low[cV]) {
					low[cV] = low[aV];
				}
			} else if (disc[aV] < low[cV]) { // back edge, forward edge, or cross edge; only vertices in stack have disc
				low[cV] = disc[aV];
			}
			aI = nxt[aI];
		}

		if (low[cV] == disc[cV]) {
			while (stack.peek() != cV) {
				int nV = stack.pop();
				cID[nV] = cV;
				if ((nV < 100_000 ? cID[nV + 100_000] : cID[nV - 100_000]) == cV) {
					System.out.println("No");
					System.exit(0);
				}
			}
			int nV = stack.pop();
			cID[nV] = cV;
			if ((nV < 100_000 ? cID[nV + 100_000] : cID[nV - 100_000]) == cV) {
				System.out.println("No");
				System.exit(0);
			}
		}

		if (cV < 100_000) {
			if (state[cV] == -1) {
				state[cV] = 0;
			}
		} else {
			if (state[cV - 100_000] == -1) {
				state[cV - 100_000] = 1;
			}
		}

		disc[cV] = Integer.MAX_VALUE; // so that cross edges will not interfere
	}
}