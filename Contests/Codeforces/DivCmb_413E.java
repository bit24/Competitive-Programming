import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class DivCmb_413E {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		final int numO = Integer.parseInt(inputData.nextToken());
		final int numTR = Integer.parseInt(inputData.nextToken());
		final int numLR = Integer.parseInt(inputData.nextToken());

		int[] cost = new int[numO];
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numO; i++) {
			cost[i] = Integer.parseInt(inputData.nextToken());
		}

		boolean[][] like = new boolean[numO][2];
		for (int i = 0; i < 2; i++) {
			int numI = Integer.parseInt(reader.readLine());
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < numI; j++) {
				like[Integer.parseInt(inputData.nextToken()) - 1][i] = true;
			}
		}
		reader.close();

		ArrayList<Integer>[] sLike = new ArrayList[] { new ArrayList<Integer>(), new ArrayList<Integer>() };

		ArrayList<Integer> bLike = new ArrayList<Integer>();
		PriorityQueue<Integer> nQueue = new PriorityQueue<Integer>();

		for (int i = 0; i < numO; i++) {
			if (like[i][0] && like[i][1]) {
				bLike.add(cost[i]);
			} else if (!like[i][0] && !like[i][1]) {
				nQueue.add(cost[i]);
			} else {
				sLike[like[i][0] ? 0 : 1].add(cost[i]);
			}
		}

		Collections.sort(sLike[0]);
		Collections.sort(sLike[1]);
		Collections.sort(bLike);

		ArrayDeque<Integer> bQueue = new ArrayDeque<Integer>(bLike);

		ArrayDeque<Integer>[] sQueue = new ArrayDeque[] { new ArrayDeque<Integer>(sLike[0]),
				new ArrayDeque<Integer>(sLike[1]) };

		ArrayDeque<Integer>[] cItems = new ArrayDeque[] { new ArrayDeque<Integer>(), new ArrayDeque<Integer>(),
				new ArrayDeque<Integer>() };

		long cCost = 0;

		int bLR = (int) max(0L, ((long) numTR) - sQueue[0].size() - sQueue[1].size() - nQueue.size(),
				((long) numLR) - sQueue[0].size(), ((long) numLR) - sQueue[1].size(), 2L * numLR - numTR);

		assert (bLR >= 0);

		if (bLR > bQueue.size() || bLR > numTR) {
			System.out.println(-1);
			return;
		}

		for (int i = 0; i < bLR; i++) {
			cCost += bQueue.remove();
		}

		for (int i = 0; i < 2; i++) {
			assert (sQueue[i].size() >= numLR - bLR);
			for (int j = bLR; j < numLR; j++) {
				int nx = sQueue[i].remove();
				cItems[i].add(nx);
				cCost += nx;
			}
			nQueue.addAll(sQueue[i]);
		}

		for (int i = 0; i < ((long) numTR) - bLR - cItems[0].size() - cItems[1].size(); i++) {
			int nItem = nQueue.remove();
			cCost += nItem;
			cItems[2].add(nItem);
		}

		while (!bQueue.isEmpty()) {
			int nx = bQueue.remove();

			int rep1 = 0;
			for (int i = 0; i < 3; i++) {
				if (!cItems[i].isEmpty() && rep1 < cItems[i].getLast() - nx) {
					rep1 = cItems[i].getLast() - nx;
				}
			}

			if (!cItems[0].isEmpty() && !cItems[1].isEmpty() && !nQueue.isEmpty()
					&& ((long) cItems[0].getLast()) + cItems[1].getLast() - nx - nQueue.peek() > rep1) {
				if (((long) cItems[0].getLast()) + cItems[1].getLast() - nx - nQueue.peek() <= 0) {
					break;
				}

				cCost += ((long) -cItems[0].getLast()) - cItems[1].getLast() + nx + nQueue.remove();

				nQueue.add(cItems[0].removeLast());
				nQueue.add(cItems[1].removeLast());
			} else {
				if (rep1 <= 0) {
					break;
				}

				for (int i = 0; i < 3; i++) {
					if (!cItems[i].isEmpty() && rep1 == cItems[i].getLast() - nx) {
						int rem = cItems[i].removeLast();
						cCost += ((long) -rem) + nx;
						nQueue.add(rem);
						break;
					}
				}
			}
		}

		assert (cCost != -1);
		if (cCost == -1) {
			throw new RuntimeException();
		}
		System.out.println(cCost);
	}

	static long max(long... items) {
		long max = items[0];
		for (long i : items) {
			if (i > max) {
				max = i;
			}
		}
		return max;
	}

}
