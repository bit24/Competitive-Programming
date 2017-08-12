import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_425A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		long total = Long.parseLong(inputData.nextToken());
		long turn = Long.parseLong(inputData.nextToken());

		if (total % (2 * turn) < turn) {
			System.out.println("NO");
		} else {
			System.out.println("YES");
		}
	}

}
