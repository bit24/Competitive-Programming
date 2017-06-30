import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Div2_405E {

	static int numI;

	static int[] items;
	static int[][][][] dp;

	static int[][] cnt;
	static int[][] pos;

	static final int K = 0;
	static final int V = 1;
	static final int O = 2;
	static final int TRUE = 1;
	static final int FALSE = 0;

	public static void push(int[] amt, int lastV) {
		int oCost = dp[amt[0]][amt[1]][amt[2]][lastV];
		if (oCost >= Integer.MAX_VALUE / 2) {
			return;
		}
		for (int nChar = 0; nChar < 3; nChar++) {
			if ((lastV == TRUE && nChar == K)) {
				continue;
			}
			amt[nChar]++;
			
			if (amt[nChar] > cnt[nChar][numI]) {
				amt[nChar]--;
				continue;
			}

			int cInd = pos[nChar][amt[nChar]];

			if (cInd == -1) {
				amt[nChar]--;
				continue;
			}

			int toLeft = Math.max(0, cnt[0][cInd] - amt[0]) + Math.max(0, cnt[1][cInd] - amt[1])
					+ Math.max(0, cnt[2][cInd] - amt[2]);

			int nCost = oCost + toLeft;
			if (nCost < dp[amt[0]][amt[1]][amt[2]][nChar == V ? TRUE : FALSE]) {
				dp[amt[0]][amt[1]][amt[2]][nChar == V ? TRUE : FALSE] = nCost;
			}
			amt[nChar]--;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numI = Integer.parseInt(reader.readLine());
		items = new int[numI + 1];
		String nLine = reader.readLine();
		for (int i = 0; i < numI; i++) {
			switch (nLine.charAt(i)) {
				case 'V':
					items[i + 1] = V;
					break;
				case 'K':
					items[i + 1] = K;
					break;
				default:
					items[i + 1] = O;
			}
		}
		int[] cCnt = new int[3];

		cnt = new int[3][numI + 1];
		pos = new int[3][numI + 1];

		for (int i = 0; i < 3; i++) {
			Arrays.fill(cnt[i], -1);
			Arrays.fill(pos[i], -1);
		}

		for (int i = 1; i <= numI; i++) {
			cCnt[items[i]]++;
			cnt[0][i] = cCnt[0];
			cnt[1][i] = cCnt[1];
			cnt[2][i] = cCnt[2];
			pos[items[i]][cCnt[items[i]]] = i;
		}

		dp = new int[numI + 1][numI + 1][numI + 1][2];
		for (int[][][] a : dp) {
			for (int[][] b : a) {
				for (int[] c : b) {
					c[0] = c[1] = Integer.MAX_VALUE / 2;
				}
			}
		}

		dp[0][0][0][0] = 0;

		for (int i = 0; i <= numI; i++) {
			for (int j = 0; j <= numI; j++) {
				for (int k = 0; k <= numI; k++) {
					for (int lV = 0; lV < 2; lV++) {
						push(new int[] { i, j, k }, lV);
					}
				}
			}
		}
		int ans = Math.min(dp[cnt[0][numI]][cnt[1][numI]][cnt[2][numI]][0],
				dp[cnt[0][numI]][cnt[1][numI]][cnt[2][numI]][1]);
		System.out.println(ans);
	}

}
