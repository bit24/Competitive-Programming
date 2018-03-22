
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Photo {

	class MaxMonotonicQueue {
		LinkedList<Integer> queue = new LinkedList<Integer>();

		LinkedList<Integer> max = new LinkedList<Integer>();

		public void insert(int x) {
			queue.addLast(x);
			while (!max.isEmpty() && max.peekLast() < x) {
				max.removeLast();
			}
			max.addLast(x);
		}

		public int delete() {
			int result = queue.removeFirst();
			if (result == max.peekFirst()) {
				max.removeFirst();
			}
			return result;
		}

		public int max() {
			if (max.isEmpty()) {
				return 0;
			}
			return max.peekFirst();
		}
	}

	public static void main(String[] args) throws IOException {
		MaxMonotonicQueue queue = new Photo().new MaxMonotonicQueue();

		BufferedReader reader = new BufferedReader(new FileReader("photo.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("photo.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numCows = Integer.parseInt(inputData.nextToken());
		int numPhotos = Integer.parseInt(inputData.nextToken());

		int[] rangeMin = new int[numCows + 3];
		Arrays.fill(rangeMin, numCows + 2);

		int[] rangeMax = new int[numCows + 3];

		for (int i = 0; i < numPhotos; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int left = Integer.parseInt(inputData.nextToken());
			int right = Integer.parseInt(inputData.nextToken());
			rangeMin[left] = Math.min(rangeMin[left], right + 1);
			rangeMax[left] = Math.max(rangeMax[left], right + 1);
		}
		reader.close();

		rangeMin[numCows + 1] = numCows + 2;
		for (int i = numCows - 1; i >= 0; i--) {
			rangeMin[i] = Math.min(rangeMin[i], rangeMin[i + 1]);
		}

		for (int i = 1; i <= numCows; i++) {
			rangeMax[i] = Math.max(rangeMax[i], rangeMax[i - 1]);
		}
		int[] dp = new int[numCows + 3];
		dp[numCows + 1] = 0;

		int totalLow = numCows;
		int totalHigh = numCows;

		for (int i = numCows; i >= 0; i--) {
			int rangeLow = Math.max(i + 1, rangeMax[i]);
			int rangeHigh = rangeMin[i + 1];

			for (; rangeLow <= totalLow; totalLow--) {
				queue.insert(dp[totalLow]);
			}

			for (; totalLow < totalHigh && rangeHigh <= totalHigh; totalHigh--) {
				queue.delete();
			}
			dp[i] = rangeLow < rangeHigh ? queue.max() : -1;
			if (i != 0 && dp[i] != -1) {
				dp[i]++;
			}
		}

		printer.println(dp[0]);
		printer.close();
	}

}