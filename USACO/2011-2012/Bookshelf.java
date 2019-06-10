import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Bookshelf {
	static int numE;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("bookshelf.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("bookshelf.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		int mW = Integer.parseInt(inputData.nextToken());

		int[] h = new int[numE + 1];
		int[] w = new int[numE + 1];

		int[] pS = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			inputData = new StringTokenizer(reader.readLine());
			h[i] = Integer.parseInt(inputData.nextToken());
			w[i] = Integer.parseInt(inputData.nextToken());
			pS[i] = pS[i - 1] + w[i];
		}
		reader.close();

		// queue contains cost[left-1] + hBnd
		// query min
		// add 1

		PriorityQueue<Integer> tQ = new PriorityQueue<Integer>();
		PriorityQueue<Integer> rQ = new PriorityQueue<Integer>();
		ArrayDeque<Integer> sI = new ArrayDeque<Integer>();

		int[] cost = new int[numE + 1];
		int[] qVal = new int[numE + 1];

		int lMPos = 1;

		for (int i = 1; i <= numE; i++) {
			while (lMPos < i && pS[i] - pS[lMPos - 1] > mW) {
				lMPos++;
			}
			while (!sI.isEmpty() && sI.peekFirst() < lMPos) {
				rQ.add(qVal[sI.removeFirst()]);
			}

			if (!sI.isEmpty() && qVal[sI.peekFirst()] != cost[lMPos - 1] + h[sI.peekFirst()]) {
				rQ.add(qVal[sI.peekFirst()]);
				tQ.add(cost[lMPos - 1] + h[sI.peekFirst()]);
				qVal[sI.peekFirst()] = cost[lMPos - 1] + h[sI.peekFirst()];
			}

			while (!sI.isEmpty() && h[sI.peekLast()] <= h[i]) {
				rQ.add(qVal[sI.removeLast()]);
			}

			if (sI.isEmpty()) {
				tQ.add(cost[lMPos - 1] + h[i]);
				qVal[i] = cost[lMPos - 1] + h[i];
			} else {
				tQ.add(cost[sI.peekLast()] + h[i]);
				qVal[i] = cost[sI.peekLast()] + h[i];
			}
			sI.add(i);
			while (!rQ.isEmpty() && tQ.peek().equals(rQ.peek())) {
				tQ.remove();
				rQ.remove();
			}
			cost[i] = tQ.peek();
		}
		printer.println(cost[numE]);
		printer.close();
	}

}
