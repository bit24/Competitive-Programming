import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LightsOut {

	static int nP;
	static int[] pSum;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		long sTime = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new FileReader("lightsout.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("lightsout.out")));
		nP = Integer.parseInt(reader.readLine());
		int[] x = new int[nP];
		int[] y = new int[nP];
		for (int i = 0; i < nP; i++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			x[i] = Integer.parseInt(inputData.nextToken());
			y[i] = Integer.parseInt(inputData.nextToken());
		}
		reader.close();

		int[] pt = new int[nP];
		int[] sd = new int[nP];
		pt[0] = -3;
		sd[0] = Math.abs(x[1] - x[0] + y[1] - y[0]);
		for (int i = 1; i + 1 < nP; i++) {
			pt[i] = cross(x[i] - x[i - 1], y[i] - y[i - 1], x[i + 1] - x[i], y[i + 1] - y[i]) > 0 ? -1 : -2;
			sd[i] = Math.abs(x[i + 1] - x[i] + y[i + 1] - y[i]);
		}
		pt[nP - 1] = cross(x[nP - 1] - x[nP - 2], y[nP - 1] - y[nP - 2], x[0] - x[nP - 1], y[0] - y[nP - 1]) > 0 ? -1
				: -2;
		sd[nP - 1] = Math.abs(x[0] - x[nP - 1] + y[0] - y[nP - 1]);

		pSum = new int[nP];
		pSum[0] = sd[0];
		for (int i = 1; i < nP; i++) {
			pSum[i] = pSum[i - 1] + sd[i];
		}

		int[][] orig = new int[nP][nP];
		for (int lft = 0; lft < nP; lft++) {
			for (int len = 0; len < nP; len++) {
				orig[lft][len] = -1;
			}
		}

		ArrayList<Integer>[][] member = new ArrayList[nP][nP];

		for (int lft = 0; lft < nP; lft++) {
			for (int len = 0; len < nP; len++) {
				member[lft][len] = new ArrayList<Integer>();
			}
		}

		for (int lft = 0; lft < nP; lft++) {
			for (int len = 0; len < nP; len++) {
				if (orig[lft][len] == -1) {
					orig[lft][len] = lft;
				}
				member[orig[lft][len]][len].add(lft);
			}

			for (int rgt = lft + 1; rgt < nP; rgt++) {
				if (pt[lft] == pt[rgt]) {
					orig[rgt][0] = orig[lft][0];
					for (int len = 1; len < nP && pt[(lft + len) % nP] == pt[(rgt + len) % nP]
							&& sd[(lft + len - 1 + nP) % nP] == sd[(rgt + len - 1 + nP) % nP]; len++) {
						orig[rgt][len] = orig[lft][len];
					}
				}
			}
		}

		int[][][][] cost = new int[nP][nP][][];

		for (int lft = 0; lft < nP; lft++) {
			for (int len = 0; len < nP; len++) {
				cost[lft][len] = new int[len + 1][2];
			}
		}

		// for each not-necessarily unique "string of data," determine whether to extend left or right by seeing which
		// one minimizes the maximum increase
		for (int len = nP - 1; len >= 0; len--) {
			for (int lft = 0; lft < nP; lft++) {
				if (lft + len > nP) {
					// impossible to have 0 already encountered and not left
					continue;
				}
				if (orig[lft][len] == lft) {
					if (lft == 0 || lft + len == nP) {
						for (int sR = 0; sR <= len; sR++) {
							int sP = (lft + sR) % nP;
							int dCost = Math.min(dist(0, sP), dist(sP, 0));
							cost[lft][len][sR][0] = -dCost;
							cost[lft][len][sR][1] = -dCost;
						}
					} else {

						for (int sR = 0; sR <= len; sR++) {
							// left end case
							int lCost = Integer.MIN_VALUE;
							int rCost = Integer.MIN_VALUE;

							for (int inst : member[lft][len]) {
								lCost = max(lCost, cost[m1(inst)][len + 1][sR + 1][0] + sd[m1(inst)]);
								rCost = max(rCost, cost[inst][len + 1][sR][1] + dist(inst, (inst + len + 1) % nP));
							}

							if (lCost < rCost) {
								for (int inst : member[lft][len]) {
									cost[inst][len][sR][0] = cost[m1(inst)][len + 1][sR + 1][0] + sd[m1(inst)];
								}
							} else {
								for (int inst : member[lft][len]) {
									cost[inst][len][sR][0] = cost[inst][len + 1][sR][1]
											+ dist(inst, (inst + len + 1) % nP);
								}
							}

							// right end case
							lCost = Integer.MIN_VALUE;
							rCost = Integer.MIN_VALUE;

							for (int inst : member[lft][len]) {
								lCost = max(lCost,
										cost[m1(inst)][len + 1][sR + 1][0] + dist(m1(inst), (inst + len) % nP));
								rCost = max(rCost, cost[inst][len + 1][sR][1] + sd[(inst + len) % nP]);
							}
							if (lCost < rCost) {
								for (int inst : member[lft][len]) {
									cost[inst][len][sR][1] = cost[m1(inst)][len + 1][sR + 1][0]
											+ dist(m1(inst), (inst + len) % nP);

								}
							} else {
								for (int inst : member[lft][len]) {
									cost[inst][len][sR][1] = cost[inst][len + 1][sR][1] + sd[(inst + len) % nP];
								}
							}
						}
					}
				}
			}
		}

		int ans = 0;
		for (int cP = 1; cP < nP; cP++) {
			ans = max(ans, cost[cP][0][0][0]);
		}

		printer.println(ans);
		printer.close();

		System.out.println(System.currentTimeMillis() - sTime);
	}

	static int max(int a, int b) {
		return a > b ? a : b;
	}

	static int dist(int lft, int rgt) {
		if (rgt < lft) {
			return pSum[nP - 1] - (lft > 0 ? pSum[lft - 1] : 0) + (rgt > 0 ? pSum[rgt - 1] : 0);
		} else {
			return (rgt > 0 ? pSum[rgt - 1] : 0) - (lft > 0 ? pSum[lft - 1] : 0);
		}
	}

	static int m1(int i) {
		return i >= 1 ? i - 1 : i - 1 + nP;
	}

	static long cross(int a, int b, int c, int d) {
		return (long) a * d - (long) b * c;
	}

}
