import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: rectbarn
*/

//import java.util.TreeSet;

public class rectbarn {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("rectbarn.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("rectbarn.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numRows = Integer.parseInt(inputData.nextToken());
		int numColumns = Integer.parseInt(inputData.nextToken());
		int numBlocked = Integer.parseInt(inputData.nextToken());

		// TreeSet<Pair> blocked = new TreeSet<Pair>();
		boolean[][] blocked = new boolean[numRows][numColumns];
		for (int i = 0; i < numBlocked; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			blocked[a][b] = true;
		}
		reader.close();

		int[] currentHeights = new int[numColumns];

		int maxArea = 0;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (blocked[i][j]) {
					currentHeights[j] = 0;
				} else {
					currentHeights[j]++;
				}
			}

			int area = maxArea(currentHeights);
			if (maxArea < area) {
				maxArea = area;
			}
		}

		printer.println(maxArea);
		printer.close();
	}

	public static int maxArea(int[] heights) {
		Stack<Integer> restrictStack = new Stack<Integer>();

		int maxArea = 0;

		for (int i = 0; i < heights.length; i++) {
			int currentHeight = heights[i];
			while (!restrictStack.isEmpty() && heights[restrictStack.peek()] > currentHeight) {
				int hRestict = heights[restrictStack.pop()];
				int area = (restrictStack.isEmpty() ? i : (i - restrictStack.peek() - 1)) * hRestict;
				if (maxArea < area) {
					maxArea = area;
				}
			}
			restrictStack.add(i);
		}

		int i = heights.length;
		while (!restrictStack.isEmpty()) {
			int hRestict = heights[restrictStack.pop()];
			int area = (restrictStack.isEmpty() ? i : (i - restrictStack.peek() - 1)) * hRestict;
			if (maxArea < area) {
				maxArea = area;
			}
		}
		return maxArea;
	}
	/*
	 * class Pair implements Comparable<Pair> {
	 * 
	 * int a; int b;
	 * 
	 * Pair(int a, int b) { this.a = a; this.b = b; }
	 * 
	 * public int compareTo(Pair o) { if (a < o.a) { return -1; } if (a > o.a) { return 1; } else { return
	 * Integer.compare(b, o.b); } }
	 * 
	 * public boolean equals(Object obj) { Pair other = (Pair) obj; return a == other.a && b == other.b; } }
	 */
}
