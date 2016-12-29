import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_386A {

	public static void main(String[] args) throws IOException {
		new Div2_386A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int a = Integer.parseInt(reader.readLine());
		int b = Integer.parseInt(reader.readLine());
		int c = Integer.parseInt(reader.readLine());
		int sum = 0;

		while (a >= 1 && b >= 2 && c >= 4) {
			a -= 1;
			b -= 2;
			c -= 4;
			sum += 7;
		}
		System.out.println(sum);
	}

}
