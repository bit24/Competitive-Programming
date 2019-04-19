import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Div1_441D {

	static int nE;
	static int[] e;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		nE = Integer.parseInt(reader.readLine());

		e = new int[nE];
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		for (int i = 0; i < nE; i++) {
			e[i] = Integer.parseInt(inputData.nextToken());
		}

		int[] hDif = new int[nE];
		hDif[0] = -1;
		for (int i = 1; i < nE; i++) {
			int pos = i - 1;
			while (pos != -1) {
				if ((e[pos] & e[i]) != e[pos]) {
					hDif[i] = pos;
					break;
				}
				pos = hDif[pos];
			}
			hDif[i] = pos;
		}

		boolean[] nAct = new boolean[nE];

		int[] covr = new int[nE];
		int actA = 0;

		int[] sub = new int[nE];

		int[][] needBit = new int[31][nE];
		int[] needBitS = new int[31];

		int[] stack = new int[nE];
		int sCnt = 0;

		long ans = 0;
		for (int i = 0; i < nE; i++) {
			int cE = e[i];
			while (sCnt > 0 && e[stack[sCnt - 1]] <= cE) {
				int rI = stack[sCnt - 1];
				if (!nAct[rI]) {
					actA -= covr[rI];
				} else {
					nAct[rI] = false;
					if(sub[rI] != 0) {
						actA -= sub[rI];
						sub[rI] = 0;
					}
				}
				sCnt--;
			}

			for (int bI = 0; bI < 31; bI++) {
				if ((cE & (1 << bI)) != 0) {
					for (int lI = 0; lI < needBitS[bI]; lI++) {
						int lEI = needBit[bI][lI]; // element index
						if (nAct[lEI]) {
							actA += covr[lEI] - sub[lEI];
							sub[lEI] = 0;
							nAct[lEI] = false;
						}
					}
					needBitS[bI] = 0;
				}
			}

			covr[i] = sCnt > 0 ? i - stack[sCnt - 1] : i + 1;

			nAct[i] = true;

			int uCovr = i - covr[i];
			if (hDif[i] != -1) {
				actA += Math.max(0, hDif[i] - uCovr);
				sub[i] = Math.max(0, hDif[i] - uCovr);
			}

			for (int bI = 0; bI < 31; bI++) {
				if ((cE & (1 << bI)) == 0) {
					needBit[bI][needBitS[bI]++] = i;
				}
			}

			stack[sCnt] = i;
			sCnt++;
			ans += actA;
		}
		printer.println(ans);
		printer.close();
	}
}
