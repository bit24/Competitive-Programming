import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class PonyExpress {

	static DecimalFormat formatter = new DecimalFormat("0.000000");

	public static void main(String[] args) throws IOException {
		formatter.setRoundingMode(RoundingMode.HALF_UP);
		new PonyExpress().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int nT = Integer.parseInt(reader.readLine());
		for (int cT = 1; cT <= nT; cT++) {
			StringTokenizer inputData = new StringTokenizer(reader.readLine());
			int nV = Integer.parseInt(inputData.nextToken());
			int nQ = Integer.parseInt(inputData.nextToken());
			int[] cTravel = new int[nV];
			int[] speed = new int[nV];

			for (int i = 0; i < nV; i++) {
				inputData = new StringTokenizer(reader.readLine());
				cTravel[i] = Integer.parseInt(inputData.nextToken());
				speed[i] = Integer.parseInt(inputData.nextToken());
			}

			long[][] cost = new long[nV][nV];
			for (int i = 0; i < nV; i++) {
				inputData = new StringTokenizer(reader.readLine());
				for (int j = 0; j < nV; j++) {
					cost[i][j] = Long.parseLong(inputData.nextToken());
				}
			}

			double[][] mAMat = new double[nV][nV];

			for (int i = 0; i < nV; i++) {
				Arrays.fill(mAMat[i], -1);
			}

			for (int sV = 0; sV < nV; sV++) {
				double[] dFS = new double[nV];
				Arrays.fill(dFS, -1);
				dFS[sV] = 0;
				PriorityQueue<State> pQueue = new PriorityQueue<State>();
				pQueue.add(new State(sV, 0));

				while (!pQueue.isEmpty()) {
					State cState = pQueue.remove();
					if (cState.c > cTravel[sV]) {
						break;
					}
					if (cState.c > dFS[cState.v]) {
						continue;
					}

					mAMat[sV][cState.v] = (double) dFS[cState.v] / speed[sV];
					for (int adj = 0; adj < nV; adj++) {
						if (cost[cState.v][adj] == -1) {
							continue;
						}

						if (dFS[adj] == -1 || cState.c + cost[cState.v][adj] < dFS[adj]) {
							dFS[adj] = cState.c + cost[cState.v][adj];
							pQueue.add(new State(adj, dFS[adj]));
						}
					}
				}
			}

			double[][] fCost = new double[nV][nV];
			for (int sV = 0; sV < nV; sV++) {
				double[] cFS = new double[nV];
				Arrays.fill(cFS, -1);
				cFS[sV] = 0;
				PriorityQueue<State> pQueue = new PriorityQueue<State>();
				pQueue.add(new State(sV, 0));

				while (!pQueue.isEmpty()) {
					State cState = pQueue.remove();
					if (cState.c > cFS[cState.v]) {
						continue;
					}
					fCost[sV][cState.v] = cState.c;
					for (int adj = 0; adj < nV; adj++) {
						if (mAMat[cState.v][adj] == -1) {
							continue;
						}

						if (cFS[adj] == -1 || cState.c + mAMat[cState.v][adj] < cFS[adj]) {
							cFS[adj] = cState.c + mAMat[cState.v][adj];
							pQueue.add(new State(adj, cFS[adj]));
						}
					}
				}
			}

			printer.print("Case #" + cT + ": ");
			while (nQ-- > 1) {
				inputData = new StringTokenizer(reader.readLine());
				int a = Integer.parseInt(inputData.nextToken()) - 1;
				int b = Integer.parseInt(inputData.nextToken()) - 1;
				printer.print(formatter.format(fCost[a][b]));
				printer.print(" ");
			}

			inputData = new StringTokenizer(reader.readLine());
			int a = Integer.parseInt(inputData.nextToken()) - 1;
			int b = Integer.parseInt(inputData.nextToken()) - 1;
			printer.print(formatter.format(fCost[a][b]));
			printer.println();
		}
		printer.close();
	}

	class State implements Comparable<State> {
		int v;
		double c;

		State(int v, double c) {
			this.v = v;
			this.c = c;
		}

		public int compareTo(State o) {
			return c < o.c ? -1 : (c == o.c ? 0 : 1);
		}
	}

}
