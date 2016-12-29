import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Div2_386B {

	public static void main(String[] args) throws IOException {
		new Div2_386B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		reader.readLine();
		String inputData = reader.readLine();

		ArrayDeque<Character> answer = new ArrayDeque<Character>();

		boolean toggle = (inputData.length() & 1) == 1;
		for (int i = 0; i < inputData.length(); i++) {
			if (toggle) {
				answer.addLast(inputData.charAt(i));
			} else {
				answer.addFirst(inputData.charAt(i));
			}
			toggle = !toggle;
		}

		StringBuilder str = new StringBuilder();
		while (!answer.isEmpty()) {
			str.append(answer.removeFirst());
		}
		System.out.println(str.toString());
	}

}
