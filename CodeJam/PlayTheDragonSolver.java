import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class PlayTheDragonSolver implements Runnable {

	public static void main(String[] args) throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		execute();
		System.out.println(System.currentTimeMillis() - startTime);
	}

	static int numT;
	static int[][] tInput;
	static long[] tOutput;

	static void execute() throws IOException, InterruptedException {
		BufferedReader reader = new BufferedReader(new FileReader("dragon.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("dragon.out")));

		numT = Integer.parseInt(reader.readLine());

		tInput = new int[numT + 1][6];

		for (int cT = 1; cT <= numT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			for (int i = 0; i < 6; i++) {
				tInput[cT][i] = Integer.parseInt(inputData.nextToken());
			}
		}

		tOutput = new long[numT + 1];

		Thread oddThread = new Thread(new PlayTheDragonSolver("ODDS"));
		oddThread.start();

		Thread evenThread = new Thread(new PlayTheDragonSolver("EVENS"));
		evenThread.start();

		oddThread.join();
		evenThread.join();

		for (int cT = 1; cT <= numT; cT++) {
			long ans = tOutput[cT];
			if (ans == Integer.MAX_VALUE) {
				printer.println("Case #" + cT + ": IMPOSSIBLE");
			} else {
				printer.println("Case #" + cT + ": " + ans);
			}
		}
		reader.close();
		printer.close();
	}

	int sPos;

	PlayTheDragonSolver(String config) {
		if (config.equals("ODDS")) {
			sPos = 1;
		} else {
			sPos = 2;
		}
	}

	public void run() {
		for (int cT = sPos; cT <= numT; cT += 2) {
			b_dH = tInput[cT][0];
			b_dA = tInput[cT][1];
			b_kH = tInput[cT][2];
			b_kA = tInput[cT][3];
			bV = tInput[cT][4];
			dV = tInput[cT][5];
			tOutput[cT] = findMinScheme();
		}
	}

	int b_dH;
	int b_dA;
	int b_kH;
	int b_kA;
	int bV;
	int dV;

	// to be inlined
	int divUp(int a, int b) {
		return (a - 1) / b + 1;
	}

	long findMinScheme() {
		int bufAtkC = findBufAtkScheme();
		// runs a simulation

		if (bufAtkC == 1) {
			return 1;
		}

		long minA = Integer.MAX_VALUE;

		{
			int bufAtkL = bufAtkC - 1;
			int cycleL = (b_dH - 1) / b_kA + 1 - 1;
			int bufAtk_perCycle = cycleL - 1;

			if (bufAtk_perCycle > 0) {
				int numCycles = bufAtkL / bufAtk_perCycle; // rounds down
				bufAtkL %= bufAtk_perCycle;

				if (numCycles > 0 && bufAtkL == 0) {
					// In the case where bufAtkL == 0, we know that an unneeded healing action is used in the
					// last cycle. As such, we can remove the complete cycle for an incomplete one.
					numCycles--;
					bufAtkL += bufAtk_perCycle;
				}

				int nCount = 1 + numCycles * cycleL;
				if (bufAtkL > 1 || numCycles == 0) {
					nCount += bufAtkL;
				}

				if (nCount < minA) {
					minA = nCount;
				}
			} else if (bufAtk_perCycle == 0) {
				if (bufAtkC <= 2) {
					return bufAtkC;
				}
			}
		}

		int c_dH = b_dH;
		int c_kA = b_kA;

		if (dV != 0) {
			long mCount = 0;
			while (true) {
				mCount++;
				int n_kA = c_kA - dV;
				if (n_kA <= 0) {
					long nCount = mCount + bufAtkC;
					if (nCount < minA) {
						minA = nCount;
					}
					break;
				}
				if (c_dH - n_kA <= 0) {
					int restored = b_dH - c_kA;
					if (restored <= 0 || c_dH == restored) {
						break;
					}
					c_dH = restored;
				} else {
					c_kA = n_kA;
					c_dH -= c_kA;
					int fHeal = (c_dH - 1) / c_kA + 1;

					long nCount;
					if (fHeal >= bufAtkC) {
						// no healing necessary
						nCount = mCount + bufAtkC;
					} else {
						int bufAtkL = bufAtkC - (fHeal - 1);
						int cycleL = (b_dH - 1) / c_kA + 1 - 1;
						int bufAtk_perCycle = cycleL - 1;

						if (bufAtk_perCycle <= 0) {
							continue;
						}

						int numCycles = bufAtkL / bufAtk_perCycle; // rounds down

						bufAtkL = bufAtkL % bufAtk_perCycle;

						if (/* numCycles > 0 && */bufAtkL == 0) {
							assert (numCycles > 0);
							// In the case where bufAtkL == 0, we know that an unneeded healing action is used in the
							// last cycle. As such, we can remove the complete cycle for an incomplete one.
							numCycles--;
							bufAtkL = bufAtk_perCycle;
						}

						nCount = mCount + fHeal +  ((long)numCycles * cycleL);
						if (bufAtkL > 1 /* || numCycles == 0 */) {
							nCount += bufAtkL;
						}
					}
					if (nCount < minA) {
						minA = nCount;
					}
				}
			}
		}
		return minA;
	}

	int findBufAtkScheme() {
		long lCost = (b_kH - 1) / b_dA + 1;

		long bC = 0;
		while (true) {
			bC++;
			long nCost = bC + (b_kH - 1) / (b_dA + bC * bV) + 1;
			if (nCost > lCost) {
				break;
			}
			lCost = nCost;
		}
		assert(lCost <= Integer.MAX_VALUE);
		return (int) lCost;
	}
}
