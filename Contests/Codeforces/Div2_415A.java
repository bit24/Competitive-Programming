import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_415A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());

		int s = 0;
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < n; i++) {
			s += Integer.parseInt(inputData.nextToken());
		}
		System.out.println(Math.max(0, n * (2 * k - 1) - 2 * s));
	}

}
