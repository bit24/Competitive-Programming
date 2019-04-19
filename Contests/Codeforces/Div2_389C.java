import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Div2_389C {

	public static void main(String[] args) throws IOException {
		new Div2_389C().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int numE = Integer.parseInt(reader.readLine());
		String nextLine = reader.readLine();
		reader.close();
		int[] elements = new int[numE];

		for (int i = 0; i < numE; i++) {
			if (nextLine.charAt(i) == 'U') {
				elements[i] = 0;
			}
			if (nextLine.charAt(i) == 'R') {
				elements[i] = 1;
			}
			if (nextLine.charAt(i) == 'D') {
				elements[i] = 2;
			}
			if (nextLine.charAt(i) == 'L') {
				elements[i] = 3;
			}
		}

		boolean[] used = new boolean[4];
		int ans = 1;
		for (int i = 0; i < numE; i++) {
			if (used[(elements[i] + 2) % 4]) {
				ans++;
				used = new boolean[4];
			}
			used[elements[i]] = true;
		}
		System.out.println(ans);
	}

}
