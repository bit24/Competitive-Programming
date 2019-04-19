import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_417B {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numR = Integer.parseInt(inputData.nextToken());
		int numC = Integer.parseInt(inputData.nextToken()) + 2;

		int[] first = new int[numR + 1];
		int[] last = new int[numR + 1];

		for (int i = numR; i >= 1; i--) {
			String nLine = reader.readLine();
			for (int j = 1; j <= numC; j++) {
				if (nLine.charAt(j - 1) == '1') {
					if (first[i] == 0) {
						first[i] = j;
					}
					last[i] = j;
				}
			}
			if (i == numR && first[i] == 0) {
				numR--;
			}
		}

		if (numR == 0) {
			System.out.println(0);
			return;
		}

		int[][] minC = new int[numR + 1][2];

		minC[0][1] = numC - 1;

		for (int i = 1; i <= numR; i++) {
			if (first[i] == 0) {
				minC[i][0] = min(minC[i - 1][0], minC[i - 1][1] + numC - 1);
				minC[i][1] = min(minC[i - 1][1], minC[i - 1][0] + numC - 1);
			}
			else{
				minC[i][0] = min(minC[i - 1][0] + (last[i] - 1 << 1), minC[i - 1][1] + numC - 1);
				minC[i][1] = min(minC[i - 1][1] + (numC - first[i] << 1), minC[i - 1][0] + numC - 1);
			}
		}
		System.out.println(numR - 1 + min(minC[numR - 1][0] + last[numR] - 1, minC[numR - 1][1] + numC - first[numR]));
	}

	static int min(int a, int b) {
		return a < b ? a : b;
	}

}
