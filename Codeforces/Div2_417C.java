import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Div2_417C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		long bud = Long.parseLong(inputData.nextToken());

		int[] base = new int[numE + 1];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numE; i++) {
			base[i] = Integer.parseInt(inputData.nextToken());
		}

		int low = 0;
		int high = numE;

		long posCost = 0;

		while (low != high) {
			int check = (low + high + 1) / 2;

			PriorityQueue<Long> maxQueue = new PriorityQueue<Long>(Collections.reverseOrder());
			for (int i = 1; i <= numE; i++) {
				maxQueue.add(base[i] + (long) check * i);

				if (maxQueue.size() > check) {
					maxQueue.remove();
				}
			}

			long sum = 0;
			for (long i : maxQueue) {
				sum += i;
			}

			if (sum > bud) {
				high = check - 1;
			} else {
				low = check;
				posCost = sum;
			}
		}

		System.out.println(low + " " + posCost);
	}

}
