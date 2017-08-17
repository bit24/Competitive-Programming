import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_424F {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numB = Integer.parseInt(inputData.nextToken());
		long bound = Long.parseLong(inputData.nextToken());

		int[] target = new int[numB];
		inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < numB; i++) {
			int cHeight = Integer.parseInt(inputData.nextToken());
			target[i] = cHeight;
			bound += cHeight;
		}

		int sz = 0;
		int[] beginPt = new int[7_000_000];

		for (int i = 0; i < numB; i++) {
			int cHeight = target[i];

			int sqrt = (int) Math.sqrt(cHeight);

			for (int j = 1; j <= sqrt; j++) {
				beginPt[sz++] = j;
				beginPt[sz++] = (cHeight + j - 1) / j;
			}
		}
		Arrays.sort(beginPt, 0 , sz);

		long ans = 0;
		for (int i = 0; i < sz; i++) {
			int dBegin = beginPt[i];
			while (i + 1 < sz && beginPt[i + 1] == dBegin) {
				i++;
			}
			long sum = 0;
			for (int j = 0; j < numB; j++) {
				sum += (target[j] + dBegin - 1) / dBegin;
			}
			long d = bound / sum;
			if (dBegin <= d && ans < d) {
				ans = d;
			}
		}
		System.out.println(ans);
	}

}
