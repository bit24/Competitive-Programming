import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class IslandTravels {

	static int nIsl;

	static int maxX;
	static int maxY;

	static int[][] terrain;

	static final int LAND = 0;
	static final int SHALLOW = 1;
	static final int DEEP = 2;

	static boolean[][] visited;

	static final int[] xMod = { 0, -1, 1, 0 };
	static final int[] yMod = { 1, 0, 0, -1 };

	static int[][] island;

	static int distance[][];

	static ArrayList<ArrayList<Coordinate>> islGroups = new ArrayList<ArrayList<Coordinate>>();

	public static void main(String[] args) throws IOException {
		new IslandTravels().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("island.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("island.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		maxY = Integer.parseInt(inputData.nextToken());
		maxX = Integer.parseInt(inputData.nextToken());

		terrain = new int[maxX][maxY];
		for (int y = maxY - 1; y >= 0; y--) {
			String cRow = reader.readLine();

			for (int x = 0; x < maxX; x++) {
				char current = cRow.charAt(x);
				switch (current) {
					case 'X':
						terrain[x][y] = LAND;
						break;
					case 'S':
						terrain[x][y] = SHALLOW;
						break;
					case '.':
						terrain[x][y] = DEEP;
						break;
				}
			}
		}
		reader.close();

		visited = new boolean[maxX][maxY];

		island = new int[maxX][maxY];

		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				if ((!visited[i][j]) && terrain[i][j] == LAND) {
					islGroups.add(dfs(i, j, nIsl++));
				}
			}
		}

		distance = new int[nIsl][nIsl];
		for (int i = 0; i < nIsl; i++) {
			for (int j = 0; j < nIsl; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
		}

		findDistance();

		for (int i = 0; i < nIsl; i++) {
			distance[i][i] = Integer.MAX_VALUE;
		}

		TSPSOLVER answerMachine = new TSPSOLVER(nIsl, distance);
		printer.println(answerMachine.getAnswer());
		printer.close();
	}

	ArrayList<Coordinate> dfs(int x, int y, int cIsl) {
		ArrayList<Coordinate> adjacent = new ArrayList<Coordinate>();
		dfs(x, y, cIsl, adjacent);
		return adjacent;
	}

	// prerequisite: visited has to be up to date
	// adjacent has to be for this island group only
	// isAdjacentMarked has to be for this island group only
	void dfs(int x, int y, int cIsl, ArrayList<Coordinate> group) {

		island[x][y] = cIsl;
		visited[x][y] = true;
		group.add(new Coordinate(x, y));

		for (int currentDirection = 0; currentDirection < 4; currentDirection++) {
			int nX = x + xMod[currentDirection];
			int nY = y + yMod[currentDirection];
			if (0 <= nX && nX < maxX && 0 <= nY && nY < maxY) {
				if (terrain[nX][nY] == LAND && visited[nX][nY] == false) {
					dfs(nX, nY, cIsl, group);
				}
			}
		}
	}

	void findDistance() {
		for (int sIsl = 0; sIsl < nIsl; sIsl++) {
			visited = new boolean[maxX][maxY];
			int[][] tDist = new int[maxX][maxY];

			LinkedList<Coordinate> queue = new LinkedList<Coordinate>();
			for (Coordinate sIslMem : islGroups.get(sIsl)) {
				queue.add(sIslMem);
				visited[sIslMem.x][sIslMem.y] = true;
			}

			while (!queue.isEmpty()) {
				Coordinate cV = queue.remove();
				int x = cV.x;
				int y = cV.y;
				for (int cDir = 0; cDir < 4; cDir++) {
					int nX = x + xMod[cDir];
					int nY = y + yMod[cDir];
					if (0 <= nX && nX < maxX && 0 <= nY && nY < maxY && !visited[nX][nY]) {
						if (terrain[nX][nY] == LAND) {
							int cIsl = island[nX][nY];

							distance[sIsl][cIsl] = tDist[x][y];

							for (Coordinate cIslMem : islGroups.get(cIsl)) {
								int cX = cIslMem.x;
								int cY = cIslMem.y;

								tDist[cX][cY] = tDist[x][y];
								queue.addFirst(new Coordinate(cX, cY));
								visited[cX][cY] = true;
							}
						} else if (terrain[nX][nY] == SHALLOW) {
							tDist[nX][nY] = tDist[x][y] + 1;
							queue.add(new Coordinate(nX, nY));
						}
						visited[nX][nY] = true;
					}
				}
			}
		}
	}

	class Coordinate {
		int x;
		int y;

		Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class TSPSOLVER {
		int[][] tCost;
		static final int UNDECIDED = -1;
		int numV;
		int[][] eCost;

		TSPSOLVER(int nV, int[][] cost) {
			this.numV = nV;
			tCost = new int[nV][1 << nV];
			this.eCost = cost;
			for (int i = 0; i < nV; i++) {
				for (int j = 0; j < (1 << nV); j++) {
					tCost[i][j] = UNDECIDED;
				}
			}
		}

		int getAnswer() {
			int[][] tCost = new int[1 << numV][numV];

			for (int i = 0; i < (1 << numV); i++) {
				Arrays.fill(tCost[i], Integer.MAX_VALUE);
			}

			for (int i = 0; i < numV; i++) {
				tCost[1 << i][i] = 0;
			}

			for (int bSet = 1; bSet < (1 << numV); bSet++) {
				for (int eV = 0; eV < numV; eV++) {
					if ((bSet & (1 << eV)) == 0) {
						continue;
					}

					for (int fV = 0; fV < numV; fV++) {
						if ((bSet & (1 << fV)) != 0) {
							continue;
						}
						int nBSet = bSet | (1 << fV);
						if (eCost[eV][fV] != Integer.MAX_VALUE && tCost[bSet][eV] + eCost[eV][fV] < tCost[nBSet][fV]) {
							tCost[nBSet][fV] = tCost[bSet][eV] + eCost[eV][fV];
						}
					}
				}
			}
			int mAns = Integer.MAX_VALUE;
			int fSet = (1 << numV) - 1;
			for (int i = 0; i < numV; i++) {
				mAns = Math.min(mAns, tCost[fSet][i]);
			}
			return mAns;
		}
	}
}