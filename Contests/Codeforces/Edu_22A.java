import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Edu_22A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numP = Integer.parseInt(reader.readLine());
		int sum = 0;
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		while (numP-- > 0) {
			sum += Integer.parseInt(inputData.nextToken());
		}
		int numT = Integer.parseInt(reader.readLine());

		while (numT-- > 0) {
			inputData = new StringTokenizer(reader.readLine());
			int l = Integer.parseInt(inputData.nextToken());
			int r = Integer.parseInt(inputData.nextToken());
			if (l <= sum && sum <= r) {
				System.out.println(sum);
				return;
			} else if (sum < l) {
				System.out.println(l);
				return;
			}
		}
		System.out.println(-1);
	}

}
