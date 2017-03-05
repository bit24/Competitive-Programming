import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_760A {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int m = Integer.parseInt(inputData.nextToken());
		int wD = Integer.parseInt(inputData.nextToken());

		int nD;
		if (m == 2) {
			nD = 28;
		} else if (m <= 7 && (m & 1) == 1) {
			nD = 31;
		} else if (8 <= m && (m & 1) == 0) {
			nD = 31;
		} else {
			nD = 30;
		}

		int cD = 1;
		int numC = 1;

		if (wD != 1) {
			cD += 8 - wD;
			wD = 1;
			numC++;
		}

		while (cD + 7 <= nD) {
			cD += 7;
			numC++;
		}
		System.out.println(numC);

	}

}
