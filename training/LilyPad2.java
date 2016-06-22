import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class LilyPad2 {
	
	static int M;
	static int N;
	static int[][] graph;
	static int startX;
	static int startY;
	static int endX;
	static int endY;
	static LilyPad2 lilypad = new LilyPad2();
	
	static int[] xMoves = new int[]{ -2, -1,  1,  2, 2, 1, -1, -2};
	static int[] yMoves = new int[]{ -1, -2, -2, -1, 1, 2,  2,  1};
	
	static int[][] distances;
	
	static int[][] numWays;
	
	static boolean[][] visited;
	
	static int[][] numWays2;
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		M = Integer.parseInt(inputData.nextToken());
		N = Integer.parseInt(inputData.nextToken());
		graph = new int[M][N];
		
		for(int i = 0; i < M; i++){
			inputData = new StringTokenizer(reader.readLine());
			for(int j = 0; j < N; j++){
				graph[i][j] = Integer.parseInt(inputData.nextToken());
				if(graph[i][j] == 3){
					startX = i;
					startY = j;
				}
				else if(graph[i][j] == 4){
					endX = i;
					endY = j;
				}
			}
		}
		
		PriorityQueue<Coordinate> queue = new PriorityQueue<Coordinate>();
		
		Coordinate startCoor = lilypad.new Coordinate(startX, startY, 0);
		queue.add(startCoor);
		
		distances = new int[M][N];
		numWays = new int[M][N];
		visited = new boolean[M][N];
		
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				distances[i][j] = Integer.MAX_VALUE;
				numWays[i][j] = 0;
			}
		}
		
		numWays[startX][startY] = 1;
		
		distances[startX][startY] = 0;
		
		while(!queue.isEmpty()){
			Coordinate currentCoordinate = queue.remove();
			int xCoor = currentCoordinate.x;
			int yCoor = currentCoordinate.y;
			int ways = numWays[xCoor][yCoor];
			
			if(currentCoordinate.distance < distances[xCoor][yCoor]){
				continue;
			}
			if(visited[xCoor][yCoor]){
				continue;
			}
			else{
				visited[xCoor][yCoor] = true;
			}
			
			for(int neighbor = 0; neighbor < 8; neighbor++){
				int nXCoor = xCoor + xMoves[neighbor];
				int nYCoor = yCoor + yMoves[neighbor];
				if(!isLegal(nXCoor, nYCoor)){
					continue;
				}
				int newDistance = currentCoordinate.distance;
				boolean onLilyPad = isOnLilyPad(graph[nXCoor][nYCoor]);
				if(!onLilyPad){
					newDistance++;
				}
				
				if(distances[nXCoor][nYCoor] > newDistance){
					System.out.println(nXCoor + " " + nYCoor + " =" + ways);
					System.out.println();
					distances[nXCoor][nYCoor] = newDistance;
					Coordinate nCoor = lilypad.new Coordinate(nXCoor, nYCoor, newDistance);
					queue.add(nCoor);
					
					
					numWays[nXCoor][nYCoor] = ways;
					
					
				}
				else if(distances[nXCoor][nYCoor] == newDistance){
					assert numWays[nXCoor][nYCoor] != 0;
					//if(wasOnLilyPad){
						numWays[nXCoor][nYCoor] += ways;
					//}
					//else{
						//numWays[nXCoor][nYCoor] += 1;
					//}
					System.out.println(nXCoor + " " + nYCoor + " +=" + ways);
					System.out.println(nXCoor + " " + nYCoor + " =" + numWays[nXCoor][nYCoor]);
					System.out.println();
				}
				
			}
		}
		
		
		boolean isPossible = true;
		if(distances[endX][endY] == Integer.MAX_VALUE){
			isPossible = false;
		}
		
		if(!isPossible){
			System.out.println(-1);
		}
		else{
			System.out.println(distances[endX][endY]);
			System.out.println(numWays[endX][endY]);
		}
		
		for(int i = 0; i < M; i++){
			System.out.println();
			for(int j = 0; j < N; j++){
				System.out.print(numWays[i][j] + " ");
			}
		}
		
	}
	
	public static boolean isOnLilyPad(int number){
		if(number == 1 || number == 3 || number == 4){
			return true;
		}
		return false;
	}
	
	public static boolean isLegal(int x, int y){
		if(x < 0 || y < 0){
			return false;
		}
		if(x >= M || y >= N){
			return false;
		}
		if(graph[x][y] == 2){
			return false;
		}
		return true;
		
	}
	
	
	class Coordinate implements Comparable<Coordinate>{
		int x;
		int y;
		int distance;
		
		public Coordinate(int x, int y, int distance){
			this.x = x;
			this.y = y;
			this.distance = distance;
		}
		
		public int compareTo(Coordinate o) {
			return Integer.compare(distance, o.distance);
		}
		
		
	}
	

}
