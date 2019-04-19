import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Div2_432D {

	public static void main(String[] args) throws IOException {
		int[][] pF = new int[1_000_001][7];
		int[] pFC = new int[1_000_001];

		for (int i = 2; i <= 1_000_000; i++) {
			if (pFC[i] == 0) {
				for (int m = i; m <= 1_000_000; m += i) {
					pF[m][pFC[m]++] = i;
				}
			}
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nE = Integer.parseInt(inputData.nextToken());
		long x = Integer.parseInt(inputData.nextToken());
		long y = Integer.parseInt(inputData.nextToken());

		int[] el = new int[nE];
		int[] cnt = new int[1_000_001];

		inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			el[i] = Integer.parseInt(inputData.nextToken());
			for (int j = 0; j < pFC[el[i]]; j++) {
				cnt[pF[el[i]][j]]++;
			}
		}

		long mCost = Long.MAX_VALUE;
		for (int cP = 2; cP <= 1_000_000; cP++) {
			if ((nE - cnt[cP]) * Math.min(x, y) > mCost) {
				continue;
			}
			long cCost = 0;
			for (int i : el) {
				int mod = i % cP;
				int upgrade = mod == 0 ? 0 : cP - mod;
				cCost += Math.min(x, upgrade * y);
			}
			if (cCost < mCost) {
				mCost = cCost;
			}
		}
		System.out.println(mCost);
	}

}
