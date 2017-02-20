import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_391B {

	public static void main(String[] args) throws IOException {
		new Div2_391B().execute();
	}

	int[] primes = new int[9592];
	int[][] fIndex = new int[100_001][10];

	void execute() throws IOException {
		fPrimes();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numE = Integer.parseInt(reader.readLine());

		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		int[] count = new int[9592];
		for (int i = 0; i < numE; i++) {
			int cNum = Integer.parseInt(inputData.nextToken());
			for (int j = 0; fIndex[cNum][j] != -1; j++) {
				count[fIndex[cNum][j]]++;
			}
		}

		int max = 1;
		for (int i = 0; i < 9592; i++) {
			if (count[i] > max) {
				max = count[i];
			}
		}
		System.out.println(max);
	}

	void fPrimes() {
		for (int i = 0; i <= 100_000; i++) {
			for (int j = 0; j < 10; j++) {
				fIndex[i][j] = -1;
			}
		}
		int pCount = 0;

		for (int i = 2; i <= 100_000; i++) {
			int fCount = 0;
			for (int j = 0; j < pCount && primes[j] * 2 <= i; j++) {
				if (i % primes[j] == 0) {
					fIndex[i][fCount++] = j;
				}
			}
			if (fCount == 0) {
				fIndex[i][0] = pCount;
				primes[pCount++] = i;
			}
		}
	}

}
