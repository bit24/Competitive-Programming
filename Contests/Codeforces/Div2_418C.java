import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Div2_418C {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		final int len = Integer.parseInt(reader.readLine());

		int[] type = new int[len + 2];
		String nLine = reader.readLine();
		for (int i = 1; i <= len; i++) {
			type[i] = nLine.charAt(i - 1) - 'a';
		}
		type[0] = type[len + 1] = -1;

		// stores the cost to get param2 items of type param1
		int[][] minCost = new int[26][len + 1];

		for (int i = 0; i < 26; i++) {
			Arrays.fill(minCost[i], Integer.MAX_VALUE / 8);
		}

		// compute all points of significance
		for (int l = 1; l <= len; l++) {
			int cType = type[l];
			int[] cMinCost = minCost[cType];
			int oTCnt = 0;

			for (int r = l; r <= len; r++) {
				if (type[r] != cType) {
					oTCnt++;
				}
				if (cMinCost[r - l + 1] > oTCnt) {
					cMinCost[r - l + 1] = oTCnt;
				}
			}
		}

		int[][] maxEffect = new int[26][len + 1];

		// smoothing minCost and computing maxEffect
		for (int cType = 0; cType < 26; cType++) {
			int[] cMinCost = minCost[cType];
			int[] cEffect = maxEffect[cType];

			cMinCost[0] = 0;

			for (int cLen = 1; cLen <= len; cLen++) {
				int pos = cMinCost[cLen - 1] + 1;
				if (pos < cMinCost[cLen]) {
					cMinCost[cLen] = pos;
				}

				if (cEffect[cMinCost[cLen]] < cLen) {
					cEffect[cMinCost[cLen]] = cLen;
				}
			}
		}

		// smoothing maxEffect
		for (int cType = 0; cType < 26; cType++) {
			int[] cEffect = maxEffect[cType];
			for (int cLen = 0; cLen <= len - 1; cLen++) {
				if (cEffect[cLen] > cEffect[cLen + 1]) {
					cEffect[cLen + 1] = cEffect[cLen];
				}
			}
		}

		int numQ = Integer.parseInt(reader.readLine());
		while (numQ-- > 0) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int qCost = Integer.parseInt(inputData.nextToken());
			int qType = inputData.nextToken().charAt(0) - 'a';

			printer.println(maxEffect[qType][qCost]);
		}
		printer.close();
	}

}
