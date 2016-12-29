import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_386D {

	public static void main(String[] args) throws IOException {
		new Div2_386D().execute();
	}

	int maxR;

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		inputData.nextToken();
		maxR = Integer.parseInt(inputData.nextToken());
		int numG = Integer.parseInt(inputData.nextToken());
		int numB = Integer.parseInt(inputData.nextToken());

		System.out.println(calculate(numG, numB));
	}

	String calculate(int numG, int numB) {
		if (numB < numG) {
			return reverse(calculate(numB, numG));
		}
		assert (numG <= numB);

		StringBuilder ans = new StringBuilder();
		while (numG != 0 || numB != 0) {
			int iterations = Math.min(maxR, numB - numG);
			if (iterations != 0) {
				for (int i = 0; i < iterations; i++) {
					ans.append('B');
					numB--;
				}
				if (numG == 0) {
					if (numB == 0) {
						return ans.toString();
					} else {
						System.out.println("NO");
						System.exit(0);
					}
				} else {
					ans.append('G');
					numG--;
				}
			} else {
				for (int i = 0; i < numG; i++) {
					ans.append('B');
					ans.append('G');
				}
				return ans.toString();
			}
		}
		return ans.toString();
	}

	String reverse(String input) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			str.append(input.charAt(i) == 'B' ? 'G' : 'B');
		}
		return str.toString();
	}

}
