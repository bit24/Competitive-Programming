import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_428A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nD = Integer.parseInt(inputData.nextToken());
		int qA = Integer.parseInt(inputData.nextToken());

		int stock = 0;
		int given = 0;
		inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= nD; i++) {
			stock += Integer.parseInt(inputData.nextToken());
			int delta = Math.min(stock, 8);
			stock -= delta;
			given += delta;
			if (given >= qA) {
				System.out.println(i);
				return;
			}
		}
		System.out.println(-1);
	}

}
