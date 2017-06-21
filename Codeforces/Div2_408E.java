import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Div2_408E {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int numE = Integer.parseInt(inputData.nextToken());
		int numO = Integer.parseInt(inputData.nextToken());
		int oLen = Integer.parseInt(inputData.nextToken());
		numO = Math.min(numO, 2 * ((numE - 1) / oLen + 1));

		boolean[][] ansd = new boolean[2][numE + 1];
		for (int i = 0; i < 2; i++) {
			inputData = new StringTokenizer(reader.readLine());
			int numA = Integer.parseInt(inputData.nextToken());
			for (int j = 0; j < numA; j++) {
				ansd[i][Integer.parseInt(inputData.nextToken())] = true;
			}
		}

		int[][] preSum = new int[2][numE + 1];

		int[] preISum = new int[numE + 1];
		for (int i = 1; i <= numE; i++) {
			preSum[0][i] = ansd[0][i] ? preSum[0][i - 1] + 1 : preSum[0][i - 1];
			preSum[1][i] = ansd[1][i] ? preSum[1][i - 1] + 1 : preSum[1][i - 1];
			preISum[i] = ansd[0][i] && ansd[1][i] ? preISum[i - 1] + 1 : preISum[i - 1];
		}

		int[][][] oDP = new int[2][numE + 1][oLen + 1];
		int[][][] nDP = new int[2][numE + 1][oLen + 1];

		for (int cObs = 1; cObs <= numO; cObs++) {
			for (int ahI = 0; ahI <= numE; ahI++) {
				for (int back = Math.min(ahI, oLen); back >= 0; back--) {
					for (int ahPart = 0; ahPart < 2; ahPart++) {
						int bhI = ahI - back;
						int bhPart = ahPart ^ 1;

						int cCnt = oDP[ahPart][ahI][back];

						// push ahead to new ahead
						int nAhI = Math.min(ahI + oLen, numE);
						int nCnt = preSum[ahPart][nAhI] - preSum[ahPart][ahI];
						if (nDP[ahPart][nAhI][Math.min(nAhI - bhI, oLen)] < cCnt + nCnt) {
							nDP[ahPart][nAhI][Math.min(nAhI - bhI, oLen)] = cCnt + nCnt;
						}

						// push behind to new ahead
						nAhI = Math.min(bhI + oLen, numE);
						nCnt = preSum[bhPart][nAhI] - preSum[bhPart][bhI] - (preISum[ahI] - preISum[bhI]);
						if (nDP[bhPart][nAhI][Math.min(nAhI - ahI, oLen)] < cCnt + nCnt) {
							nDP[bhPart][nAhI][Math.min(nAhI - ahI, oLen)] = cCnt + nCnt;
						}

						if (ahI < numE) {
							if (back != oLen) {
								if (oDP[ahPart][ahI + 1][back + 1] < cCnt) {
									oDP[ahPart][ahI + 1][back + 1] = cCnt;
								}
							} else {
								if (oDP[ahPart][ahI + 1][back] < cCnt) {
									oDP[ahPart][ahI + 1][back] = cCnt;
								}
							}

							if (back != 0) {
								if (oDP[ahPart][ahI][back - 1] < cCnt) {
									oDP[ahPart][ahI][back - 1] = cCnt;
								}
							} else {
								if (oDP[bhPart][ahI + 1][1] < cCnt) {
									oDP[bhPart][ahI + 1][1] = cCnt;
								}
							}
						}
					}
				}
			}
			int[][][] temp = oDP;
			oDP = nDP;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j <= numE; j++) {
					for (int k = 0; k <= oLen; k++) {
						temp[i][j][k] = 0;
					}
				}
			}
			nDP = temp;
		}

		int ans = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j <= numE; j++) {
				for (int k = 0; k <= oLen; k++) {
					if (oDP[i][j][k] > ans) {
						ans = oDP[i][j][k];
					}
				}
			}
		}
		System.out.println(ans);
	}
}