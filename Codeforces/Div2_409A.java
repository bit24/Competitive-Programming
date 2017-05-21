import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_409A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String line = reader.readLine();

		int count = 0;
		for (int i = 1; i < line.length(); i++) {
			if (line.charAt(i - 1) == 'V' && line.charAt(i) == 'K') {
				count++;
			}
		}

		for (int i = 1; i < line.length(); i++) {
			if (line.charAt(i - 1) == 'V' && line.charAt(i) != 'K'
					&& (i == line.length() - 1 || line.charAt(i) != 'V' || line.charAt(i + 1) != 'K')) {
				System.out.println(count + 1);
				return;
			}
			if (line.charAt(i) == 'K' && line.charAt(i - 1) != 'V'
					&& (i == 1 || line.charAt(i - 1) != 'K' || line.charAt(i - 2) != 'V')) {
				System.out.println(count + 1);
				return;
			}
		}
		System.out.println(count);
	}

}
