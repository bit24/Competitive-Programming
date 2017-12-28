import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class PushABox {

	public static void main(String[] args) throws IOException {
		long sTime = System.currentTimeMillis();
		new PushABox().execute();
		System.out.println(System.currentTimeMillis() - sTime);
	}

	static final int[] rM = new int[] { -1, 0, 1, 0 };
	static final int[] cM = new int[] { 0, 1, 0, -1 };

	int numR;
	int numC;

	char[][] grid;

	int[][] sComp;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("pushabox.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("pushabox.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numR = Integer.parseInt(inputData.nextToken());
		numC = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());
		grid = new char[numR + 2][numC + 2];

		int c_sR = 0;
		int c_sC = 0;
		int b_sR = 0;
		int b_sC = 0;
		for (int i = 0; i < numR; i++) {
			String nLine = reader.readLine();
			for (int j = 0; j < numC; j++) {
				char cChar = nLine.charAt(j);
				grid[i + 1][j + 1] = cChar;
				if (cChar == 'A') {
					grid[i + 1][j + 1] = '.';
					c_sR = i + 1;
					c_sC = j + 1;
				}
				if (cChar == 'B') {
					grid[i + 1][j + 1] = '.';
					b_sR = i + 1;
					b_sC = j + 1;
				}
			}
		}

		for (int i = 0; i < numR + 2; i++) {
			grid[i][0] = grid[i][numC + 1] = '#';
		}
		for (int i = 0; i < numC + 2; i++) {
			grid[0][i] = grid[numR + 1][i] = '#';
		}

		long sTime = System.currentTimeMillis();
		findBCCs();

		System.out.println(System.currentTimeMillis() - sTime);

		sComp = new int[numR + 2][numC + 2];

		for (ArrayList<Pos> cBCC : BCCs) {
			for (Pos cPos : cBCC) {
				for (int oDir = 0; oDir < 4; oDir++) {
					int bR = cPos.r - rM[oDir];
					int bC = cPos.c - cM[oDir];
					for (int nDir = 0; nDir < 4; nDir++) {
						int nR = bR + rM[nDir];
						int nC = bC + cM[nDir];
						if (Collections.binarySearch(cBCC, new Pos(nR, nC)) >= 0) {
							sComp[bR][bC] |= 1 << (4 * oDir + nDir);
						}
					}
				}
			}
		}

		boolean[][][] rble = new boolean[numR + 2][numC + 2][4];

		char[][] gCopy = copy(grid);
		gCopy[b_sR][b_sC] = '#';
		dfs(c_sR, c_sC, gCopy);

		ArrayDeque<State> queue = new ArrayDeque<State>();
		for (int dir = 0; dir < 4; dir++) {
			int nR = b_sR + rM[dir];
			int nC = b_sC + cM[dir];
			if (gCopy[nR][nC] == 'R') {
				rble[b_sR][b_sC][dir] = true;
				queue.add(new State(b_sR, b_sC, dir));
			}
		}

		while (!queue.isEmpty()) {
			State cS = queue.remove();
			for (int nDir = 0; nDir < 4; nDir++) {
				if (!rble[cS.bR][cS.bC][nDir] && ((sComp[cS.bR][cS.bC] & (1 << (cS.cDir * 4 + nDir))) > 0)) {
					rble[cS.bR][cS.bC][nDir] = true;
					queue.add(new State(cS.bR, cS.bC, nDir));
				}
			}
			int oDir = (cS.cDir + 2) % 4;
			int nR = cS.bR + rM[oDir];
			int nC = cS.bC + cM[oDir];
			if (grid[nR][nC] == '.' && !rble[nR][nC][cS.cDir]) {
				rble[nR][nC][cS.cDir] = true;
				queue.add(new State(nR, nC, cS.cDir));
			}
		}

		qLoop:
		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int qR = Integer.parseInt(inputData.nextToken());
			int qC = Integer.parseInt(inputData.nextToken());
			if (qR == b_sR && qC == b_sC) {
				printer.println("YES");
				continue qLoop;
			}
			for (int cDir = 0; cDir < 4; cDir++) {
				if (rble[qR][qC][cDir]) {
					printer.println("YES");
					continue qLoop;
				}
			}
			printer.println("NO");
		}
		reader.close();
		printer.close();
	}

	char[][] copy(char[][] orig) {
		char[][] copy = new char[numR + 2][numC + 2];
		for (int i = 0; i < numR + 2; i++) {
			for (int j = 0; j < numC + 2; j++) {
				copy[i][j] = orig[i][j];
			}
		}
		return copy;
	}

	void dfs(int cR, int cC, char[][] grid) {
		grid[cR][cC] = 'R';

		for (int dir = 0; dir < 4; dir++) {
			int nR = cR + rM[dir];
			int nC = cC + cM[dir];
			if (grid[nR][nC] == '.') {
				dfs(nR, nC, grid);
			}
		}
	}

	class State {
		int bR;
		int bC;
		int cDir;

		State(int bR, int bC, int cDir) {
			this.bR = bR;
			this.bC = bC;
			this.cDir = cDir;
		}
	}

	int nV;

	int[][] disc;
	int[][] low;
	int cCnt = 0;

	ArrayDeque<Edge> stack = new ArrayDeque<Edge>();

	ArrayList<ArrayList<Pos>> BCCs = new ArrayList<>();

	void findBCCs() {
		disc = new int[numR + 2][numC + 2];
		low = new int[numR + 2][numC + 2];

		for (int i = 1; i <= numR; i++) {
			for (int j = 1; j <= numC; j++) {
				disc[i][j] = -1;
			}
		}

		for (int i = 1; i <= numR; i++) {
			for (int j = 1; j <= numC; j++) {
				if (disc[i][j] == -1) {
					dfs(new Pos(i, j), null);
					if (!stack.isEmpty()) { // root is part of another biconnected component
						ArrayList<Pos> cBCC = new ArrayList<>();
						BCCs.add(cBCC);
						while (!stack.isEmpty()) {
							Edge e = stack.pop();
							cBCC.add(e.p1);
							cBCC.add(e.p2);
						}
					}
				}
			}
		}

		for (int i = 0; i < BCCs.size(); i++) {
			ArrayList<Pos> cBCC = BCCs.get(i);
			Collections.sort(cBCC);
			ArrayList<Pos> pBCC = new ArrayList<>();
			pBCC.add(cBCC.get(0));
			for (Pos j : cBCC) {
				if (!pBCC.get(pBCC.size() - 1).equals(j)) {
					pBCC.add(j);
				}
			}
			BCCs.set(i, pBCC);
		}
	}

	void dfs(Pos cP, Pos pP) {
		int cR = cP.r;
		int cC = cP.c;
		disc[cR][cC] = low[cR][cC] = cCnt++;

		int nChldn = 0;

		for (int cD = 0; cD < 4; cD++) {
			int aR = cR + rM[cD];
			int aC = cC + cM[cD];
			if (grid[aR][aC] == '#') {
				continue;
			}

			Pos aP = new Pos(aR, aC);
			if (disc[aR][aC] == -1) {
				nChldn++;
				Edge cEdge = new Edge(cP, aP);
				stack.push(cEdge);
				dfs(aP, cP);
				if (low[aR][aC] < low[cR][cC]) {
					low[cR][cC] = low[aR][aC];
				}

				if (pP != null ? (low[aR][aC] >= disc[cR][cC]) : nChldn > 1) {
					ArrayList<Pos> cBCC = new ArrayList<>();
					BCCs.add(cBCC);
					while (!stack.peek().equals(cEdge)) {
						Edge e = stack.pop();
						cBCC.add(e.p1);
						cBCC.add(e.p2);
					}
					Edge e = stack.pop();
					cBCC.add(e.p1);
					cBCC.add(e.p2);
				}
			} else if (low[cR][cC] > disc[aR][aC]) {
				low[cR][cC] = disc[aR][aC];
			}
		}
	}

	class Pos implements Comparable<Pos> {
		int r, c;

		Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}

		public boolean equals(Object oth) {
			return ((Pos) oth).r == r && ((Pos) oth).c == c;
		}

		public int compareTo(Pos o) {
			return r < o.r ? -1 : r == o.r ? (c < o.c ? -1 : c == o.c ? 0 : 1) : 1;
		}
		
		public String toString() {
			return "(" + r + ", " + c + ")";
		}
	}

	class Edge {
		Pos p1, p2;

		Edge(Pos v1, Pos v2) {
			this.p1 = v1;
			this.p2 = v2;
		}

		public boolean equals(Object oth) {
			return ((Edge) oth).p1.equals(p1) && ((Edge) oth).p2.equals(p2);
		}
	}
}
