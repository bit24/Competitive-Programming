import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_407C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numI = Integer.parseInt(reader.readLine());
		int[] items = new int[numI + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numI; i++) {
			items[i] = Integer.parseInt(inputData.nextToken());
		}

		long[] pItems = new long[numI];
		for (int i = 1; i < numI; i++) {
			pItems[i] = ((i & 1) == 1 ? 1 : -1) * Math.abs(items[i] - items[i + 1]);
		}

		long ans = Long.MIN_VALUE;

		long prev = 0;
		for (int i = 1; i < numI; i++) {
			prev = prev > 0 ? prev + pItems[i] : pItems[i];
			if (prev > ans) {
				ans = prev;
			}
		}

		for (int i = 1; i < numI; i++) {
			pItems[i] = -pItems[i];
		}

		prev = 0;
		for (int i = 1; i < numI; i++) {
			prev = prev > 0 ? prev + pItems[i] : pItems[i];
			if (prev > ans) {
				ans = prev;
			}
		}
		System.out.println(ans);
	}

}
