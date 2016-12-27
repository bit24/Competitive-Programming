import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class Div2_389B {

	public static void main(String[] args) throws IOException {
		new Div2_389B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

		String original = reader.readLine();
		String output = reader.readLine();

		LinkedHashMap<Character, Character> swapped = new LinkedHashMap<Character, Character>();

		for (int i = 0; i < original.length(); i++) {
			if (!swapped.containsKey(original.charAt(i))) {
				if (swapped.containsKey(output.charAt(i))) {
					System.out.println(-1);
					return;
				}
				swapped.put(original.charAt(i), output.charAt(i));
				swapped.put(output.charAt(i), original.charAt(i));
			} else {
				if (!swapped.get(original.charAt(i)).equals(output.charAt(i))) {
					System.out.println(-1);
					return;
				}
			}
		}

		int count = 0;
		for (Character i : swapped.keySet()) {
			if (i > swapped.get(i)) {
				count++;
			}
		}

		printer.println(count);

		for (Character i : swapped.keySet()) {
			if (i > swapped.get(i)) {
				printer.println(i + " " + swapped.get(i));
			}
		}
		printer.close();
	}

	class Pair {
		char a;
		char b;

		Pair(char a, char b) {
			this.a = a;
			this.b = b;
		}
	}

}
