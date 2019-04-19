import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Div1_393B {

	public static void main(String[] args) throws IOException {
		new Div1_393B().execute();
	}

	int[] length = new int[] { 1, 90, 1440 };
	int[] cost = new int[] { 20, 50, 120 };

	int numE;
	int[] times;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		numE = Integer.parseInt(reader.readLine());
		times = new int[numE + 1];

		times[0] = Integer.MIN_VALUE;
		for (int i = 1; i <= numE; i++) {
			times[i] = Integer.parseInt(reader.readLine());
		}
		reader.close();

		int[] minCost = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			minCost[i] = Integer.MAX_VALUE / 3;
		}
		minCost[0] = 0;

		for (int i = 1; i <= numE; i++) {
			int min = Integer.MAX_VALUE / 3;
			for (int tr = 0; tr < 3; tr++) {
				int iI = after(times[i] - length[tr]);
				if (min > minCost[iI - 1] + cost[tr]) {
					min = minCost[iI - 1] + cost[tr];
				}
			}
			minCost[i] = min;
			printer.println(minCost[i] - minCost[i - 1]);
		}
		printer.close();
	}

	int after(int index) {
		int left = 0;
		int right = numE;

		while (left != right) {
			int mid = (left + right) / 2;
			if (index < times[mid]) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}
		return left;
	}

}
