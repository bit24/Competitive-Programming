import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class CowDecathalon {

	public static void main(String[] args) throws IOException {
		new CowDecathalon().execute();
	}

	void execute() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("dec.in"));
		PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter("dec.out")));
		StringTokenizer inputData = new StringTokenizer(reader.readLine());
		int nC = Integer.parseInt(inputData.nextToken());
		int nB = Integer.parseInt(inputData.nextToken());
		@SuppressWarnings("unchecked")
		ArrayList<Bonus>[] bonuses = new ArrayList[nC];
		for (int i = 0; i < nC; i++) {
			bonuses[i] = new ArrayList<Bonus>();
		}

		for (int i = 0; i < nB; i++) {
			inputData = new StringTokenizer(reader.readLine());
			bonuses[Integer.parseInt(inputData.nextToken()) - 1]
					.add(new Bonus(Integer.parseInt(inputData.nextToken()), Integer.parseInt(inputData.nextToken())));
		}
		for (int i = 0; i < nC; i++) {
			Collections.sort(bonuses[i]);
		}
		int[][] perf = new int[nC][nC];
		for (int i = 0; i < nC; i++) {
			inputData = new StringTokenizer(reader.readLine());
			for (int j = 0; j < nC; j++) {
				perf[i][j] = Integer.parseInt(inputData.nextToken());
			}
		}
		reader.close();

		int[] mVal = new int[1 << nC];

		for (int cE = 0; cE < nC; cE++) {
			for (int pSet = 0; pSet < (1 << nC); pSet++) {
				if (Integer.bitCount(pSet) != cE) {
					continue;
				}
				for (int sC = 0; sC < nC; sC++) {
					if ((pSet & (1 << sC)) == 0) {
						int nSet = pSet | (1 << sC);
						int nVal = mVal[pSet] + perf[sC][cE];
						for (Bonus cB : bonuses[cE]) {
							if (nVal >= cB.req) {
								nVal += cB.rew;
							} else {
								break;
							}
						}
						mVal[nSet] = Math.max(mVal[nSet], nVal);
					}
				}
			}
		}
		printer.println(mVal[(1 << nC) - 1]);
		printer.close();
	}

	class Bonus implements Comparable<Bonus> {
		int req;
		int rew;

		Bonus(int req, int rew) {
			this.req = req;
			this.rew = rew;
		}

		public int compareTo(Bonus o) {
			return Integer.compare(req, o.req);
		}
	}

}
