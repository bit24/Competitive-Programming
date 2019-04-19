import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Garage {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nS = Integer.parseInt(inputData.nextToken());
		int nC = Integer.parseInt(inputData.nextToken());
		int[] rates = new int[nS + 1];
		for (int i = 1; i <= nS; i++) {
			rates[i] = Integer.parseInt(reader.readLine());
		}
		int[] wt = new int[nC + 1];
		for (int i = 1; i <= nC; i++) {
			wt[i] = Integer.parseInt(reader.readLine());
		}
		int[] assigned = new int[nC + 1];

		PriorityQueue<Integer> avail = new PriorityQueue<>();
		for (int i = 1; i <= nS; i++) {
			avail.add(i);
		}

		ArrayDeque<Integer> queue = new ArrayDeque<>();

		int sum = 0;
		for (int i = 0; i < 2 * nC; i++) {
			int nxt = Integer.parseInt(reader.readLine());
			if (nxt > 0) {
				if (avail.isEmpty()) {
					queue.add(nxt);
				} else {
					assigned[nxt] = avail.remove();
				}
			} else {
				nxt = -nxt;
				sum += wt[nxt] * (rates[assigned[nxt]]);
				if (queue.isEmpty()) {
					avail.add(assigned[nxt]);
				} else {
					assigned[queue.remove()] = assigned[nxt];
				}
			}
		}
		printer.println(sum);
		printer.close();
	}

}
