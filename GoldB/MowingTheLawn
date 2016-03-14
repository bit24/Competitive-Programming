
//TODO
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class MowingTheLawn {

	static int numCows;
	static int maxRowLength;

	static long[] prefixSum;

	static long[] dp;

	static MowingTheLawn solver = new MowingTheLawn();

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numCows = Integer.parseInt(inputData.nextToken());
		maxRowLength = Integer.parseInt(inputData.nextToken());

		prefixSum = new long[numCows + 2];
		for (int i = 0; i < numCows; i++) {
			prefixSum[i+1] = prefixSum[i] + Integer.parseInt(reader.readLine());
		}

		TreeSet<Tuple> window = new TreeSet<Tuple>();

		dp = new long[numCows+1];

		window.add(solver.new Tuple(-1, 0));

		for (int current = 0; current <= numCows; current++) {

			while (window.last().index < current - maxRowLength - 1) {
				window.pollLast();
			}

			dp[current] = prefixSum[current] + window.last().value;

			window.add(solver.new Tuple(current, dp[current] - prefixSum[current + 1]));
		}

		System.out.println(dp[numCows]);

	}

	class Tuple implements Comparable<Tuple> {
		long index;
		long value;

		Tuple() {
		}

		Tuple(long a, long b) {
			this.index = a;
			this.value = b;
		}

		public int compareTo(Tuple o) {
			int result = Long.compare(value, o.value);
			if (result == 0) {
				result = Long.compare(index, o.index);
				if (result == 0) {
					result = 1;
				}
			}
			return result;
		}
	}

}
