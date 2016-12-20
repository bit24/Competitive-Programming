import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeDecorator {

	public static void main(String[] args) throws IOException {
		new TreeDecorator().execute();
	}

	long[] cost;
	long[] req;

	long totalCost;

	ArrayList<ArrayList<Integer>> aList = new ArrayList<ArrayList<Integer>>();

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numVertices = Integer.parseInt(reader.readLine());

		cost = new long[numVertices];
		req = new long[numVertices];

		for (int i = 0; i < numVertices; i++) {
			aList.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numVertices; i++) {

			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			if (i != 0) {
				aList.get(Integer.parseInt(inputData.nextToken()) - 1).add(i);
			} else {
				inputData.nextToken();
			}
			req[i] = Integer.parseInt(inputData.nextToken());
			cost[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		dfs(0);

		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		printer.println(totalCost);
		printer.close();
	}

	Data dfs(int index) {
		Data currentData = new Data(0, cost[index]);
		for (int child : aList.get(index)) {
			Data result = dfs(child);
			if (result.minCost < currentData.minCost) {
				currentData.minCost = result.minCost;
			}
			currentData.numPlaced += result.numPlaced;
		}
		if (currentData.numPlaced < req[index]) {
			totalCost += (req[index] - currentData.numPlaced) * currentData.minCost;
			currentData.numPlaced = req[index];
		}
		// System.out.println(index + ": " + currentData.numPlaced + ", " + currentData.minCost);
		return currentData;
	}

	class Data {
		long numPlaced;
		long minCost;

		Data(long a, long b) {
			numPlaced = a;
			minCost = b;
		}
	}

}
