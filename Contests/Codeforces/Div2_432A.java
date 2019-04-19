import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_432A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		int t = Integer.parseInt(inputData.nextToken());

		if (t <= k) {
			System.out.println(t);
		} else if (t <= n) {
			System.out.println(k);
		} else {
			System.out.println(k - (t - n));
		}
	}

}
