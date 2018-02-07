import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.StringTokenizer;

public class Div1_443D {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nT = Integer.parseInt(inputData.nextToken());
		int nS = Integer.parseInt(inputData.nextToken());
		int nO = Integer.parseInt(inputData.nextToken());

		int[][] traits = new int[nS][nT];

		for (int cC = 0; cC < nS; cC++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int cT = 0; cT < nT; cT++) {
				traits[cC][cT] = Integer.parseInt(inputData.nextToken());
			}
		}

		int nST = nT * nS;

		int[] sTOwn = new int[nST];

		for (int cST = 0; cST < nST; cST++) {
			int cT = cST / nS;
			int cTOwn = cST % nS;

			for (int cand = 0; cand < nS; cand++) {
				if (traits[cand][cT] >= traits[cTOwn][cT]) {
					sTOwn[cST] |= 1 << cand;
				}
			}
		}

		int[] redSI = new int[1 << nS];
		Arrays.fill(redSI, -1);

		ArrayList<Integer> rSTraits = new ArrayList<>();

		for (int cST = 0; cST < nST; cST++) {
			if (redSI[sTOwn[cST]] == -1) {
				redSI[sTOwn[cST]] = rSTraits.size();
				rSTraits.add(cST);
			}
		}

		BitSet[] tBlocks = new BitSet[nS + nO];

		for (int cC = 0; cC < nS; cC++) {
			tBlocks[cC] = new BitSet();
			for (int cRTI = 0; cRTI < rSTraits.size(); cRTI++) {
				if ((sTOwn[rSTraits.get(cRTI)] & (1 << cC)) != 0) {
					tBlocks[cC].set(cRTI);
				}
			}
		}

		int nI = nS;
		for (int i = 0; i < nO; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int op = Integer.parseInt(inputData.nextToken());
			int x = Integer.parseInt(inputData.nextToken()) - 1;
			int y = Integer.parseInt(inputData.nextToken()) - 1;
			if (op == 1) {
				tBlocks[nI] = (BitSet) tBlocks[x].clone();
				tBlocks[nI].or(tBlocks[y]);
				nI++;
			} else if (op == 2) {
				tBlocks[nI] = (BitSet) tBlocks[x].clone();
				tBlocks[nI].and(tBlocks[y]);
				nI++;
			} else {
				int sTB = y * nS;
				int max = 0;
				for (int add = 0; add < nS; add++) {
					int sTI = sTB + add;
					int rTI = redSI[sTOwn[sTI]];
					if (tBlocks[x].get(rTI)) {
						max = Math.max(max, traits[add][y]);
					}
				}
				printer.println(max);
			}
		}
		printer.close();
	}
}
