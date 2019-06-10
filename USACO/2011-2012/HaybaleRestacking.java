import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class HaybaleRestacking {
	static int nE;
	static int sum;
	static int[] start;
	static int[] end;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("restack.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("restack.out")));
		nE = Integer.parseInt(reader.readLine());
		start = new int[nE];
		end = new int[nE];
		for (int i = 0; i < nE; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			sum += start[i] = Integer.parseInt(inputData.nextToken());
			end[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		int low = -sum;
		int high = sum;

		while (low < high) {
			int sum = low + high;
			int lMid = sum < 0 ? (sum - 1) / 2 : sum / 2;
			int uMid = lMid + 1;
			long lCost = solve(lMid);
			long uCost = solve(uMid);
			if (lCost == uCost) {
				printer.println(lCost);
				printer.close();
				return;
			} else if (lCost < uCost) {
				high = lMid;
			} else {
				low = uMid;
			}
		}
		printer.println(solve(low));
		printer.close();
	}

	static long solve(int flow) {
		int cur = flow;
		long cost = 0;
		for (int i = 0; i < nE; i++) {
			cost += Math.abs(cur);
			cur += start[i];
			cur -= end[i];
		}
		return cost;
	}

}
