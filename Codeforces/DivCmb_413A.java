import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DivCmb_413A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		int t = Integer.parseInt(inputData.nextToken());
		int k = Integer.parseInt(inputData.nextToken());
		int d = Integer.parseInt(inputData.nextToken());

		System.out.println(d < t * ((n - 1) / k) ? "YES" : "NO");
	}

}
