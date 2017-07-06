import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_404B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int l1 = 0;
		int r1 = Integer.MAX_VALUE;
		int numI = Integer.parseInt(reader.readLine());

		while (numI-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int cur = Integer.parseInt(inputData.nextToken());
			if (cur > l1) {
				l1 = cur;
			}
			cur = Integer.parseInt(inputData.nextToken());
			if (cur < r1) {
				r1 = cur;
			}
		}

		int l2 = 0;
		int r2 = Integer.MAX_VALUE;
		numI = Integer.parseInt(reader.readLine());

		while (numI-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int cur = Integer.parseInt(inputData.nextToken());
			if (cur > l2) {
				l2 = cur;
			}
			cur = Integer.parseInt(inputData.nextToken());
			if (cur < r2) {
				r2 = cur;
			}
		}

		System.out.println(Math.max(0, Math.max(l1 - r2, l2 - r1)));
	}

}
