import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class shitori {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int n = Integer.parseInt(reader.readLine());
		char prev;
		String first = reader.readLine();
		prev = first.charAt(first.length() - 1);
		HashSet<String> used = new HashSet<String>();
		used.add(first);
		for(int i = 1; i < n; i++) {
			String line = reader.readLine();
			if(used.contains(line) || line.charAt(0) != prev) {
				System.out.println("Player " + (i % 2 + 1) + " lost");
				return;
			}
			prev = line.charAt(line.length() - 1);
			used.add(line);
		}
		System.out.println("Fair Game");
	}

}
