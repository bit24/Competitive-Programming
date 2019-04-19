import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div1_433A {

	static Integer[] costs;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nF = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		costs = new Integer[nF];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nF; i++) {
			costs[i] = Integer.parseInt(inputData.nextToken());
		}
		PriorityQueue<Integer> cArray = new PriorityQueue<Integer>(cCost);

		for (int i = 0; i < Math.min(nF, k); i++) {
			cArray.add(i);
		}

		int[] ans = new int[nF];

		long total = 0;
		for (int i = k; i < nF + k; i++) {
			if (i < nF) {
				cArray.add(i);
			}
			int nxt = cArray.remove();
			total += (long) (i - nxt) * costs[nxt];
			ans[nxt] = i + 1;
		}

		printer.println(total);
		for (int i = 0; i < nF; i++) {
			printer.print(ans[i] + " ");
		}
		printer.println();
		printer.close();
	}

	static Comparator<Integer> cCost = new Comparator<Integer>() {
		public int compare(Integer o1, Integer o2) {
			return -Integer.compare(costs[o1], costs[o2]);
		}
	};

}
