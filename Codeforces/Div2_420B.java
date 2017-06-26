import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_420B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int m = Integer.parseInt(inputData.nextToken());
		int b = Integer.parseInt(inputData.nextToken());

		long ans = 0;
		for (int x = 0; true; x++) {
			int y = -((x + m - 1) / m) + b;
			if (y < 0) {
				break;
			}
			ans = Math.max(ans, ((long) (x + 1) * y * (y + 1) >> 1) + ((long) (y + 1) * x * (x + 1) >> 1));
		}
		System.out.println(ans);
	}

}
