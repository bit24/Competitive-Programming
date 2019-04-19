import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_404C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long cap = Long.parseLong(inputData.nextToken());
		long add = Long.parseLong(inputData.nextToken());

		if (cap <= add) {
			System.out.println(cap);
			return;
		}

		long low = 0;
		long high = 10_000_000_000L;

		while (low != high) {
			long mid = (low + high) / 2;
			if (add + (mid + 1) * (mid) / 2 >= cap) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}
		System.out.println(add + low);
	}

}
