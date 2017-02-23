import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Div2_400C {

	public static void main(String[] args) throws IOException {
		new Div2_400C().execute();
	}

	int numE;
	long base;
	long[] elements;

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		base = Long.parseLong(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());

		elements = new long[numE];

		for (int i = 0; i < numE; i++) {
			elements[i] = Long.parseLong(inputData.nextToken());
		}

		long mSum = numE * 1_000_000_000L;

		if (base == 1) {
			System.out.println(sweep(1));
			return;
		}
		if (base == -1) {
			System.out.println(sweep(1) + sweep(-1));
			return;
		}

		long ans = 0;

		long cV = 1;
		while (cV < 0 ? -mSum <= cV : cV <= mSum) {
			ans += sweep(cV);
			cV *= base;
		}
		printer.println(ans);
		printer.close();
	}

	long sweep(long target) {
		HashMap<Long, Integer> count = new HashMap<Long, Integer>();

		long pSum = 0;

		long ans = 0;

		count.put(0L, 1);
		for (int i = 0; i < elements.length; i++) {
			pSum += elements[i];
			if (count.containsKey(pSum - target)) {
				ans += count.get(pSum - target);
			}
			if (!count.containsKey(pSum)) {
				count.put(pSum, 1);
			} else {
				count.put(pSum, count.get(pSum) + 1);
			}
		}
		return ans;
	}
}
