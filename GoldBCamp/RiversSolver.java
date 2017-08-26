import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class RiversSolver {

	static int numVillages;
	static int numSawMills;

	static int[] value;
	static int[] parent;
	static int[] accumulativeDistance;

	static ArrayList<Integer>[] children;
	static int[] numChildren;

	static int[][] distance;

	// vertex, child, predecessor, numMills
	static long dp[][][];

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numVillages = Integer.parseInt(inputData.nextToken());
		numSawMills = Integer.parseInt(inputData.nextToken());

		value = new int[numVillages + 1];
		parent = new int[numVillages + 1];
		accumulativeDistance = new int[numVillages + 1];

		children = new ArrayList[numVillages + 1];
		numChildren = new int[numVillages + 1];

		distance = new int[numVillages + 1][numVillages + 1];

		for (int i = 0; i <= numVillages; i++) {
			children[i] = new ArrayList<Integer>();
		}

		parent[0] = -1;
		for (int i = 1; i <= numVillages; i++) {
			inputData = new StringTokenizer(reader.readLine());
			value[i] = Integer.parseInt(inputData.nextToken());
			parent[i] = Integer.parseInt(inputData.nextToken());
			children[parent[i]].add(i);
			distance[parent[i]][i] = Integer.parseInt(inputData.nextToken());
		}

		for (int i = 0; i <= numVillages; i++) {
			numChildren[i] = children[i].size();
		}

		computeAccumulativeDistance(0, 0);

		dp = new long[numVillages + 1][numVillages + 1][numSawMills+1];

		computeDP(0);

		long ans = dp[0][0][numSawMills];
		System.out.println(ans);
		
	}

	public static void computeDP(int currentVertex) {
		
		long[][] previous = new long[numVillages + 1][numSawMills+1];
		long[][] current = new long[numVillages + 1][numSawMills+1];
		
		// if you have no children, it either is simply predecessor -> current
		// or free it you have at least 1 mill
		// no matter what predecessor
		for (int predecessor = currentVertex; predecessor != -1; predecessor = parent[predecessor]) {

			// if there are no mills, simply calculate cost
			current[predecessor][0] = value[currentVertex]
					* (accumulativeDistance[currentVertex] - accumulativeDistance[predecessor]);

			// if there is at least 1 mill, then it is free
			for (int totalAvailibleMills = 1; totalAvailibleMills <= numSawMills; totalAvailibleMills++) {
				current[predecessor][totalAvailibleMills] = 0;
			}
		}

		// you don't need to worry about children if there are none
		if (numChildren[currentVertex] == 0) {
			dp[currentVertex] = current;
			return;
		}
		
		
		// when currentChildNumber == 0 taken care of
		for (int currentChildNumber = 1; currentChildNumber <= children[currentVertex].size(); currentChildNumber++) {
			
			long[][] temp = previous;
			previous = current;
			current = temp;


			int currentChildVertex = children[currentVertex].get(currentChildNumber - 1);
			computeDP(currentChildVertex);

			for (int predecessor = currentVertex; predecessor != -1; predecessor = parent[predecessor]) {
				for (int totalAvailibleMills = numSawMills; totalAvailibleMills >= 0; totalAvailibleMills--) {

					// we will be trying to find minimum value for:
					// dp[currentVertex][currentChildNumber][predecessor][totalAvailibleMills]
					current[predecessor][totalAvailibleMills] = Integer.MAX_VALUE;

					for (int amountForCurrentChild = 0; amountForCurrentChild <= totalAvailibleMills; amountForCurrentChild++) {

						// build mill at vertex

						// first, there can't already be a mill
						// second, there has to be at least one child subtracted
						// form totalAvailbleMills to build a new mill
						if (predecessor != currentVertex && amountForCurrentChild < totalAvailibleMills) {

							// in order to calculate cost, given that you have a
							// certain amount of windmills for current child:
							// take the cost of all the previous children
							// add the cost of this child in particular
							long posAmount = previous[currentVertex][totalAvailibleMills - amountForCurrentChild - 1]

									+ dp[currentChildVertex][currentVertex][amountForCurrentChild];

							// if amount is less than minimum, update minimum
							if (current[predecessor][totalAvailibleMills] > posAmount) {
								current[predecessor][totalAvailibleMills] = posAmount;
							}
						}

						// leave it be

						// in order to calculate cost, given that you have a
						// certain amount of windmills for current child:
						// take the cost of just the current node delivering to
						// the previous
						// add cost of all previous children
						// add the cost of this child in particular

						long posAmount = /*value[currentVertex]
								* (accumulativeDistance[currentVertex] - accumulativeDistance[predecessor])

								+*/ previous[predecessor][totalAvailibleMills
										- amountForCurrentChild]

								+ dp[currentChildVertex][predecessor][amountForCurrentChild];

						// if amount is less than minimum, update minimum
						if (current[predecessor][totalAvailibleMills] > posAmount) {
							current[predecessor][totalAvailibleMills] = posAmount;
						}
					}
				}
			}
		}
		dp[currentVertex] = current;
	}

	public static void computeAccumulativeDistance(int i, int sum) {
		accumulativeDistance[i] = sum;
		for (int child : children[i]) {
			computeAccumulativeDistance(child, sum + distance[i][child]);
		}
	}
}
