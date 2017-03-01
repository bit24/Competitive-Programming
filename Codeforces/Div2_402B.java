import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_402B {

	public static void main(String[] args) throws IOException {
		new Div2_402B().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		String n = inputData.nextToken();
		int k = Integer.parseInt(inputData.nextToken());

		int count = 0;
		for (int i = n.length() - 1; i >= 0; i--) {
			if (k == 0) {
				break;
			}
			if (n.charAt(i) == '0') {
				k--;
			} else {
				count++;
			}
		}
		if (k > 0) {
			System.out.println(n.length() - 1);
			return;
		}

		System.out.println(count);
	}

}
