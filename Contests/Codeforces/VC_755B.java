import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class VC_755B {

	public static void main(String[] args) throws IOException {
		new VC_755B().execute();
	}

	void execute() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int a = Integer.parseInt(inputData.nextToken());
		int b = Integer.parseInt(inputData.nextToken());

		HashSet<String> firstSet = new HashSet<String>();
		for (int i = 0; i < a; i++) {
			firstSet.add(reader.readLine());
		}

		int both = 0;

		for (int i = 0; i < b; i++) {
			if (firstSet.contains(reader.readLine())) {
				both++;
			}
		}

		a -= both;
		b -= both;

		if (a > b) {
			System.out.println("YES");
			return;
		}
		if (a < b) {
			System.out.println("NO");
			return;
		}

		if ((both & 1) == 0) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
		}
	}
}
