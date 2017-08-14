import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_427A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int l = Integer.parseInt(inputData.nextToken());
		int s1 = Integer.parseInt(inputData.nextToken());
		int s2 = Integer.parseInt(inputData.nextToken());
		int p1 = Integer.parseInt(inputData.nextToken());
		int p2 = Integer.parseInt(inputData.nextToken());

		if (s1 * l + 2 * p1 < s2 * l + 2 * p2) {
			System.out.println("First");
		} else if (s1 * l + 2 * p1 == s2 * l + 2 * p2) {
			System.out.println("Friendship");
		} else {
			System.out.println("Second");
		}
	}

}
