import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_419E {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numV = Integer.parseInt(inputData.nextToken());
		long bud = Integer.parseInt(inputData.nextToken());

		ArrayList<Integer>[] chldn = new ArrayList[numV + 1];
		for (int i = 1; i <= numV; i++) {
			chldn[i] = new ArrayList<Integer>();
		}

		long[] orig = new long[numV + 1];
		long[] sale = new long[numV + 1];

		for (int i = 1; i <= numV; i++) {
			inputData = new StringTokenizer(reader.readLine());
			orig[i] = Long.parseLong(inputData.nextToken());
			sale[i] = orig[i] - Long.parseLong(inputData.nextToken());
			if (i != 1) {
				chldn[Integer.parseInt(inputData.nextToken())].add(i);
			}
		}

		int[] sSize = new int[numV + 1];

		for (int sR = numV; sR >= 1; sR--) {
			sSize[sR] = 1;
			for (int chld : chldn[sR]) {
				sSize[sR] += sSize[chld];
			}
		}

		// mCost[0][b][c] stores the mCost to get c items from the subtree rooted at b, given that b can't be on sale
		// mCost[1][b][c] stores the mCost to get c items from the subtree rooted at b, given that b can be on sale
		long[][][] mCost = new long[2][numV + 1][];

		for (int sR = numV; sR >= 1; sR--) {
			// first iteration of the loop will compute mCost for all obtainable amounts of items without coupons
			// second iteration of the loop will compute mCost for all obtainable amounts of items with coupons
			for (int canS = 0; canS <= 1; canS++) {

				long[] cMCost = mCost[canS][sR] = new long[sSize[sR] + 1];
				Arrays.fill(cMCost, Long.MAX_VALUE / 8);
				cMCost[0] = 0;
				cMCost[1] = canS == 1 ? sale[sR] : orig[sR];

				// maximum number of items presently obtainable
				int cMaxObt = 1;

				for (int chld : chldn[sR]) {
					// if canS is true, then the first item can't be changed otherwise do as you wish
					for (int oUsed = cMaxObt; oUsed >= canS; oUsed--) {
						for (int nUsed = 1; nUsed <= sSize[chld]; nUsed++) {
							if (cMCost[oUsed] + mCost[canS][chld][nUsed] < cMCost[oUsed + nUsed]) {
								cMCost[oUsed + nUsed] = cMCost[oUsed] + mCost[canS][chld][nUsed];
							}
						}
					}
					cMaxObt += sSize[chld];
				}
			}

			// if not using coupons results in a lower cost than using coupons, we'll note that
			for (int cAmt = 0; cAmt <= sSize[sR]; cAmt++) {
				if (mCost[0][sR][cAmt] < mCost[1][sR][cAmt]) {
					mCost[1][sR][cAmt] = mCost[0][sR][cAmt];
				}
			}
		}

		long[] fCost = mCost[1][1];
		for (int amt = 1; amt <= numV; amt++) {
			if (fCost[amt] > bud) {
				System.out.println(amt - 1);
				return;
			}
		}
		System.out.println(numV);
	}

}
