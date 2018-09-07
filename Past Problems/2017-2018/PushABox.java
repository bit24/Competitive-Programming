import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class PushABox {

	public static void main(String[] args) throws IOException {
		new PushABox().execute();
	}

	State sCow = null;
	State sBox = null;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("pushabox.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("pushabox.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		nR = Integer.parseInt(inputData.nextToken());
		nC = Integer.parseInt(inputData.nextToken());
		int nQ = Integer.parseInt(inputData.nextToken());

		block = new boolean[nR + 2][nC + 2];

		for (int i = 1; i <= nR; i++) {
			String nLine = reader.readLine();
			for (int j = 1; j <= nC; j++) {
				char cChar = nLine.charAt(j - 1);
				if (cChar == 'A') {
					sCow = new State(i, j, -1);
				}
				if (cChar == 'B') {
					sBox = new State(i, j, -1);
				}
				if (cChar == '#') {
					block[i][j] = true;
				}
			}
		}
		for (int i = 0; i <= nC + 1; i++) {
			block[0][i] = block[nR + 1][i] = true;
		}
		for (int i = 0; i <= nR + 1; i++) {
			block[i][0] = block[i][nC + 1] = true;
		}

		findBCCs();

		block[sBox.r][sBox.c] = true;
		initBFS();
		block[sBox.r][sBox.c] = false;

		mainBFS();

		while (nQ-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int r = Integer.parseInt(inputData.nextToken());
			int c = Integer.parseInt(inputData.nextToken());
			if (visited[0][r][c] || visited[1][r][c] || visited[2][r][c] || visited[3][r][c]) {
				printer.println("YES");
			} else if (sBox.r == r && sBox.c == c) {
				printer.println("YES");
			} else {
				printer.println("NO");
			}
		}
		printer.close();
	}

	boolean[][][] visited;

	void mainBFS() {
		visited = new boolean[4][nR + 2][nC + 2];
		ArrayDeque<State> sQueue = new ArrayDeque<>();

		for (int d = 0; d < 4; d++) {
			int aR = sBox.r + rMod[d];
			int aC = sBox.c + cMod[d];
			if (iVisited[aR][aC]) {
				visited[d][sBox.r][sBox.c] = true;
				sQueue.add(new State(sBox.r, sBox.c, d));
			}
		}

		while (!sQueue.isEmpty()) {
			State cState = sQueue.remove();
			// move along movement direction
			int mD = (cState.d + 2) & 3;
			int nR = cState.r + rMod[mD];
			int nC = cState.c + cMod[mD];

			if (!block[nR][nC] && !visited[cState.d][nR][nC]) {
				visited[cState.d][nR][nC] = true;
				sQueue.add(new State(nR, nC, cState.d));
			}

			for (int nD = 0; nD < 4; nD++) {
				if (nD == cState.d) {
					continue;
				}

				int nCR = cState.r + rMod[nD];
				int nCC = cState.c + cMod[nD];
				if (block[nCR][nCC]) {
					continue;
				}
				if (!sBCC[cState.d][nD][cState.r][cState.c]) {
					continue;
				}
				if (!visited[nD][cState.r][cState.c]) {
					visited[nD][cState.r][cState.c] = true;
					sQueue.add(new State(cState.r, cState.c, nD));
				}
			}
		}
	}

	boolean[][] iVisited;

	void initBFS() {
		iVisited = new boolean[nR + 2][nC + 2];
		iVisited[sCow.r][sCow.c] = true;

		ArrayDeque<State> sQueue = new ArrayDeque<>();
		sQueue.add(sCow);

		while (!sQueue.isEmpty()) {
			State cState = sQueue.remove();

			for (int d = 0; d < 4; d++) {
				int nR = cState.r + rMod[d];
				int nC = cState.c + cMod[d];
				if (block[nR][nC] || iVisited[nR][nC]) {
					continue;
				}
				iVisited[nR][nC] = true;
				sQueue.add(new State(nR, nC, -1));
			}
		}
	}

	static final int[] rMod = new int[] { -1, 0, 1, 0 };
	static final int[] cMod = new int[] { 0, -1, 0, 1 };

	boolean[][] block;

	int nR;
	int nC;

	int nV;

	int cCnt = 0;
	int[][] disc;
	int[][] low;

	boolean[][][][] sBCC;

	void findBCCs() {
		disc = new int[nR + 2][nC + 2];
		low = new int[nR + 2][nC + 2];
		sBCC = new boolean[4][4][nR + 2][nC + 2];

		for (int i = 1; i <= nR; i++) {
			for (int j = 1; j <= nC; j++) {
				disc[i][j] = -1;
			}
		}

		for (int i = 1; i <= nR; i++) {
			for (int j = 1; j <= nC; j++) {
				if (!block[i][j]) {
					if (disc[i][j] == -1) {
						dfs(i, j, -1);
					}
				}
			}
		}
	}

	void dfs(int cR, int cC, int pD) {
		disc[cR][cC] = low[cR][cC] = cCnt++;

		boolean[] pReach = new boolean[4];
		sUpd(cR, cC, pReach);

		for (int d = 0; d < 4; d++) {
			int nR = cR + rMod[d];
			int nC = cC + cMod[d];

			if (block[nR][nC]) {
				continue;
			}

			if (disc[nR][nC] == -1) { // tree edge
				dfs(nR, nC, (d + 2) & 3);
				if (low[nR][nC] < low[cR][cC]) {
					low[cR][cC] = low[nR][nC];
				}

				if (pD != -1) {
					if (low[nR][nC] < disc[cR][cC]) {
						for (int oD = 0; oD < 4; oD++) {
							int oNR = cR + rMod[oD];
							int oNC = cC + cMod[oD];
							if (block[oNR][oNC] || disc[oNR][oNC] == -1 || pReach[oD]) {
								continue;
							}	
							sBCC[pD][oD][cR][cC] = true;
							sBCC[oD][pD][cR][cC] = true;
						}
					}
				}

				sUpd(cR, cC, pReach);
			} else if (low[cR][cC] > disc[nR][nC]) { // back edge or front edge
				low[cR][cC] = disc[nR][nC];
			}
		}
	}

	void sUpd(int cR, int cC, boolean[] pReach) {
		for (int d = 0; d < 4; d++) {
			int nR = cR + rMod[d];
			int nC = cC + cMod[d];

			if (block[nR][nC] || disc[nR][nC] == -1 || pReach[d]) {
				continue;
			}

			pReach[d] = true;

			for (int oD = d + 1; oD < 4; oD++) {
				int oNR = cR + rMod[oD];
				int oNC = cC + cMod[oD];
				if (block[oNR][oNC] || disc[oNR][oNC] == -1 || pReach[oD]) {
					continue;
				}

				sBCC[d][oD][cR][cC] = true;
				sBCC[oD][d][cR][cC] = true;
			}
		}
	}

	class State {
		int r, c, d;

		State(int r, int c, int d) {
			this.r = r;
			this.c = c;
			this.d = d;
		}
	}
}