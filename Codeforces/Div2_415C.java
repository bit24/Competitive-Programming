import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_415C {

	static final long MOD = 1_000_000_007L;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		final int numE = Integer.parseInt(reader.readLine());

		Integer[] pos = new Integer[numE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());

		for (int i = 0; i < numE; i++) {
			pos[i] = Integer.parseInt(inputData.nextToken());
		}
		Arrays.sort(pos);

		long cPow = 1;

		long stS = 0;

		long ans = stS;
		for (int r = 1; r < numE; r++) {
			int delta = pos[r] - pos[r - 1];
			stS = (((stS + cPow * delta) << 1) - delta) % MOD;
			cPow <<= 1;
			if (cPow >= MOD) {
				cPow -= MOD;
			}
			ans += stS;
			if (ans >= MOD) {
				ans -= MOD;
			}
		}

		System.out.println(ans);
	}

}
