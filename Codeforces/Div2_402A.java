import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_402A {

	public static void main(String[] args) throws IOException {
		new Div2_402A().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());

		int[] aCount = new int[6];
		int[] bCount = new int[6];

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			aCount[Integer.parseInt(inputData.nextToken())]++;
		}

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			bCount[Integer.parseInt(inputData.nextToken())]++;
		}

		int ans = 0;
		for (int i = 1; i <= 5; i++) {
			if (((aCount[i] + bCount[i]) & 1) == 1) {
				System.out.println(-1);
				return;
			} else {
				ans += Math.abs((aCount[i] + bCount[i]) / 2 - aCount[i]);
			}
		}
		System.out.println(ans / 2);
	}

}
