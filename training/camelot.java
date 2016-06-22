import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*ID: eric.ca1
 LANG: JAVA
 TASK: camelot
 */

public class camelot {
	
	public static camelot helper = new camelot();
	
	public static int[] knightX = new int[]{1, -1, 2, 2, 1, -1, -2, -2};
	
	public static int[] knightY = new int[]{2, 2, 1, -1, -2, -2, 1, -1};
	
	public static int[][][][] distance;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("camelot.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("camelot.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int rows = Integer.parseInt(inputData.nextToken());
		int columns = Integer.parseInt(inputData.nextToken());
		
		inputData = new StringTokenizer(reader.readLine());
		
		Coordinate kingPosition = helper. new Coordinate(inputData.nextToken().charAt(0) - 'A', Integer.parseInt(inputData.nextToken()) - 1);
		
		ArrayList<Coordinate> knightCoors = new ArrayList<Coordinate>();
		
		while(inputData.hasMoreTokens()){
			Coordinate knightPosition = helper.new Coordinate(inputData.nextToken().charAt(0) - 'A', Integer.parseInt(inputData.nextToken()) - 1);
			knightCoors.add(knightPosition);
		}
		
		String inputLine = reader.readLine();
		while(inputLine != null){
			inputData = new StringTokenizer(inputLine);
			while(inputData.hasMoreTokens()){
				Coordinate knightPosition = helper.new Coordinate(inputData.nextToken().charAt(0) - 'A', Integer.parseInt(inputData.nextToken()) - 1);
				knightCoors.add(knightPosition);
			}
			inputLine = reader.readLine();
		}
		reader.close();
		
		computeDistances(rows, columns);
		
		int minSteps = Integer.MAX_VALUE;
		for(int xCoor = 0; xCoor < columns; xCoor++){
			for(int yCoor = 0; yCoor < rows; yCoor++){
				minSteps = Math.min(minSteps, getNumSteps(xCoor, yCoor, knightCoors, kingPosition, columns, rows));
			}
		}
		
		printer.println(minSteps);
		printer.close();
		
	}
	
	public static int getNumSteps(int goalXCoor, int goalYCoor, ArrayList<Coordinate> knights, Coordinate kingPosition, int xUpperBound, int yUpperBound){
		int knightMoves = 0;
		for(Coordinate currentCoor : knights){
			int distanceToGoal = distance[currentCoor.xCoor][currentCoor.yCoor][goalXCoor][goalYCoor];
			if(distanceToGoal == Integer.MAX_VALUE){
				return Integer.MAX_VALUE;
			}
			else{
				knightMoves += distanceToGoal;
			}
		}
		
		int minXCoor = Math.max(0, kingPosition.xCoor-2);
		int maxXCoor = Math.min(xUpperBound-1, kingPosition.xCoor+2);
		
		int minYCoor = Math.max(0, kingPosition.yCoor-2);
		int maxYCoor = Math.min(yUpperBound - 1, kingPosition.yCoor+2);
		
		int minKingsTraverse = Math.max(Math.abs(kingPosition.xCoor - goalXCoor), Math.abs(kingPosition.yCoor - goalYCoor));
		
		for(int currentXCoor = minXCoor; currentXCoor <= maxXCoor; currentXCoor++){
			for(int currentYCoor = minYCoor; currentYCoor <= maxYCoor; currentYCoor++){
				if(distance[currentXCoor][currentYCoor][goalXCoor][goalYCoor] == Integer.MAX_VALUE){
					continue;
				}
				int kingMovement = Math.max(Math.abs(kingPosition.xCoor - currentXCoor), Math.abs(kingPosition.yCoor - currentYCoor));
				
				for(Coordinate possibleKnight : knights){
					if(distance[possibleKnight.xCoor][possibleKnight.yCoor][currentXCoor][currentYCoor] == Integer.MAX_VALUE){
						continue;
					}
					int tempValue = 0;
					tempValue += kingMovement;
					tempValue += distance[possibleKnight.xCoor][possibleKnight.yCoor][currentXCoor][currentYCoor];
					tempValue += distance[currentXCoor][currentYCoor][goalXCoor][goalYCoor];
					tempValue -= distance[possibleKnight.xCoor][possibleKnight.yCoor][goalXCoor][goalYCoor];
					if(minKingsTraverse > tempValue){
						minKingsTraverse = tempValue;
					}
					
				}
			}
		}
		
		
		
		return knightMoves + minKingsTraverse;
	}
	
	
	public static void computeDistances(int numRows, int numColumns){
		distance = new int[numColumns][numRows][numColumns][numRows];
		for(int a = 0; a < numColumns; a++){
			for(int b = 0; b < numRows; b++){
				for(int c = 0; c < numColumns; c++){
					for(int d = 0; d < numRows; d++){
						distance[a][b][c][d] = Integer.MAX_VALUE;
					}
				}
			}
		}
		for(int currentXCoor = 0; currentXCoor < numColumns; currentXCoor++){
			for(int currentYCoor = 0; currentYCoor < numRows; currentYCoor++){
				computeDistancesFrom(currentXCoor, currentYCoor, numColumns, numRows);
			}
		}
	}
	
	public static void computeDistancesFrom(int startXCoor, int startYCoor, int xUpperBound, int yUpperBound){
		LinkedList<Coordinate> queue = new LinkedList<Coordinate>();
		queue.add(helper.new Coordinate(startXCoor, startYCoor));
		int[][] distanceFromStart = distance[startXCoor][startYCoor];
		distanceFromStart[startXCoor][startYCoor] = 0;
		
		while(!queue.isEmpty()){
			Coordinate currentCoor = queue.removeFirst();
			for(int currentNeighbor = 0; currentNeighbor < 8; currentNeighbor++){
				int newX = currentCoor.xCoor + knightX[currentNeighbor];
				int newY = currentCoor.yCoor + knightY[currentNeighbor];
				if(isLegal(newX, newY, xUpperBound, yUpperBound)){
					if(distanceFromStart[newX][newY] > distanceFromStart[currentCoor.xCoor][currentCoor.yCoor] + 1){
						distanceFromStart[newX][newY] = distanceFromStart[currentCoor.xCoor][currentCoor.yCoor] + 1;
						queue.add(helper. new Coordinate(newX, newY));
					}
				}
				
			}
			
		}
	}
	
	public static boolean isLegal(int xCoor, int yCoor, int  xUpperBound, int yUpperBound){
		if(xCoor >= 0 && xCoor < xUpperBound && yCoor >= 0 && yCoor < yUpperBound){
			return true;
		}
		else{
			return false;
		}
	}

	class Coordinate{
		public int xCoor;
		public int yCoor;
		
		public Coordinate(int xCoor, int yCoor){
			this.xCoor = xCoor;
			this.yCoor = yCoor;
		}
		
	}
}
