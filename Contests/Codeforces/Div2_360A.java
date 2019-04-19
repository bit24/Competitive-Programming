import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_360A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numOpponents = Integer.parseInt(inputData.nextToken());
		int numDays = Integer.parseInt(inputData.nextToken());
		boolean[] countIncrement = new boolean[numDays];

		for (int i = 0; i < numDays; i++) {
			String inputLine = reader.readLine();
			for (int j = 0; j < numOpponents; j++) {
				if (inputLine.charAt(j) == '0') {
					countIncrement[i] = true;
					break;
				}
			}
		}

		int max = 0;
		for (int i = 0; i < numDays; i++) {

			for (int j = 0; i + j < numDays; j++) {
				if (countIncrement[i + j]) {
					max = Math.max(max, j + 1);
				} else {
					break;
				}
			}

		}
		System.out.println(max);

	}

}
