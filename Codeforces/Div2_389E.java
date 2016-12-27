import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// credit: Kostroma's editorial
public class Div2_389E {

	public static void main(String[] args) throws IOException {
		new Div2_389E().execute();
	}

	int numD;

	int numE;
	long[] countParts;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numE = Integer.parseInt(inputData.nextToken());
		numD = Integer.parseInt(inputData.nextToken());

		inputData = new StringTokenizer(reader.readLine());

		countParts = new long[10_000_001];
		for (int i = 0; i < numE; i++) {
			countParts[Integer.parseInt(inputData.nextToken())]++;
		}

		int maxToSplit = 10_000_000;
		long setSize = 0;
		int currentAns;

		for (currentAns = 10_000_000; currentAns >= 1; currentAns--) {
			setSize += countParts[currentAns];

			// round down on purpose
			while (maxToSplit / 2 >= currentAns) {
				if (countParts[maxToSplit] != 0) {
					countParts[(maxToSplit + 1) / 2] += countParts[maxToSplit];
					countParts[maxToSplit / 2] += countParts[maxToSplit];
					setSize += countParts[maxToSplit];
					// sanitization
					countParts[maxToSplit] = 0;
				}
				maxToSplit--;
			}
			if (setSize >= numD) {
				System.out.println(currentAns);
				return;
			}
		}
		System.out.println(-1);
	}
}