import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_411A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int l = Integer.parseInt(inputData.nextToken());
		int r = Integer.parseInt(inputData.nextToken());

		if (l == r) {
			System.out.println(l);
		} else {
			System.out.println(r / 2 - (l - 1) / 2 > r / 3 - (l - 1) / 3 ? 2 : 3);
		}
	}

}
