import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*ID: eric.ca1
LANG: JAVA
TASK: stall4
*/

public class stall4 {

	static int numLeft;
	static int numRight;

	static int numVertices;

	static ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader("stall4.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("stall4.out")));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numLeft = Integer.parseInt(inputData.nextToken());
		numRight = Integer.parseInt(inputData.nextToken());

		numVertices = numLeft + numRight;

		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int v1 = 0; v1 < numLeft; v1++) {
			inputData = new StringTokenizer(reader.readLine());

			int numPreferred = Integer.parseInt(inputData.nextToken());

			for (int i = 0; i < numPreferred; i++) {
				int v2 = Integer.parseInt(inputData.nextToken()) - 1 + numLeft;

				aList.get(v1).add(v2);
			}
		}
		reader.close();

		MaxBiparteMatcher maxMatch = new stall4().new MaxBiparteMatcher();

		printer.println(maxMatch.compute(aList));
		printer.close();
	}

	class MaxBiparteMatcher {
		int[] rightMatch;
		int[] leftMatch;

		boolean[] assigned;

		ArrayList<ArrayList<Integer>> aList;

		public boolean isPossible(int currentVertex) {
			for (int neighbor : aList.get(currentVertex)) {
				if (!assigned[neighbor]) {
					assigned[neighbor] = true;

					if (leftMatch[neighbor] == -1 || isPossible(leftMatch[neighbor])) {
						rightMatch[currentVertex] = neighbor;
						leftMatch[neighbor] = currentVertex;
						return true;
					}
				}
			}
			return false;

		}

		public int compute(ArrayList<ArrayList<Integer>> aList) {
			rightMatch = new int[aList.size()];
			leftMatch = new int[aList.size()];
			this.aList = aList;

			Arrays.fill(rightMatch, -1);
			Arrays.fill(leftMatch, -1);

			int count = 0;
			for (int i = 0; i < aList.size(); i++) {
				assigned = new boolean[aList.size()];
				if (isPossible(i)) {
					count++;
				}
			}
			return count;
		}

	}
}
