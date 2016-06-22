import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

public class DeliveryRoute {

	// north, west, normal, east, south
	static int[] xMods = new int[] { 0, -1, 0, 1, 0 };
	static int[] yMods = new int[] { 1, 0, 0, 0, -1 };

	int numFarms;

	ArrayList<Point> points = new ArrayList<Point>();

	LinkedHashSet<Point> farmSet = new LinkedHashSet<Point>();

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();
	
	int[] farms;

	public static void main(String[] args) throws IOException {
		new DeliveryRoute().solve();
	}

	public void solve() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numFarms = Integer.parseInt(reader.readLine());

		int[] xFarms = new int[numFarms];
		int[] yFarms = new int[numFarms];
		for (int i = 0; i < numFarms; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			xFarms[i] = Integer.parseInt(inputData.nextToken());
			yFarms[i] = Integer.parseInt(inputData.nextToken());
			farmSet.add(new Point(xFarms[i], yFarms[i]));
		}

		points = new ArrayList<Point>();

		farms = new int[numFarms];
		int farmNum = 0;
		
		for (int i = 0; i < numFarms; i++) {
			for (int modification = 0; modification < 5; modification++) {
				if (modification == 2) {
					points.add(new Point(xFarms[i], yFarms[i]));
					farms[farmNum++] = points.size() - 1;

				} else if (!farmSet
						.contains(new Point(xFarms[i] + xMods[modification], yFarms[i] + yMods[modification]))) {
					points.add(new Point(xFarms[i] + xMods[modification], yFarms[i] + yMods[modification]));
				}
			}
		}

		aList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < points.size(); i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int a = 0; a < points.size(); a++) {
			for (int b = a+1; b < points.size(); b++) {
				if (a == b) {
					continue;
				}
				if (isPossible(a, b)) {
					aList.get(a).add(b);
					aList.get(b).add(a);
				}
			}
		}

		int total = 0;

		for (int cFarm = 0; cFarm < numFarms; cFarm++) {
			int newAmount = mincost(farms[cFarm], farms[(cFarm + 1) % numFarms]);
			if (newAmount < 0) {
				total = -1;
				break;
			} else {
				total += newAmount;
			}
		}
		System.out.println(total);

	}

	int mincost(int start, int end) {
		boolean[] visited = new boolean[points.size()];
		int[] distance = new int[points.size()];

		for (int i = 0; i < points.size(); i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < farms.length; i++){
			if(farms[i] != start && farms[i] != end){
				visited[farms[i]] = true;
			}
		}

		distance[start] = 0;

		// double forLoop djikstra; avoid priority queue hassle
		for (int i = 0; i < points.size(); i++) {
			int current = 0;
			for (int j = 0; j < points.size(); j++) {
				if (!visited[j] && distance[j] < distance[current] || visited[current]) {
					current = j;
				}
			}
			if (visited[current] || distance[current] == Integer.MAX_VALUE) {
				return -1;
			}
			if (current == end) {
				return distance[current];
			}

			visited[current] = true;
			for (int neighbor : aList.get(current)) {
				if (!visited[neighbor]) {
					distance[neighbor] = Math.min(distance[neighbor],
							distance[current] + distance(points.get(current), points.get(neighbor)));
				}
			}
		}
		return -1;
	}

	class Point {
		int x;
		int y;

		Point() {
		}

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean equals(Object obj) {
			Point other = (Point) obj;
			return this.x == other.x && this.y == other.y;
		}

		public int hashCode() {
			return x ^ y;
		}

	}

	class Line {
		Point a;
		Point b;

		Line() {
		}

		Line(Point a, Point b) {
			this.a = a;
			this.b = b;
		}

	}

	// it is given that the line is either horizontal or vertical
	public boolean intersects(Point p, Line l) {
		// case line is vertical
		if (l.a.x == l.b.x) {
			return p.x == l.a.x && Math.min(l.a.y, l.b.y) <= p.y && p.y <= Math.max(l.a.y, l.b.y);
		} else if (l.a.y == l.b.y) {
			return p.y == l.a.y && Math.min(l.a.x, l.b.x) <= p.x && p.x <= Math.max(l.a.x, l.b.x);
		} else {
			assert (false);
			return false;
		}
	}

	public int distance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	public boolean isPossible(int aIndex, int bIndex) {
		
		Point a = points.get(aIndex);
		Point b = points.get(bIndex);

		// case 1 : straight line
		if (a.x == b.x || a.y == b.y) {
			Line l = new Line(a, b);

			boolean possible = true;
			for (Point currentPoint : points) {
				if (currentPoint.equals(a) || currentPoint.equals(b)) {
					continue;
				}
				if (intersects(currentPoint, l)) {
					possible = false;
					break;
				}
			}
			return possible;
		}

		// case 2: vertical, then horizontal
		boolean case2 = true;
		Point middlePoint = new Point(a.x, b.y);
		Line l1 = new Line(a, middlePoint);
		Line l2 = new Line(middlePoint, b);

		for (Point currentPoint : points) {
			if (currentPoint.equals(a) || currentPoint.equals(b)) {
				continue;
			}
			if (intersects(currentPoint, l1) || intersects(currentPoint, l2)) {
				case2 = false;
				break;
			}
		}
		if (case2) {
			return true;
		}

		// case 3 : horizontal, then vertical
		boolean case3 = true;

		// different
		middlePoint = new Point(b.x, a.y);
		l1 = new Line(a, middlePoint);
		l2 = new Line(middlePoint, b);

		for (Point currentPoint : points) {
			if (currentPoint.equals(a) || currentPoint.equals(b)) {
				continue;
			}
			if (intersects(currentPoint, l1) || intersects(currentPoint, l2)) {
				case3 = false;
				break;
			}
		}
		if (case3) {
			return true;
		}
		return false;
	}

}
