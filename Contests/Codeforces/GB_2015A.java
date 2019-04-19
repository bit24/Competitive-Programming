import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GB_2015A {

	public static void main(String[] args) throws IOException {
		new GB_2015A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int num = Integer.parseInt(inputData.nextToken());

		inputData.nextToken();

		if (inputData.nextToken().equals("week")) {
			int value = 52;
			if (num == 5 || num == 6) {
				value++;
			}
			System.out.println(value);
		} else {
			int value = 0;
			if (num <= 29) {
				value = 12;
			}
			if (num == 30) {
				value = 11;
			}
			if (num == 31) {
				value = 7;
			}
			System.out.println(value);
		}

	}

}
