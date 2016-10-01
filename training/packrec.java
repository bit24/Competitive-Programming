import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*ID: eric.ca1
LANG: JAVA
TASK: packrec
*/

public class packrec {

	int minArea = Integer.MAX_VALUE;
	TreeSet<Pair> solutions = new TreeSet<Pair>();
	int[] side1 = new int[4];
	int[] side2 = new int[4];

	public static void main(String[] args) throws IOException {
		new packrec().execute();
	}

	public void execute() throws IOException {
		input();
		findSolutionsOrient();
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("packrec.out")));
		printer.println(minArea);
		for (Pair p : solutions) {
			printer.println(p.a + " " + p.b);
		}
		printer.close();
	}

	public void input() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("packrec.in"));

		for (int i = 0; i < 4; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			side1[i] = Integer.parseInt(inputData.nextToken());
			side2[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();
	}

	public void findSolutionsOrient() {
		for (int orientations = 0; orientations < 16; orientations++) {
			int[] height = new int[4];
			int[] width = new int[4];
			for (int i = 0; i < 4; i++) {
				if ((orientations & (1 << i)) == 0) {
					height[i] = side1[i];
					width[i] = side2[i];
				} else {
					width[i] = side1[i];
					height[i] = side2[i];
				}
			}
			findSolutionsP(height, width, new int[4], new int[4], new boolean[4]);
		}
	}

	public void findSolutionsP(int[] rectHeight, int[] rectWidth, int[] newHeight, int[] newWidth, boolean[] used) {
		int cIndex = 0;
		for (int i = 0; i < 4; i++) {
			if (used[i]) {
				cIndex++;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (!used[i]) {
				newHeight[cIndex] = rectHeight[i];
				newWidth[cIndex] = rectWidth[i];
				used[i] = true;
				findSolutionsP(rectHeight, rectWidth, newHeight, newWidth, used);
				used[i] = false;
			}
		}

		if (cIndex == 4) {
			findSolutions(newHeight, newWidth, new int[205], 0);
		}
	}

	public void findSolutions(int[] rectHeight, int[] rectWidth, int[] gridHeight, int cRec) {
		if (cRec == 4) {
			int totalHeight = gridHeight[0];
			int totalWidth = 0;

			while (gridHeight[totalWidth] != 0) {
				totalWidth++;
			}

			if (totalWidth * totalHeight < minArea) {
				minArea = totalWidth * totalHeight;
				solutions.clear();
				solutions.add(new Pair(Math.min(totalHeight, totalWidth), Math.max(totalHeight, totalWidth)));
			} else if (totalWidth * totalHeight == minArea) {
				solutions.add(new Pair(Math.min(totalHeight, totalWidth), Math.max(totalHeight, totalWidth)));
			}
			return;
		}

		for (int left = 0; left < 205; left++) {
			if (left == 0 || gridHeight[left - 1] >= rectHeight[cRec] + gridHeight[left]) {
				int newHeight = rectHeight[cRec] + gridHeight[left];

				int[] newGridHeight = Arrays.copyOf(gridHeight, gridHeight.length);
				for (int sweep = left; sweep < left + rectWidth[cRec]; sweep++) {
					newGridHeight[sweep] = newHeight;
				}
				findSolutions(rectHeight, rectWidth, newGridHeight, cRec + 1);
			}
		}
	}

	class Pair implements Comparable<Pair> {
		int a;
		int b;

		Pair(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int compareTo(Pair o) {
			if (a != o.a) {
				return a < o.a ? -1 : 1;
			}
			if (b != o.b) {
				return b < o.b ? -1 : 1;
			}
			return 0;
		}

		public boolean equals(Object other) {
			return a == ((Pair) other).a && b == ((Pair) other).b;
		}
	}

}
