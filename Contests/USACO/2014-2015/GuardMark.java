import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GuardMark {

	static int numElements;
	static int goalHeight;

	static long[] height;
	static long[] weight;
	static long[] strength;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("guard.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("guard.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		numElements = Integer.parseInt(inputData.nextToken());
		goalHeight = Integer.parseInt(inputData.nextToken());

		height = new long[numElements];
		weight = new long[numElements];
		strength = new long[numElements];

		for (int i = 0; i < numElements; i++) {
			inputData = new StringTokenizer(reader.readLine());
			height[i] = Long.parseLong(inputData.nextToken());
			weight[i] = Long.parseLong(inputData.nextToken());
			strength[i] = Long.parseLong(inputData.nextToken());
		}
      		reader.close();

		long[] dp = new long[1 << numElements];
		Arrays.fill(dp, -1);
		dp[0] = Long.MAX_VALUE / 4;

		for (int bitSet = 0; bitSet < (1 << numElements); bitSet++) {
			long currentValue = dp[bitSet];
			if (currentValue == -1) {
				continue;
			}

			for (int cElement = 0; cElement < numElements; cElement++) {
				if ((bitSet & (1 << cElement)) == 0) {
					int newBitSet = bitSet ^ (1 << cElement);

					long posValue = Math.min(currentValue - weight[cElement], strength[cElement]);
					if (dp[newBitSet] < posValue) {
						dp[newBitSet] = posValue;
					}
				}
			}
		}

		long ans = -1;
		for (int bitSet = 0; bitSet < (1 << numElements); bitSet++) {
			int currentHeight = 0;
			for (int cElement = 0; cElement < numElements; cElement++) {
				if ((bitSet & (1 << cElement)) != 0) {
					currentHeight += height[cElement];
				}
			}
			if (currentHeight >= goalHeight) {
				ans = Math.max(ans, dp[bitSet]);
			}
		}
		if (ans == -1) {
			printer.println("Mark is too tall");
		} else {
			printer.println(ans);
		}
		printer.close();
	}
}
