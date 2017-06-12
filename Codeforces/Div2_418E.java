import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_418E {

	// solution completely based on that of the editorial

	static final long MOD = 1_000_000_007L;

	static int numV;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		numV = Integer.parseInt(reader.readLine());

		int[] deg = new int[numV + 1];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 1; i <= numV; i++) {
			deg[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		// let assign[A][B][C] represent the number of ways to move to a level with A vertices from a level with B
		// 1-opening vertices and C 2-opening vertices
		long[][][] aCnt = new long[numV + 5][numV + 5][numV + 5];

		aCnt[0][0][0] = 1;

		// loop ordering is non-trivial cnt2 has to be before the cnt1 loop
		for (int cntE = 0; cntE <= numV; cntE++) {
			for (int cnt2 = 0; cnt2 <= numV; cnt2++) {
				for (int cnt1 = 0; cnt1 <= numV; cnt1++) {
					long cCnt = 0;

					// ext, ext
					// ext, t1
					// ext, t2
					// t1, t1
					// t1, t2
					// t2, t2

					if (cnt2 > 0) {
						if (cntE > 1) {
							cCnt += aCnt[cntE - 2][cnt1][cnt2 - 1] * cntE * (cntE - 1) >> 1;
						}
						if (cntE > 0 && cnt1 > 0) {
							cCnt += aCnt[cntE - 1][cnt1 - 1][cnt2 - 1] * cntE * cnt1;
						}
						if (cntE > 0 && cnt2 > 1) {
							cCnt += aCnt[cntE - 1][cnt1 + 1][cnt2 - 2] * cntE * (cnt2 - 1);
						}
						if (cnt1 > 1) {
							cCnt += aCnt[cntE][cnt1 - 2][cnt2 - 1] * cnt1 * (cnt1 - 1) >> 1;
						}
						if (cnt1 > 0 && cnt2 > 1) {
							cCnt += aCnt[cntE][cnt1][cnt2 - 2] * cnt1 * (cnt2 - 1);
						}
						if (cnt2 > 2) {
							cCnt += aCnt[cntE][cnt1 + 2][cnt2 - 3] * (cnt2 - 1) * (cnt2 - 2) >> 1;
						}
						cCnt %= MOD;
						aCnt[cntE][cnt1][cnt2] = cCnt;
					} else if (cnt1 > 0) {
						if (cntE > 0) {
							cCnt += aCnt[cntE - 1][cnt1 - 1][0] * cntE;
						}
						if (cnt1 > 1) {
							cCnt += aCnt[cntE][cnt1 - 2][0] * (cnt1 - 1);
						}
						cCnt %= MOD;
						aCnt[cntE][cnt1][cnt2] = cCnt;
					}
				}
			}
		}

		int[] preCnt1 = new int[numV + 1];
		for (int i = 1; i <= numV; i++) {
			preCnt1[i] = deg[i] == 2 ? 1 + preCnt1[i - 1] : preCnt1[i - 1];
		}

		long[][] ans = new long[numV + 5][numV + 5];

		// smart way of initializing everything with the last object to 1
		ans[numV + 1][numV] = 1;

		for (int l = numV; l > 1; l--) {
			for (int r = l; r <= numV; r++) {
				int cnt1 = preCnt1[r] - preCnt1[l - 1];
				int cnt2 = r - l + 1 - cnt1;

				for (int nLevLen = 0; nLevLen <= (cnt2 << 1) + cnt1 && r + nLevLen <= numV; nLevLen++) {
					ans[l][r] = (ans[l][r] + ans[r + 1][r + nLevLen] * aCnt[nLevLen][cnt1][cnt2]) % MOD;
				}
			}
		}

		System.out.println(ans[2][1 + deg[1]]);
	}

}
