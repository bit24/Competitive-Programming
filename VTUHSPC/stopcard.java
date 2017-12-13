import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class stopcard {

	static double permute(int nI, int nC) {
		double cnt = 1;
		for (int cC = 0; cC < nC; cC++) {
			cnt *= (nI - cC);
		}
		return cnt;
	}

	static double fact(int n) {
		return permute(n, n);
	}

	static int c;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int n = Integer.parseInt(inputData.nextToken());
		c = Integer.parseInt(inputData.nextToken());
		int[] deck = new int[n];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < n; i++) {
			deck[i] = Integer.parseInt(inputData.nextToken());
		}
		Arrays.sort(deck);
		
		if(c == 0) {
			double num = 0;
			for(int i = 0; i < n; i++) {
				num += deck[i];
			}
			System.out.printf("%.6f", num / n);
			System.out.println();
			return;
		}

		double num = 0;
		for (int mB = 0; mB < n; mB++) {
			int less = mB;
			for (int nB = c; nB <= less + 1; nB++) {
				for (int g = mB + 1; g < n; g++) {
					num += deck[g] * fact(n - nB - 1) * c * permute(less, nB - 1);
				}
			}
		}

		for (int i = 0; i < n - 1; i++) {
			num += c * fact(n - 2) * deck[i];
		}
		double ans = num / fact(n);
		System.out.printf("%.6f", ans);
		System.out.println();
	}

}
