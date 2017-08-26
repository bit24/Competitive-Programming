import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class AboveTheMedianSolver {

	public static void main(String[] args) throws IOException {
		new AboveTheMedianSolver().execute();
	}

	public void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numElements = Integer.parseInt(inputData.nextToken());
		int limit = Integer.parseInt(inputData.nextToken());

		int[] prefixSum = new int[numElements + 1];
		for (int i = 1; i <= numElements; i++) {
			prefixSum[i] = prefixSum[i - 1] + (Integer.parseInt(reader.readLine()) >= limit ? 1 : -1);
		}

		FenwickTree tree = new FenwickTree();
		tree.data = new int[numElements * 4];

		long answer = 0;
		for (int i = 0; i <= numElements; i++) {
			answer += tree.query(prefixSum[i] + numElements + 1);
			tree.update(prefixSum[i] + numElements + 1, 1);
		}
		System.out.println(answer);
	}

	class FenwickTree {
		int[] data;

		public void update(int x, long v) {
			while (x < data.length) {
				data[x] += v;
				x += x & -x;
			}
		}

		public long query(int x) {
			return x > 0 ? data[x] + query(x - (x & -x)) : 0;
		}
	}

}
