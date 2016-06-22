import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class IslandTravels {

	static int numIslands;
	
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
	static IslandTravels solver = new IslandTravels();

	static int distance[][];
	
	static ArrayList<ArrayList<Coordinate>> islandGroups = new ArrayList<ArrayList<Coordinate>>();

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		maxY = Integer.parseInt(inputData.nextToken());
		maxX = Integer.parseInt(inputData.nextToken());
		
		terrain = new int[maxX][maxY];
		for (int y = maxY-1; y >= 0; y--) {
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

		visited = new boolean[maxX][maxY];

		island = new int[maxX][maxY];

		int currentIslandNum = 0;
		for (int i = 0; i < maxX; i++) {
			for (int j = 0; j < maxY; j++) {
				if ((!visited[i][j]) && terrain[i][j] == LAND) {
					islandGroups.add(dfs(i, j, currentIslandNum++));
				}
			}
		}
		
		numIslands = islandGroups.size();

		distance = new int[numIslands][numIslands];
		for(int i = 0; i < numIslands; i++){
			for(int j = 0; j < numIslands; j++){
				distance[i][j] = Integer.MAX_VALUE;
			}
		}
		
		findDistance();
		
		for(int i = 0; i < numIslands; i++){
			distance[i][i] = Integer.MAX_VALUE;
		}
		
		
		TSPSOLVER answerMachine = solver . new TSPSOLVER(numIslands, distance);
		System.out.println(answerMachine.getAnswer());
		
	}

	static ArrayList<Coordinate> dfs(int x, int y, int islandNumber) {
		ArrayList<Coordinate> adjacent = new ArrayList<Coordinate>();
		dfs(x, y, islandNumber, adjacent);
		return adjacent;
	}

	// prerequisite: visited has to be up to date
	// adjacent has to be for this island group only
	// isAdjacentMarked has to be for this island group only
	static void dfs(int x, int y, int islandNumber, ArrayList<Coordinate> group) {

		island[x][y] = islandNumber;
		visited[x][y] = true;
		group.add(solver.new Coordinate(x, y));

		for (int currentDirection = 0; currentDirection < 4; currentDirection++) {
			int nX = x + xMod[currentDirection];
			int nY = y + yMod[currentDirection];
			if (0 <= nX && nX < maxX && 0 <= nY && nY < maxY) {
				if (terrain[nX][nY] == LAND && visited[nX][nY] == false) {
					dfs(nX, nY, islandNumber, group);
				}
			}
		}
	}
	
	static void findDistance(){
		for (int currentIsland = 0; currentIsland < numIslands; currentIsland++) {
			visited = new boolean[maxX][maxY];
			int[][] distanceFromStartIsland = new int[maxX][maxY];

			LinkedList<Coordinate> queue = new LinkedList<Coordinate>();
			for (Coordinate islandMember : islandGroups.get(currentIsland)) {
				queue.add(islandMember);
				visited[islandMember.x][islandMember.y] = true;
			}

			while (!queue.isEmpty()) {
				Coordinate currentVertex = queue.remove();
				int x = currentVertex.x;
				int y = currentVertex.y;
				for (int currentDirection = 0; currentDirection < 4; currentDirection++) {
					int nX = x + xMod[currentDirection];
					int nY = y + yMod[currentDirection];
					if (0 <= nX && nX < maxX && 0 <= nY && nY < maxY && !visited[nX][nY]) {
						if (terrain[nX][nY] == LAND) {
							int otherIsland = island[nX][nY];

							distance[currentIsland][otherIsland] = distanceFromStartIsland[x][y];
							
							for(Coordinate otherIslandMember : islandGroups.get(otherIsland)){
								int oX = otherIslandMember.x;
								int oY = otherIslandMember.y;

								distanceFromStartIsland[oX][oY] = distanceFromStartIsland[x][y];
								queue.addFirst(solver.new Coordinate(oX, oY));
								visited[oX][oY] = true;
							}							
						} else if (terrain[nX][nY] == SHALLOW) {
							distanceFromStartIsland[nX][nY] = distanceFromStartIsland[x][y] + 1;
							queue.add(solver.new Coordinate(nX, nY));
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
	
	class TSPSOLVER{
		int[][] cache;
		static final int UNDECIDED = -1;
		int numVertices;
		int[][] cost;
		
		TSPSOLVER(int numVertices, int[][] cost){
			this.numVertices = numVertices;
			cache = new int[numVertices][1 << numVertices];
			this.cost = cost;
			for (int i = 0; i < numVertices; i++) {
				for (int j = 0; j < (1 << numVertices); j++) {
					cache[i][j] = UNDECIDED;
				}
			}
		}
		
		public int getAnswer(){
			int totalMin = Integer.MAX_VALUE;
			int wholeSet = 0;
			for (int i = 0; i < numVertices; i++) {
				wholeSet <<= 1;
				wholeSet |= 1;
			}

			for (int i = 0; i < numVertices; i++) {
				totalMin = Math.min(totalMin, tsp(i, wholeSet));
			}
			return totalMin;
		}
		
		public int tsp(int endVertex, int bitSet) {
			if (cache[endVertex][bitSet] != UNDECIDED) {
				return cache[endVertex][bitSet];
			}

			int newBitSet = bitSet & (~(1 << endVertex));

			int min = Integer.MAX_VALUE;
			
			int processingBitSet = newBitSet;

			if (newBitSet == 0) {
				min = 0;
			} else {
				
				for(int previous = 0; previous < numVertices; previous++){
					if((processingBitSet & 1) != 0 && cost[previous][endVertex] != Integer.MAX_VALUE){
						int subsetCost = cache[endVertex][bitSet] != UNDECIDED ? cache[endVertex][bitSet]
								: tsp(previous, newBitSet);

						if (subsetCost != Integer.MAX_VALUE) {
							min = Math.min(min, subsetCost + cost[previous][endVertex]);
						}
					}
					processingBitSet >>= 1;
					if(processingBitSet == 0){
						continue;
					}
				}
			}
			cache[endVertex][bitSet] = min;
			return min;
		}
		
		
		
	}

}
