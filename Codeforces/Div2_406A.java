import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_406A {

	static int gcd;
	static int pRes;
	static int qRes;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int b = Integer.parseInt(inputData.nextToken());
		int a = Integer.parseInt(inputData.nextToken());
		inputData = new StringTokenizer(reader.readLine());
		int d = Integer.parseInt(inputData.nextToken());
		int c = Integer.parseInt(inputData.nextToken());

		for (int i = 0; i <= 100; i++) {
			int cT = a + b * i;

			if (c <= cT && (cT - c) % d == 0) {
				System.out.println(cT);
				return;
			}
		}
		System.out.println(-1);
	}

}
