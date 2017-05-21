import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Yandex2011_D {

	public static void main(String[] args) throws IOException {
		new Yandex2011_D().execute();
	}

	int numE;
	int sqrt;
	int numQ;

	int[] elements;
	Pair[] queries;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numE = Integer.parseInt(inputData.nextToken());
		sqrt = (int) Math.ceil(Math.sqrt(numE));
		int numQ = Integer.parseInt(inputData.nextToken());

		elements = new int[numE];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			elements[i] = Integer.parseInt(inputData.nextToken());
		}

		queries = new Pair[numQ];

		for (int i = 0; i < numQ; i++) {
			inputData = new StringTokenizer(reader.readLine());
			queries[i] = new Pair(Integer.parseInt(inputData.nextToken()) - 1, Integer.parseInt(inputData.nextToken()) - 1, i);
		}
		reader.close();

		Arrays.sort(queries);

		int[] count = new int[1_000_001];
		count[elements[0]] = 1;
		long cVal = elements[0];

		int cLeft = 0;
		int cRight = 0;

		long[] qAns = new long[numQ];

		for (Pair query : queries) {
			while (cLeft < query.left) {
				long cCount = count[elements[cLeft]]--;
				cVal += elements[cLeft] * (-(cCount << 1) + 1);
				cLeft++;
			}
			while (cLeft > query.left) {
				cLeft--;
				long cCount = count[elements[cLeft]]++;
				cVal += elements[cLeft] * ((cCount << 1) + 1);
			}
			while (cRight < query.right) {
				cRight++;
				long cCount = count[elements[cRight]]++;
				cVal += elements[cRight] * ((cCount << 1) + 1);
			}
			while (cRight > query.right) {
				long cCount = count[elements[cRight]]--;
				cVal += elements[cRight] * (-(cCount << 1) + 1);
				cRight--;
			}
			qAns[query.index] = cVal;
		}
		
		for(long cAns : qAns){
			printer.println(cAns);
		}
		printer.close();
	}

	class Pair implements Comparable<Pair> {
		int left;
		int right;
		int index;

		Pair(int left, int right, int index) {
			this.left = left;
			this.right = right;
			this.index = index;
		}

		public int compareTo(Pair o) {
			return left / sqrt < o.left / sqrt ? -1
					: (left / sqrt == o.left / sqrt ? (right < o.right ? -1 : (right == o.right ? 0 : 1)) : 1);
		}
	}

}
