import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: snail
*/

public class snail {

	static int size;
	static int numBarriers;
	static boolean[][] containsBarrier;
	static boolean[][] visited;
	static int[] iMod = new int[] { 0, -1, 0, 1 };
	static int[] jMod = new int[] { -1, 0, 1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("snail.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("snail.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		size = Integer.parseInt(inputData.nextToken());
		numBarriers = Integer.parseInt(inputData.nextToken());
		containsBarrier = new boolean[size][size];

		for (int i = 0; i < numBarriers; i++) {
			String inputString = reader.readLine();
			int column = inputString.charAt(0) - 'A';
			int row = Integer.parseInt(inputString.substring(1)) - 1;
			containsBarrier[row][column] = true;
		}
		reader.close();

		visited = new boolean[size][size];
		visited[0][0] = true;
		printer.println(dfs(0, 0) + 1);
		printer.close();
	}

	// assume (i, j) is marked and counted

	public static int attemptAll(int i, int j) {
		int maxDistance = 0;

		for (int direction = 0; direction < 4; direction++) {
			int additionalDistance = 0;
			int currentI = i;
			int currentJ = j;
			int nextI = currentI + iMod[direction];
			int nextJ = currentJ + jMod[direction];

			// count
			currentI = i;
			currentJ = j;
			nextI = currentI + iMod[direction];
			nextJ = currentJ + jMod[direction];

			while (0 <= nextI && nextI < size && 0 <= nextJ && nextJ < size && !containsBarrier[nextI][nextJ]
					&& !visited[nextI][nextJ]) {
				currentI = nextI;
				currentJ = nextJ;
				additionalDistance++;
				nextI = currentI + iMod[direction];
				nextJ = currentJ + jMod[direction];
			}

			maxDistance = Math.max(maxDistance, additionalDistance);
		}

		return maxDistance;

	}

	public static int dfs(int i, int j) {
		int maxDistance = 0;

		directionLoop: for (int direction = 0; direction < 4; direction++) {
			int additionalDistance = 0;
			int currentI = i;
			int currentJ = j;
			int nextI = currentI + iMod[direction];
			int nextJ = currentJ + jMod[direction];

			while (0 <= nextI && nextI < size && 0 <= nextJ && nextJ < size && !containsBarrier[nextI][nextJ]) {
				if (visited[nextI][nextJ]) {
					continue directionLoop;
				}
				currentI = nextI;
				currentJ = nextJ;
				nextI = currentI + iMod[direction];
				nextJ = currentJ + jMod[direction];
			}

			// didn't go anywhere
			if (i == currentI && j == currentJ) {
				continue directionLoop;
			}

			// all clear

			// mark and count
			currentI = i;
			currentJ = j;
			nextI = currentI + iMod[direction];
			nextJ = currentJ + jMod[direction];

			while (0 <= nextI && nextI < size && 0 <= nextJ && nextJ < size && !containsBarrier[nextI][nextJ]) {
				currentI = nextI;
				currentJ = nextJ;
				visited[currentI][currentJ] = true;
				additionalDistance++;
				nextI = currentI + iMod[direction];
				nextJ = currentJ + jMod[direction];
			}

			additionalDistance += dfs(currentI, currentJ);
			maxDistance = Math.max(maxDistance, additionalDistance);

			currentI = i;
			currentJ = j;
			nextI = currentI + iMod[direction];
			nextJ = currentJ + jMod[direction];

			while (0 <= nextI && nextI < size && 0 <= nextJ && nextJ < size && !containsBarrier[nextI][nextJ]) {
				currentI = nextI;
				currentJ = nextJ;
				visited[currentI][currentJ] = false;
				additionalDistance++;
				nextI = currentI + iMod[direction];
				nextJ = currentJ + jMod[direction];
			}
		}

		return Math.max(maxDistance, attemptAll(i, j));
	}

}
