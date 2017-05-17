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
import java.util.TreeSet;

public class BeamSolver {

	static final char UL = '\\';
	static final char UR = '/';
	static final char BK = '#';
	static final char B1 = '|';
	static final char B2 = '-';
	static final char EM = '.';

	static final int[] rMod = new int[] { -1, +1, 0, 0 };
	static final int[] cMod = new int[] { 0, 0, -1, +1 };

	static final int[] ULChange = new int[] { 2, 3, 0, 1 };
	static final int[] URChange = new int[] { 3, 2, 1, 0 };

	static ArrayList<Integer>[] aList;

	static int numV;

	static int cTime;
	static int[] disc;
	static int[] low;

	static ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

	static ArrayDeque<Integer> rTopOrd = new ArrayDeque<Integer>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("beam.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("beam.out")));
		int numT = Integer.parseInt(reader.readLine());

		tLoop:
		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int numR = Integer.parseInt(inputData.nextToken());
			int numC = Integer.parseInt(inputData.nextToken());
			char[][] maze = new char[numR + 2][numC + 2];
			for (int i = 0; i <= numC + 1; i++) {
				maze[0][i] = BK;
				maze[numR + 1][i] = BK;
			}
			for (int i = 1; i <= numR; i++) {
				String nLine = reader.readLine();
				maze[i][0] = BK;
				for (int j = 1; j <= numC; j++) {
					maze[i][j] = nLine.charAt(j - 1);
				}
				maze[i][numC + 1] = BK;
			}

			int sInd = 0;

			int[][][] gridOp = new int[numR + 2][numC + 2][2];
			for (int i = 0; i <= numR + 1; i++) {
				for (int j = 0; j <= numC + 1; j++) {
					for (int k = 0; k <= 1; k++) {
						gridOp[i][j][k] = -1;
					}
				}
			}

			boolean[] illegal = new boolean[200];

			for (int i = 1; i <= numR; i++) {
				for (int j = 1; j <= numC; j++) {
					if (maze[i][j] == B1 || maze[i][j] == B2) {
						oLoop:
						for (int orient = 0; orient <= 2; orient += 2, sInd++) {
							for (int dir = orient, cDir = orient; dir <= orient + 1; dir++, cDir = dir) {
								int nI = i + rMod[cDir];
								int nJ = j + cMod[cDir];
								while (maze[nI][nJ] != BK) {
									if (maze[nI][nJ] == B1 || maze[nI][nJ] == B2) {
										illegal[sInd] = true;
										continue oLoop;
									}
									if (maze[nI][nJ] == UL) {
										cDir = ULChange[cDir];
									} else if (maze[nI][nJ] == UR) {
										cDir = URChange[cDir];
									}
									nI += rMod[cDir];
									nJ += cMod[cDir];
								}
							}

							for (int dir = orient, cDir = orient; dir <= orient + 1; dir++, cDir = dir) {
								int nI = i + rMod[dir];
								int nJ = j + cMod[dir];
								while (maze[nI][nJ] != BK) {
									int[] cGridOp = gridOp[nI][nJ];
									if (maze[nI][nJ] == UL) {
										cDir = ULChange[cDir];
									} else if (maze[nI][nJ] == UR) {
										cDir = URChange[cDir];
									} else if (cGridOp[0] == -1) {
										cGridOp[0] = sInd;
									} else {
										cGridOp[1] = sInd;
									}
									nI += rMod[cDir];
									nJ += cMod[cDir];
								}
							}
						}
					}
				}
			}

			numV = sInd;

			aList = new ArrayList[numV];

			for (int i = 0; i < numV; i++) {
				aList[i] = new ArrayList<Integer>();
			}

			for (int i = 0; i < numV; i++) {
				if (illegal[i]) {
					aList[i].add(i ^ 1);
				}
			}

			for (int cR = 1; cR <= numR; cR++) {
				for (int cC = 1; cC <= numC; cC++) {
					if (maze[cR][cC] == EM) {
						int[] cGridOp = gridOp[cR][cC];
						if (cGridOp[0] != -1 && cGridOp[1] != -1) {
							aList[cGridOp[0] ^ 1].add(cGridOp[1]);
							aList[cGridOp[1] ^ 1].add(cGridOp[0]);
						} else if (cGridOp[0] == -1 && cGridOp[1] == -1) {
							printer.println("Case #" + cT + ": IMPOSSIBLE");
							continue tLoop;
						} else {
							aList[cGridOp[0] ^ 1].add(cGridOp[0]);
						}
					}
				}
			}

			if (!processSCCs()) {
				printer.println("Case #" + cT + ": IMPOSSIBLE");
				continue tLoop;
			} else {
				TreeSet<Integer> visited = new TreeSet<Integer>();
				boolean[] status = new boolean[numV / 2];
				for (int cV : rTopOrd) {
					if ((cV & 1) == 0) {
						if (visited.contains(cV + 1)) {
							status[cV / 2] = false;
						}
					} else {
						if (visited.contains(cV - 1)) {
							status[cV / 2] = true;
						}
					}

					visited.add(cV);
				}

				printer.println("Case #" + cT + ": POSSIBLE");

				int dCnt = 0;
				for (int cR = 1; cR <= numR; cR++) {
					for (int cC = 1; cC <= numC; cC++) {
						if (maze[cR][cC] != B1 && maze[cR][cC] != B2) {
							printer.print(maze[cR][cC]);
						} else {
							printer.print(status[dCnt++] ? B1 : B2);
						}
					}
					printer.println();
				}
			}
		}
		reader.close();
		printer.close();
	}

	static boolean processSCCs() {
		disc = new int[numV];
		low = new int[numV];
		Arrays.fill(disc, -1);

		stack.clear();
		rTopOrd.clear();
		for (int cV = 0; cV < numV; cV++) {
			if (disc[cV] == -1 && !dfs(cV)) {
				return false;
			}
		}
		return true;
	}

	static boolean dfs(int cV) {
		disc[cV] = low[cV] = cTime++;
		stack.push(cV);
		for (int adj : aList[cV]) {
			if (disc[adj] == -1) {
				if (!dfs(adj)) {
					return false;
				}
			}
			if (low[adj] < low[cV]) {
				low[cV] = low[adj];
			}
		}
		if (disc[cV] == low[cV]) {
			TreeSet<Integer> cComp = new TreeSet<Integer>();
			while (stack.peek() != cV) {
				low[stack.peek()] = Integer.MAX_VALUE;
				if (cComp.contains(stack.peek() ^ 1)) {
					return false;
				}
				cComp.add(stack.peek());
				rTopOrd.add(stack.pop());
			}
			low[stack.peek()] = Integer.MAX_VALUE;
			if (cComp.contains(stack.peek() ^ 1)) {
				return false;
			}
			cComp.add(stack.peek());
			rTopOrd.add(stack.pop());
		}
		return true;
	}
}
