import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ReplayValue {

	static final long MOD = 1_000_000_007;

	public static void main(String[] args) throws IOException {
		new ReplayValue().execute();
	}

	static long[][][][] cDP = new long[52][52][52][52];
	static long[][][][] nDP = new long[52][52][52][52];

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("replay.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("replay.out")));
		int nT = Integer.parseInt(reader.readLine());

		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int N = Integer.parseInt(inputData.nextToken());
			int sY = Integer.parseInt(inputData.nextToken());
			int eY = Integer.parseInt(inputData.nextToken());

			Pt[] pts = new Pt[N];
			for (int i = 0; i < N; i++) {
				inputData = new StringTokenizer(reader.readLine());
				pts[i] = new Pt(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken()));
			}
			Arrays.sort(pts);

			int[] yCoor = new int[N + 2];
			yCoor[0] = sY;
			for (int i = 1; i <= N; i++) {
				yCoor[i] = pts[i - 1].y;
			}
			yCoor[N + 1] = eY;

			if (sY > eY) {
				int maxY = 0;
				for (int i : yCoor) {
					maxY = Math.max(maxY, i);
				}
				for (int i = 0; i <= N + 1; i++) {
					yCoor[i] = maxY - yCoor[i];
				}
				sY = yCoor[0];
				eY = yCoor[N + 1];
			}

			for (long[][][] a : cDP) {
				for (long[][] b : a) {
					for (long[] c : b) {
						Arrays.fill(c, 0);
					}
				}
			}
			for (long[][][] a : nDP) {
				for (long[][] b : a) {
					for (long[] c : b) {
						Arrays.fill(c, 0);
					}
				}
			}

			cDP[N + 1][N + 1][N + 1][N + 1] = 1;

			for (int i = 0; i <= N - 1; i++) {
				for (int hD = 1; hD <= N + 1; hD++) {
					for (int lU = 1; lU <= N + 1; lU++) {
						for (int hR = 1; hR <= N + 1; hR++) {
							for (int lR = 1; lR <= N + 1; lR++) {
								long cCnt = cDP[hD][lU][hR][lR];
								if (cCnt == 0) {
									continue;
								}
								int cY = yCoor[i + 1];

								// case 1: left
								if (cY < sY) {
									if (lU == N + 1 || yCoor[lU] > cY) {
										nDP[hD][lU][hR][lR] = (nDP[hD][lU][hR][lR] + cCnt) % MOD;
									}
								} else {
									if (hD == N + 1 || yCoor[hD] < cY) {
										nDP[hD][lU][hR][lR] = (nDP[hD][lU][hR][lR] + cCnt) % MOD;
									}
								}

								// case 2: up
								int nLU = lU;
								if (nLU == N + 1 || yCoor[nLU] > cY) {
									nLU = i + 1;
								}
								if (hR == N + 1 || yCoor[hR] < cY) {
									nDP[hD][nLU][hR][lR] = (nDP[hD][nLU][hR][lR] + cCnt) % MOD;
								}

								// case 3: down
								int nHD = hD;
								if (nHD == N + 1 || yCoor[nHD] < cY) {
									nHD = i + 1;
								}
								if (lR == N + 1 || yCoor[lR] > cY) {
									nDP[nHD][lU][hR][lR] = (nDP[nHD][lU][hR][lR] + cCnt) % MOD;
								}

								// case 4: right
								int nHR = hR;
								int nLR = lR;
								if (cY < eY) {
									if (nHR == N + 1 || cY > yCoor[nHR]) {
										nHR = i + 1;
									}
								} else {
									if (nLR == N + 1 || cY < yCoor[nLR]) {
										nLR = i + 1;
									}
								}
								nDP[hD][lU][nHR][nLR] = (nDP[hD][lU][nHR][nLR] + cCnt) % MOD;
							}
						}
					}
				}
				long[][][][] temp = cDP;
				cDP = nDP;
				nDP = temp;
				for (long[][][] a : nDP) {
					for (long[][] b : a) {
						for (long[] c : b) {
							Arrays.fill(c, 0);
						}
					}
				}
			}

			long sum = 0;
			for (int i = 0; i <= N + 1; i++) {
				for (int j = 0; j <= N + 1; j++) {
					for (int k = 0; k <= N + 1; k++) {
						for (int l = 0; l <= N + 1; l++) {
							sum = (sum + cDP[i][j][k][l]) % MOD;
						}
					}
				}
			}

			long full = 1;
			for (int i = 1; i <= N; i++) {
				full = full * 4 % MOD;
			}
			long res = (full - sum + MOD) % MOD;
			printer.println("Case #" + cT + ": " + res);
		}
		reader.close();
		printer.close();
	}

	class Pt implements Comparable<Pt> {
		int x;
		int y;

		Pt(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Pt o) {
			return Integer.compare(x, o.x);
		}
	}
}