import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class CowsOnIce {

	static int numRocks;

	// x, y of starting position
	static int startX;
	static int startY;

	// x, y of starting position
	static int endX;
	static int endY;
	
	static CowsOnIce solver = new CowsOnIce();
	
	static Coordinate[] coordinatesByX;
	static Coordinate[] coordinatesByY;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		// init IO
		numRocks = Integer.parseInt(inputData.nextToken());
		startX = Integer.parseInt(inputData.nextToken());
		startY = Integer.parseInt(inputData.nextToken());
		endX = Integer.parseInt(inputData.nextToken());
		endY = Integer.parseInt(inputData.nextToken());

		// two int[]s representing coordinates e.g. (x[0], y[0])
		int[] rockX = new int[numRocks];
		int[] rockY = new int[numRocks];

		//Coordinate[]
		Coordinate[] coordinate = new Coordinate[numRocks];
		
		// rock location IO
		for (int i = 0; i < numRocks; i++) {
			inputData = new StringTokenizer(reader.readLine());
			rockX[i] = Integer.parseInt(inputData.nextToken());
			rockY[i] = Integer.parseInt(inputData.nextToken());
			coordinate[i] = solver.new Coordinate(rockX[i], rockY[i]);
		}
		
		coordinatesByX = Arrays.copyOf(coordinate, numRocks);
		Arrays.sort(coordinatesByX, SORTBYX);
		
		coordinatesByY = Arrays.copyOf(coordinate, numRocks);
		Arrays.sort(coordinatesByY, SORTBYY);
		
		LinkedList<Coordinate> queue = new LinkedList<Coordinate>();
		
		HashMap<Coordinate, Integer> cost = new HashMap<Coordinate, Integer>();
		queue.add(solver.new Coordinate(startX, startY));
		cost.put(solver.new Coordinate(startX, startY), 0);
		
		
		while(!queue.isEmpty()){
			Coordinate currentCoordinate = queue.remove();
			if(currentCoordinate.xPosition == endX && currentCoordinate.yPosition == endY){
				break;
			}
			
			for(int i = 0; i < 4; i++){
				Coordinate neighbor = null;
				switch(i){
					case 0 : neighbor = getRight(currentCoordinate); break;
					case 1 : neighbor = getLeft(currentCoordinate); break;
					case 2 : neighbor = getUp(currentCoordinate); break;
					case 3 : neighbor = getDown(currentCoordinate); break;
				}
				if(neighbor == null){
					continue;
				}
				if(!cost.containsKey(neighbor) || cost.get(neighbor) > cost.get(currentCoordinate) + 1){
					cost.put(neighbor, cost.get(currentCoordinate) + 1);
					queue.add(neighbor);
				}
			}
		}
		System.out.println(cost.get(solver.new Coordinate(endX, endY)));
		
		
		
	}
	
	static Coordinate getRight(Coordinate currentCoordinate){
		int rightIndex = Arrays.binarySearch(coordinatesByY, currentCoordinate, SORTBYY) * -1 - 1;
		if(rightIndex >= numRocks){
			return null;
		}
		if(coordinatesByY[rightIndex].yPosition != currentCoordinate.yPosition){
			return null;
		}
		return solver.new Coordinate(coordinatesByY[rightIndex].xPosition-1, coordinatesByY[rightIndex].yPosition);
	}
	
	static Coordinate getLeft(Coordinate currentCoordinate){
		int leftIndex = Arrays.binarySearch(coordinatesByY, currentCoordinate, SORTBYY) * - 1 - 2;
		if(leftIndex < 0){
			return null;
		}
		if(coordinatesByY[leftIndex].yPosition != currentCoordinate.yPosition){
			return null;
		}
		return solver.new Coordinate(coordinatesByY[leftIndex].xPosition+1, coordinatesByY[leftIndex].yPosition);
	}
	
	static Coordinate getUp(Coordinate currentCoordinate){
		int upIndex = Arrays.binarySearch(coordinatesByX, currentCoordinate, SORTBYX) * -1 - 1;
		if(upIndex >= numRocks){
			return null;
		}
		if(coordinatesByX[upIndex].xPosition != currentCoordinate.xPosition){
			return null;
		}
		return solver.new Coordinate(coordinatesByX[upIndex].xPosition, coordinatesByX[upIndex].yPosition-1);
	}
	
	static Coordinate getDown(Coordinate currentCoordinate){
		int downIndex = Arrays.binarySearch(coordinatesByX, currentCoordinate, SORTBYX) * -1 - 2;
		if(downIndex < 0){
			return null;
		}
		if(coordinatesByX[downIndex].xPosition != currentCoordinate.xPosition){
			return null;
		}
		return solver.new Coordinate(coordinatesByX[downIndex].xPosition, coordinatesByX[downIndex].yPosition+1);
	}			

	class Coordinate {
		int xPosition;
		int yPosition;

		Coordinate(int xPosition, int yPosition) {
			this.xPosition = xPosition;
			this.yPosition = yPosition;
		}

		Coordinate() {
		};
		
		public int hashCode(){
			return xPosition ^ yPosition;
		}
		
		public boolean equals(Object obj){
			Coordinate other = (Coordinate) obj;
			return this.xPosition == other.xPosition && this.yPosition == other.yPosition;
		}
		
	}

	static SortByX SORTBYX = solver.new SortByX();
	class SortByX implements Comparator<Coordinate> {
		public int compare(Coordinate a, Coordinate b) {
			int result = Integer.compare(a.xPosition, b.xPosition);
			if (result == 0) {
				result = Integer.compare(a.yPosition, b.yPosition);
			}
			return result;
		}
	}

	static SortByY SORTBYY = solver.new SortByY();
	class SortByY implements Comparator<Coordinate> {
		public int compare(Coordinate a, Coordinate b) {
			int result = Integer.compare(a.yPosition, b.yPosition);
			if (result == 0) {
				result = Integer.compare(a.xPosition, b.xPosition);
			}
			return result;
		}
	}
}
