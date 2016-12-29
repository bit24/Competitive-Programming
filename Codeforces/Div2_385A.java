import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Div2_385A {

	public static void main(String[] args) throws IOException {
		new Div2_385A().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringBuilder input = new StringBuilder(reader.readLine());
		HashSet<String> seen = new HashSet<String>();

		for (int i = 0; i < input.length(); i++) {
			seen.add(input.toString());
			input.append(input.charAt(0));
			input.deleteCharAt(0);
		}
		System.out.println(seen.size());

	}

}
