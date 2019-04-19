import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div1_411A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(reader.readLine());
		System.out.println((n + 1) / 2 - 1);
	}
}
