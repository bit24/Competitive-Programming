import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_405C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int numE = Integer.parseInt(inputData.nextToken());
		int K = Integer.parseInt(inputData.nextToken());

		int[] el = new int[numE];

		int nI = 1;

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < numE; i++) {
			if (el[i] == 0) {
				el[i] = nI++;
			}
			if (i + K - 1 < numE && inputData.nextToken().charAt(0) == 'N') {
				el[i + K - 1] = el[i];
			}
		}

		StringBuilder output = new StringBuilder();
		for (int i = 0; i < numE; i++) {
			appendString(output, el[i]);
		}
		System.out.println(output);
	}

	static void appendString(StringBuilder current, int ind) {
		current.append('A');
		while (ind > 0) {
			current.append((char) ('a' + ind % 26));
			ind /= 26;
		}
		current.append(' ');
	}

}
