import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_397A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numI = Integer.parseInt(reader.readLine());

		if ((numI & 1) == 0) {
			System.out.println("home");
		} else {
			System.out.println("contest");
		}
	}

}
