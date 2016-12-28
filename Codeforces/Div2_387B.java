import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Div2_387B {

	public static void main(String[] args) throws IOException {
		new Div2_387B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int length = Integer.parseInt(reader.readLine());
		if (length % 4 != 0) {
			System.out.println("===");
			return;
		}

		int target = length / 4;

		String inputString = reader.readLine();

		int[] numLeft = new int[4];
		Arrays.fill(numLeft, target);

		for (int i = 0; i < inputString.length(); i++) {
			if (inputString.charAt(i) != '?') {
				numLeft[toInt(inputString.charAt(i))]--;
			}
		}

		for (int i = 0; i < 4; i++) {
			if (numLeft[i] < 0) {
				System.out.println("===");
				return;
			}
		}

		StringBuilder ans = new StringBuilder();
		for (int i = 0; i < inputString.length(); i++) {
			if (inputString.charAt(i) != '?') {
				ans.append(inputString.charAt(i));
			} else {
				for (int j = 0; j < 4; j++) {
					if (numLeft[j] != 0) {
						ans.append(toChar(j));
						numLeft[j]--;
						break;
					}
				}
			}
		}
		System.out.println(ans.toString());
	}

	int toInt(char c) {
		switch (c) {
		case 'A':
			return 0;
		case 'C':
			return 1;
		case 'G':
			return 2;
		case 'T':
			return 3;
		}
		return -1;
	}

	char toChar(int v) {
		switch (v) {
		case 0:
			return 'A';
		case 1:
			return 'C';
		case 2:
			return 'G';
		case 3:
			return 'T';
		}
		return ' ';
	}

}
