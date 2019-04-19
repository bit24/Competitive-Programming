import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Div2_391C {

	int numG;
	int numE;

	public static void main(String[] args) throws IOException {
		new Div2_391C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		numG = Integer.parseInt(inputData.nextToken());
		numE = Integer.parseInt(inputData.nextToken());

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] owners = new ArrayList[numE];
		for (int i = 0; i < numE; i++) {
			owners[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < numG; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int count = Integer.parseInt(inputData.nextToken());
			while (count-- > 0) {
				owners[Integer.parseInt(inputData.nextToken()) - 1].add(i);
			}
		}
		reader.close();

		for (int i = 0; i < numE; i++) {
			Collections.sort(owners[i]);
		}

		Arrays.sort(owners, comp);

		long count = 1;
		final long MOD = 1_000_000_007;

		long[] factorial = new long[numE + 1];
		factorial[1] = 1;

		for (int i = 2; i <= numE; i++) {
			factorial[i] = (i * factorial[i - 1]) % MOD;
		}

		for (int i = 0; i < numE;) {
			int j;
			for (j = i; j + 1 < numE && comp.compare(owners[i], owners[j + 1]) == 0;) {
				j = j + 1;
			}

			count = (count * factorial[j - i + 1]) % MOD;
			i = j + 1;
		}
		System.out.println(count);
	}

	Comparator<ArrayList<Integer>> comp = new Comparator<ArrayList<Integer>>() {
		public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
			if (o1.size() < o2.size()) {
				return -1;
			}
			if (o1.size() > o2.size()) {
				return 1;
			}

			for (int i = 0; i < o1.size(); i++) {
				int a = o1.get(i);
				int b = o2.get(i);
				if (a < b) {
					return -1;
				}
				if (a > b) {
					return 1;
				}
			}
			return 0;
		}
	};

}
