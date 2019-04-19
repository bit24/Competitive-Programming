import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DivCmb_414A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		int lE = Integer.parseInt(inputData.nextToken());
		int rE = Integer.parseInt(inputData.nextToken());

		int numB = Integer.parseInt(reader.readLine());
		inputData = new StringTokenizer(reader.readLine());

		int ans = 0;
		for (int i = 0; i < numB; i++) {
			int nx = Integer.parseInt(inputData.nextToken());
			if (lE < nx && nx < rE) {
				ans++;
			}
		}
		System.out.println(ans);
	}

}
